package chuangbang.app;

import java.util.Map;

import android.content.Context;

import chuangbang.db.UserDao;
import chuangbang.entity.ChuangUser;


public class Application {
	public static Context applicationContext;
	private static Application instance;
	private Map<String, ChuangUser> contactList;

	/**
	 * 获取内存中好友user list
	 * 
	 * @return
	 */
	public Map<String, ChuangUser> getContactList() {
		// if(getUserName() != null &&contactList == null)
		if (getUser() != null && contactList == null) {
			UserDao dao = new UserDao(applicationContext);
			// 获取本地好友user list到内存,方便以后获取好友list
			contactList = dao.getContactList();
		}
		return contactList;
	}

	public String getUser() {
		// TODO Auto-generated method stub
		return null;
	}

	public static Application getInstance() {
		return instance;
	}

	public String getVersionName() {
		// TODO Auto-generated method stub
		return null;
	}

}
