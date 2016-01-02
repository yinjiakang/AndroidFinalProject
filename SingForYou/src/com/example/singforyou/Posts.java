package com.example.singforyou;

import java.util.ArrayList;
import java.util.jar.Attributes.Name;

import android.R.string;
import android.text.GetChars;

public class Posts {
	protected String Name, PostTitle, Content;
	protected int PostID, NumOfFloor;
	protected ArrayList<Floor> FloorList;
	
	public Posts() {
		Name = PostTitle = Content = "";
		PostID = -1;
	}
	public Posts(String name, String posttitle, String content, int postid, int numoffloor, ArrayList<Floor> floorlist) {
		Name = name;
		PostTitle = posttitle;
		Content = content;
		PostID = PostID;
		NumOfFloor = numoffloor;
		FloorList = floorlist;
	}
	public String getName() {
		return Name;
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
	public ArrayList<Floor> getFloorList() {
		return FloorList;
	}
	public void setName(String name) {
		Name = name;
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
	public void setFloorList(ArrayList<Floor> floorlist) {
		FloorList = floorlist;
	}
	public void addFloor(Floor floor) {
		setNumOfFloor(NumOfFloor+1);
		FloorList.add(floor);
	}
} 