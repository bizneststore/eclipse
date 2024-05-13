package gr.sr.database.pool.impl;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import l2r.Config;
import l2r.L2DatabaseFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * C3P0 Connection Factory implementation.<br>
 * @author vGodFather
 */
public class C3P0ConnectionFactory extends L2DatabaseFactory
{
	private final ComboPooledDataSource _dataSource;
	
	public C3P0ConnectionFactory()
	{
		if (Config.DATABASE_MAX_CONNECTIONS < 2)
		{
			Config.DATABASE_MAX_CONNECTIONS = 2;
			// LOG.warn("A minimum of {} database connections are required.", Config.DATABASE_MAX_CONNECTIONS);
		}
		
		_dataSource = new ComboPooledDataSource();
		_dataSource.setAutoCommitOnClose(true);
		
		_dataSource.setInitialPoolSize(10);
		_dataSource.setMinPoolSize(10);
		_dataSource.setMaxPoolSize(Math.max(10, Config.DATABASE_MAX_CONNECTIONS));
		
		_dataSource.setAcquireRetryAttempts(0); // try to obtain connections indefinitely (0 = never quit)
		_dataSource.setAcquireRetryDelay(500); // 500 milliseconds wait before try to acquire connection again
		_dataSource.setCheckoutTimeout(0); // 0 = wait indefinitely for new connection if pool is exhausted
		_dataSource.setAcquireIncrement(5); // if pool is exhausted, get 5 more connections at a time
		// cause there is a "long" delay on acquire connection
		// so taking more than one connection at once will make connection pooling
		// more effective.
		
		// this "connection_test_table" is automatically created if not already there
		_dataSource.setAutomaticTestTable("connection_test_table");
		_dataSource.setTestConnectionOnCheckin(false);
		
		// testing OnCheckin used with IdleConnectionTestPeriod is faster than testing on checkout
		
		_dataSource.setIdleConnectionTestPeriod(3600); // test idle connection every 60 sec
		_dataSource.setMaxIdleTime(Config.DATABASE_MAX_IDLE_TIME); // 0 = idle connections never expire
		// *THANKS* to connection testing configured above
		// but I prefer to disconnect all connections not used
		// for more than 1 hour
		
		// enables statement caching, there is a "semi-bug" in c3p0 0.9.0 but in 0.9.0.2 and later it's fixed
		_dataSource.setMaxStatementsPerConnection(100);
		
		_dataSource.setBreakAfterAcquireFailure(false); // never fail if any way possible
		// setting this to true will make
		// c3p0 "crash" and refuse to work
		// till restart thus making acquire
		// errors "FATAL" ... we don't want that
		// it should be possible to recover
		try
		{
			_dataSource.setDriverClass(Config.DATABASE_DRIVER);
		}
		catch (PropertyVetoException e)
		{
			// LOG.error("There has been a problem setting the driver class!", e);
		}
		_dataSource.setJdbcUrl(Config.DATABASE_URL);
		_dataSource.setUser(Config.DATABASE_LOGIN);
		_dataSource.setPassword(Config.DATABASE_PASSWORD);
		
		/* Test the connection */
		try
		{
			_dataSource.getConnection().close();
		}
		catch (SQLException e)
		{
			// LOG.warn("There has been a problem closing the test connection!", e);
		}
		
		// LOG.debug("Database connection working.");
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
