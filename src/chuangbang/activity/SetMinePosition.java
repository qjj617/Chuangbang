package chuangbang.activity;

import chuangbang.utils.Final;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SetMinePosition extends Activity implements Final{
	private EditText etPosition;
	private String userPosition;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_position);
		etPosition=(EditText)findViewById(R.id.et_position);
	}
	
	public void doClick(View v){
		Intent intent=null;
		switch (v.getId()) {
		case R.id.bt_save:
			userPosition=etPosition.getText().toString();
	/*		intent=new Intent(INTENT_ACTION_USERPOSITION);
			intent.putExtra(INTENT_EXTRA_USERPOSITION, userPosition);*/
			sendBroadcast(intent);
			break;

		case R.id.bt_back:
			finish();
			break;
		}
	}
}
