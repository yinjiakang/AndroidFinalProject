package com.example.singforyou;

import java.util.ArrayList;
import java.util.jar.Attributes.Name;

import android.R.array;
import android.R.string;

public class Person {
	protected String Account, Password, Name;
	protected int SingTime, ExperienceValue;
	//protected ArrayList<Integer> PostsList;
	public Person() {
		Account = Password = Name = "";
		SingTime = ExperienceValue = 0;
	}
	/*public Person(String account, String password, String name, int singtime, int experience, ArrayList<Integer> postslist) {
		Account = account;
		Password = password;
		Name = name;
		SingTime = singtime;
		ExperienceValue = experience;
		PostsList = postslist;
	}*/
	public Person(String account, String password, String name, int singtime, int experience) {
		Account = account;
		Password = password;
		Name = name;
		SingTime = singtime;
		ExperienceValue = experience;
	}
	
	//get
	public String getAccount() {
		return Account;
	}
	public String getPassword() {
		return Password;
	}
	public String getName() {
		return Name;
	}
	public int getSingTime() {
		return SingTime;
	}
	public int getExperienceValue() {
		return ExperienceValue;
	}
	/*public ArrayList<Integer> getPostList() {
		return PostsList;
	}
	*/
	//set
	public void setAccount(String aString) {
		Account = aString;
	}
	public void setPassword(String newP) {
		Password = newP;
	}
	public void setName(String newN) {
		Name = newN;
	}
	public void setSingTime(int newS) {
		SingTime = newS;
	}
	public void getExperienceValue(int newE) {
		ExperienceValue = newE;
	}
	/*
	public void setPostList(ArrayList<Integer> newL) {
		PostsList = newL;
	}*/
} 