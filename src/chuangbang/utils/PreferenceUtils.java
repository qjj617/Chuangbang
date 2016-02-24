package chuangbang.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {

	private static final String SHARED_KEY_SETTING_USER_PIC = "shared_key_setting_user_pic";
	/**
	 * 保存Preference的name
	 */
	public static final String PREFERENCE_NAME = "saveInfo";
	private static SharedPreferences.Editor editor;
	private static SharedPreferences mSharedPreferences;
	private static PreferenceUtils mPreferenceUtils;

	public PreferenceUtils(Context context) {
		mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,
				Context.MODE_PRIVATE);
	}

	/**
	 * 单例模式，获取instance实例
	 * 
	 * @param cxt
	 * @return
	 */
	public static PreferenceUtils getInstance(Context context) {
		if (mPreferenceUtils == null) {
			mPreferenceUtils = new PreferenceUtils(context);
		}
		editor = mSharedPreferences.edit();
		return mPreferenceUtils;
	}

	/**
	 * 设置用户头像
	 * 
	 * @param UserPic
	 */
	public void setSettingUserPic(String UserPic) {
		editor.putString(SHARED_KEY_SETTING_USER_PIC, UserPic);
		editor.commit();
	}

	/**
	 * 获取用户头像
	 */
	public String getSettingUserPic() {
		return mSharedPreferences.getString(SHARED_KEY_SETTING_USER_PIC, "");
	}
}
