package com.example.gridview;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import com.example.gridview.data.AssignmentDB;


import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Instances;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Assignment extends ActionBarActivity {
	public static final String[] EVENT_PROJECTION = new String[] {
	    Calendars._ID,                           // 0
	    Calendars.ACCOUNT_NAME,                  // 1
	    Calendars.CALENDAR_DISPLAY_NAME,         // 2
	    Calendars.OWNER_ACCOUNT                  // 3
	};
	Long event_id;
	  
	// The indices for the projection array above.
	private static final int PROJECTION_ID_INDEX = 0;
	private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
	private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
	private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
	
	
	
	ListView lv;
	ArrayList<AssignmentObject> list;
	CustomArrayAdapter ad;
	AssignmentDB mhelper=new AssignmentDB(this);
	public static SQLiteDatabase database;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.assignment);
		
		lv=(ListView) findViewById(R.id.lvAssignment);
		list=new ArrayList<AssignmentObject>();
		
//		String[] assign={"no 1","no 2","no 3"};
//		for(String s:assign)
//		{
//			if(s.equals("no 1"))
//			{
//				AssignmentObject ob=new AssignmentObject("no 1","this is 1","10","21","2014");
//				list.add(ob);
//			}
//			if(s.equals("no 2"))
//			{
//				AssignmentObject ob=new AssignmentObject("no 2","this is 2","11","02","1998");
//				list.add(ob);
//			}
//		}
		ad=new CustomArrayAdapter(this,0,list);
		lv.setAdapter(ad);
		readFromDatabase();
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i=new Intent();
				AssignmentObject t=(AssignmentObject)parent.getItemAtPosition(position);
				i.putExtra("AssignmentName",t.name);
				i.putExtra("AssignmentDescription", t.Description);
				i.putExtra("AssignmentDate",t.lastdate);
				i.putExtra("ID",t._id);
				i.setClass(getApplicationContext(), DetailedAssignmentActivity.class);
				Log.i("item","set class");
				startActivity(i);
				Log.i("item","activity started");
				
			}		
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.assignment_menu, menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if(id==R.id.add)
		{
			AlertDialog.Builder b=new AlertDialog.Builder(this);
			b.setTitle("Add New Assignment:");
			Log.i("inside ","builder");
			LayoutInflater inflator=(LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View output=inflator.inflate(R.layout.add_new_assignment,null);
			Log.i("view ","inflated");
			b.setView(output);
			Log.i("view ","set");
			b.setPositiveButton("add",new OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				
					EditText edName=(EditText) output.findViewById(R.id.etAssignmentName);
					EditText edDesc=(EditText) output.findViewById(R.id.etAssignmentDescription);
					EditText edMonth=(EditText) output.findViewById(R.id.etMonth);
					EditText edDay=(EditText) output.findViewById(R.id.etDate);
					EditText edYear=(EditText) output.findViewById(R.id.etYear);
					String date=edDay.getText().toString();
					String month=edMonth.getText().toString();
					String year=edYear.getText().toString();
					
					//adding assignment to calender
					Cursor cur = null;
					ContentResolver cr = getContentResolver();
					Uri uri = Calendars.CONTENT_URI;   
					String selection = "((" + Calendars.ACCOUNT_NAME + " = ?) AND (" 
					                        + Calendars.ACCOUNT_TYPE + " = ?) AND ("
					                        + Calendars.OWNER_ACCOUNT + " = ?))";
					String[] selectionArgs = new String[] {"archit2495@gmail.com", "com.google",
					        "archit2495@gmail.com"}; 
					cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
					long startMillis = 0; 
					long endMillis = 0;     
					Calendar beginTime = Calendar.getInstance();
					beginTime.set(Integer.parseInt(edYear.getText().toString()),Integer.parseInt(edMonth.getText().toString())-1 , Integer.parseInt(edDay.getText().toString()),0,0);//can enter time as well
					startMillis = beginTime.getTimeInMillis();
					Calendar endTime = Calendar.getInstance();
					endTime.set(Integer.parseInt(edYear.getText().toString()),Integer.parseInt(edMonth.getText().toString())-1 , Integer.parseInt(edDay.getText().toString()),0,1);
					endMillis = endTime.getTimeInMillis();
					long calID = 0;
					while (cur.moveToNext()) {
					     calID = 0;
					    String displayName = null;
					    String accountName = null;
					    String ownerName = null;
					      
					    // Get the field values
					    calID = cur.getLong(PROJECTION_ID_INDEX);
					    displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
					    accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
					    ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);
					              
					    // Do something with the values...
					   
					}
					ContentResolver cr1 = getContentResolver();
					ContentValues values = new ContentValues();
					values.put(Events.DTSTART, startMillis);
					values.put(Events.DTEND, endMillis);
					values.put(Events.TITLE, edName.getText().toString());
					values.put(Events.DESCRIPTION,edDesc.getText().toString());
					values.put(Events.CALENDAR_ID,calID );
					values.put(Events.EVENT_TIMEZONE, "India");
					Uri uri1 = cr1.insert(Events.CONTENT_URI, values);
					 event_id=Long.parseLong(uri1.getLastPathSegment());
					
					//adding assignment to database 
					database=mhelper.getWritableDatabase();
					ContentValues cv=new ContentValues();
//					cv.put(mhelper.ASSIGNMENT_NAME_COLUMN,edName.getText().toString());
//					cv.put(mhelper.ASSIGNMENT_DESCRIPTION_COLUMN,edDesc.getText().toString());
//					cv.put(mhelper.ASSIGNMENT_MONTH_COLUMN,Integer.parseInt(month));
//					cv.put(mhelper.ASSIGNMENT_DATE_COLUMN,Integer.parseInt(date));
//					cv.put(mhelper.ASSIGNMENT_YEAR_COLUMN,Integer.parseInt(year));
					cv.put(mhelper.ASSIGNMENT_EVENT_ID_COLUMN,event_id);
					database.insert(AssignmentDB.ASSIGNMENT_TABLE_NAME, null,cv);
					cv.clear();
					Log.i("event_id",""+event_id);	
					readFromDatabase();
				}				
			});
			b.create().show();
		}

		return super.onOptionsItemSelected(item);
	}
	void readFromDatabase()
	{
	
		list.clear();
		SQLiteDatabase db=mhelper.getReadableDatabase();
		//String columns[]={mhelper.ASSIGNMENT_NAME_COLUMN,mhelper.ASSIGNMENT_DESCRIPTION_COLUMN,mhelper.ASSIGNMENT_DATE_COLUMN,mhelper.ASSIGNMENT_MONTH_COLUMN,mhelper.ASSIGNMENT_YEAR_COLUMN};
		String columns[]={mhelper.ASSIGNMENT_EVENT_ID_COLUMN};
		Cursor c=db.query(true,mhelper.ASSIGNMENT_TABLE_NAME,columns,null,null,null,null,null,null);
		while(c.moveToNext())
		{
//			String name=c.getString(c.getColumnIndexOrThrow(mhelper.ASSIGNMENT_NAME_COLUMN));
//			String des=c.getString(c.getColumnIndexOrThrow(mhelper.ASSIGNMENT_DESCRIPTION_COLUMN));
//			String date=c.getString(c.getColumnIndexOrThrow(mhelper.ASSIGNMENT_DATE_COLUMN));
//			String month=c.getString(c.getColumnIndexOrThrow(mhelper.ASSIGNMENT_MONTH_COLUMN));
//			String year=c.getString(c.getColumnIndexOrThrow(mhelper.ASSIGNMENT_YEAR_COLUMN));
			
			long event_id=c.getLong(c.getColumnIndexOrThrow(mhelper.ASSIGNMENT_EVENT_ID_COLUMN));
			
			Calendar beginTime = Calendar.getInstance();
			beginTime.set(2014, 8, 23, 8, 0);
			long startMillis = beginTime.getTimeInMillis();
			Calendar endTime = Calendar.getInstance();
			endTime.set(2025, 8, 24, 8, 0);
			long endMillis = endTime.getTimeInMillis();
			  
			 final String[] INSTANCE_PROJECTION = new String[] {
			    Instances.EVENT_ID,      // 0
			    Instances.BEGIN,         // 1
			    Instances.TITLE,		 // 2	
			    Instances.DESCRIPTION,    // 3
			    Instances.END			 // 4	
			  };
			  
			// The indices for the projection array above.
			 final int PROJECTION_BEGIN_TIME = 1;
			 final int PROJECTION_TITLE_INDEX = 2;
			 final int PROJECTION_DESCRIPTION_INDEX=3;
			 final int PROJECTION_END_TIME=4;
		
			Cursor cur = null;
			ContentResolver cr = getContentResolver();

			// The ID of the recurring event whose instances you are searching
			// for in the Instances table
			String selection = Instances.EVENT_ID + " = ?";
			String[] selectionArgs = new String[] {""+event_id};

			// Construct the query with the desired date range.
			Uri.Builder builder = Instances.CONTENT_URI.buildUpon();
			ContentUris.appendId(builder, startMillis);
			ContentUris.appendId(builder, endMillis);

			// Submit the query
			cur =  cr.query(builder.build(), 
			    INSTANCE_PROJECTION, 
			    selection, 
			    selectionArgs, 
			    null);
			   
			while (cur.moveToNext()) {
			    String title = null;
			    String description = null;
			    
			    long endVal = 0;
			    long beginVal = 0;    
			    
			    // Get the field values
			    
			    title = cur.getString(PROJECTION_TITLE_INDEX);
			    description=cur.getString(PROJECTION_DESCRIPTION_INDEX);
			    beginVal=cur.getLong(PROJECTION_BEGIN_TIME);
			    endVal=cur.getLong(PROJECTION_END_TIME);
			    long idpas=cur.getLong(PROJECTION_ID_INDEX);
			    
			    Date updatedate = new Date(beginVal);
			    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			    String dat= format.format(updatedate);
			    list.add(new AssignmentObject(title,description,dat,idpas));
			    Log.i("idPAS",""+idpas);
				ad.notifyDataSetChanged();
			}
		}
		ad.notifyDataSetChanged();
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		readFromDatabase();
	}
}
