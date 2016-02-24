package chuangbang.app;



import com.easemob.chat.EMChat;

import android.app.Application;
import android.content.Context;

public class TestUserApp extends Application{
	
	private static TestUserApp instance;
	private static Context context;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance=this;
		context=this;
		EMChat.getInstance().init(this);
	}
	
	public static TestUserApp getInstance(){
		return instance;
	}

}
