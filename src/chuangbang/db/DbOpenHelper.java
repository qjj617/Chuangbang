package chuangbang.db;

import chuangbang.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {
	private static DbOpenHelper instance;
	private static final int DATABASE_VERSION = 1;

	private static final String USERNAME_TABLE_CREATE = "CREATE TABLE "
			+ UserDao.TABLE_NAME + " (" + UserDao.COLUMN_NAME_NICK + " TEXT, "
			+ UserDao.COLUMN_NAME_IS_STRANGER + " TEXT, "
			+ UserDao.COLUMN_U_AVATAR + " TEXT, " + UserDao.COLUMN_NAME_ID
			+ " TEXT PRIMARY KEY);";

	public DbOpenHelper(Context context) {
		super(context, geUserDatabaseName(), null, DATABASE_VERSION);
	}

	private static String geUserDatabaseName() {
		// TODO Auto-generated method stub
		return Application.getInstance().getUser() + "_demo.db";
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(USERNAME_TABLE_CREATE);
	}

	/**
	 * 此类表示更新数据库操作
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public static DbOpenHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DbOpenHelper(context.getApplicationContext());
		}
		return instance;
	}

	/**
	 * 最后关闭DB
	 */
	public void closeDB() {
		if (instance != null) {
			try {
				SQLiteDatabase db = instance.getWritableDatabase();
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			instance = null;
		}
	}
}
