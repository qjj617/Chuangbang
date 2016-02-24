package chuangbang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 我的简历界面
 * 
 * @author Administrator
 * 
 */
public class MyResumeActivity extends Activity implements OnClickListener {
	// 教育背景、工作经历、我的优势按钮
	private Button bt_resume_add_education, bt_resume_add_work,
			bt_resume_add_goodnes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_resume);
		setupViews();
		setupListeners();
	}

	// 获取控件属性值
	private void setupViews() {
		bt_resume_add_education = (Button) findViewById(R.id.bt_resume_add_education);
		bt_resume_add_work = (Button) findViewById(R.id.bt_resume_add_work);
		bt_resume_add_goodnes = (Button) findViewById(R.id.bt_resume_add_goodnes);

	}

	// 为按钮添加监听器
	private void setupListeners() {
		bt_resume_add_education.setOnClickListener(this);
		bt_resume_add_goodnes.setOnClickListener(this);
		bt_resume_add_work.setOnClickListener(this);

	}

	// 跳转到教育背景、工作经历、我的优势
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_resume_add_education:
			Intent intent = new Intent(getApplicationContext(),
					EducationalBackgroundAcitivity.class);
			startActivity(intent);
			break;
		case R.id.bt_resume_add_work:
			Intent i = new Intent(getApplicationContext(),
					WorkExperienceActivity.class);
			startActivity(i);
			break;
		case R.id.bt_resume_add_goodnes:
			Intent in = new Intent(getApplicationContext(),
					MyAdvantageActivity.class);
			startActivity(in);
			break;

		}

	}

	/**
	 * 返回到我的Fragment
	 * 
	 * @param v
	 */
	public void back(View v) {
		finish();
	}
}
