package chuangbang.activity;



import com.easemob.EMError;
import com.easemob.chat.EMChatManager;
import com.easemob.exceptions.EaseMobException;

import chuangbang.utils.MsgHandler;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistActivity extends Activity{
	private EditText etUserName,etPassword,etComfirmPassword,etValidateCode;
	private Handler handler;
	private Message msg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		handler= new MsgHandler(RegistActivity.this);
		/*
		 * 控件绑定
		 */
		etUserName=(EditText)findViewById(R.id.et_name);
		etPassword=(EditText)findViewById(R.id.et_password);
		etComfirmPassword=(EditText)findViewById(R.id.et_password_again);//确认密码
		etValidateCode=(EditText)findViewById(R.id.et_ValidateCode);
	}
	
	
	public void doClick(View v){
		
		final String phoneNumber=etUserName.getText().toString();
		final String password=etPassword.getText().toString();
		String comfirmPassword=etComfirmPassword.getText().toString();
		String code = etValidateCode.getText().toString();
		
		
		switch (v.getId()) {
		
		//点击获取验证码
		case R.id.bt_Getcode_Id:
			if (phoneNumber == null || phoneNumber.length() != 11) {
				Toast.makeText(this, "错误!请输入正确的手机号!", Toast.LENGTH_SHORT)
						.show();
				// btnCode.setCancel(true);
			}
			/**
			 * 请求验证码
			 */
			BmobSMS.requestSMSCode(this, phoneNumber, "注册模板",
					new RequestSMSCodeListener() {

						@Override
						public void done(Integer smsId, BmobException ex) {
							if (ex == null) {// 验证码发送成功
								Log.i("", "短信id：" + smsId);// 用于查询本次短信发送详情
							}
							
						}});
			
			
			break;

		case R.id.bt_regist:
			
			/**
			 * 判断手机号11位验证
			 * 
			 */
			if (phoneNumber == null || phoneNumber.length() != 11) {
				Toast.makeText(this, "错误，请输入正确号码", Toast.LENGTH_SHORT).show();
				return;
			}
			if (password == null || password.length() < 6) {
				Toast.makeText(this, "错误!密码的长度不得少于6位!", Toast.LENGTH_SHORT)
						.show();

				return;
			}
			if (TextUtils.isEmpty(code)) {
				Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
				return;
			}
			if (!(password.equals(comfirmPassword))) {
				Toast.makeText(this, "两次密码不一样", Toast.LENGTH_SHORT).show();
				return;
			}
			
			/**
			 * 验证验证码
			 */
			BmobSMS.verifySmsCode(this, phoneNumber, code,new VerifySMSCodeListener() {

						@Override
						public void done(BmobException ex) {
							// 对验证码进行判断
							if (ex == null) {// 短信验证码已验证成功
								Log.i("", "验证通过");
								/*
								 * 注册bmob用户账号
								 */
								BmobUser user = new BmobUser();
								user.setUsername(phoneNumber);
								user.setPassword(password);
								
								user.signUp(getApplicationContext(),
										new SaveListener() {

											@Override
											public void onSuccess() {
												
												new Thread(new Runnable() {
													/**
													 * 在此编写注册环信用户
													 */
													@Override
													public void run() {
														 try {
															// Looper.prepare();
													         // 调用sdk注册方法
													         EMChatManager.getInstance().createAccountOnServer(phoneNumber, password);
													         msg=handler.obtainMessage();
													  			msg.arg1=5;
													  			handler.sendMessage(msg);
													      // Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
													      }catch (final EaseMobException e) {
													          //注册失败
													  		int errorCode=e.getErrorCode();
													  		if(errorCode==EMError.NONETWORK_ERROR){
													  		   // Toast.makeText(getApplicationContext(), "网络异常，请检查网络！", Toast.LENGTH_SHORT).show();
													  			msg=handler.obtainMessage();
													  			msg.arg1=4;
													  			handler.sendMessage(msg);
													  		}else if(errorCode==EMError.USER_ALREADY_EXISTS){
													  		 
													  			msg=handler.obtainMessage();
													  			msg.arg1=1;
													  			handler.sendMessage(msg);
													  			Log.i("Tag", "用户已经存在");
													  		}else if(errorCode==EMError.UNAUTHORIZED){
													  			//Toast.makeText(getApplicationContext(), "注册失败，无权限！", Toast.LENGTH_SHORT).show();
													  			msg=handler.obtainMessage();
													  			msg.arg1=6;
													  			handler.sendMessage(msg);
													  		}else{
													  			//Toast.makeText(getApplicationContext(), "注册失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
													  			msg=handler.obtainMessage();
													  			msg.arg1=3;
													  			handler.sendMessage(msg);
													        }
														
													}
												}}).start();

											}

											@Override
											public void onFailure(int arg0,
													String arg1) {
												//Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_SHORT).show();
												
											}
										});
								
								

							} else {
								Log.i("", "验证失败：code =" + ex.getErrorCode()
										+ ",msg = " + ex.getLocalizedMessage());

							}

						}
					});
		finish();
			
			break;
		}
	}

}
