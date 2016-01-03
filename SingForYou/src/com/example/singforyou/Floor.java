package com.example.singforyou;

import java.util.jar.Attributes.Name;

import android.R.string;

public class Floor {
	protected String Content, HostName, MusicID;
	protected int BelongTo, FloorID;
	
	public Floor() {
		Content = HostName = MusicID =  "";
		BelongTo = FloorID = -1;
	}
	public Floor(String content, String hostname, String musicid, int belongto, int floorid) {
		Content = content;
		BelongTo = belongto;
		MusicID = musicid;
		FloorID = floorid;
		HostName = hostname;
	}
	// get
	public String getContent() {
		return Content;
	}
	public String getHostName() {
		return HostName;
	}
	public String getMusicID() {
		return MusicID;
	}
	public int getBelongTo() {
		return BelongTo;
	}
	public int getFloorID() {
		return FloorID;
	}
	// set
	public void setContent(String content) {
		Content = content;
	}
	public void setHostName(String hostname) {
		HostName = hostname;
	}
	public void setMusicID(String musicid) {
		MusicID = musicid;
	}
	public void setBelongTo(int belongto) {
		BelongTo = belongto;
	}
	public void setFloorID(int floorid) {
		FloorID = floorid;
	}
} 