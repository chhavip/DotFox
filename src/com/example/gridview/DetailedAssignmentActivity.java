package com.example.gridview;

import com.example.gridview.data.AssignmentDB;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract.Events;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DetailedAssignmentActivity extends ActionBarActivity implements OnClickListener {

	EditText name,description,date;
	Button b1,b2;
	SQLiteDatabase database;
	AssignmentDB mhelper=new AssignmentDB(this);
	String assignmentName;
	Long id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailed_assignment_activity);
		
		 name=(EditText) findViewById(R.id.editText1);
		 description=(EditText) findViewById(R.id.editText2);
		 date=(EditText) findViewById(R.id.editText3);
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		
		
		Intent i=getIntent();
		Bundle b=i.getExtras();
		
		assignmentName=b.getString("AssignmentName");
		String assignmentDescription=b.getString("AssignmentDescription");
		String assignmentDate=b.getString("AssignmentDate");
		id=b.getLong("ID");
		Log.i("ID:",""+id);
		
		name.setText(assignmentName);
		description.setText(assignmentDescription);
		date.setText(assignmentDate);
		
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.button1:
			String columns[]={mhelper.ASSIGNMENT_EVENT_ID_COLUMN};
//			String selection = mhelper.ASSIGNMENT_NAME_COLUMN+ "=" +assignmentName;
//			
//			database=mhelper.getReadableDatabase();
//			//Cursor c=database.query(true,mhelper.ASSIGNMENT_TABLE_NAME,
//					                columns, selection,null,null,null,null,null,null);
//			event_id=(long) c.getInt(c.getColumnIndexOrThrow(mhelper.ASSIGNMENT_EVENT_ID_COLUMN));
//			
//			ContentResolver cr=getContentResolver();
//			ContentValues values=new ContentValues();
//			Uri updateuri=null;
//			values.put(Events.TITLE, name.getText().toString());
//			
//			values.put(Events.DESCRIPTION, description.getText().toString());
//			//values.put(Events., name.getText().toString());

			
			
//						
//			ContentValues cv=new ContentValues();
//			cv.put(mhelper.ASSIGNMENT_NAME_COLUMN, name.getText().toString());
//			cv.put(mhelper.ASSIGNMENT_DESCRIPTION_COLUMN,description.getText().toString());
//			cv.put(mhelper.ASSIGNMENT_DATE_COLUMN,date.getText().toString());
//			String[] args={assignmentName};
//			database=mhelper.getWritableDatabase();
//			database.update(mhelper.ASSIGNMENT_TABLE_NAME, cv, mhelper.ASSIGNMENT_NAME_COLUMN+"=?", args);
//			database.close();
//			
			finish();
			
			
			break;
		case R.id.button2:
			
			database=mhelper.getWritableDatabase();
			String[] clause={""+id};
			database.delete(mhelper.ASSIGNMENT_TABLE_NAME, mhelper.ASSIGNMENT_EVENT_ID_COLUMN+" = ?",clause);
			database.close();
			
			ContentResolver cr = getContentResolver();
			ContentValues values = new ContentValues();
			Uri deleteUri = null;
			deleteUri = ContentUris.withAppendedId(Events.CONTENT_URI, id);
			int rows = getContentResolver().delete(deleteUri, null, null);
			
			finish();
			
				
			break;
		
		
		}
		
	}

}
