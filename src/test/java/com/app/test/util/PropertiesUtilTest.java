package com.app.test.util;

import com.app.util.PropertiesUtil;

import java.io.IOException;

import java.net.URL;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jonathan McCann
 */
public class PropertiesUtilTest {

	@Test
	public void testLoadConfigurationProperties() throws IOException {
		Class<?> clazz = getClass();

		URL resource = clazz.getResource("/test-config.properties");

		PropertiesUtil.loadConfigurationProperties(resource.getPath());

		Assert.assertEquals(
			"Application ID",
			PropertiesUtil.getConfigurationProperty(
				PropertiesUtil.APPLICATION_ID));
		Assert.assertEquals(
			"JDBC Default Password",
			PropertiesUtil.getConfigurationProperty(
				PropertiesUtil.DATABASE_PASSWORD));
		Assert.assertEquals(
			"JDBC Default URL",
			PropertiesUtil.getConfigurationProperty(
				PropertiesUtil.DATABASE_URL));
		Assert.assertEquals(
			"JDBC Default Username",
			PropertiesUtil.getConfigurationProperty(
				PropertiesUtil.DATABASE_USERNAME));
		Assert.assertEquals(
			"5",
			PropertiesUtil.getConfigurationProperty(
				PropertiesUtil.NUMBER_OF_SEARCH_RESULTS));
		Assert.assertEquals(
			"test@test.com",
			PropertiesUtil.getConfigurationProperty(
				PropertiesUtil.OUTBOUND_EMAIL_ADDRESS));
		Assert.assertEquals(
			"test",
			PropertiesUtil.getConfigurationProperty(
				PropertiesUtil.OUTBOUND_EMAIL_ADDRESS_PASSWORD));
		Assert.assertEquals(
			"true",
			PropertiesUtil.getConfigurationProperty(
				PropertiesUtil.MAIL_SMTP_AUTH));
		Assert.assertEquals(
			"true",
			PropertiesUtil.getConfigurationProperty(
				PropertiesUtil.MAIL_SMTP_STARTTLS_ENABLE));
		Assert.assertEquals(
			"smtp.gmail.com",
			PropertiesUtil.getConfigurationProperty(
				PropertiesUtil.MAIL_SMTP_HOST));
		Assert.assertEquals(
			"587",
			PropertiesUtil.getConfigurationProperty(
				PropertiesUtil.MAIL_SMTP_PORT));
		Assert.assertEquals(
			"test@test.com,test2@test2.com",
			PropertiesUtil.getConfigurationProperty(
				PropertiesUtil.RECIPIENT_EMAIL_ADDRESSES));
		Assert.assertEquals(
			"1234567890,2345678901",
			PropertiesUtil.getConfigurationProperty(
				PropertiesUtil.RECIPIENT_PHONE_NUMBERS));
		Assert.assertEquals(
			"AT&T",
			PropertiesUtil.getConfigurationProperty(
				PropertiesUtil.RECIPIENT_PHONE_CARRIER));
	}

	@Test
	public void testSetConfigurationProperties() throws IOException {
		Properties properties = new Properties();

		properties.setProperty(
			PropertiesUtil.APPLICATION_ID, "Updated Application ID");
		properties.setProperty(
			PropertiesUtil.DATABASE_PASSWORD, "Updated JDBC Default Password");
		properties.setProperty(
			PropertiesUtil.DATABASE_URL, "Updated JDBC Default URL");
		properties.setProperty(
			PropertiesUtil.DATABASE_USERNAME, "Updated JDBC Default Username");

		PropertiesUtil.setConfigurationProperties(properties);

		properties = PropertiesUtil.getConfigurationProperties();

		Assert.assertEquals(
			"Updated Application ID",
			properties.getProperty(PropertiesUtil.APPLICATION_ID));
		Assert.assertEquals(
			"Updated JDBC Default Password",
			properties.getProperty(PropertiesUtil.DATABASE_PASSWORD));
		Assert.assertEquals(
			"Updated JDBC Default URL",
			properties.getProperty(PropertiesUtil.DATABASE_URL));
		Assert.assertEquals(
			"Updated JDBC Default Username",
			properties.getProperty(PropertiesUtil.DATABASE_USERNAME));
	}

	@Test(expected = IOException.class)
	public void testLoadInvalidConfigurationProperties() throws Exception {
		PropertiesUtil.loadConfigurationProperties();
	}

}