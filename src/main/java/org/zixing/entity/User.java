package org.zixing.entity;

public class User {
	private int userId;
	private String userName;
	private String userLogin;
	private String userPassword;
	private String userDate;
	private boolean userRoot;
	private String infoOne;//查询日期
	private String infoTwo;//筛选条件
	private String infoThree;//编号
	private String endDate;
	
	
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public User(){
		super();
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserDate() {
		return userDate;
	}
	public void setUserDate(String userDate) {
		this.userDate = userDate;
	}
	public boolean isUserRoot() {
		return userRoot;
	}
	public void setUserRoot(boolean userRoot) {
		this.userRoot = userRoot;
	}
	public String getInfoOne() {
		return infoOne;
	}
	public void setInfoOne(String infoOne) {
		this.infoOne = infoOne;
	}
	public String getInfoTwo() {
		return infoTwo;
	}
	public void setInfoTwo(String infoTwo) {
		this.infoTwo = infoTwo;
	}
	public String getInfoThree() {
		return infoThree;
	}
	public void setInfoThree(String infoThree) {
		this.infoThree = infoThree;
	}
	public User(int userId, String userName, String userLogin,
			String userPassword, String userDate) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userLogin = userLogin;
		this.userPassword = userPassword;
		this.userDate = userDate;
	}
	public User(String userName, String userLogin,
			String userPassword, String userDate) {
		super();
		this.userName = userName;
		this.userLogin = userLogin;
		this.userPassword = userPassword;
		this.userDate = userDate;
	}
	public User(String userLogin,String userPassword) {
		super();
		this.userLogin = userLogin;
		this.userPassword = userPassword;
	}
	
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName
				+ ", userLogin=" + userLogin + ", userPassword=" + userPassword
				+ ", userDate=" + userDate + ", userRoot=" + userRoot
				+ ", infoOne=" + infoOne + ", infoTwo=" + infoTwo
				+ ", infoThree=" + infoThree + "]";
	}
}
