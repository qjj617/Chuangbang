package chuangbang.fragment;

import chuangbang.activity.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainFragment extends Fragment implements OnPageChangeListener,
		OnCheckedChangeListener {
	private RadioGroup rgMeun;
	private ViewPager viewPager;
	private FragmentPagerAdapter adapter;
	private Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.main_fragment, null);
		context = getActivity();
		viewPager = (ViewPager) view.findViewById(R.id.viewpager);
		adapter = new InnerPagerAdapter(getActivity()
				.getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		rgMeun = (RadioGroup) view.findViewById(R.id.rg_group);
		//radioGroup注册监听
		rgMeun.setOnCheckedChangeListener(this);
		viewPager.setOnPageChangeListener(this);
		return view;
	}

	/**
	 * 
	 * @author Administrator
	 * 
	 */
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
				fragment = new BusinessCircleFragment();
				break;

			case 1:
				fragment = new FoundFragment();
				break;
			case 2:
				fragment = new ServerFragment();
				break;
			case 3:
				fragment = new ChatFragment();
				break;
			case 4:
				fragment = new MineFragment();
				break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 5;
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
	public void onPageSelected(int position) {
		switch (position) {
		case 0:
			rgMeun.check(R.id.rb_business_circle);
			break;

		case 1:
			rgMeun.check(R.id.rb_found);
			break;
		case 2:
			rgMeun.check(R.id.rb_server);
			break;
		case 3:
			rgMeun.check(R.id.rb_chat);
			break;
		case 4:
			rgMeun.check(R.id.rb_mine);
			break;
		}

	}

	/**
	 * 点击按钮滑动页面
	 */
	@Override
	public void onCheckedChanged(RadioGroup arg0, int id) {
		switch (id) {
		case R.id.rb_business_circle:
			viewPager.setCurrentItem(0);
			break;
		case R.id.rb_found:
			viewPager.setCurrentItem(1);
			break;
		case R.id.rb_server:
			viewPager.setCurrentItem(2);
			break;
		case R.id.rb_chat:
			viewPager.setCurrentItem(3);
			break;
		case R.id.rb_mine:
			viewPager.setCurrentItem(4);
			break;
		}

	}
}
