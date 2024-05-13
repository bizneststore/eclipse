package l2r.gameserver.network.clientpackets;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import l2r.Config;
import l2r.gameserver.network.serverpackets.KeyPacket;
import l2r.gameserver.network.serverpackets.L2GameServerPacket;

import gr.sr.network.handler.types.ServerType;

public final class ProtocolVersion extends L2GameClientPacket
{
	private static final Logger _logAccounting = Logger.getLogger("accounting");
	
	private int _version;
	
	@Override
	protected void readImpl()
	{
		_version = readD();
	}
	
	@Override
	protected void runImpl()
	{
		// this packet is never encrypted
		if (_version == -2)
		{
			if (Config.DEBUG)
			{
				_log.info("Ping received");
			}
			// this is just a ping attempt from the new C2 client
			getClient().close((L2GameServerPacket) null);
			return;
		}
		
		if (!ServerType.isValidProtocol(_version))
		{
			LogRecord record = new LogRecord(Level.WARNING, "Wrong protocol");
			record.setParameters(new Object[]
			{
				_version,
				getClient()
			});
			_logAccounting.log(record);
			
			getClient().setProtocolOk(false);
			getClient().close(new KeyPacket(getClient().enableCrypt(), 0));
			return;
		}
		
		getClient().setRevision(_version);
		
		if (Config.DEBUG)
		{
			_log.info("Client Protocol Revision is ok: " + _version);
		}
		
		getClient().sendPacket(new KeyPacket(getClient().enableCrypt(), 1));
		getClient().setProtocolOk(true);
	}
}
