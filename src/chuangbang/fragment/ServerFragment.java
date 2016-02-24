package chuangbang.fragment;

import chuangbang.activity.R;

import chuangbang.fragment.found.SeeProjectFragment;
import chuangbang.fragment.found.Touzi;
import chuangbang.fragment.server.IncubatorFragment;
import chuangbang.fragment.server.PartnerFragment;
import chuangbang.fragment.server.TechDevelopment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ServerFragment extends Fragment implements
		OnCheckedChangeListener, OnPageChangeListener,OnClickListener {
	private ViewPager viewPager;
	private FragmentPagerAdapter adapter;
	private RadioGroup rgMenu;
	// 搜索合伙人ImageButton
	private ImageButton imag_bt_search;
	// 添加
	private ImageButton imag_bt_add;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.server_fragment, null);
		viewPager = (ViewPager) view.findViewById(R.id.vp_server);
		rgMenu = (RadioGroup) view.findViewById(R.id.rg_group);
		imag_bt_add = (ImageButton) view.findViewById(R.id.imag_bt_add);
		imag_bt_search = (ImageButton) view.findViewById(R.id.imag_bt_search);
		adapter = new InnerPagerAdapter(getChildFragmentManager());
		viewPager.setAdapter(adapter);
		// 添加监听器
		rgMenu.setOnCheckedChangeListener(this);
		viewPager.setOnPageChangeListener(this);
		imag_bt_add.setOnClickListener(this);
		imag_bt_search.setOnClickListener(this);
		return view;
	}

	private class InnerPagerAdapter extends FragmentPagerAdapter {

		public InnerPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			Fragment fragment = null;
			switch (arg0) {
			case 0:
				fragment = new PartnerFragment();
				break;

			case 1:
				fragment = new TechDevelopment();
				break;
			case 2:
				fragment = new IncubatorFragment();
				break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3;
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		switch (arg1) {
		case 0:
		case R.id.rb_partner:
			viewPager.setCurrentItem(0);
			break;

		case R.id.rb_tech_development:
			viewPager.setCurrentItem(1);
			break;
		case R.id.rb_incubator:
			viewPager.setCurrentItem(2);
			break;
		}

	}
    /**
     * 点击进入搜索、添加界面
     */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
