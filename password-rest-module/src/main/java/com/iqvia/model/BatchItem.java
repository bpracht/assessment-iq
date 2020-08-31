package com.iqvia.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;

@SuppressWarnings("serial")
@JsonPropertyOrder(value = { "batchId","email","initialPassword","item","name" ,"reasonForAccess","role"})

public class BatchItem implements Serializable {
	@JsonProperty
	private int batchId;

	@JsonProperty
	private String email;

	@JsonProperty
	private String initialPassword;

	@JsonProperty
	private int item;

	@JsonProperty
	private String name;

	@JsonProperty
	private String reasonForAccess;

	@JsonProperty
	private String role;

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

	@Override
	public String toString() {
		return "BatchItem [batchId=" + batchId + ", email=" + email + ", initialPassword=" + initialPassword + ", item="
				+ item + ", name=" + name + ", reasonForAccess=" + reasonForAccess + ", role=" + role + "]";
	}
}
