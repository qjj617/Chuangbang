package chuangbang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * 创建我的项目界面
 * 
 * @author Administrator
 * 
 */
public class NewMyProjectActivity extends Activity implements OnClickListener {
	// 下一步TextView
	private TextView tv_set_next_step;
	// 项目历程、项目领域TableRow
	private TableRow tr_set_project_course, tr_set_project_field;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_project);
		tv_set_next_step = (TextView) findViewById(R.id.tv_set_next_step);
		tr_set_project_course = (TableRow) findViewById(R.id.tr_set_project_course);
		tr_set_project_field = (TableRow) findViewById(R.id.tr_set_project_field);
		// 设置监听器
		setupListeners();
	}

	// 为子控件设置监听器
	private void setupListeners() {
		// 下一步按钮
		tv_set_next_step.setOnClickListener(this);
		tr_set_project_course.setOnClickListener(this);
		tr_set_project_field.setOnClickListener(this);

	}

	/**
	 * 返回到我的项目界面
	 * 
	 * @param v
	 */
	public void back(View v) {
		Intent intent = new Intent(getApplicationContext(),
				MyProjectActivity.class);
		startActivity(intent);
	}

	// 触发事件，进行跳转到相应界面
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 进入项目历程界面
		case R.id.tr_set_project_course:
			Intent intent = new Intent(NewMyProjectActivity.this,
					ProjectCourseActivity.class);
			startActivity(intent);
			break;
		// 进入项目领域界面
		case R.id.tr_set_project_field:
			Intent i = new Intent(NewMyProjectActivity.this,
					ProjectFieldsActivity.class);
			startActivity(i);
			break;
		// 进入融资信息界面
		case R.id.tv_set_next_step:
			Intent in = new Intent(NewMyProjectActivity.this,
					FinancingInformationActivity.class);
			startActivity(in);
			break;
		}

	}

}
