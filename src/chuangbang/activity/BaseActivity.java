package chuangbang.activity;

import com.easemob.chat.EMChatManager;

import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {
	@Override
	protected void onResume() {
		//onresume时，取消notification显示
		EMChatManager.getInstance().activityResumed();
		super.onResume();
	}
}
