package chuangbang.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 * 进驻孵化器界面
 * 
 * @author Administrator
 * 
 */
public class IncubationIncubatorActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.incubation_incubator);
	

	}
  /**
   * 返回到进驻孵化器Fragment
   * @param view
   * 
   */
	public void back(View view){
		finish();
	}

	
}
