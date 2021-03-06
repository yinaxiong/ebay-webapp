package com.app.listener;

import com.app.exception.DatabaseConnectionException;
import com.app.util.DatabaseUtil;
import com.app.util.PropertiesUtil;
import com.app.util.eBayAPIUtil;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jonathan McCann
 */
public class eBayServletContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		_log.info("Destroying servlet context");
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent)
		throws RuntimeException {

		_log.info("Initializing servlet context");

		try {
			_log.info("Loading configuration properties");

			PropertiesUtil.loadConfigurationProperties();

			_log.info("Loading eBay service client");

			eBayAPIUtil.loadeBayServiceClient();

			_log.info("Loading database properties");

			DatabaseUtil.loadDatabaseProperties();

			_log.info("Initializing database");

			DatabaseUtil.initializeDatabase();
		}
		catch (DatabaseConnectionException | IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private static final Logger _log = LoggerFactory.getLogger(
		eBayServletContextListener.class);

}