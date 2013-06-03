package com.krl109.scheduler.db;

public class SMSManager {
	
	
	String phoneNumber;
	String message;
	
	public SMSManager(SMSActivity sms){
		sms.sendSMS(phoneNumber, message);
	}
	
	public SMSManager(String phoneNumber, String message){
		this.phoneNumber = phoneNumber;
		this.message = message;
	}
}
