package gr.sr.javaBuffer;

import l2r.gameserver.data.xml.impl.SkillData;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.model.skills.L2Skill;

import gr.sr.configsEngine.configs.impl.BufferConfigs;
import gr.sr.javaBuffer.xml.dataHolder.BuffsHolder;

public class AutoBuff
{
	private static int[] _mageBuffs = BufferConfigs.MAGE_BUFFS_LIST;
	private static int[] _fighterBuffs = BufferConfigs.FIGHTER_BUFFS_LIST;
	private static int _buffsCount;
	
	public static void autoBuff(L2PcInstance activeChar)
	{
		if (!PlayerMethods.checkDelay(activeChar))
		{
			return;
		}
		
		if (activeChar.isMageClass())
		{
			_buffsCount = _mageBuffs.length;
			if (!PlayerMethods.checkPriceConsume(activeChar, _buffsCount))
			{
				return;
			}
			
			for (int id : _mageBuffs)
			{
				BuffsInstance buff = BuffsHolder.getInstance().getBuff(id);
				
				if (buff == null)
				{
					continue;
				}
				
				L2Skill skill = (activeChar.getInventory().getItemByItemId(BufferConfigs.DONATE_BUFF_ITEM_ID) != null) || (activeChar.isPremium()) ? SkillData.getInstance().getInfo(buff.getId(), buff.getCustomLevel()) : SkillData.getInstance().getInfo(buff.getId(), buff.getLevel());
				if (skill == null)
				{
					continue;
				}
				
				skill.getEffects(activeChar, activeChar);
				
				if (activeChar.hasSummon())
				{
					skill.getEffects(activeChar, activeChar.getSummon());
				}
			}
		}
		else
		{
			_buffsCount = _fighterBuffs.length;
			if (!PlayerMethods.checkPriceConsume(activeChar, _buffsCount))
			{
				return;
			}
			
			for (int id : _fighterBuffs)
			{
				BuffsInstance buff = BuffsHolder.getInstance().getBuff(id);
				
				if (buff == null)
				{
					continue;
				}
				
				L2Skill skill = (activeChar.getInventory().getItemByItemId(BufferConfigs.DONATE_BUFF_ITEM_ID) != null) || (activeChar.isPremium()) ? SkillData.getInstance().getInfo(buff.getId(), buff.getCustomLevel()) : SkillData.getInstance().getInfo(buff.getId(), buff.getLevel());
				if (skill == null)
				{
					continue;
				}
				
				skill.getEffects(activeChar, activeChar);
				
				if (activeChar.hasSummon())
				{
					skill.getEffects(activeChar, activeChar.getSummon());
				}
			}
		}
		
		if (BufferConfigs.HEAL_PLAYER_AFTER_ACTIONS)
		{
			activeChar.setCurrentHpMp(activeChar.getMaxHp(), activeChar.getMaxMp());
			activeChar.setCurrentCp(activeChar.getMaxCp());
		}
		PlayerMethods.addDelay(activeChar);
	}
}