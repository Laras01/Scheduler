package com.krl109.scheduler.linkedlist;

import com.krl109.scheduler.db.SMSManager;

public class ListQueue{
	
	public String phoneNumber;
	public String message;
	public ListQueue next;
	
	Identifier head, tail;
	
	public ListQueue(){}
	
	public void createIdentifier(){
		Identifier temporary = new Identifier();
		temporary.identifier = null;
		head = temporary;
		tail = temporary;
	}
	
	public ListQueue createNode(String phoneNumber, String message){
		ListQueue newNode = new ListQueue();
		newNode.phoneNumber = phoneNumber;
		newNode.message = message;
		newNode.next = null;
		
		return newNode;
	}
}
