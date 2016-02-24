package chuangbang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 添加好友界面
 * 
 * @author Administrator
 * 
 */
public class AddFriendsActivity extends Activity implements OnClickListener {
	// 点击添加手机联系人TextView,点击取消TextView
	private TextView tv_set_add_friends, tv_cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_friends);
		// 获取控件属性值
		setupViews();
		// 设置监听器
		setListeners();
	}

	private void setupViews() {
		tv_set_add_friends = (TextView) findViewById(R.id.tv_set_add_friends);
		tv_cancel = (TextView) findViewById(R.id.tv_cancel);

	}

	private void setListeners() {
		tv_cancel.setOnClickListener(this);
		tv_set_add_friends.setOnClickListener(this);

	}

	// 分别点击进入手机联系人界面、返回到聊天Fragment
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_set_add_friends:
			Intent intent = new Intent(getApplicationContext(),
					AddCellPhoneContactActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_cancel:
			finish();
			break;

		}

	}
}
