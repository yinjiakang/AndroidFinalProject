package com.example.singforyou;

import java.util.jar.Attributes.Name;

import android.R.string;

public class Floor {
	protected String Content, HostName;
	protected int BelongTo, FloorID;
	
	public Floor() {
		Content = HostName = "";
		BelongTo = FloorID = -1;
	}
	public Floor(String content, String hostname, int belongto, int floorid) {
		Content = content;
		BelongTo = belongto;
		FloorID = floorid;
		HostName = hostname;
	}
	public String getContent() {
		return Content;
	}
	public String getHostName() {
		return HostName;
	}
	public int getBelongTo() {
		return BelongTo;
	}
	public int getFloorID() {
		return FloorID;
	}
	public void setContent(String content) {
		Content = content;
	}
	public void setHostName(String hostname) {
		HostName = hostname;
	}
	public void setBelongTo(int belongto) {
		BelongTo = belongto;
	}
	public void setFloorID(int floorid) {
		FloorID = floorid;
	}
} 