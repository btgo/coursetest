package cn.hnust.jsj.smartdiary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class AllActivity extends ListActivity{ 
    
	private DBAdapter db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all);
		        
		db = new DBAdapter(this);
		
        setListAdapter(new SimpleAdapter(this,   
                getData(" "),   
                android.R.layout.simple_list_item_2,   
                new String[]{"title", "time"},   
                new int[]{android.R.id.text1, android.R.id.text2}));
        
	}
	
	/** 
     * SimpleAdapter List
     * @param style 
     * @return 
     */  
    private List<Map<String, String>> getData(String style) {  
        List<Map<String, String>> listData = new ArrayList<Map<String, String>>();  
        
        //---get all diary---
        db.open();
        Cursor c = db.getAllContacts();
        if (c.moveToFirst())
        {
            do {
                //DisplayDiary(c);
                Map<String, String> map = new HashMap<String, String>();  
                map.put("title", c.getString(2));  
                map.put("time", c.getString(1));  
                map.put("_id", c.getString(0));
                listData.add(map);
            } while (c.moveToNext());
        }
        db.close();  
 
        return listData;  
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
    
    /** 
     * ListView
     */  
    protected void onListItemClick(ListView listView, View v, int position, long id) {  
        Map map = (Map)listView.getItemAtPosition(position);
        String str_id = map.get("_id").toString(); 
        //Toast toast = Toast.makeText(this, "id="+str_id+" is selected.", Toast.LENGTH_SHORT);  
        //toast.show();  
        
    	Intent i = new Intent();
    	i.setClass(this, ModifyActivity.class);
    	i.putExtra("modify_id", str_id);
    	startActivity(i);
    }  
	
    
}
