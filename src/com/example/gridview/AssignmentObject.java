package com.example.gridview;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

public class AssignmentObject {
	
	String name;
	String Description;
	String lastdate;
	long _id;
	
	AssignmentObject(String name,String description,String mm,String dd,String yyyy,long id)
	{
		this.name=name;
		this.Description=description;
		this._id=id;
		
		String dat=dd+"/"+mm+"/"+yyyy;
		this.lastdate=dat;
		
		/*try {
			Date date=format.parse(dat);
			this.lastdate=date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
	
	AssignmentObject(String name,String description,String date,long id)
	{
		this.name=name;
		this.Description=description;
		this.lastdate=date;
		this._id=id;
	}

}
