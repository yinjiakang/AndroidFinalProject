package com.example.singforyou;

import java.util.ArrayList;

import android.R.string;
import android.text.GetChars;

public class Posts {
	protected String PostAccount, PostTitle, Content;
	protected int PostID, NumOfFloor;
	protected Boolean isGood;
	// protected ArrayList<Floor> FloorList;
	
	public Posts() {
		PostAccount = PostTitle = Content = "";
		isGood = false;
		PostID = NumOfFloor = -1;
	}
	public Posts(String postaccount, String posttitle, String content, int postid, int numoffloor) {
		PostAccount = postaccount;
		PostTitle = posttitle;
		Content = content;
		PostID = postid;
		NumOfFloor = numoffloor;
	}
	/*
	public Posts(String postaccount, String posttitle, String content, int postid, int numoffloor, ArrayList<Floor> floorlist) {
		PostAccount = postaccount;
		PostTitle = posttitle;
		Content = content;
		PostID = postid;
		NumOfFloor = numoffloor;
		FloorList = floorlist;
	}*/
	// get
	public String getPostAccount() {
		return PostAccount;
	}
	public String getPostTitle() {
		return PostTitle;
	}
	public String getContent() {
		return Content;
	}
	public int getPostID() {
		return PostID;
	}
	public int getNumOfFloor() {
		return NumOfFloor;
	}
	public Boolean IsGood() {
		return isGood;
	}
	/*public ArrayList<Floor> getFloorList() {
		return FloorList;
	}*/
	// set
	public void setPostAccount(String postaccount) {
		PostAccount = postaccount;
	}
	public void setPostTitle(String posttitle) {
		PostTitle = posttitle;
	}
	public void setContent(String content) {
		Content = content;
	}
	public void setPostID(int numoffloor) {
		PostID = numoffloor;
	}
	public void setNumOfFloor(int postid) {
		NumOfFloor = postid;
	}
	/*
	public void setFloorList(ArrayList<Floor> floorlist) {
		FloorList = floorlist;
	}*/
	public void setIsGood(Boolean isgood) {
		isGood = isgood;
	}
	/*
	public void addFloor(Floor floor) {
		setNumOfFloor(NumOfFloor+1);
		FloorList.add(floor);
	}*/
} 