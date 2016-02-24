package chuangbang.activity;

import chuangbang.utils.Final;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class SetMineEmail extends Activity implements Final{
	private EditText etEmail;
	
	private String userEmail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_email);
		etEmail=(EditText)findViewById(R.id.et_email);
		
		
		
		
	}
	
	public void doClick(View v){
		Intent intent=null;
		switch (v.getId()) {
		case R.id.bt_save:
			userEmail=etEmail.getText().toString();
		/*	intent=new Intent(INTENT_ACTION_USEREMAIL);
			intent.putExtra(INTENT_EXTRA_USEREMAIL,userEmail);*/
			sendBroadcast(intent);
			Log.i("Tag","intent="+intent.toString()+"值="+userEmail);
			//finish();
			break;

		case R.id.bt_back:
			finish();
			break;
		}
	}
}
