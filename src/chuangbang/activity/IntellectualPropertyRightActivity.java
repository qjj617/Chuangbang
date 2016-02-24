package chuangbang.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 * 知识产权界面
 * 
 * @author Administrator
 * 
 */
public class IntellectualPropertyRightActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intellectual_property_right);

	}

	/**
	 * 回到进驻孵化器Fragment
	 * 
	 * @param v
	 */
	public void back(View v) {
		finish();

	}
}
