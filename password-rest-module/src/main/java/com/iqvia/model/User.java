package com.iqvia.model;

public class User {
	private int batchId;
	private int item;
	private String name;
	private String email;
	private String initialPassword;
	private String role;
	private String reasonForAccess;
	public int getBatchId() {
		return batchId;
	}
	public String getEmail() {
		return email;
	}
	public String getInitialPassword() {
		return initialPassword;
	}
	public int getItem() {
		return item;
	}
	public String getName() {
		return name;
	}
	public String getReasonForAccess() {
		return reasonForAccess;
	}
	public String getRole() {
		return role;
	}
	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setInitialPassword(String initialPassword) {
		this.initialPassword = initialPassword;
	}
	public void setItem(int item) {
		this.item = item;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setReasonForAccess(String reasonForAccess) {
		this.reasonForAccess = reasonForAccess;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
