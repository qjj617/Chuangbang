package chuangbang.activity;

import cn.bmob.v3.Bmob;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * 主界面
 * @author Administrator
 *
 */
public class MainActivity extends FragmentActivity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "Your Application ID");
    }


    
}
