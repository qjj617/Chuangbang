package chuangbang.fragment.server;

import chuangbang.activity.ApplicationDevelopmentActivity;
import chuangbang.activity.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * 申请开发Fragment
 * 
 * @author Administrator
 * 
 */
public class TechDevelopment extends Fragment implements OnClickListener {
	// 申请开发
	private Button btn_application_development;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tech_development, null);
		btn_application_development = (Button) view
				.findViewById(R.id.btn_application_development);
		// 设置监听器
		btn_application_development.setOnClickListener(this);
		return view;
	}

	/**
	 * 点击进入申请开发界面
	 */
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getActivity(),
				ApplicationDevelopmentActivity.class);
		startActivity(intent);

	}

}
