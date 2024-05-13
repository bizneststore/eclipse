/*
 * Copyright (C) 2004-2015 L2J Server
 * 
 * This file is part of L2J Server.
 * 
 * L2J Server is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package l2r.gameserver.model.skills.l2skills;

import l2r.Config;
import l2r.gameserver.enums.ShotType;
import l2r.gameserver.features.balanceEngine.BalancerConfigs;
import l2r.gameserver.features.balanceEngine.classBalancer.ClassBalanceManager;
import l2r.gameserver.features.balanceEngine.skillBalancer.SkillsBalanceManager;
import l2r.gameserver.model.L2Object;
import l2r.gameserver.model.StatsSet;
import l2r.gameserver.model.actor.L2Character;
import l2r.gameserver.model.actor.L2Playable;
import l2r.gameserver.model.actor.L2Summon;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.model.effects.L2Effect;
import l2r.gameserver.model.skills.L2Skill;
import l2r.gameserver.model.stats.Formulas;
import l2r.gameserver.model.stats.Stats;
import l2r.gameserver.network.Debug;
import l2r.log.filter.Log;

import gr.sr.balanceEngine.BalanceHandler;
import gr.sr.configsEngine.configs.impl.FormulasConfigs;

public class L2SkillChargeDmg extends L2Skill
{
	public L2SkillChargeDmg(StatsSet set)
	{
		super(set);
	}
	
	@Override
	public void useSkill(L2Character caster, L2Object[] targets)
	{
		final L2PcInstance attacker = caster instanceof L2PcInstance ? (L2PcInstance) caster : null;
		if (attacker == null)
		{
			return;
		}
		
		if (caster.isAlikeDead())
		{
			return;
		}
		
		boolean ss = useSoulShot() && caster.isChargedShot(ShotType.SOULSHOTS);
		double ssBoost = ss ? FormulasConfigs.ALT_CHARGE_SKILL_SS_MULTIPLIER : 1.0;
		final double weaponMod = attacker.getAttackType().isRanged() ? FormulasConfigs.ALT_BOOST_PHYS_SKILL_RANGE_MULTIPLIER : FormulasConfigs.ALT_BOOST_PHYS_SKILL_MELEE_MULTIPLIER;
		final L2Skill skill = this;
		
		for (L2Character target : (L2Character[]) targets)
		{
			if (target.isAlikeDead())
			{
				continue;
			}
			
			// Calculate skill evasion
			final boolean skillIsEvaded = Formulas.calcPhysicalSkillEvasion(caster, target, this);
			if (skillIsEvaded)
			{
				continue;
			}
			
			final boolean isPvP = attacker.isPlayable() && target.isPlayable();
			final boolean isPvE = attacker.isPlayable() && target.isAttackable();
			final double weaponTraitMod = Formulas.calcWeaponTraitBonus(attacker, target);
			final double generalTraitMod = Formulas.calcGeneralTraitBonus(attacker, target, skill, false);
			final double attributeMod = Formulas.calcAttributeBonus(attacker, target, skill);
			double defence = target.getPDef(attacker);
			double damage = 1;
			final boolean critical = Formulas.calcCrit(attacker, target, this);
			double pvpBonus = 1;
			double pveBonus = 1;
			// charge count should be the count before casting the skill but since its reduced before calling effects
			// we add skill consume charges to current charges
			final double energyChargesBoost = (((attacker.getCharges() + skill.getChargeConsume()) - 1) * 0.2) + 1;
			
			if (isPvP)
			{
				// Damage bonuses in PvP fight
				pvpBonus = attacker.getStat().calcStat(Stats.PVP_PHYS_SKILL_DMG, 1.0);
				// Defense bonuses in PvP fight
				defence *= target.getStat().calcStat(Stats.PVP_PHYS_SKILL_DEF, 1.0);
			}
			else
			{
				// Damage bonuses in PvE fight
				pveBonus = attacker.getStat().calcStat(Stats.PVE_PHYS_SKILL_DMG, 1.0);
				// Defense bonuses in PvE fight
				defence *= target.getStat().calcStat(Stats.PVE_PHYS_SKILL_DEF, 1.0);
			}
			
			final double baseMod = ((weaponMod * (skill.getPower(isPvP, isPvE) + (attacker.getPAtk(target) * ssBoost))) / defence);
			
			if (!ignoreShield())
			{
				byte shield = Formulas.calcShldUse(attacker, target, skill, true);
				switch (shield)
				{
					case Formulas.SHIELD_DEFENSE_FAILED:
					{
						break;
					}
					case Formulas.SHIELD_DEFENSE_SUCCEED:
					{
						defence += target.getShldDef();
						break;
					}
					case Formulas.SHIELD_DEFENSE_PERFECT_BLOCK:
					{
						defence = -1;
						break;
					}
				}
			}
			
			if (defence != -1)
			{
				damage = baseMod * pvpBonus * pveBonus;
				damage *= weaponTraitMod;
				damage *= generalTraitMod;
				damage *= attributeMod;
				damage *= energyChargesBoost;
				damage = attacker.getStat().calcStat(Stats.PHYSICAL_SKILL_POWER, damage);
				
				if (critical)
				{
					damage *= 2;
				}
			}
			
			double balancedDamage = 1.00d;
			
			int skillId = skill.getId();
			double svsAll[] = SkillsBalanceManager.getInstance().getBalance((skillId * -1) - 65536, false);
			if ((svsAll != null) && (BalancerConfigs.SKILLS_BALANCER_AFFECTS_MONSTERS || (target instanceof L2Playable)))
			{
				damage *= svsAll[1];
			}
			if ((target instanceof L2PcInstance) || (target instanceof L2Summon))
			{
				L2PcInstance t = target instanceof L2PcInstance ? target.getActingPlayer() : ((L2Summon) target).getOwner();
				int targetClassId = SkillsBalanceManager.getInstance().getClassId(t.getClassId().getId());
				double vsTarget[] = SkillsBalanceManager.getInstance().getBalance(skillId + (targetClassId * 65536), t.isInOlympiadMode());
				if (vsTarget != null)
				{
					balancedDamage *= vsTarget[1];
				}
			}
			
			int playerClassId = ClassBalanceManager.getInstance().getClassId(attacker.getClassId().getId());
			double vsAll[] = ClassBalanceManager.getInstance().getBalance(playerClassId * -256, attacker.isInOlympiadMode());
			if ((vsAll != null) && (BalancerConfigs.CLASS_BALANCER_AFFECTS_MONSTERS || (target instanceof L2Playable)))
			{
				if (critical)
				{
					balancedDamage *= vsAll[6];
				}
				else if (!critical)
				{
					balancedDamage *= vsAll[5];
				}
				else if (critical)
				{
					balancedDamage *= vsAll[1];
				}
				else if (!critical)
				{
					balancedDamage *= vsAll[0];
				}
			}
			
			if ((target instanceof L2PcInstance) || (target instanceof L2Summon))
			{
				L2PcInstance t = target instanceof L2PcInstance ? target.getActingPlayer() : ((L2Summon) target).getOwner();
				int targetClassId = ClassBalanceManager.getInstance().getClassId(t.getClassId().getId());
				double vsTarget[] = ClassBalanceManager.getInstance().getBalance((playerClassId * 256) + targetClassId, attacker.isInOlympiadMode());
				if (vsTarget != null)
				{
					if (critical)
					{
						balancedDamage *= vsTarget[6];
					}
					else if (!critical)
					{
						balancedDamage *= vsTarget[5];
					}
					else if (critical)
					{
						balancedDamage *= vsTarget[1];
					}
					else if (!critical)
					{
						balancedDamage *= vsTarget[0];
					}
				}
			}
			
			damage *= 1 + (balancedDamage / 100);
			
			if (attacker.isDebug())
			{
				final StatsSet set = new StatsSet();
				set.set("Formula", "Charge skill damage");
				set.set("skillPower", skill.getPower(isPvP, isPvE));
				set.set("ssboost", ssBoost);
				set.set("pvpBonus", pvpBonus);
				set.set("pveBonus", pveBonus);
				set.set("baseMod", baseMod);
				set.set("weaponTraitMod", weaponTraitMod);
				set.set("generalTraitMod", generalTraitMod);
				set.set("attributeMod", attributeMod);
				// set.set("randomMod", randomMod);
				set.set("balanceMod", balancedDamage);
				set.set("damage", (int) damage);
				set.set("is critical", critical);
				Debug.sendSkillDebug(attacker, target, skill, set);
			}
			
			// Reunion balancer
			damage = BalanceHandler.getInstance().calc(attacker, target, skill, damage, false);
			// Reunion balancer - End
			
			if (damage > 0)
			{
				attacker.sendDamageMessage(target, (int) damage, false, critical, false);
				target.reduceCurrentHp(damage, attacker, skill);
				target.notifyDamageReceived(damage, attacker, skill, critical, false, false);
				
				// vGodFather: Do we need to trigger this?
				// Maybe launch chance skills on us
				if (caster.getChanceSkills() != null)
				{
					caster.getChanceSkills().onSkillHit(target, this, false, damage);
				}
				// Maybe launch chance skills on target
				if (target.getChanceSkills() != null)
				{
					target.getChanceSkills().onSkillHit(caster, this, true, damage);
				}
				
				// Check if damage should be reflected
				Formulas.calcDamageReflected(attacker, target, skill, damage, critical);
				
				if (Config.LOG_GAME_DAMAGE && attacker.isPlayable() && target.isPlayer() && (damage > Config.LOG_GAME_DAMAGE_THRESHOLD))
				{
					Log.LogPlayerPDamages("", new Object[]
					{
						attacker,
						" did damage ",
						damage,
						skill,
						" crit(" + critical + ") ",
						" to ",
						target
					});
				}
			}
		}
		
		// effect self :]
		if (hasSelfEffects())
		{
			L2Effect effect = caster.getFirstEffect(getId());
			if ((effect != null) && effect.isSelfEffect())
			{
				// Replace old effect with new one.
				effect.exit();
			}
			// cast self effect if any
			getEffectsSelf(caster);
		}
		
		// Consume shot
		caster.setChargedShot(ShotType.SOULSHOTS, false);
	}
}
