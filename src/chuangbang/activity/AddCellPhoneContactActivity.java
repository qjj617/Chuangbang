package chuangbang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 手机联系人界面
 * 
 * @author Administrator
 * 
 */
public class AddCellPhoneContactActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_cell_phone_contact);
	}

	/**
	 * 返回到添加 好友界面
	 * 
	 * @param v
	 */
	public void back(View v) {
		Intent intent = new Intent(getApplicationContext(),
				AddFriendsActivity.class);
		startActivity(intent);
	}
}
