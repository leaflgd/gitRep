package org.zixing.util;

import java.util.List;

public class UserList {
	private String logName="";
	private String cardOne="";
	private String cardTwo=""; 
	private String cardThree="";
	private String cardFour="";
	private String cardFive="";
    private	String cardSix="";
    
	public String getLogName() {
		return logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}

	public String getCardOne() {
		return cardOne;
	}

	public void setCardOne(String cardOne) {
		this.cardOne = cardOne;
	}
	public void setCardOne(List<String> cardOnes) {
		for(String str:cardOnes){
			this.cardOne+=str+"/*/";
		}
	}

	public String getCardTwo() {
		return cardTwo;
	}

	public void setCardTwo(String cardTwo) {
		this.cardTwo = cardTwo;
	}
	public void setCardTwo(List<String> cardTwos) {
		for(String str:cardTwos){
			this.cardTwo+=str+"/*/";
		}
	}

	
	public String getCardThree() {
		return cardThree;
	}

	public void setCardThree(String cardThree) {
		this.cardThree = cardThree;
	}
	public void setCardThree(List<String> cardThrees) {
		for(String str:cardThrees){
			this.cardThree+=str+"/*/";
		}
	}

	
	public String getCardFour() {
		return cardFour;
	}

	public void setCardFour(String cardFour) {
		this.cardFour = cardFour;
	}
	public void setCardFour(List<String> cardFours) {
		for(String str:cardFours){
			this.cardFour+=str+"/*/";
		}
	}
	
	public String getCardFive() {
		return cardFive;
	}

	public void setCardFive(String cardFive) {
		this.cardFive = cardFive;
	}
	public void setCardFive(List<String> cardFives) {
		for(String str:cardFives){
			this.cardFive+=str+"/*/";
		}
	}

	public String getCardSix() {
		return cardSix;
	}

	public void setCardSix(String cardSix) {
		this.cardSix = cardSix;
	}
	public void setCardSix(List<String> cardSixs) {
		for(String str:cardSixs){
			this.cardSix+=str+"/*/";
		}
	}

	@Override
	public String toString() {
		return "userList [logName=" + logName + ", cardOne=" + cardOne
				+ ", cardTwo=" + cardTwo + ", cardThree=" + cardThree
				+ ", cardFour=" + cardFour + ", cardFive=" + cardFive
				+ ", cardSix=" + cardSix + "]";
	}
    
}
