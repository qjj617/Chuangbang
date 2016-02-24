package chuangbang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 项目历程界面
 * 
 * @author Administrator
 * 
 */
public class ProjectCourseActivity extends Activity {
	private Button btn_add_project_course;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_course);
		btn_add_project_course = (Button) findViewById(R.id.btn_add_project_course);
		btn_add_project_course.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 跳转到添加项目历程界面
				Intent intent = new Intent(ProjectCourseActivity.this,
						AddProjectCourseActivity.class);
				startActivity(intent);
			}
		});

	}

	/**
	 * 返回到创建项目界面
	 * 
	 * @param v
	 */
	public void back(View v) {
		Intent intent = new Intent(ProjectCourseActivity.this,
				NewMyProjectActivity.class);
		startActivity(intent);
	}

}
