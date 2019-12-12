/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.email;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class EmailUtil {
	//private static final Logger LOGGER = LoggerFactory.getLogger(EmailUtil.class);
	
	private static EmailUtil instance; 
	
	// ========================================================================
	
	private EmailUtil() {}
	
	public static EmailUtil getInstance() {
		if(instance == null) {
			instance = new EmailUtil();
		}
		return instance;
	}
	
	// ========================================================================

	public boolean send(EmailDetails emailDetails) throws Exception {
		String logTag = "send(): ";
		//LOGGER.info("entering into " + logTag);
		MimeMessage message = null;
		boolean isMailSent = false;
		try {
			if (emailDetails.getToRecipients() != null && emailDetails.getSubject() != null && emailDetails.getMessageContent() != null) {
				message = EmailStore.getInstance().getMimeMessage();
				if (message != null) {
					// Setting To Address
					message = populateRecipients(message, emailDetails.getToRecipients(), Message.RecipientType.TO);
					// Setting CC
					if (emailDetails.getCcRecipients() != null && emailDetails.getCcRecipients().trim().length() > 0) {
						String[] ccRecipients = emailDetails.getCcRecipients().trim().split(",");
						message = populateRecipients(message, ccRecipients, Message.RecipientType.CC);
					}
					// Setting Subject
					message.setSubject(emailDetails.getSubject());
					message = populateMultipart(message, emailDetails.getMessageContent(), emailDetails.getAttachmentFilePath());
					// send message
					Transport.send(message);
					isMailSent = true;
					//LOGGER.info(logTag + " message sent successfully");
				} else {
					//LOGGER.error(logTag + " Unable to get the message object");
				}
			} else {
				//LOGGER.error(logTag + " Invalid Input");
			}
		} catch (MessagingException e) {
			//new WMSException(logTag+"unable to send email, mail conctivity issue", e);
			throw new RuntimeException(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isMailSent;
	}

	// ========================================================================
	
	private String getFileName(String attachementFilePath) {
		return attachementFilePath.substring(attachementFilePath.lastIndexOf("/") + 1, attachementFilePath.length());
	}

	// ========================================================================

	private MimeMessage populateRecipients(MimeMessage message, String[] recipients, Message.RecipientType recipientType) throws MessagingException {
		Address[] addresses = null;
		Address address = null;
		int addressesCount = 0;

		addresses = new Address[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			if (recipients[i] != null) {
				address = new InternetAddress(recipients[i].trim());
				addresses[addressesCount] = address;
				addressesCount++;
			}
		}
		message.setRecipients(recipientType, addresses);
		return message;
	}

	// ========================================================================

	private MimeMessage populateMultipart(MimeMessage message, String messageContent, String attachmentFilePath) throws MessagingException {
		// Create MimeBodyPart object and set your message text
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(messageContent);
		Multipart multiPart = new MimeMultipart();
		multiPart.addBodyPart(messageBodyPart);
		
		if (attachmentFilePath != null) {
			// create new MimeBodyPart object and set DataHandler object to this object
			MimeBodyPart attachmentMimeBodyPart = new MimeBodyPart();
			DataSource dataSource = new FileDataSource(attachmentFilePath);
			attachmentMimeBodyPart.setDataHandler(new DataHandler(dataSource));
			attachmentMimeBodyPart.setFileName(getFileName(attachmentFilePath));
			// Create multipart object and add MimeBodyPart objects to this object
			multiPart.addBodyPart(attachmentMimeBodyPart);
		}
		// Set the multipart object to the message object
		message.setContent(multiPart);
		return message;
	}

	// ========================================================================
	
	public void notifyException(String subject, String messageBody) throws Exception {
		String logTag = "notifyException(): ";
		//LOGGER.info(logTag + " START of the method");
		MimeMessage message = null;
		
		try {
			message = EmailStore.getInstance().getMimeMessage();
			
			if (message != null) {
				String toRecipientsString = EmailStore.exceptionMailRecipients;
				String[] toRecipients = null;
				
				if(toRecipientsString != null){
					toRecipients = toRecipientsString.split(",");
					message = populateRecipients(message, toRecipients, Message.RecipientType.TO);
					message.setSubject(subject);
					
					BodyPart messageBodyPart = new MimeBodyPart();
					messageBodyPart.setText(messageBody);
					Multipart multiPart = new MimeMultipart();
					multiPart.addBodyPart(messageBodyPart);
					message.setContent(multiPart);
					Transport.send(message);
					//LOGGER.info("Notification of exception is successful");
				} else {
					//LOGGER.error("Exception Mail Recipients loaded from property file are null");
				}
			} 
		} catch (Exception e) {
			//LOGGER.error(logTag + " EXCEPTION : "+e);
		}
		//LOGGER.info(logTag + " START of the method");
	}
	
	// ========================================================================
	
}
