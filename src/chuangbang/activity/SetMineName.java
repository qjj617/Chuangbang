package chuangbang.activity;

import chuangbang.utils.Final;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SetMineName extends Activity implements Final{
	private EditText etName;
	
	
	private String userName="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_name);
		
		etName=(EditText)findViewById(R.id.et_name);
	}
	
	public void doClick(View v){
		Intent intentAction=null;
		switch (v.getId()) {
		case R.id.bt_save:
			userName=etName.getText().toString();
			/*intentAction=new Intent(INTENT_ACTION_USERNAME);
			intentAction.putExtra(INTENT_EXTRA_USERNAME,userName);*/
			sendBroadcast(intentAction);
			break;

		case R.id.bt_back:
			finish();
			break;
		}
	}
	
}
