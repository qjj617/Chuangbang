package chuangbang.db;

import java.util.HashMap;
import java.util.Map;

import chuangbang.entity.ChuangUser;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class UserDao {
	private DbOpenHelper dbOpenHelper;

	public static final String TABLE_NAME = "uers";
	public static final String COLUMN_NAME_ID = "uName";
	public static final String COLUMN_NAME_NICK = "nick";
	public static final String COLUMN_U_AVATAR = " uAvatar";
	public static final String COLUMN_NAME_IS_STRANGER = "is_stranger";// 0是陌生人
																		// 2是好友
																		// (1应该是关注的人,3是关注自己的)

	public UserDao(Context applicationContext) {
		dbOpenHelper = DbOpenHelper.getInstance(applicationContext);

	}

	/**
	 * 从这里获取User集合，并返回
	 * 
	 * @return
	 */
	public Map<String, ChuangUser> getContactList() {
		// 创建SQLite对象
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		// 获取Users
		Map<String, ChuangUser> users = new HashMap<String, ChuangUser>();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select * from " + TABLE_NAME
					+ " WHERE " + COLUMN_NAME_IS_STRANGER + " = '2' "/*
																	 * + " desc"
																	 */, null);
			/*while (cursor.moveToNext()) {
				String username = cursor.getString(cursor
						.getColumnIndex(COLUMN_NAME_ID));
				String nick = cursor.getString(cursor
						.getColumnIndex(COLUMN_NAME_NICK));
				ChuangUser user = new ChuangUser();
				user.setUsername(username);
				//user.setNick(nick);
				String headerName = null;
				if (!TextUtils.isEmpty(user.getNick())) {
					headerName = user.getNick();
				} else {
					headerName = user.getUsername();
				}

				if (username.equals(Constant.NEW_FRIENDS_USERNAME)) {
					user.setHeader("");
				} else if (Character.isDigit(headerName.charAt(0))) {
					user.setHeader("#");
				} else {
					user.setHeader(HanziToPinyin.getInstance()
							.get(headerName.substring(0, 1)).get(0).target
							.substring(0, 1).toUpperCase());
					char header = user.getHeader().toLowerCase().charAt(0);
					if (header < 'a' || header > 'z') {
						user.setHeader("#");
					}
				}
				users.put(username, user);
			}*/
			cursor.close();
		}
		return users;

	}

	/**
	 * 查询指定User信息
	 */
	public ChuangUser getUser(String username) {
		SQLiteDatabase sqLiteDatabase = dbOpenHelper.getReadableDatabase();
		ChuangUser user = new ChuangUser();
		if (sqLiteDatabase.isOpen()) {
			// 建立Cursor对象，进行查询
			Cursor cursor = sqLiteDatabase.rawQuery("select * from "
					+ TABLE_NAME + " WHERE " + COLUMN_NAME_ID + " = '"
					+ username + "'"/* + " desc" */, null);
			if (cursor.moveToFirst() == false) {
			} else {
				String nick = cursor.getString(cursor
						.getColumnIndex(COLUMN_NAME_NICK));
				String headpic = cursor.getString(cursor
						.getColumnIndex(COLUMN_U_AVATAR));
				String is_stranger = cursor.getString(cursor
						.getColumnIndex(COLUMN_NAME_IS_STRANGER));
				user.setUsername(username);
				//user.setNick(nick);
				//user.setHeaderurl(headpic);
				//user.setIs_stranger(is_stranger);
				// 最后关闭cursor 避免浪费不必要资源
				cursor.close();
			}
		}
		return user;

	}
}
