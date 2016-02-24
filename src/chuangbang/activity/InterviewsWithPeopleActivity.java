package chuangbang.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * 约谈的人界面
 * 
 * @author Administrator
 * 
 */
public class InterviewsWithPeopleActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.interviews_with_people);
	}

	/**
	 * 返回到我的界面
	 * 
	 * @param view
	 */
	public void back(View view) {
		finish();
	}

}
