package com.krl109.scheduler.db;

public class SMSManager {

	public String phoneNumber;
	public String message;
	
	public SMSManager(){}
	
	public SMSManager(String phoneNumber, String message){
		this.phoneNumber = phoneNumber;
		this.message = message;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
