package chuangbang.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import chuangbang.activity.AddFriendsActivity;
import chuangbang.activity.MenuDialogActivity;
import chuangbang.activity.MyProjectActivity;
import chuangbang.activity.MyResumeActivity;
import chuangbang.activity.MyStatusAcitivity;
import chuangbang.activity.R;
import chuangbang.activity.SettingMineActivity;
import chuangbang.activity.VIPActivity;
import chuangbang.entity.ChuangUser;
import chuangbang.utils.Final;
import chuangbang.view.RoundRectImageView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MineFragment extends Fragment implements OnClickListener, Final {

	private View llMyProject, llSetMine, llMyStatus, llMineResume, llVIP;
	private RoundRectImageView roundImage;
	private String avatarPath;
	private byte[] mContent;
	private Bitmap image;
	private BroadcastReceiver receiver;
	File avaterFile;
	Handler handler;

	// private Button bt;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.mine_fragment, null);
		/*
		 * 绑定控件
		 */
		roundImage = (RoundRectImageView) view
				.findViewById(R.id.iv_mine_avater);
		llMyProject = (View) view.findViewById(R.id.ll_mine_project);
		llSetMine = (View) view.findViewById(R.id.ll_set_mine);
		llMyStatus = (View) view.findViewById(R.id.ll_mine_status);
		llMineResume = (View) view.findViewById(R.id.ll_mine_resume);
		llVIP = (View) view.findViewById(R.id.ll_VIP);
		// bt=(Button)view.findViewById(R.id.bt_out_login);
		/*
		 * 添加监听
		 */
		llMyProject.setOnClickListener(this);
		llSetMine.setOnClickListener(this);
		llMyStatus.setOnClickListener(this);
		llMineResume.setOnClickListener(this);
		llVIP.setOnClickListener(this);
		// bt.setOnClickListener(this);
		roundImage.setOnClickListener(this);
		handler = new InnerHandler();

		roundImage.setCircleImageBitmap(BitmapFactory.decodeResource(
				getResources(), R.drawable.imag_avater));
		/*
		 * 注册广播接受者
		 */
		receiver = new InnerReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(INTENT_ACTION_AVATER);

		getActivity().registerReceiver(receiver, filter);

		avaterIsExists();
		return view;
	}

	// 点击进入
	@Override
	public void onClick(View view) {
		Intent intent = null;
		switch (view.getId()) {
		case R.id.ll_set_mine:
			intent = new Intent(getActivity(), SettingMineActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_mine_project:
			intent = new Intent(getActivity(), MyProjectActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_mine_status:
			intent = new Intent(getActivity(), MyStatusAcitivity.class);
			startActivity(intent);

			break;
		case R.id.ll_mine_resume:
			intent = new Intent(getActivity(), MyResumeActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_VIP:
			intent=new Intent(getActivity(), VIPActivity.class);
			startActivity(intent);
		// 点击头像
		case R.id.iv_mine_avater:
			intent = new Intent(getActivity(), MenuDialogActivity.class);
			startActivity(intent);
			break;

		}
	}

	/*
	 * 广播接受
	 */
	private class InnerReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (INTENT_ACTION_AVATER.equals(action)) {
				image = intent.getParcelableExtra(INTENT_EXTRA_AVATER);
				saveBitmap(image);
				roundImage.setCircleImageBitmap(image);
				uploadAvater();// 上传头像到bmob
			}
		}
	}

	/*
	 * 将剪切后的头像保存拿到本地
	 */
	private void saveBitmap(Bitmap bitmap) {
		avaterFile = new File(Environment.getExternalStorageDirectory(),
				"chuangbang/avater.jpg");
		FileOutputStream out = null;
		try {
			File destDir = new File(Environment.getExternalStorageDirectory(),
					"chuangbang");
			if (!destDir.exists()) {
				destDir.mkdirs();
			}
			out = new FileOutputStream(avaterFile);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

			out.close();
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 上传头像到bmob服务器
	 */
	private void uploadAvater() {
		String path = Environment.getExternalStorageDirectory().getPath()
				+ "/chuangbang/avater.jpg";
		Log.i("Avater", "头像在本地的地址" + path);
		final BmobFile bmobFile = new BmobFile(new File(path));
		bmobFile.uploadblock(getActivity(), new UploadFileListener() {

			@Override
			public void onSuccess() {
				/*
				 * 如果成功，获取头像在服务器上的uri后再将此uri存入User表中
				 */
				String uri = bmobFile.getFileUrl(getActivity());
				Log.i("Avater", "头像uri" + uri);
				updateAvater(uri);

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// avaterFile.delete();
				// Toast.makeText(getActivity(), "头像上传bmob失败",
				// Toast.LENGTH_LONG).show();

			}
		});
	}

	/**
	 * 将头像在服务器上的uri地址存入User表
	 */
	private void updateAvater(String uri) {
		ChuangUser newUser = new ChuangUser();
		newUser.setuAvatar(uri);
		BmobUser user = BmobUser.getCurrentUser(getActivity());
		newUser.update(getActivity(), user.getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
				Toast.makeText(getActivity(), "头像上传成功", Toast.LENGTH_LONG)
						.show();

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Toast.makeText(getActivity(), "头像上传User表失败", Toast.LENGTH_LONG)
						.show();
				avaterFile.delete();
			}
		});
	}

	/*
	 * 判断本地是否有头像文件 1，如果存在本地，则加载本地 2，不存在本地文件，加载网络User表的数据 如果不为空，下载头像到本地，再加载头像
	 * 如果为空，则加载默认的头像
	 */
	private void avaterIsExists() {
		try {
			File f = new File(Environment.getExternalStorageDirectory(),
					"chuangbang/avater.jpg");
			if (f.exists()) {
				roundImage.setCircleImageBitmap(BitmapFactory
						.decodeFile(Environment.getExternalStorageDirectory()
								.getPath() + "/chuangbang/avater.jpg"));
			}
			// 加载网络User表
			else {
				queryUserAvater();

			}

		} catch (Exception e) {

		}

	}

	private void queryUserAvater() {
		BmobUser user = BmobUser.getCurrentUser(getActivity());

		BmobQuery<ChuangUser> query = new BmobQuery<ChuangUser>();
		query.addWhereEqualTo("objectId", user.getObjectId());
		query.findObjects(getActivity(), new FindListener<ChuangUser>() {

			@Override
			public void onSuccess(List<ChuangUser> arg0) {
				Log.i("Avater", "查询用户的头像" + arg0.get(0).getuAvatar());
				final String avater = arg0.get(0).getuAvatar();
				if (avater != null) {
					Log.i("Avater", "开启线程");
					new Thread() {
						public void run() {
							downloadPic(avater);
							handler.obtainMessage(1).sendToTarget();
						};
					}.start();

				} else {
					roundImage.setCircleImageBitmap(BitmapFactory
							.decodeResource(getResources(),
									R.drawable.ic_launcher));
				}

			}

			@Override
			public void onError(int arg0, String arg1) {

			}
		});

	}

	/**
	 * 下载图片到本地
	 */
	private void downloadPic(String avaterPath) {
		URL url = null;
		HttpURLConnection conn = null;
		InputStream in = null;
		// FileOutputStream out=null;
		Bitmap bit = null;

		try {
			Log.i("Avater", "开始下载");
			url = new URL(avaterPath);

			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.connect();
			in = conn.getInputStream();
			bit = BitmapFactory.decodeStream(in);
			saveBitmap(bit);
			in.close();

			// out=new
			// FileOutputStream(Environment.getExternalStorageDirectory(),"/chuangbang/avater.jpg");
			// byte[] buffer=new byte[1024*4];
			// int end=-1;
			// while((end=in.read(buffer))!=-1){
			// out.write(buffer,0,end);
			// }

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class InnerHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// 在线程中下载完图片后，在此加载头像
				roundImage.setCircleImageBitmap(BitmapFactory
						.decodeFile(Environment.getExternalStorageDirectory()
								.getPath() + "/chuangbang/avater.jpg"));
				break;

			}

		}
	}

}
