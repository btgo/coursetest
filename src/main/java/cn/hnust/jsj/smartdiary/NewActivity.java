package cn.hnust.jsj.smartdiary;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewActivity extends Activity{
	
	private EditText et_title;
	private EditText et_content;
	private DBAdapter db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new);

        db = new DBAdapter(this);
   
        et_title = (EditText)findViewById(R.id.editTextTitle);
        et_content = (EditText)findViewById(R.id.editTextContent);

    }
    
	
    public void onButtonSave(View v){
    	//---add a diary---
        db.open();
        //long id = db.insertContact("aaaaa", "aaaaaaaaaa");
        //id = db.insertContact("bbbbb", "bbbbbbbbbb");
        //id = db.insertContact("ccccc", "cccccccccc");
        String str_title = et_title.getText().toString();
        String str_content = et_content.getText().toString();
        long id = db.insertDiary(str_title, str_content);
        Toast.makeText(this, "插入成功  id="+id, Toast.LENGTH_SHORT).show();
        db.close();
    }
    
    public void onButtonCancel(View v){
    	finish();
    }
	
}
