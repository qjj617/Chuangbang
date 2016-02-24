package chuangbang.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.ImageMessageBody;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.chat.TextMessageBody;
import com.easemob.util.PathUtil;

import chuangbang.adapter.EmojPagerAdapter;
import chuangbang.adapter.ExpressionAdapter;
import chuangbang.adapter.MessageAdapter;
import chuangbang.app.Application;
import chuangbang.utils.CommonUtils;
import chuangbang.utils.ImageUtils;
import chuangbang.utils.SmileUtils;
import chuangbang.view.ExpandGridView;
import chuangbang.view.PasteEditText;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.text.ClipboardManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * 聊天界面
 * 
 * @author Administrator
 * 
 */
public class ChatActivity extends BaseActivity implements OnClickListener {
	private static final int REQUEST_CODE_EMPTY_HISTORY = 2;
	public static final int REQUEST_CODE_CONTEXT_MENU = 3;
	private static final int REQUEST_CODE_MAP = 4;
	public static final int REQUEST_CODE_TEXT = 5;
	public static final int REQUEST_CODE_VOICE = 6;
	public static final int REQUEST_CODE_PICTURE = 7;
	public static final int REQUEST_CODE_LOCATION = 8;
	public static final int REQUEST_CODE_NET_DISK = 9;
	public static final int REQUEST_CODE_FILE = 10;
	public static final int REQUEST_CODE_COPY_AND_PASTE = 11;
	public static final int REQUEST_CODE_PICK_VIDEO = 12;
	public static final int REQUEST_CODE_DOWNLOAD_VIDEO = 13;
	public static final int REQUEST_CODE_VIDEO = 14;
	public static final int REQUEST_CODE_DOWNLOAD_VOICE = 15;
	public static final int REQUEST_CODE_SELECT_USER_CARD = 16;
	public static final int REQUEST_CODE_SEND_USER_CARD = 17;
	public static final int REQUEST_CODE_CAMERA = 18;
	public static final int REQUEST_CODE_LOCAL = 19;
	public static final int REQUEST_CODE_CLICK_DESTORY_IMG = 20;
	public static final int REQUEST_CODE_GROUP_DETAIL = 21;
	public static final int REQUEST_CODE_SELECT_VIDEO = 23;
	public static final int REQUEST_CODE_SELECT_FILE = 24;
	public static final int REQUEST_CODE_ADD_TO_BLACKLIST = 25;
	public static final int REQUEST_CODE_ADD_TO_CONTACT = 26;

	public static final int RESULT_CODE_COPY = 1;
	public static final int RESULT_CODE_DELETE = 2;
	public static final int RESULT_CODE_FORWARD = 3;
	public static final int RESULT_CODE_OPEN = 4;
	public static final int RESULT_CODE_DWONLOAD = 5;
	public static final int RESULT_CODE_TO_CLOUD = 6;
	public static final int RESULT_CODE_EXIT_GROUP = 7;

	public static final int CHATTYPE_SINGLE = 1;
	public static final int CHATTYPE_CHATROOM = 2;
	public static final int CHATTYPE_GROUP = 3;

	public static final String COPY_IMAGE = "EASEMOBIMG";

	private int chatType;
	// 给谁发送消息
	private String toChatUsername;
	private MessageAdapter adapter;
	// 会话
	private EMConversation conversation;
	// 表情集合
	private List<String> reslist;
	// 表情ViewPager
	private ViewPager emojvPager;
	// 表情图片
	private LinearLayout expressionContainer;
	private ImageView iv_emoticons_normal;
	private ImageView iv_emoticons_checked;
	// 设置键盘
	private InputMethodManager manager;
	private View buttonSetModeKeyboard;
	// 自定义文本框
	private PasteEditText mEditTextContent;
	// 发送
	private View btn_send;
	private View more;
	// 文本Layout
	private RelativeLayout edittext_layout;
	// 图片、拍照
	private LinearLayout btnContainer;
	private ListView listView;
	// 更多按钮
	private Button btn_more;
	private Drawable[] micImages;
	// private ImageView micImage;
	// 消息Adapter
	private MessageAdapter messageAdapter;
	// 注册消息广播机制
	private NewMessageBroadcastReceiver receiver;
	// 照片文件
	private File cameraFile;
	// 判断是否在下载
	private boolean isloading;
	// 系统复制粘贴
	private ClipboardManager clipboard;
	// 更多数据
	private boolean haveMoreData = true;
	// 往上拉加载数据
	private ProgressBar loadmorePB;
	// 最多加载20条数据
	private final int pagesize = 20;
	public static ChatActivity activityInstance = null;
	static int resendPos;

	/**
	 * 处理图片的handler
	 */
	/*
	 * private Handler micImageHandler = new Handler() { public void
	 * handleMessage(android.os.Message msg) { // 切换msg切换图片
	 * micImage.setImageDrawable(micImages[msg.what]);
	 * 
	 * } };
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		initView();
		setupView();
	}

	/**
	 * 获取xml资源
	 */
	private void initView() {
		edittext_layout = (RelativeLayout) findViewById(R.id.edittext_layout);
		// 获取输入框图片
		edittext_layout.setBackgroundResource(R.drawable.input_bar_bg_normal);
		btnContainer = (LinearLayout) findViewById(R.id.ll_btn_container);
		expressionContainer = (LinearLayout) findViewById(R.id.ll_face_container);
		iv_emoticons_normal = (ImageView) findViewById(R.id.iv_emoticons_normal);
		iv_emoticons_checked = (ImageView) findViewById(R.id.iv_emoticons_checked);
		// 表情图片设置为可见、不可见
		iv_emoticons_normal.setVisibility(View.VISIBLE);
		iv_emoticons_checked.setVisibility(View.INVISIBLE);
		emojvPager = (ViewPager) findViewById(R.id.vPager);
		btn_send = findViewById(R.id.btn_send);
		btn_more = (Button) findViewById(R.id.btn_more);
		buttonSetModeKeyboard = findViewById(R.id.btn_set_mode_keyboard);
		listView = (ListView) findViewById(R.id.list);
		mEditTextContent = (PasteEditText) findViewById(R.id.et_sendmessage);
		more = findViewById(R.id.more);

		// 表情List
		reslist = getExpressionRes(35);
		// 初始化表情viewpager
		List<View> views = new ArrayList<View>();
		View gv1 = getGridChildView(1);
		View gv2 = getGridChildView(2);
		views.add(gv1);
		views.add(gv2);
		emojvPager.setAdapter(new EmojPagerAdapter(views));
		// 文本框处于聚焦状态
		edittext_layout.requestFocus();
		// 给文本框设置监听
		mEditTextContent.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					edittext_layout
							.setBackgroundResource(R.drawable.input_bar_bg_active);
				} else {
					edittext_layout
							.setBackgroundResource(R.drawable.input_bar_bg_normal);
				}
			}
		});
		mEditTextContent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				edittext_layout
						.setBackgroundResource(R.drawable.input_bar_bg_active);
				iv_emoticons_normal.setVisibility(View.VISIBLE);
				iv_emoticons_checked.setVisibility(View.INVISIBLE);
				more.setVisibility(View.GONE);
				expressionContainer.setVisibility(View.GONE);
				btnContainer.setVisibility(View.GONE);

			}
		});
		// 监听文字框
		mEditTextContent.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!TextUtils.isEmpty(s)) {
					btn_more.setVisibility(View.GONE);
					btn_send.setVisibility(View.VISIBLE);
				} else {
					btn_more.setVisibility(View.VISIBLE);
					btn_send.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int before,
					int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void setupView() {
		activityInstance = this;
		iv_emoticons_normal.setOnClickListener(this);
		iv_emoticons_checked.setOnClickListener(this);

		// 注册消息广播
		receiver = new NewMessageBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(EMChatManager
				.getInstance().getNewMessageBroadcastAction());
		// 设置广播的优先级别大于Mainacitivity,这样如果消息来的时候正好在chat页面，直接显示消息，而不是提示消息未读
		intentFilter.setPriority(3);
		registerReceiver(receiver, intentFilter);

		// 注册一个ack回执消息的BroadcastReceiver
		IntentFilter ackMessageIntentFilter = new IntentFilter(EMChatManager
				.getInstance().getAckMessageBroadcastAction());
		ackMessageIntentFilter.setPriority(5);
		registerReceiver(ackMessageReceiver, ackMessageIntentFilter);
		// 显示转发的消息
		String forward_msg_id = getIntent().getStringExtra("forward_msg_id");
		if (forward_msg_id != null) {
			// 显示发送要转发的消息
			forwardMessage(forward_msg_id);
		}

		// 点击更多弹出拍照、图片按钮
		btn_more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (more.getVisibility() == View.GONE) {
					Log.i("我的TAG", "log more gone");
					hideKeyboard();
					more.setVisibility(View.VISIBLE);
					btnContainer.setVisibility(View.VISIBLE);
					expressionContainer.setVisibility(View.GONE);
				} else {
					if (expressionContainer.getVisibility() == View.VISIBLE) {
						expressionContainer.setVisibility(View.GONE);
						btnContainer.setVisibility(View.VISIBLE);
						iv_emoticons_normal.setVisibility(View.VISIBLE);
						iv_emoticons_checked.setVisibility(View.INVISIBLE);
					} else {
						more.setVisibility(View.GONE);
					}

				}

			}
		});
		// 调用系统的复制粘贴板
		clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		// 显示软键盘
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		// 获取聊天对象
		// conversation = EMChatManager.getInstance().getConversation(
		// toChatUsername);
		// 把此会话的未读数置为0
		// conversation.resetUnsetMsgCount();
		// messageAdapter = new MessageAdapter(this, toChatUsername, chatType);
		// 显示消息
		listView.setAdapter(messageAdapter);
		listView.setOnScrollListener(new ListScrollListener());
		int count = listView.getCount();
		if (count > 0) {
			listView.setSelection(count - 1);
		}

		listView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				hideKeyboard();
				more.setVisibility(View.GONE);
				iv_emoticons_normal.setVisibility(View.VISIBLE);
				iv_emoticons_checked.setVisibility(View.INVISIBLE);
				btnContainer.setVisibility(View.GONE);
				expressionContainer.setVisibility(View.GONE);
				/*
				 * //触碰ListView隐藏系统键盘 manager=(InputMethodManager)
				 * getSystemService(INPUT_METHOD_SERVICE);
				 * if((event.getAction()==
				 * MotionEvent.ACTION_DOWN)&&v.getId()==R.id.list){
				 * manager.hideSoftInputFromWindow(v.getWindowToken(),0); }
				 */

				return false;
			}
		});
	}

	/**
	 * 转发消息
	 * 
	 * @param forward_msg_id
	 */
	private void forwardMessage(String forward_msg_id) {
		EMMessage forward_msg = EMChatManager.getInstance().getMessage(
				forward_msg_id);
		EMMessage.Type type = forward_msg.getType();
		switch (type) {
		case TXT:
			// 获取消息内容，发送消息
			String content = ((TextMessageBody) forward_msg.getBody())
					.getMessage();
			sendText(content);
			break;
		case IMAGE:
			// 发送图片
			String filePath = ((ImageMessageBody) forward_msg.getBody())
					.getLocalUrl();
			if (filePath != null) {
				File file = new File(filePath);
				if (!file.exists()) {
					// 不存在大图发送缩略图
					filePath = ImageUtils.getThumbnailImagePath(filePath);
				}
				sendPicture(filePath);
			}
			break;
		}
	}

	/**
	 * 获取表情的gridview的子view
	 * 
	 * @param i
	 * @return
	 */
	private View getGridChildView(int i) {
		View view = View.inflate(this, R.layout.expression_gridview, null);
		// 多功能的扩展GridView
		ExpandGridView expandGridView = (ExpandGridView) view
				.findViewById(R.id.gridview);
		List<String> list = new ArrayList<String>();
		if (i == 1) {
			List<String> list1 = reslist.subList(0, 20);
			list.addAll(list1);
		} else if (i == 2) {
			list.addAll(reslist.subList(20, reslist.size()));
		}
		list.add("show_emoj");
		final ExpressionAdapter expressionAdapter = new ExpressionAdapter(this,
				1, list);
		// 设置Adapter
		expandGridView.setAdapter(expressionAdapter);
		// 设置点击item事件
		expandGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String filename = expressionAdapter.getItem(position);
				// 文字输入框可见时，才可输入表情
				try {
					if (buttonSetModeKeyboard.getVisibility() != View.VISIBLE) {
						// 显示表情
						if (filename != "show_emoj") {
							// 这里用的反射，所以混淆的时候不要混淆SmileUtils这个类
							Class clz = Class
									.forName("chuangbang.utils.SmileUtils");
							Field field = clz.getField(filename);
							// 文本框添加表情
							mEditTextContent.append(SmileUtils.getSmiledText(
									ChatActivity.this, (String) field.get(null)));

						} else {
							// 删除文字或者表情
							if (!TextUtils.isEmpty(mEditTextContent.getText())) {
								// 获取光标的位置
								int selectionStart = mEditTextContent
										.getSelectionStart();
								if (selectionStart > 0) {
									String body = mEditTextContent.getText()
											.toString();
									String temStr = body.substring(0,
											selectionStart);
									// 获取最后表情位置
									int i = temStr.lastIndexOf("[");
									if (i != -1) {
										//
										CharSequence charSequence = temStr
												.substring(i, selectionStart);
										if (SmileUtils.containsKey(charSequence
												.toString()))
											// 删除起始光标
											mEditTextContent.getEditableText()
													.delete(i, selectionStart);
										else
											mEditTextContent.getEditableText()
													.delete(selectionStart - 1,
															selectionStart);
									} else {
										mEditTextContent.getEditableText()
												.delete(selectionStart - 1,
														selectionStart);
									}
								}
							}
						}
					}
				} catch (Exception e) {

				}

			}
		});
		return view;

	}

	public List<String> getExpressionRes(int i) {
		// 表情集合
		List<String> reList = new ArrayList<String>();
		for (int x = 1; x <= i; x++) {
			String filename = "ee_" + x;
			reList.add(filename);
		}
		return reList;
	}

	/**
	 * 各消息图标点击事件
	 */
	@Override
	public void onClick(View v) {
		/*
		 * int id = v.getId(); if (id == R.id.btn_send) { String s =
		 * mEditTextContent.getText().toString(); sendText(s); // 点击照相图标 } else
		 * if (id == R.id.btn_take_picture) { selectPicFromCamera(); // 点击图片图标 }
		 * else if (id == R.id.btn_picture) { selectPicFromLocal(); // 点击显示表情 }
		 * else if (id == R.id.iv_emoticons_normal) {
		 * more.setVisibility(View.VISIBLE);
		 * iv_emoticons_normal.setVisibility(View.INVISIBLE);
		 * iv_emoticons_checked.setVisibility(View.VISIBLE);
		 * btnContainer.setVisibility(View.GONE);
		 * expressionContainer.setVisibility(View.VISIBLE);
		 */
		switch (v.getId()) {
		case R.id.btn_send:
			String s = mEditTextContent.getText().toString();
			sendText(s);
			break;
		case R.id.btn_take_picture:
			selectPicFromCamera();
		case R.id.btn_picture:
			selectPicFromLocal();
			break;
		case R.id.iv_emoticons_normal:
			more.setVisibility(View.VISIBLE);
			iv_emoticons_normal.setVisibility(View.INVISIBLE);
			iv_emoticons_checked.setVisibility(View.VISIBLE);
			btnContainer.setVisibility(View.GONE);
			expressionContainer.setVisibility(View.VISIBLE);
			buttonSetModeKeyboard.setVisibility(View.GONE);
			break;
		case R.id.iv_emoticons_checked:
			iv_emoticons_normal.setVisibility(View.VISIBLE);
			iv_emoticons_checked.setVisibility(View.INVISIBLE);
			btnContainer.setVisibility(View.VISIBLE);
			expressionContainer.setVisibility(View.GONE);
			more.setVisibility(View.GONE);
			buttonSetModeKeyboard.setVisibility(View.VISIBLE);
			break;
		}
		// 继续隐藏软键盘
		hideKeyboard();
		// 点击隐藏表情
		/*
		 * } else if (id == R.id.iv_emoticons_checked) {
		 * iv_emoticons_normal.setVisibility(View.VISIBLE);
		 * iv_emoticons_checked.setVisibility(View.INVISIBLE);
		 * btnContainer.setVisibility(View.VISIBLE);
		 * expressionContainer.setVisibility(View.GONE);
		 * more.setVisibility(View.GONE); }
		 */

	}

	/***
	 * 发送文本消息
	 * 
	 * @param s
	 */
	private void sendText(String content) {
		if (content.length() > 0) {
			EMMessage message = EMMessage.createSendMessage(EMMessage.Type.TXT);
			// 设置默认为单聊
			// if (chatType == CHATTYPE_CHATROOM) {
			// message.setChatType(ChatType.ChatRoom);
			// }
			// 如果是群聊，设置chattype,默认是单聊
			if (chatType == CHATTYPE_GROUP)
				message.setChatType(ChatType.GroupChat);
			TextMessageBody textMessageBody = new TextMessageBody(content);
			// 设置消息body
			message.addBody(textMessageBody);
			// 设置要发送给谁，用户username
			// message.setReceipt(toChatUsername);
			// 把messgage加到conversation中
			// conversation.addMessage(message);
			// 通知adapter有消息变动，adapter会根据加入的这条message显示消息和调用sdk的发送方法
			// messageAdapter.refresh();
			listView.setSelection(listView.getCount() - 1);
			mEditTextContent.setText("");
			// listView.setAdapter(messageAdapter);
			setResult(RESULT_OK);
			Log.i("我的TAG", "content-->" + content);

		}
	}

	/**
	 * 点击文字输入框
	 * 
	 * @param v
	 */
	public void editClick(View v) {
		listView.setSelection(listView.getCount() - 1);
		if (more.getVisibility() == View.VISIBLE) {
			more.setVisibility(View.GONE);
			iv_emoticons_normal.setVisibility(View.VISIBLE);
			iv_emoticons_checked.setVisibility(View.INVISIBLE);
		}
	}

	/***
	 * 通过照相获取照片
	 */
	public void selectPicFromCamera() {
		if (!CommonUtils.isExitsSdcard()) {
			Toast.makeText(getApplicationContext(), "SD不存在，不能拍照",
					Toast.LENGTH_SHORT).show();
			return;
		}
		cameraFile = new File(PathUtil.getInstance().getImagePath(),
				Application.getInstance().getUser()
						+ System.currentTimeMillis() + ".jpg");
		cameraFile.getParentFile().mkdirs();
		startActivityForResult(
				new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(
						MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
				REQUEST_CODE_CAMERA);
	}

	/**
	 * 通过从手机相册获取照片
	 */
	private void selectPicFromLocal() {
		Intent intent;
		if (Build.VERSION.SDK_INT < 19) {
			intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");

		} else {
			intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		}
		startActivityForResult(intent, REQUEST_CODE_LOCAL);

	}

	/**
	 * 发送图片
	 */
	@SuppressWarnings("unused")
	private void sendPicture(final String filePath) {
		String to = toChatUsername;
		final EMMessage message = EMMessage
				.createSendMessage(EMMessage.Type.IMAGE);
		// 如果是群聊，设置chattype,默认是单聊
		if (chatType == CHATTYPE_GROUP)
			message.setChatType(ChatType.GroupChat);
		// 发给聊天对象
		// message.setReceipt(to);
		ImageMessageBody imageMessageBody = new ImageMessageBody(new File(
				filePath));
		message.addBody(imageMessageBody);
		// conversation.addMessage(message);
		listView.setAdapter(messageAdapter);
		// messageAdapter.refresh();
		listView.setSelection(listView.getCount() - 1);
		setResult(RESULT_OK);
		Log.i("我的TAG", "filepath-->" + filePath);

	}

	/***
	 * 根据图库图片uri发送图片
	 */
	@SuppressWarnings("unused")
	private void sendPicByUri(Uri selectedImage) {
		Cursor cursor = getContentResolver().query(selectedImage, null, null,
				null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex("_data");
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			cursor = null;

			if (picturePath == null || picturePath.equals("null")) {
				Toast toast = Toast.makeText(this, "找不到图片", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}
			sendPicture(picturePath);
		} else {
			File file = new File(selectedImage.getPath());
			if (!file.exists()) {
				Toast toast = Toast.makeText(this, "找不到图片", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;

			}
			sendPicture(file.getAbsolutePath());
		}
	}

	/**
	 * 隐藏软键盘
	 */
	private void hideKeyboard() {
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}

	}

	/**
	 * 消息广播接收者
	 */
	private class NewMessageBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String username = intent.getStringExtra("from");
			String msgid = intent.getStringExtra("msgid");
			// 收到这个广播的时候，message已经在db和内存里了，可以通过id获取mesage对象
			EMMessage message = EMChatManager.getInstance().getMessage(msgid);
			// 如果是群聊消息，获取到group id
			if (message.getChatType() == ChatType.GroupChat) {
				username = message.getTo();
			}
			if (!username.equals(toChatUsername)) {
				// 消息不是发给当前会话，return
				return;
			}
			// conversation =
			// EMChatManager.getInstance().getConversation(toChatUsername);
			// 通知adapter有新消息，更新ui
			messageAdapter.refresh();
			listView.setSelection(listView.getCount() - 1);
			// 记得把广播给终结掉
			abortBroadcast();

		}

	}

	/**
	 * 消息回执BroadcastReceiver
	 */
	private BroadcastReceiver ackMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String msgid = intent.getStringExtra("msgid");
			String from = intent.getStringExtra("from");
			EMConversation conversation = EMChatManager.getInstance()
					.getConversation(from);
			if (conversation != null) {
				// 把message设为已读
				EMMessage msg = conversation.getMessage(msgid);
				if (msg != null) {
					msg.isAcked = true;
				}
			}
			abortBroadcast();
			messageAdapter.notifyDataSetChanged();
		}

	};

	/**
	 * listView滑动监听，显示加载数据
	 */
	private class ListScrollListener implements OnScrollListener {

		@Override
		public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {

		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			switch (scrollState) {
			case OnScrollListener.SCROLL_STATE_IDLE:
				if (view.getFirstVisiblePosition() == 0 && !isloading
						&& haveMoreData) {
					loadmorePB.setVisibility(View.VISIBLE);
					// sdk初始化加载的聊天记录为20条，到顶时去db里获取更多
					List<EMMessage> messages;
					try {
						// 获取更多messges，调用此方法的时候从db获取的messages
						// sdk会自动存入到此conversation中
						if (chatType == CHATTYPE_SINGLE)
							messages = conversation.loadMoreMsgFromDB(
									messageAdapter.getItem(0).getMsgId(),
									pagesize);
						else
							messages = conversation.loadMoreGroupMsgFromDB(
									messageAdapter.getItem(0).getMsgId(),
									pagesize);
					} catch (Exception e1) {
						loadmorePB.setVisibility(View.GONE);
						return;
					}
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
					}
					if (messages.size() != 0) {
						// 刷新ui
						messageAdapter.notifyDataSetChanged();
						listView.setSelection(messages.size() - 1);
						if (messages.size() != pagesize)
							haveMoreData = false;
					} else {
						haveMoreData = false;
					}
					loadmorePB.setVisibility(View.GONE);
					isloading = false;

				}
				break;
			}
		}

	}

	/**
	 * 此处是注销广播
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		activityInstance = this;
		// 注销广播
		try {
			unregisterReceiver(receiver);
			receiver = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			unregisterReceiver(ackMessageReceiver);
			ackMessageReceiver = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 当再次启动聊天界面时，自动刷新
	 */
	/*
	 * @Override protected void onResume() { // TODO Auto-generated method stub
	 * super.onResume(); messageAdapter.refresh(); }
	 */
	/**
	 * 显示键盘图标
	 */
	/*
	 * public void setModeKeyBoard(View view) {
	 * edittext_layout.setVisibility(View.VISIBLE);
	 * more.setVisibility(View.GONE); view.setVisibility(View.GONE);
	 * mEditTextContent.requestFocus(); if
	 * (TextUtils.isEmpty(mEditTextContent.getText())) {
	 * btn_more.setVisibility(View.VISIBLE); btn_send.setVisibility(View.GONE);
	 * } else { btn_more.setVisibility(View.GONE);
	 * btn_send.setVisibility(View.VISIBLE); }
	 * 
	 * }
	 */
	/**
	 * 此方法为了保证只有一个聊天界面
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		String username = intent.getStringExtra("userId");
		if (toChatUsername.equals(username)) {
			super.onNewIntent(intent);
		} else {
			finish();
			startActivity(intent);
		}
	}

	/**
	 * 返回结果
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_CONTEXT_MENU) {
			switch (resultCode) {
			case RESULT_CODE_COPY: // 复制消息
				EMMessage copyMsg = ((EMMessage) messageAdapter.getItem(data
						.getIntExtra("position", -1)));
				if (copyMsg.getType() == EMMessage.Type.IMAGE) {
					ImageMessageBody imageBody = (ImageMessageBody) copyMsg
							.getBody();
					// 加上一个特定前缀，粘贴时知道这是要粘贴一个图片
					clipboard.setText(COPY_IMAGE + imageBody.getLocalUrl());
				} else {
					// clipboard.setText(SmileUtils.getSmiledText(ChatActivity.this,
					// ((TextMessageBody) copyMsg.getBody()).getMessage()));
					clipboard.setText(((TextMessageBody) copyMsg.getBody())
							.getMessage());
				}
				/*
				 * break; case RESULT_CODE_DELETE: // 删除消息 EMMessage deleteMsg =
				 * (EMMessage) messageAdapter.getItem(data
				 * .getIntExtra("position", -1));
				 * conversation.removeMessage(deleteMsg.getMsgId());
				 * messageAdapter.refresh();
				 * listView.setSelection(data.getIntExtra("position",
				 * messageAdapter.getCount()) - 1); break;
				 */

			case RESULT_CODE_FORWARD: // 转发消息
				EMMessage forwardMsg = (EMMessage) messageAdapter.getItem(data
						.getIntExtra("position", 0));
				Intent intent = new Intent(this, ForwardMessageActivity.class);
				intent.putExtra("forward_msg_id", forwardMsg.getMsgId());
				startActivity(intent);
				break;
			}
		}
		if (resultCode == RESULT_OK) { // 清空消息
			if (requestCode == REQUEST_CODE_EMPTY_HISTORY) {
				// 清空会话
				EMChatManager.getInstance().clearConversation(toChatUsername);
				messageAdapter.refresh();
			} else if (requestCode == REQUEST_CODE_CAMERA) { // 发送照片
				if (cameraFile != null && cameraFile.exists())
					sendPicture(cameraFile.getAbsolutePath());
			} else if (requestCode == REQUEST_CODE_LOCAL) { // 发送本地图片
				if (data != null) {
					Uri selectedImage = data.getData();
					if (selectedImage != null) {
						sendPicByUri(selectedImage);
					}
				}
				// 重发消息
			} else if (requestCode == REQUEST_CODE_TEXT) {
				resendMessage();
			} else if (requestCode == REQUEST_CODE_PICTURE) {
				resendMessage();
			} else if (requestCode == REQUEST_CODE_COPY_AND_PASTE) {
				// 粘贴
				if (!TextUtils.isEmpty(clipboard.getText())) {
					String pasteText = clipboard.getText().toString();
					if (pasteText.startsWith(COPY_IMAGE)) {
						// 把图片前缀去掉，还原成正常的path
						sendPicture(pasteText.replace(COPY_IMAGE, ""));
					}

				}
			}
		}
	}

	/**
	 * 重发消息
	 */
	private void resendMessage() {
		EMMessage emMessage = null;
		emMessage = conversation.getMessage(resendPos);
		emMessage.status = EMMessage.Status.CREATE;
		messageAdapter.refresh();
		listView.setSelection(resendPos);

	}

	public String getToChatUsername() {
		return toChatUsername;

	}

	/**
	 * 返回到聊天主界面
	 */
	public void back(View v) {
		finish();
	}

}
