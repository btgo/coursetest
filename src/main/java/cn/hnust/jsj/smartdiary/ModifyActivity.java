package cn.hnust.jsj.smartdiary;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyActivity extends Activity{
	
	private EditText et_title;
	private EditText et_content;
	private DBAdapter db;
	private int _id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify);
		
		db = new DBAdapter(this);
		et_title = (EditText)findViewById(R.id.editTextTitle);
	    et_content = (EditText)findViewById(R.id.editTextContent);
	        
		String str_id = getIntent().getExtras().getString("modify_id");
		_id = Integer.parseInt(str_id);
		
		//Toast toast = Toast.makeText(this, str_id, Toast.LENGTH_SHORT);  
        //toast.show(); 
        
        
        //---get a diary---
        db.open();
        Cursor c = db.getDiary(_id);
        if (c.moveToFirst()){        
            //DisplayDiary(c);
        	et_title.setText(c.getString(2).toString());
        	et_content.setText(c.getString(3).toString());
        }else
            Toast.makeText(this, "No diary found", Toast.LENGTH_LONG).show();
        db.close();     
        
	}
	
	
	public void DisplayDiary(Cursor c)
    {
        Toast.makeText(this,
                "id: " + c.getString(0) + "\n" +
                c.getString(1) + "\n" +
                c.getString(2)+ "\n" +
                c.getString(3),
                Toast.LENGTH_LONG).show();
    }
	
	public void onButtonUpdate(View v){
		
        //---update diary---
        db.open();
        if (db.updateDiary(_id, et_title.getText().toString(), et_content.getText().toString()))
            Toast.makeText(this, "修改成功", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "修改错误", Toast.LENGTH_LONG).show();
        db.close();
        
	}
	
	public void onButtonDelete(View v){
		
        //---delete a diary---
        db.open();
        if (db.deleteDiary(_id))
            Toast.makeText(this, "删除成功", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "删除错误", Toast.LENGTH_LONG).show();
        db.close();
        
		finish();
    }
	
	public void onButtonCancel(View v){
    	finish();
    }
}
