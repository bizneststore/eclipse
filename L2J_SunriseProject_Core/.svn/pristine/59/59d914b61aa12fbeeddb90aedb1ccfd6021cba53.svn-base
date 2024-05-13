package gr.sr.database.pool.impl;

import java.util.Properties;

import l2r.Config;
import l2r.L2DatabaseFactory;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.pool.impl.GenericKeyedObjectPoolFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

/**
 * The base implementation of the thread pool using DBCP
 * @author vGodFather
 */
public class ApacheConnectionFactory extends L2DatabaseFactory
{
	// private final PoolingDataSource _dataSource;
	// private final ObjectPool _connectionPool;
	
	public ApacheConnectionFactory()
	{
		GenericObjectPool connectionPool = new GenericObjectPool(null);
		
		connectionPool.setMaxActive(Config.DATABASE_MAX_CONNECTIONS);
		connectionPool.setMaxIdle(Config.DATABASE_MAX_CONNECTIONS);
		connectionPool.setMinIdle(1);
		connectionPool.setMaxWait(-1L);
		connectionPool.setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_GROW);
		connectionPool.setTestOnBorrow(false);
		connectionPool.setTestWhileIdle(true);
		connectionPool.setTimeBetweenEvictionRunsMillis(Config.DATABASE_IDLE_TEST_PERIOD * 1000L);
		connectionPool.setNumTestsPerEvictionRun(Config.DATABASE_MAX_CONNECTIONS);
		connectionPool.setMinEvictableIdleTimeMillis(Config.DATABASE_MAX_IDLE_TIME * 1000L);
		
		GenericKeyedObjectPoolFactory statementPoolFactory = null;
		// if (poolPreparedStatements)
		// {
		// statementPoolFactory = new GenericKeyedObjectPoolFactory(null, -1, GenericObjectPool.WHEN_EXHAUSTED_FAIL, 0L, 1, GenericKeyedObjectPool.DEFAULT_MAX_TOTAL);
		// }
		
		Properties connectionProperties = new Properties();
		connectionProperties.put("user", Config.DATABASE_LOGIN);
		connectionProperties.put("password", Config.DATABASE_PASSWORD);
		
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(Config.DATABASE_URL, connectionProperties);
		
		@SuppressWarnings("unused")
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, connectionPool, statementPoolFactory, "SELECT 1", false, true);
		
		// PoolingDataSource dataSource = new PoolingDataSource(connectionPool);
		
		// _connectionPool = connectionPool;
		// _dataSource = dataSource;
	}
	
	// @Override
	// public void close()
	// {
	// try
	// {
	// _connectionPool.close();
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
