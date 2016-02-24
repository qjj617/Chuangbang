package chuangbang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 工作经验界面
 * 
 * @author Administrator
 * 
 */
public class WorkExperienceActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.work_experience);
	}

	/**
	 * 返回到我的简历界面
	 * 
	 * @param v
	 */
	public void back(View v) {
		Intent intent = new Intent(WorkExperienceActivity.this,
				MyResumeActivity.class);
		startActivity(intent);
	}
}
