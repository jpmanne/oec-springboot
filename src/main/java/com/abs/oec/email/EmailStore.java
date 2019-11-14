/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.email;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailStore {
	
	private static Properties environmentProperties = null;
	private static String gmailBoxUserName = null;
	private static String gmailBoxPassword = null;
	private static String mailSmtpHost = null;
	private static String mailSmtpSocketFactoryPort = null;
	private static String mailSmtpSocketFactoryClass = null;
	private static String mailSmtpPort = null;
	private static String mailSmtpAuth = null;
	private static String stgMailboxStore = null;
	
	public static String exceptionMailRecipients = null;
	
	private static EmailStore emailStore = null;
	
	//========================================================================
	
	private EmailStore() {}
	
	//========================================================================
	
	public static EmailStore getInstance() {
		if(emailStore == null) {
			emailStore = new EmailStore();
			loadMailConfigurations();
		}
		return emailStore;
	}
	
	//========================================================================
	
	private static void loadMailConfigurations(){
		environmentProperties = getEnvironmentProperties();
		mailSmtpHost = environmentProperties.getProperty("mail.smtp.host");
		mailSmtpSocketFactoryPort = environmentProperties.getProperty("mail.smtp.socketFactory.port");
		gmailBoxUserName = environmentProperties.getProperty("stg.mailbox.username");
		gmailBoxPassword = environmentProperties.getProperty("stg.mailbox.password");
		mailSmtpSocketFactoryClass = environmentProperties.getProperty("mail.smtp.socketFactory.class");
		mailSmtpPort = environmentProperties.getProperty("mail.smtp.port");
		mailSmtpAuth = environmentProperties.getProperty("mail.smtp.auth");
		stgMailboxStore = environmentProperties.getProperty("stg.mailbox.store");
		
		exceptionMailRecipients = environmentProperties.getProperty("exception.mail.recipients");
	}
	
	//========================================================================
	
	public Store getStore() throws Exception {
		Properties props = new Properties();
		Session session = null;
		Store store = null;
		
		try {
			props.put("mail.smtp.host", mailSmtpHost);
			props.put("mail.smtp.socketFactory.port", mailSmtpSocketFactoryPort);
			props.put("mail.smtp.socketFactory.class", mailSmtpSocketFactoryClass);
			props.put("mail.smtp.port", mailSmtpPort);
			props.put("mail.smtp.auth", mailSmtpAuth);
			session = Session.getDefaultInstance(props);
			store = session.getStore(stgMailboxStore);
			store.connect(mailSmtpHost, gmailBoxUserName, gmailBoxPassword);
		} catch (Exception e) {
			//new OECException("getStore():Exception occured while Connecting to Email", e);
		}
		return store;
	}

	//========================================================================
	
	public MimeMessage getMimeMessage() throws Exception {
		MimeMessage message = null;
		
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", mailSmtpHost);
			props.put("mail.smtp.socketFactory.port", mailSmtpSocketFactoryPort);
			props.put("mail.smtp.socketFactory.class", mailSmtpSocketFactoryClass);
			props.put("mail.smtp.port", mailSmtpPort);
			props.put("mail.smtp.auth", mailSmtpAuth);
			
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(gmailBoxUserName, gmailBoxPassword);
				}
			});
			message = new MimeMessage(session);
			// Setting From
			message.setFrom(new InternetAddress(gmailBoxUserName));
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	// ========================================================================
	
	private static Properties getEnvironmentProperties() {
		Properties envProps = new Properties();
		
		try {
			envProps = new Properties();
			envProps.load(EmailStore.class.getClassLoader().getResourceAsStream("mailconfig.properties"));
		} catch (Exception e) {
			//new OECException("Exception occured while getting environment properties file", e);
			e.printStackTrace();
		}
		return envProps;
	}
	
	// ========================================================================
	
}