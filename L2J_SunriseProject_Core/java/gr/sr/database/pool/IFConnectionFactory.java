package gr.sr.database.pool;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Connection Factory interface.
 * @author vGodFather
 */
public interface IFConnectionFactory
{
	public static final Logger LOG = LoggerFactory.getLogger(IFConnectionFactory.class);
	
	/**
	 * Gets the data source.
	 * @return the data source
	 */
	DataSource getDataSource();
	
	/**
	 * Closes the data source.<br>
	 * <i>Same as shutdown.</i>
	 */
	void close();
	
	/**
	 * Gets a connection from the pool.
	 * @return a connection
	 */
	default Connection getConnection()
	{
		Connection con = null;
		while (con == null)
		{
			try
			{
				con = getDataSource().getConnection();
			}
			catch (SQLException e)
			{
				LOG.warn("{}: Unable to get a connection!", getClass().getSimpleName(), e);
			}
		}
		return con;
	}
}
