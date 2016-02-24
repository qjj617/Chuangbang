package chuangbang.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * 收到的BP
 * 
 * @author Administrator
 * 
 */

public class ReceivedBPActivity extends Activity {
	private ImageView imag_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.received_bp);

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
