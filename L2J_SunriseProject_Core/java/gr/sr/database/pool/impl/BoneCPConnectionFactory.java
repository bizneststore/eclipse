package gr.sr.database.pool.impl;

import java.util.concurrent.TimeUnit;

import l2r.Config;
import l2r.L2DatabaseFactory;

import com.jolbox.bonecp.BoneCPDataSource;

/**
 * BoneCP Connection Factory implementation.<br>
 * @author vGodFather
 */
public class BoneCPConnectionFactory extends L2DatabaseFactory
{
	private static final int PARTITION_COUNT = 5;
	
	private final BoneCPDataSource _dataSource;
	
	public BoneCPConnectionFactory()
	{
		_dataSource = new BoneCPDataSource();
		_dataSource.setJdbcUrl(Config.DATABASE_URL);
		_dataSource.setUsername(Config.DATABASE_LOGIN);
		_dataSource.setPassword(Config.DATABASE_PASSWORD);
		_dataSource.setPartitionCount(PARTITION_COUNT);
		_dataSource.setMaxConnectionsPerPartition(Config.DATABASE_MAX_CONNECTIONS);
		_dataSource.setIdleConnectionTestPeriod(Config.DATABASE_MAX_IDLE_TIME, TimeUnit.SECONDS);
	}
	
	// @Override
	// public void close()
	// {
	// try
	// {
	// _dataSource.close();
	// }
	// catch (Exception e)
	// {
	// LOG.warn("There has been a problem closing the data source!", e);
	// }
	// }
	//
	// @Override
	// public DataSource getDataSource()
	// {
	// return _dataSource;
	// }
}
