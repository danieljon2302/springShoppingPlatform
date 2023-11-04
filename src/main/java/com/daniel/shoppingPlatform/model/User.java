package com.daniel.shoppingPlatform.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
	
	private Integer userId;
	
	@JsonProperty("e_mail")
	//改變返回前端的json KEY值(原返回值得KEY為email)
	private String email;
	
	@JsonIgnore
	//密碼為敏感資訊, 故需使用此註解隱藏其值不顯示在前端
	private String password;
	
	private Date createdDate;
	private Date lastModifiedDate;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateDate() {
		return createdDate;
	}
	public void setCreateDate(Date createDate) {
		this.createdDate = createDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	

}
