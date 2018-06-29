package com.allianz.rws.joridmicro.model;

import java.util.Date;

public class Employee {

	private int id;
	private String name;
	private Date createdDate=new Date();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreatedDate() {
		return createdDate == null ? null : new Date(createdDate.getTime());
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = new Date(createdDate.getTime());
	}
	
	
}
