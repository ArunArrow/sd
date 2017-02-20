package com.example.sd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends Activity {
	EditText etmsg;
	EditText etloc;
    @SuppressLint("SdCardPath") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String path = getPreferences(MODE_PRIVATE).getString("fpath", "/sdcard/My_file1");
        etloc = (EditText) findViewById(R.id.et_loc);
        etloc.setText(path);
        etmsg = (EditText) findViewById(R.id.et_msg);
        OnClickListener saveClickListener = new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				File file = new File(etloc.getText().toString());
				FileWriter writer=null;
				try{
					writer = new FileWriter(file);
					writer.write(etmsg.getText().toString());
					writer.close();
					SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
					editor.putString("fpath", file.getPath());
					editor.commit();
					Toast.makeText(getBaseContext(),"Message saved" , Toast.LENGTH_SHORT).show();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
        	
        };
        OnClickListener readClickListener = new OnClickListener() {			
			@Override
			public void onClick(View v) {
				File file = new File(etloc.getText().toString());
								
				String strLine="";
				StringBuilder text = new StringBuilder();
				try {
					FileReader fReader = new FileReader(file);
					BufferedReader bReader = new BufferedReader(fReader);
					
					/** Reading the contents of the file , line by line */
					while( (strLine=bReader.readLine()) != null  ){
						text.append(strLine+"\n");						
					}
					
					Toast.makeText(getBaseContext(), "Successfully loaded", Toast.LENGTH_SHORT).show();
					
				} catch (IOException e) {
					e.printStackTrace();
				}etmsg.setText(text);
			}
		};
		Button btnSave = (Button) findViewById(R.id.btn_save);
		btnSave.setOnClickListener(saveClickListener);
		Button btnRead = (Button) findViewById(R.id.btn_read);
		 btnRead.setOnClickListener(readClickListener);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
