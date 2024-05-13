package gr.sr.balanceEngine;

import l2r.gameserver.model.actor.L2Character;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.model.base.ClassId;
import l2r.gameserver.model.items.L2Armor;
import l2r.gameserver.model.items.L2Weapon;
import l2r.gameserver.model.items.type.WeaponType;
import l2r.gameserver.model.skills.L2Skill;

import gr.sr.configsEngine.configs.impl.FormulasConfigs;

/**
 * @author vGodFather
 */
public class BalanceHandler
{
	public BalanceHandler()
	{
		// Dummy default
	}
	
	public double calc(L2Character attacker, L2Character target, L2Skill skill, double damage, boolean isMagic)
	{
		if (isMagic)
		{
			// We dont care about mage normal hits all are skills so we modify only them
			if (attacker.isPlayer())
			{
				if (target.isPlayer())
				{
					damage = damage * FormulasConfigs.ALT_MAGIC_DAMAGE_PLAYER_VS_PLAYER;
				}
				else if (target.isMonster())
				{
					damage = damage * FormulasConfigs.ALT_MAGIC_DAMAGE_PLAYER_VS_MOB;
				}
			}
			else if (attacker.isSummon())
			{
				if (target.isPlayer())
				{
					damage = damage * FormulasConfigs.ALT_MAGIC_DAMAGE_SUMMON_VS_PLAYER;
				}
				else if (target.isMonster())
				{
					damage = damage * FormulasConfigs.ALT_MAGIC_DAMAGE_SUMMON_VS_MOB;
				}
			}
			else if (attacker.isMonster())
			{
				if (target.isPlayer())
				{
					damage = damage * FormulasConfigs.ALT_MAGIC_DAMAGE_MOB_VS_PLAYER;
				}
				else if (target.isMonster())
				{
					damage = damage * FormulasConfigs.ALT_MAGIC_DAMAGE_MOB_VS_MOB;
				}
			}
		}
		else
		{
			if (attacker.isPlayer())
			{
				L2Weapon weapon = attacker.getActiveWeaponItem();
				if (weapon != null)
				{
					if (target.isPlayer())
					{
						// Balance skill if not null
						if (skill != null)
						{
							// Calculates damage on target if is player
							damage = BalanceHandler.getInstance().calcSkillDamage(target.getActingPlayer(), weapon.getItemType(), damage);
						}
						else
						{
							damage = BalanceHandler.getInstance().calcNormalHitDamage(target.getActingPlayer(), weapon.getItemType(), damage);
						}
					}
					else if (target.isSummon())
					{
						// Balance skill if not null
						if (skill != null)
						{
							// Calculates damage on target if is player
							damage = BalanceHandler.getInstance().calcSkillDamage(target.getActingPlayer(), weapon.getItemType(), damage);
						}
						else
						{
							damage = BalanceHandler.getInstance().calcNormalHitDamage(target.getActingPlayer(), weapon.getItemType(), damage);
						}
					}
					else if (target.isMonster())
					{
						damage = damage * FormulasConfigs.ALT_PHYSICAL_DAMAGE_PLAYER_VS_MOB;
					}
				}
			}
			else if (attacker.isSummon())
			{
				if (target.isPlayer())
				{
					damage = damage * FormulasConfigs.ALT_PHYSICAL_DAMAGE_SUMMON_VS_PLAYER;
				}
				else if (target.isMonster())
				{
					damage = damage * FormulasConfigs.ALT_PHYSICAL_DAMAGE_SUMMON_VS_MOB;
				}
			}
			else if (attacker.isMonster())
			{
				if (target.isPlayer())
				{
					damage = damage * FormulasConfigs.ALT_PHYSICAL_DAMAGE_MOB_VS_PLAYER;
				}
				else if (target.isMonster())
				{
					damage = damage * FormulasConfigs.ALT_PHYSICAL_DAMAGE_MOB_VS_MOB;
				}
			}
		}
		
		return damage;
	}
	
	private double calcSkillDamage(L2PcInstance target, WeaponType type, double damage)
	{
		L2Armor armor = target.getActiveChestArmorItem();
		if (armor != null)
		{
			switch (type)
			{
				case DAGGER:
				case DUALDAGGER:
					if (target.isWearingHeavyArmor())
					{
						damage /= FormulasConfigs.ALT_DAGGER_DMG_VS_HEAVY;
					}
					
					if (target.isWearingLightArmor())
					{
						damage /= FormulasConfigs.ALT_DAGGER_DMG_VS_LIGHT;
					}
					
					if (target.isWearingMagicArmor())
					{
						damage /= FormulasConfigs.ALT_DAGGER_DMG_VS_ROBE;
					}
					break;
				case BOW:
					if (target.isWearingHeavyArmor())
					{
						damage /= FormulasConfigs.ALT_BOW_DMG_VS_HEAVY;
					}
					
					if (target.isWearingLightArmor())
					{
						damage /= FormulasConfigs.ALT_BOW_DMG_VS_LIGHT;
					}
					
					if (target.isWearingMagicArmor())
					{
						damage /= FormulasConfigs.ALT_BOW_DMG_VS_ROBE;
					}
					break;
				case CROSSBOW:
					if (target.isWearingHeavyArmor())
					{
						damage /= FormulasConfigs.ALT_CROSSBOW_DMG_VS_HEAVY;
					}
					
					if (target.isWearingLightArmor())
					{
						damage /= FormulasConfigs.ALT_CROSSBOW_DMG_VS_LIGHT;
					}
					
					if (target.isWearingMagicArmor())
					{
						damage /= FormulasConfigs.ALT_CROSSBOW_DMG_VS_ROBE;
					}
					break;
				case POLE:
					if (target.isWearingHeavyArmor())
					{
						damage /= FormulasConfigs.ALT_POLE_DMG_VS_HEAVY;
					}
					
					if (target.isWearingLightArmor())
					{
						damage /= FormulasConfigs.ALT_POLE_DMG_VS_LIGHT;
					}
					
					if (target.isWearingMagicArmor())
					{
						damage /= FormulasConfigs.ALT_POLE_DMG_VS_ROBE;
					}
					break;
				case BLUNT:
					if (target.isWearingHeavyArmor())
					{
						damage /= FormulasConfigs.ALT_BLUNT_DMG_VS_HEAVY;
					}
					
					if (target.isWearingLightArmor())
					{
						damage /= FormulasConfigs.ALT_BLUNT_DMG_VS_LIGHT;
					}
					
					if (target.isWearingMagicArmor())
					{
						damage /= FormulasConfigs.ALT_BLUNT_DMG_VS_ROBE;
					}
					break;
				case SWORD:
					if (target.isWearingHeavyArmor())
					{
						damage /= FormulasConfigs.ALT_SWORD_DMG_VS_HEAVY;
					}
					
					if (target.isWearingLightArmor())
					{
						damage /= FormulasConfigs.ALT_SWORD_DMG_VS_LIGHT;
					}
					
					if (target.isWearingMagicArmor())
					{
						damage /= FormulasConfigs.ALT_SWORD_DMG_VS_ROBE;
					}
					break;
				case DUAL:
					if (target.isWearingHeavyArmor())
					{
						damage /= FormulasConfigs.ALT_DUAL_DMG_VS_HEAVY;
					}
					
					if (target.isWearingLightArmor())
					{
						damage /= FormulasConfigs.ALT_DUAL_DMG_VS_LIGHT;
					}
					
					if (target.isWearingMagicArmor())
					{
						damage /= FormulasConfigs.ALT_DUAL_DMG_VS_ROBE;
					}
					break;
				case DUALFIST:
					if (target.isWearingHeavyArmor())
					{
						damage /= FormulasConfigs.ALT_DUAL_FIST_DMG_VS_HEAVY;
					}
					
					if (target.isWearingLightArmor())
					{
						damage /= FormulasConfigs.ALT_DUAL_FIST_DMG_VS_LIGHT;
					}
					
					if (target.isWearingMagicArmor())
					{
						damage /= FormulasConfigs.ALT_DUAL_FIST_DMG_VS_ROBE;
					}
					break;
				case ANCIENTSWORD:
					if (target.isWearingHeavyArmor())
					{
						damage /= FormulasConfigs.ALT_ANCIENT_DMG_VS_HEAVY;
					}
					
					if (target.isWearingLightArmor())
					{
						damage /= FormulasConfigs.ALT_ANCIENT_DMG_VS_LIGHT;
					}
					
					if (target.isWearingMagicArmor())
					{
						damage /= FormulasConfigs.ALT_ANCIENT_DMG_VS_ROBE;
					}
					break;
				case RAPIER:
					if (target.isWearingHeavyArmor())
					{
						damage /= FormulasConfigs.ALT_RAPIER_DMG_VS_HEAVY;
					}
					
					if (target.isWearingLightArmor())
					{
						damage /= FormulasConfigs.ALT_RAPIER_DMG_VS_LIGHT;
					}
					
					if (target.isWearingMagicArmor())
					{
						damage /= FormulasConfigs.ALT_RAPIER_DMG_VS_ROBE;
					}
					break;
				default:
					break;
			}
		}
		
		return damage;
	}
	
	private double calcNormalHitDamage(L2PcInstance target, WeaponType type, double damage)
	{
		L2Armor armor = target.getActiveChestArmorItem();
		if (armor != null)
		{
			switch (type)
			{
				case DAGGER:
				case DUALDAGGER:
					if (target.isWearingHeavyArmor())
					{
						damage /= FormulasConfigs.ALT_DAGGER_DMG_VS_HEAVY_HIT;
					}
					
					if (target.isWearingLightArmor())
					{
						damage /= FormulasConfigs.ALT_DAGGER_DMG_VS_LIGHT_HIT;
					}
					
					if (target.isWearingMagicArmor())
					{
						damage /= FormulasConfigs.ALT_DAGGER_DMG_VS_ROBE_HIT;
					}
					break;
				case BOW:
					if (target.isWearingHeavyArmor())
					{
						damage /= FormulasConfigs.ALT_BOW_DMG_VS_HEAVY_HIT;
					}
					
					if (target.isWearingLightArmor())
					{
						damage /= FormulasConfigs.ALT_BOW_DMG_VS_LIGHT_HIT;
					}
					
					if (target.isWearingMagicArmor())
					{
						damage /= FormulasConfigs.ALT_BOW_DMG_VS_ROBE_HIT;
					}
					break;
				case CROSSBOW:
					if (target.isWearingHeavyArmor())
					{
						damage /= FormulasConfigs.ALT_CROSSBOW_DMG_VS_HEAVY_HIT;
					}
					
					if (target.isWearingLightArmor())
					{
						damage /= FormulasConfigs.ALT_CROSSBOW_DMG_VS_LIGHT_HIT;
					}
					
					if (target.isWearingMagicArmor())
					{
						damage /= FormulasConfigs.ALT_CROSSBOW_DMG_VS_ROBE_HIT;
					}
					break;
				case POLE:
					if (target.isWearingHeavyArmor())
					{
						damage /= FormulasConfigs.ALT_POLE_DMG_VS_HEAVY_HIT;
					}
					
					if (target.isWearingLightArmor())
					{
						damage /= FormulasConfigs.ALT_POLE_DMG_VS_LIGHT_HIT;
					}
					
					if (target.isWearingMagicArmor())
					{
						damage /= FormulasConfigs.ALT_POLE_DMG_VS_ROBE_HIT;
					}
					break;
				case BLUNT:
					if (target.isWearingHeavyArmor())
					{
						damage /= FormulasConfigs.ALT_BLUNT_DMG_VS_HEAVY_HIT;
					}
					
					if (target.isWearingLightArmor())
					{
						damage /= FormulasConfigs.ALT_BLUNT_DMG_VS_LIGHT_HIT;
					}
					
					if (target.isWearingMagicArmor())
					{
						damage /= FormulasConfigs.ALT_BLUNT_DMG_VS_ROBE_HIT;
					}
					break;
				case SWORD:
					if (target.isWearingHeavyArmor())
					{
						damage /= FormulasConfigs.ALT_SWORD_DMG_VS_HEAVY_HIT;
					}
					
					if (target.isWearingLightArmor())
					{
						damage /= FormulasConfigs.ALT_SWORD_DMG_VS_LIGHT_HIT;
					}
					
					if (target.isWearingMagicArmor())
					{
						damage /= FormulasConfigs.ALT_SWORD_DMG_VS_ROBE_HIT;
					}
					break;
				case DUAL:
					if (target.isWearingHeavyArmor())
					{
						damage /= FormulasConfigs.ALT_DUAL_DMG_VS_HEAVY_HIT;
					}
					
					if (target.isWearingLightArmor())
					{
						damage /= FormulasConfigs.ALT_DUAL_DMG_VS_LIGHT_HIT;
					}
					
					if (target.isWearingMagicArmor())
					{
						damage /= FormulasConfigs.ALT_DUAL_DMG_VS_ROBE_HIT;
					}
					break;
				case DUALFIST:
					if (target.isWearingHeavyArmor())
					{
						damage /= FormulasConfigs.ALT_DUAL_FIST_DMG_VS_HEAVY_HIT;
					}
					
					if (target.isWearingLightArmor())
					{
						damage /= FormulasConfigs.ALT_DUAL_FIST_DMG_VS_LIGHT_HIT;
					}
					
					if (target.isWearingMagicArmor())
					{
						damage /= FormulasConfigs.ALT_DUAL_FIST_DMG_VS_ROBE_HIT;
					}
					break;
				case ANCIENTSWORD:
					if (target.isWearingHeavyArmor())
					{
						damage /= FormulasConfigs.ALT_ANCIENT_DMG_VS_HEAVY_HIT;
					}
					
					if (target.isWearingLightArmor())
					{
						damage /= FormulasConfigs.ALT_ANCIENT_DMG_VS_LIGHT_HIT;
					}
					
					if (target.isWearingMagicArmor())
					{
						damage /= FormulasConfigs.ALT_ANCIENT_DMG_VS_ROBE_HIT;
					}
					break;
				case RAPIER:
					if (target.isWearingHeavyArmor())
					{
						damage /= FormulasConfigs.ALT_RAPIER_DMG_VS_HEAVY_HIT;
					}
					
					if (target.isWearingLightArmor())
					{
						damage /= FormulasConfigs.ALT_RAPIER_DMG_VS_LIGHT_HIT;
					}
					
					if (target.isWearingMagicArmor())
					{
						damage /= FormulasConfigs.ALT_RAPIER_DMG_VS_ROBE_HIT;
					}
					break;
				default:
					break;
			}
		}
		
		return damage;
	}
	
	// FIXME
	public double calcSkillDamageDependsOnClass(L2PcInstance attacker, L2PcInstance target, WeaponType type, double damage)
	{
		ClassId attackerClassId = attacker.getClassId();
		@SuppressWarnings("unused")
		int targetClassId = attacker.getClassId().getId();
		switch (attackerClassId)
		{
			case duelist: // Duelist
				if (target.isWearingHeavyArmor())
				{
					damage /= FormulasConfigs.ALT_DUAL_DMG_VS_HEAVY;
				}
				
				if (target.isWearingLightArmor())
				{
					damage /= FormulasConfigs.ALT_DUAL_DMG_VS_LIGHT;
				}
				
				if (target.isWearingMagicArmor())
				{
					damage /= FormulasConfigs.ALT_DUAL_DMG_VS_ROBE;
				}
				break;
			case phoenixKnight: // Phoenix Knight
				if (target.isWearingHeavyArmor())
				{
					damage /= FormulasConfigs.ALT_BOW_DMG_VS_HEAVY;
				}
				
				if (target.isWearingLightArmor())
				{
					damage /= FormulasConfigs.ALT_BOW_DMG_VS_LIGHT;
				}
				
				if (target.isWearingMagicArmor())
				{
					damage /= FormulasConfigs.ALT_BOW_DMG_VS_ROBE;
				}
				break;
			default:
				break;
		}
		
		return damage;
	}
	
	public static BalanceHandler getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final BalanceHandler _instance = new BalanceHandler();
	}
}