package gr.sr.database.pool.impl;

import l2r.Config;
import l2r.L2DatabaseFactory;

import com.zaxxer.hikari.HikariDataSource;

/**
 * HikariCP Connection Factory implementation<br>
 * @author vGodFather
 */
public class HikariCPConnectionFactory extends L2DatabaseFactory
{
	private final HikariDataSource _dataSource;
	
	public HikariCPConnectionFactory()
	{
		_dataSource = new HikariDataSource();
		_dataSource.setJdbcUrl(Config.DATABASE_URL);
		_dataSource.setUsername(Config.DATABASE_LOGIN);
		_dataSource.setPassword(Config.DATABASE_PASSWORD);
		_dataSource.setMaximumPoolSize(Config.DATABASE_MAX_CONNECTIONS);
		_dataSource.setIdleTimeout(Config.DATABASE_MAX_IDLE_TIME);
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
