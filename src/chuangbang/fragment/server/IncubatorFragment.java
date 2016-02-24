package chuangbang.fragment.server;

import chuangbang.activity.AccountingDoAccountAcitivity;
import chuangbang.activity.IncubationIncubatorActivity;
import chuangbang.activity.IntellectualPropertyRightActivity;
import chuangbang.activity.LegalAdviceActivity;
import chuangbang.activity.R;
import chuangbang.activity.RegisteredCompanyAcitivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
/**
 * 进驻孵化器Fragment
 * @author Administrator
 *
 */
public class IncubatorFragment extends Fragment implements OnClickListener {
	// 进驻孵化器
	private Button btn__incubation_incubator;
	// 注册公司
	private Button btn_registered_company;
	// 会计做账
	private Button btn_accounting_do_account;
	// 法律咨询
	private Button btn_legal_advice;
	// 知识产权
	private Button btn_intellectual_property_right;
   
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.incubator, null);

		btn__incubation_incubator = (Button) view
				.findViewById(R.id.btn_incubation_incubator);
		btn_registered_company = (Button) view
				.findViewById(R.id.btn_registered_company);
		btn_accounting_do_account = (Button) view
				.findViewById(R.id.btn_accounting_do_account);
		btn_legal_advice = (Button) view.findViewById(R.id.btn_legal_advice);
		btn_intellectual_property_right = (Button) view
				.findViewById(R.id.btn_intellectual_property_right);

		// 设置监听器
		btn__incubation_incubator.setOnClickListener(this);
		btn_registered_company.setOnClickListener(this);
		btn_accounting_do_account.setOnClickListener(this);
		btn_legal_advice.setOnClickListener(this);
		btn_intellectual_property_right.setOnClickListener(this);
		return view;
	}

	/**
	 * 点击进入进驻孵化器、注册公司、会计做账、法律咨询和知识产权界面
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_incubation_incubator:
			Intent intent = new Intent(getActivity(),
					IncubationIncubatorActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_registered_company:
			Intent intent2 = new Intent(getActivity(),
					RegisteredCompanyAcitivity.class);
			startActivity(intent2);
			break;
		case R.id.btn_accounting_do_account:
			Intent intent3 = new Intent(getActivity(),
					AccountingDoAccountAcitivity.class);
			startActivity(intent3);
			break;
		case R.id.btn_legal_advice:
			Intent intent4 = new Intent(getActivity(),
					LegalAdviceActivity.class);
			startActivity(intent4);
			break;
		case R.id.btn_intellectual_property_right:
			Intent intent5 = new Intent(getActivity(),
					IntellectualPropertyRightActivity.class);
			startActivity(intent5);
			break;
		}

	}

}
