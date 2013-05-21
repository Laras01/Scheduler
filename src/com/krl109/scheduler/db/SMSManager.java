package com.krl109.scheduler.db;

public class SMSManager {
	String phoneNumber;
	String message;
	
	public SMSManager(){}
	
	public SMSManager(String phoneNumber, String message){
		this.phoneNumber = phoneNumber;
		this.message = message;
	}
}
