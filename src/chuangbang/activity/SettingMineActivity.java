package chuangbang.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import chuangbang.adapter.SetMineAdapter;
import chuangbang.app.ChuangApp;
import chuangbang.entity.ChuangUser;
import chuangbang.utils.Final;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

import com.bmob.btp.e.a.in;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;

public class SettingMineActivity extends Activity implements OnClickListener,
		Final {
	private TextView tvName, tvCompany, tvPosition, tvCellphone, tvEmail,
			tvLocation, tvDescription;
	private RadioGroup sex;
	private String userNico, avatarPath, userCompany, userPosition,
			userCellphone, userRecord, userEmail, userDescription,
			userLocation;
	private BroadcastReceiver receiver;// 广播接收者
	private Button btBack, btSave;
	private Context context;
	private ChuangApp app;
	private BaseAdapter adapter;
	private List<String> title;
	private List<String> contant;
	private ListView lv;
	private TableRow trNico, trCompany, trPosition, trEmail, trLocation,
			trPhone, trDescription;
	private ChuangUser user;
	private String result;
	private Handler handler;
	private final int NICO = 1;// 昵称消息
	private final int COMPANY = 2;// 公司消息
	private final int POSITION = 3;// 职位消息
	private final int EMAIL = 4;// 邮箱消息
	private final int LACATION = 5;// 地区
	private final int DESCRIPTION = 6;

	// 添加监听
	private void setOnclick() {

		trNico.setOnClickListener(this);
		trCompany.setOnClickListener(this);
		trDescription.setOnClickListener(this);
		trEmail.setOnClickListener(this);
		trPosition.setOnClickListener(this);
		trDescription.setOnClickListener(this);
		trLocation.setOnClickListener(this);
		trPhone.setOnClickListener(this);
		btSave.setOnClickListener(this);

	}

	/*
	 * 绑定控件
	 */
	private void setView() {
		trNico = (TableRow) findViewById(R.id.tr_set_mine_name);
		trCompany = (TableRow) findViewById(R.id.tr_set_mine_company);
		trLocation = (TableRow) findViewById(R.id.tr_set_mine_location);
		trPosition = (TableRow) findViewById(R.id.tr_set_mine_position);
		trEmail = (TableRow) findViewById(R.id.tr_set_mine_email);
		trLocation = (TableRow) findViewById(R.id.tr_set_mine_location);
		trPhone = (TableRow) findViewById(R.id.tr_set_mine_phone);
		trDescription = (TableRow) findViewById(R.id.tr_set_mine_description);

		tvName = (TextView) findViewById(R.id.tv_set_mine_nico);
		tvEmail = (TextView) findViewById(R.id.tv_set_mine_email);
		tvPosition = (TextView) findViewById(R.id.tv_set_mine_position);
		tvLocation = (TextView) findViewById(R.id.tv_set_mine_location);
		tvDescription = (TextView) findViewById(R.id.tv_set_mine_description);
		tvCompany = (TextView) findViewById(R.id.tv_set_mine_company);

		btSave = (Button) findViewById(R.id.bt_set_mine_save);
		btBack = (Button) findViewById(R.id.bt_set_mine_back);
		sex = (RadioGroup) findViewById(R.id.rg_sex);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_mine);
		context = this;
		app = (ChuangApp) getApplication();
		title = new ArrayList<String>();
		contant = new ArrayList<String>();
		handler = new InnerHandler();

		adapter = new SetMineAdapter(SettingMineActivity.this, title, contant);
		setView();
		setOnclick();

	}

	/*
	 * 更新个人信息
	 * 初始化新的User对象，添加要更新的信息，再用BombUser对象get新的User对象，最后由User对象调用update方法完成更新
	 */
	private void updateUser() {
		// /BmobUser newUser=new BmobUser();
		Integer usex = 1;
		if (sex.getCheckedRadioButtonId() == R.id.rb_boy) {
			usex = 1;
		} else {
			usex = 2;
		}
		ChuangUser newUser = new ChuangUser();

		newUser.setuNickName(userNico);
		newUser.setEmail(userEmail);
		newUser.setuDescription(userDescription);
		newUser.setuSex(usex);
		newUser.setWorkingPosition(userPosition);
		newUser.setWorkingCompany(userCompany);
		BmobUser user = BmobUser.getCurrentUser(this);
		newUser.update(this, user.getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
				Log.i("Tag", "更新成功");

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Log.i("Tag", arg0 + "/" + arg1);
				// 301,邮箱账号格式不正确
				// 203,该邮箱已被使用

			}
		});
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		String result;
		switch (v.getId()) {

		case R.id.tr_set_mine_name:
			dialogShow("请输入名字", NICO);

			break;
		case R.id.tr_set_mine_email:
			dialogShow("请输入邮箱", EMAIL);
			break;
		case R.id.tr_set_mine_position:
			dialogShow("请输入职位", POSITION);
			break;
		case R.id.tr_set_mine_description:
			dialogShow("请输入个人标签", DESCRIPTION);
			break;
		case R.id.tr_set_mine_company:
			dialogShow("请输入公司名", COMPANY);
			break;
		/*
		 * 保存个人信息
		 */
		case R.id.bt_set_mine_save:
			updateUser();

			break;

		}

	}

	/*
	 * 对话框
	 */
	private void dialogShow(String title, final int point) {

		LayoutInflater inflater = LayoutInflater.from(this);
		LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.view_dialog,
				null);
		TextView tv = (TextView) ll.findViewById(R.id.tv_dialog_title);
		tv.setText(title);
		final Dialog dialog = new AlertDialog.Builder(SettingMineActivity.this)
				.create();
		dialog.show();
		dialog.getWindow().setContentView(ll);

		final EditText et = (EditText) ll.findViewById(R.id.et_edit);

		Button bt1 = (Button) ll.findViewById(R.id.bt_left);// 左边的按钮
		Button bt2 = (Button) ll.findViewById(R.id.bt_right);

		bt1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 确定按钮的返回值
				result = et.getText().toString();
				handler.obtainMessage(point, result).sendToTarget();
				dialog.dismiss();

			}
		});

		bt2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();

			}
		});

	}

	/*
	 * 消息处理者
	 */
	class InnerHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case NICO:
				userNico = msg.obj.toString();
				tvName.setText(userNico);
				break;

			case COMPANY:
				userCompany = msg.obj.toString();
				tvCompany.setText(userCompany);

				break;
			case POSITION:
				userPosition = msg.obj.toString();
				tvPosition.setText(userPosition);
				break;
			case EMAIL:
				userEmail = msg.obj.toString();
				tvEmail.setText(userEmail);
				break;
			case DESCRIPTION:
				userDescription = msg.obj.toString();
				tvDescription.setText(userDescription);
				break;
			}
		}
	}

}