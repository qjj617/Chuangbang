package chuangbang.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class AccountingDoAccountAcitivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accounting_do_account);

	}

	/**
	 * 返回到进驻孵化器Fragment
	 * 
	 * @param view
	 */
	public void back(View view) {
		finish();
	}
}
