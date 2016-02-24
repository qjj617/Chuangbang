package chuangbang.adapter;

import java.io.File;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import chuangbang.activity.AlertDialog;
import chuangbang.activity.ChatActivity;
import chuangbang.activity.ContextMenu;
import chuangbang.activity.R;
import chuangbang.activity.ShowBigImage;
import chuangbang.activity.Userinfo;
import chuangbang.db.UserDao;
import chuangbang.entity.ChuangUser;
import chuangbang.task.LoadImageTask;
import chuangbang.utils.ImageCache;
import chuangbang.utils.ImageOptions;
import chuangbang.utils.ImageUtils;
import chuangbang.utils.PreferenceUtils;
import chuangbang.utils.SmileUtils;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.chat.FileMessageBody;
import com.easemob.chat.ImageMessageBody;
import com.easemob.util.DateUtils;
import com.easemob.util.EMLog;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

public class MessageAdapter extends BaseAdapter {

	private final static String TAG = "msg";

	private static final int MESSAGE_TYPE_RECV_TXT = 0;
	private static final int MESSAGE_TYPE_SENT_TXT = 1;
	private static final int MESSAGE_TYPE_SENT_IMAGE = 2;
	private static final int MESSAGE_TYPE_RECV_IMAGE = 3;

	private String username;
	private LayoutInflater layoutInflater;
	private Activity activity;
	private EMConversation emConversation;
	private Context context;

	public static final String IMAGE_DIR = "chat/image/";

	private Map<String, Timer> timers = new Hashtable<String, Timer>();

	public MessageAdapter(Context context, String username, int chatType) {
		this.context = context;
		this.username = username;
		this.emConversation = EMChatManager.getInstance().getConversation(
				username);
		layoutInflater = LayoutInflater.from(context);
		activity = (Activity) context;
	}

	@Override
	// 获取item数
	public int getCount() {
		// TODO Auto-generated method stub
		return emConversation.getMsgCount();
	}

	@Override
	public EMMessage getItem(int position) {
		// TODO Auto-generated method stub
		return emConversation.getMessage(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final EMMessage message = getItem(position);
		ChatType chatType = message.getChatType();
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = createViewByMessage(message, position);
			if (message.getType() == EMMessage.Type.IMAGE) {
				try {
					holder.iv = ((ImageView) convertView
							.findViewById(R.id.iv_sendPicture));
					holder.iv_avatar = (ImageView) convertView
							.findViewById(R.id.iv_userhead);
					holder.tv = (TextView) convertView
							.findViewById(R.id.percentage);
					holder.pb = (ProgressBar) convertView
							.findViewById(R.id.progressBar);
					holder.staus_iv = (ImageView) convertView
							.findViewById(R.id.msg_status);
					holder.tv_usernick = (TextView) convertView
							.findViewById(R.id.tv_userid);
				} catch (Exception e) {
				}

			} else if (message.getType() == EMMessage.Type.TXT) {

				try {
					holder.pb = (ProgressBar) convertView
							.findViewById(R.id.pb_sending);
					holder.staus_iv = (ImageView) convertView
							.findViewById(R.id.msg_status);
					holder.iv_avatar = (ImageView) convertView
							.findViewById(R.id.iv_userhead);
					// 这里是文字内容
					holder.tv = (TextView) convertView
							.findViewById(R.id.tv_chatcontent);
					holder.tv_usernick = (TextView) convertView
							.findViewById(R.id.tv_userid);

					// holder.tvTitle = (TextView)
					// convertView.findViewById(R.id.tvTitle);
					// holder.tvList = (LinearLayout)
					// convertView.findViewById(R.id.ll_layout);
				} catch (Exception e) {
				}

			}
			// 设置标签
			convertView.setTag(holder);
		} else {
			// 得到标签
			holder = (ViewHolder) convertView.getTag();
		}

		// 如果是发送的消息并且不是群聊消息，显示已读textview
		if (message.direct == EMMessage.Direct.SEND
				&& chatType != ChatType.GroupChat) {
			holder.tv_ack = (TextView) convertView.findViewById(R.id.tv_ack);

			if (holder.tv_ack != null) {
				if (message.isAcked) {
					holder.tv_ack.setVisibility(View.VISIBLE);
				} else {
					holder.tv_ack.setVisibility(View.INVISIBLE);
				}
			}
		}

		// 此处根据消息type显示item
		switch (message.getType()) {
		case IMAGE:
			// 发送照片
			handleImageMessage(message, holder, position, convertView);
			break;

		case TXT:
			// 发送消息
			handleTextMessage(message, holder, position);
			break;
		default:
			break;
		}
		if (message.direct == EMMessage.Direct.SEND) {
			View statusView = convertView.findViewById(R.id.msg_status);
			// 重发按钮点击事件
			statusView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(activity, AlertDialog.class);
					intent.putExtra("msg",
							activity.getString(R.string.confirm_resend));
					intent.putExtra("title",
							activity.getString(R.string.resend));
					intent.putExtra("position", position);
					if (message.getType() == EMMessage.Type.TXT)
						activity.startActivityForResult(intent,
								ChatActivity.REQUEST_CODE_TEXT);
					else if (message.getType() == EMMessage.Type.IMAGE)
						activity.startActivityForResult(intent,
								ChatActivity.REQUEST_CODE_PICTURE);

				}
			});
		} else {
			/*
			 * final String st =
			 * context.getResources().getString(R.string.Into_the_blacklist);
			 * if(!((ChatActivity)activity).isRobot && chatType !=
			 * ChatType.ChatRoom){ // 长按头像，移入黑名单
			 * holder.iv_avatar.setOnLongClickListener(new OnLongClickListener()
			 * {
			 * 
			 * @Override public boolean onLongClick(View v) { Intent intent =
			 * new Intent(activity, AlertDialog.class); intent.putExtra("msg",
			 * st); intent.putExtra("cancel", true); intent.putExtra("position",
			 * position); activity.startActivityForResult(intent,
			 * ChatActivity.REQUEST_CODE_ADD_TO_BLACKLIST); return true; } }); }
			 */
			holder.head_iv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					/*** 获取本地数据库用户信息 ***/
					UserDao userdao = new UserDao(context);
					ChuangUser user = userdao.getUser(message.getFrom());
					/*** 获取本地数据库用户信息end ***/
					Intent intent = new Intent(activity, Userinfo.class);
					// intent.putExtra("userId", message.getFrom());
					// intent.putExtra("nickname", user.getNick());
					// intent.putExtra("headurl", user.getHeaderurl());

					activity.startActivity(intent);

				}
			});
		}
		// 时间值
		TextView timestamp = (TextView) convertView
				.findViewById(R.id.timestamp);
		if (position == 0) {
			timestamp.setText(DateUtils.getTimestampString(new Date(message
					.getMsgTime())));
			timestamp.setVisibility(View.VISIBLE);

		} else {
			// 当两条信息时间间隔过长，就显示时间
			EMMessage preMessage = getItem(position - 1);
			if (preMessage != null
					&& DateUtils.isCloseEnough(message.getMsgTime(),
							preMessage.getMsgTime())) {
				timestamp.setVisibility(View.GONE);
			} else {
				timestamp.setText(DateUtils.getTimestampString(new Date(message
						.getMsgTime())));
				timestamp.setVisibility(View.VISIBLE);
			}
		}
		// //非群聊显示对方头像
		if (message.direct == EMMessage.Direct.RECEIVE
				&& chatType != ChatType.GroupChat) {

		} else {
			// 获取本地数据库用户信息
			UserDao userDao = new UserDao(context);
			ChuangUser chuangUser = userDao.getUser(message.getFrom());
			ImageLoader.getInstance().displayImage(chuangUser.getuAvatar(),
					holder.head_iv, ImageOptions.getOptions());
			Log.i("我的TAG", "messageadapter_headurl" + chuangUser.getuAvatar());
			// 所有自己发送的图片显示自己的头像
			ImageLoader.getInstance().displayImage(
					PreferenceUtils.getInstance(context).getSettingUserPic(),
					holder.head_iv, ImageOptions.getOptions());
		}
		// 设置用户头像
		// /setUserAvatar(message,holder.iv_avatar);

		return convertView;
	}

	/**
	 * 图片消息
	 * 
	 * @param message
	 * @param holder
	 * @param position
	 * @param convertView
	 */
	private void handleImageMessage(final EMMessage message,
			final ViewHolder holder, final int position, View convertView) {
		holder.pb.setTag(position);

		// 设置长按监听器
		holder.iv.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				activity.startActivityForResult((new Intent(activity,
						ContextMenu.class)).putExtra("position", position)
						.putExtra("type", EMMessage.Type.IMAGE.ordinal()),
						ChatActivity.REQUEST_CODE_CONTEXT_MENU);
				return true;
			}
		});
		// 接收方的消息
		if (message.direct == EMMessage.Direct.RECEIVE) {
			if (message.status == EMMessage.Status.INPROGRESS) {
				// "!!!! back receive";
				holder.iv.setImageResource(R.drawable.default_image);
				showDownloadImageProgress(message, holder);
				// downloadImage(message, holder);
			} else {
				// "!!!! not back receive, show image directly");
				holder.pb.setVisibility(View.GONE);
				holder.tv.setVisibility(View.GONE);
				holder.iv.setImageResource(R.drawable.default_image);
				ImageMessageBody imgBody = (ImageMessageBody) message.getBody();
				if (imgBody.getLocalUrl() != null) {
					// String filePath = imgBody.getLocalUrl();
					String remotePath = imgBody.getRemoteUrl();
					String filePath = ImageUtils.getImagePath(remotePath);
					String thumbRemoteUrl = imgBody.getThumbnailUrl();
					if (TextUtils.isEmpty(thumbRemoteUrl)
							&& !TextUtils.isEmpty(remotePath)) {
						thumbRemoteUrl = remotePath;
					}
					String thumbnailPath = ImageUtils
							.getThumbnailImagePath(thumbRemoteUrl);
					showImageView(thumbnailPath, holder.iv, filePath,
							imgBody.getRemoteUrl(), message);
				}
			}
			return;
		}
		// 发送方的消息
		ImageMessageBody imgBody = (ImageMessageBody) message.getBody();
		String filePath = imgBody.getLocalUrl();
		if (filePath != null && new File(filePath).exists()) {
			showImageView(ImageUtils.getThumbnailImagePath(filePath),
					holder.iv, filePath, null, message);
		} else {
			showImageView(ImageUtils.getThumbnailImagePath(filePath),
					holder.iv, filePath, IMAGE_DIR, message);
		}

		switch (message.status) {
		case SUCCESS:
			holder.pb.setVisibility(View.GONE);
			holder.tv.setVisibility(View.GONE);
			holder.staus_iv.setVisibility(View.GONE);
			break;
		case FAIL:
			holder.pb.setVisibility(View.GONE);
			holder.tv.setVisibility(View.GONE);
			holder.staus_iv.setVisibility(View.VISIBLE);
			break;
		case INPROGRESS:
			holder.staus_iv.setVisibility(View.GONE);
			holder.pb.setVisibility(View.VISIBLE);
			holder.tv.setVisibility(View.VISIBLE);
			if (timers.containsKey(message.getMsgId()))
				return;
			// 设置一个Timer
			final Timer timer = new Timer();
			timers.put(message.getMsgId(), timer);
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					activity.runOnUiThread(new Runnable() {
						public void run() {
							holder.pb.setVisibility(View.VISIBLE);
							holder.tv.setVisibility(View.VISIBLE);
							holder.tv.setText(message.progress + "%");
							if (message.status == EMMessage.Status.SUCCESS) {
								holder.pb.setVisibility(View.GONE);
								holder.tv.setVisibility(View.GONE);
								// message.setSendingStatus(Message.SENDING_STATUS_SUCCESS);
								timer.cancel();
							} else if (message.status == EMMessage.Status.FAIL) {
								holder.pb.setVisibility(View.GONE);
								holder.tv.setVisibility(View.GONE);
								// message.setSendingStatus(Message.SENDING_STATUS_FAIL);
								// message.setProgress(0);
								holder.staus_iv.setVisibility(View.VISIBLE);
								Toast.makeText(
										activity,
										activity.getString(R.string.send_fail)
												+ activity
														.getString(R.string.connect_failuer_toast),
										0).show();
								timer.cancel();
							}

						}
					});

				}
			}, 0, 500);
			// 发送照片的消息
			sendPictureMessage(message, holder);
		}
	}

	/**
	 * 发送照片
	 * 
	 * @param message
	 * @param holder
	 */

	private void sendPictureMessage(EMMessage message, final ViewHolder holder) {
		try {
			String to = message.getTo();

			// before send, update ui
			holder.staus_iv.setVisibility(View.GONE);
			holder.pb.setVisibility(View.VISIBLE);
			holder.tv.setVisibility(View.VISIBLE);
			holder.tv.setText("0%");
			// if (chatType == ChatActivity.CHATTYPE_SINGLE) {
			EMChatManager.getInstance().sendMessage(message, new EMCallBack() {

				@Override
				public void onSuccess() {
					Log.i("我的TAG", "发送照片成功");
					activity.runOnUiThread(new Runnable() {
						public void run() {
							// send success
							holder.pb.setVisibility(View.GONE);
							holder.tv.setVisibility(View.GONE);
						}
					});
				}

				@Override
				public void onError(int code, String error) {
					activity.runOnUiThread(new Runnable() {
						public void run() {
							holder.pb.setVisibility(View.GONE);
							holder.tv.setVisibility(View.GONE);
							// message.setSendingStatus(Message.SENDING_STATUS_FAIL);
							holder.staus_iv.setVisibility(View.VISIBLE);
							Toast.makeText(
									activity,
									activity.getString(R.string.send_fail)
											+ activity
													.getString(R.string.connect_failuer_toast),
									0).show();
						}
					});
				}

				@Override
				public void onProgress(final int progress, String status) {
					activity.runOnUiThread(new Runnable() {
						public void run() {
							holder.tv.setText(progress + "%");
						}
					});
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 显示正在下载的图片的进度
	 * 
	 * @param message
	 * @param holder
	 */
	private void showDownloadImageProgress(final EMMessage message,
			final ViewHolder holder) {
		EMLog.d(TAG, "!!! show download image progress");
		final FileMessageBody fileMessageBody = (FileMessageBody) message
				.getBody();
		if (holder.pb != null)
			holder.pb.setVisibility(View.VISIBLE);
		if (holder.tv != null)
			holder.tv.setVisibility(View.VISIBLE);

		fileMessageBody.setDownloadCallback(new EMCallBack() {

			@Override
			public void onSuccess() {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// message.setBackReceive(false);
						if (message.getType() == EMMessage.Type.IMAGE) {
							holder.pb.setVisibility(View.GONE);
							holder.tv.setVisibility(View.GONE);
						}
						notifyDataSetChanged();
					}
				});
			}

			@Override
			public void onError(int code, String message) {

			}

			@Override
			public void onProgress(final int progress, String status) {
				if (message.getType() == EMMessage.Type.IMAGE) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							holder.tv.setText(progress + "%");

						}
					});
				}

			}

		});
	}

	/**
	 * 显示图片
	 * 
	 * @param thumbernailPath
	 * @param iv
	 * @param filePath
	 * @param remoteUrl
	 * @param message
	 * @return
	 */
	private boolean showImageView(String thumbernailPath, ImageView iv,
			final String localFullSizePath, String remoteDir,
			final EMMessage message) {
		final String remote = remoteDir;
		EMLog.d("###", "local = " + localFullSizePath + " remote: " + remote);
		// first check if the thumbnail image already loaded into cache
		Bitmap bitmap = ImageCache.getInstance().get(thumbernailPath);
		if (bitmap != null) {
			// thumbnail image is already loaded, reuse the drawable
			iv.setImageBitmap(bitmap);
			iv.setClickable(true);
			iv.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					System.err.println("image view on click");
					Intent intent = new Intent(activity, ShowBigImage.class);
					File file = new File(localFullSizePath);
					if (file.exists()) {
						Uri uri = Uri.fromFile(file);
						intent.putExtra("uri", uri);
						System.err
								.println("here need to check why download everytime");
					} else {
						// The local full size pic does not exist yet.
						// ShowBigImage needs to download it from the server
						// first
						// intent.putExtra("", message.get);
						ImageMessageBody body = (ImageMessageBody) message
								.getBody();
						intent.putExtra("secret", body.getSecret());
						intent.putExtra("remotepath", remote);
					}
					if (message != null
							&& message.direct == EMMessage.Direct.RECEIVE
							&& !message.isAcked
							&& message.getChatType() != ChatType.GroupChat) {
						try {
							EMChatManager.getInstance().ackMessageRead(
									message.getFrom(), message.getMsgId());
							message.isAcked = true;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					activity.startActivity(intent);
				}
			});
			return true;
		} else {

			new LoadImageTask().execute(thumbernailPath, localFullSizePath,
					remote, message.getChatType(), iv, activity, message);
			return true;
		}

	}

	/**
	 * 文本消息
	 * 
	 * @param message
	 * @param holder
	 * @param position
	 */
	private void handleTextMessage(EMMessage message, ViewHolder holder,
			final int position) {
		TextMessageBody body = (TextMessageBody) message.getBody();
		Spannable spannable = SmileUtils.getSmiledText(context,
				body.getMessage());
		// 设置内容
		holder.tv.setText(spannable, BufferType.SPANNABLE);
		// 设置监听器
		holder.tv.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				activity.startActivityForResult((new Intent(activity,
						ContextMenu.class)).putExtra("position", position)
						.putExtra("type", EMMessage.Type.TXT.ordinal()),
						ChatActivity.REQUEST_CODE_CONTEXT_MENU);
				return false;
			}
		});
		if (message.direct == EMMessage.Direct.SEND) {
			// 消息发送状态
			switch (message.status) {
			// 发送成功
			case SUCCESS:
				holder.pb.setVisibility(View.GONE);
				holder.staus_iv.setVisibility(View.GONE);
				break;
			// 发送失败
			case FAIL:
				holder.pb.setVisibility(View.GONE);
				holder.staus_iv.setVisibility(View.VISIBLE);
				break;
			// 发送过程中
			case INPROGRESS:
				holder.pb.setVisibility(View.VISIBLE);
				holder.staus_iv.setVisibility(View.GONE);
				break;
			default:
				// 发送消息
				sendMsgInBackground(message, holder);
			}
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param message
	 * @param holder
	 */
	public void sendMsgInBackground(final EMMessage message,
			final ViewHolder holder) {
		holder.staus_iv.setVisibility(View.GONE);
		holder.pb.setVisibility(View.VISIBLE);
		// 调用sdk发送异步发送方法
		EMChatManager.getInstance().sendMessage(message, new EMCallBack() {

			@Override
			public void onSuccess() {
				// UI更新消息
				updateSendMessageView(message, holder);

			}

			@Override
			public void onProgress(int code, String error) {

			}

			@Override
			public void onError(int progress, String status) {
				updateSendMessageView(message, holder);
			}

		});

	}

	/**
	 * UI实时更新信息
	 * 
	 * @param message
	 * @param holder
	 */
	private void updateSendMessageView(EMMessage message, ViewHolder holder) {
		// TODO Auto-generated method stub
	}

	/**
	 * 显示用户头像
	 * 
	 * @param message
	 * @param iv_avatar
	 */
	/*
	 * private void setUserAvatar(EMMessage message, ImageView iv_avatar) {
	 * 
	 * 
	 * }
	 */
	@Override
	public int getViewTypeCount() {
		return 2;
	}

	public int getItemViewType(int position) {
		EMMessage message = emConversation.getMessage(position);
		if (message.getType() == EMMessage.Type.TXT) {

			return message.direct == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_TXT
					: MESSAGE_TYPE_SENT_TXT;
		}
		if (message.getType() == EMMessage.Type.IMAGE) {
			return message.direct == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_IMAGE
					: MESSAGE_TYPE_SENT_IMAGE;

		}

		return -1;

	}

	@SuppressLint("InflateParams")
	public View createViewByMessage(EMMessage message, int position) {

		switch (message.getType()) {
		case IMAGE:
			return message.direct == EMMessage.Direct.RECEIVE ? layoutInflater
					.inflate(R.layout.row_received_picture, null)
					: layoutInflater.inflate(R.layout.row_sent_picture, null);
		default:
			break;
		}
		return message.direct == EMMessage.Direct.RECEIVE ? layoutInflater
				.inflate(R.layout.row_received_message, null) : layoutInflater
				.inflate(R.layout.row_sent_message, null);

	}

	class ViewHolder {
		ImageView iv;
		ImageView head_iv;
		TextView tv;
		ProgressBar pb;
		// 照片发送状态
		ImageView staus_iv;
		ImageView iv_avatar;
		TextView tv_usernick;
		TextView timeLength;
		TextView size;
		LinearLayout container_status_btn;
		LinearLayout ll_container;
		ImageView iv_read_status;
		// 显示已读回执状态
		TextView tv_ack;
		// 显示送达回执状态
		// TextView tv_delivered;

		// TextView tvTitle;
		// LinearLayout tvList;
	}

	/**
	 * 刷新
	 */
	public void refresh() {
		notifyDataSetChanged();
	}
}
