package com.mySampleApplication.client;

import com.google.gwt.user.client.ui.Composite;

import java.util.LinkedList;
import java.util.List;

public class CafaWidget extends Composite {
	
//	private CafaServiceAsync service;
	private Controller controller;
	
	public void init(){
		
	}
	
//	public void setService(CafaServiceAsync service) {
//		this.service = service;
//	}
//	public CafaServiceAsync getService() {
//		return service;
//	}
	public void setController(Controller controller) {
		this.controller = controller;
	}
	public Controller getController() {
		return controller;
	}
}
