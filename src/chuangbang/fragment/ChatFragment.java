package chuangbang.fragment;

import chuangbang.activity.AddFriendsActivity;
import chuangbang.activity.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * 聊天Fragment
 * 
 * @author Administrator
 * 
 */
public class ChatFragment extends Fragment {
	// 添加好友ImagButton
	private ImageButton ib_add_new_friends;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.chat_fragment, null);
		ib_add_new_friends = (ImageButton) view
				.findViewById(R.id.ib_add_new_friends);
		// 设置监听器跳转到添加好友界面
		ib_add_new_friends.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						AddFriendsActivity.class);
				startActivity(intent);

			}
		});
		return view;
	}

}
