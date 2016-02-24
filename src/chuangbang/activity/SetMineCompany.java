package chuangbang.activity;

import chuangbang.utils.Final;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SetMineCompany extends Activity implements Final{
	private EditText etCompany;
	
	private String userCompany;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_company);
		
		etCompany=(EditText)findViewById(R.id.et_company);
	}
	
	
	public void doClick(View v){
		Intent intent=null;
		switch (v.getId()) {
		case R.id.bt_save:
			userCompany=etCompany.getText().toString();
		/*	intent=new Intent(INTENT_ACTION_USERCOMPANY);
			intent.putExtra(INTENT_EXTRA_USERCOMPANY,userCompany );*/
			sendBroadcast(intent);
			break;
			
		case R.id.bt_back:
			finish();
			break;
		}
	}
}
