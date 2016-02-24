package chuangbang.fragment.found;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bmob.btp.e.a.in;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import chuangbang.activity.R;
import chuangbang.adapter.SeeProAdapter;
import chuangbang.entity.Project;
import chuangbang.entity.SignUp;
import chuangbang.utils.HttpHelp;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SeeProjectFragment extends Fragment implements OnScrollChangedListener, OnClickListener {
	private ListView lvSeeProject;
	private String url;
	private HttpUtils http;
	private List <Project>data;
	private BaseAdapter adapter;
	private Handler handler;
	private int page=1;
	private View loadmoreItem;  //listView下方加载更多的按钮
	private Button loadmoreButton;
	
	//测试
	private ImageView imageView1;
	private ImageView imageView2;
	
	

	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/*
		 * 通过view绑定控件
		 */
		View view = inflater.inflate(R.layout.see_project, null);
		lvSeeProject = (ListView) view.findViewById(R.id.lv_see_project);
		
		url="http://cloud.bmob.cn/56a234cda6607ec7/projectReadList";
		//增加一个view视图，用来加载底部的加载按钮
		loadmoreItem=inflater.inflate(R.layout.button_loadmore, null);		
		loadmoreButton=(Button)loadmoreItem.findViewById(R.id.bt_loadmore);
		//将底部按钮添加进lstView
		lvSeeProject.addFooterView(loadmoreItem);
		/*
		 * 数据初始化
		 */
		http = new HttpUtils();
		handler=new InnerHandler();
		data = new ArrayList<Project>();
		adapter=new SeeProAdapter(data, getActivity());
		lvSeeProject.setAdapter(adapter);
		/*
		 * 添加的监听
		 */
		loadmoreButton.setOnClickListener(this);
		
		/*
		 * 增加的执行程序
		 */
		getDate();
		return view;
	}

	
	/*
	 * 获取第一波数据
	 */
	private void getDate() {
	
		final List<Project> lists=new ArrayList<Project>();
		RequestParams params = new RequestParams("utf-8");
		String data1 = 10 + "";
		String data2 = 1 + "";
		params.addBodyParameter("limit", data1);
		params.addBodyParameter("page", data2);
		http.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> info) {
				Project pro=null;
				//pros=new ArrayList<Project>();
				Log.i("Tag", "读取项目成功");
				String result=info.result;
				Log.i("Tag","result="+result);	
				try {
					Log.i("Tag", "开始解析");
					JSONObject json = new JSONObject(result);
					String count=json.getString("count");
					Log.i("Tag",count);
					JSONArray results=json.getJSONArray("results");
				for(int i=0;i<results.length();i++){
						pro=new Project();
						JSONObject value=results.getJSONObject(i);
						String proName=value.getString("proName");
						String proDescription=value.getString("proDescription");
						String proLoaction=value.getString("proLocation");
						String proState=value.getString("proState");
						String proLogo=value.getString("proLogo");		
						Log.i("Tag", proDescription);
						pro.setProName(proName);
						pro.setProDescription(proDescription);
						pro.setProLoaction(proLoaction);
						pro.setProState(proState);
						pro.setProLogo(proLogo);
						lists.add(pro);						
					}
					Log.i("Tag","pros"+lists.toString());
					data.addAll(lists);
					adapter.notifyDataSetChanged();
					
					Log.i("Tag", "data"+data.toString());
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			
				
			}
		});
	
	}
	
	/*
	 * 点击下方加载后获取的新数据
	 */
	private void getNewData(){		
		final List<Project> newList=new ArrayList<Project>();
		RequestParams params = new RequestParams("utf-8");
		page+=1;
		String data1 = 10 + "";
		String data2 = page + "";
		params.addBodyParameter("limit", data1);
		params.addBodyParameter("page", data2);
		http.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {			
			}
			@Override
			public void onSuccess(ResponseInfo<String> info) {
				Project pro=null;
				//pros=new ArrayList<Project>();
				Log.i("Tag", "读取项目成功");
				String result=info.result;
			//	Log.i("Tag","result="+result);	
				try {
					Log.i("Tag", "开始解析");
					JSONObject json = new JSONObject(result);
					String count=json.getString("count");
					
					JSONArray results=json.getJSONArray("results");
				for(int i=0;i<results.length();i++){
						pro=new Project();
						JSONObject value=results.getJSONObject(i);
						String proName=value.getString("proName");
						String proDescription=value.getString("proDescription");
						String proLoaction=value.getString("proLocation");
						String proState=value.getString("proState");
						String proLogo=value.getString("proLogo");		
						Log.i("Tag", proDescription);
						pro.setProName(proName);
						pro.setProDescription(proDescription);
						pro.setProLoaction(proLoaction);
						pro.setProState(proState);
						pro.setProLogo(proLogo);
						newList.add(pro);	

					}	
				        //发送消息要求ui更新下啦刷新
						handler.obtainMessage(1, newList).sendToTarget();
						
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			
				
			}
		});
	}
	
	/*
	 * 消息处理者
	 */
	class InnerHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				List<Project> pros=new ArrayList<Project>();
				pros=(List<Project>) msg.obj;
				Log.i("Tag","pros="+pros);
				data.addAll(pros);
				adapter.notifyDataSetChanged();
				loadmoreButton.setText("加载更多");
				break;

		
			}
			
		}
	}


	/**
	 * 
	 */
	@Override
	public void onScrollChanged() {
		// TODO Auto-generated method stub
		
	}


	/*
	 * 加载更多的按钮
	 */
	@Override
	public void onClick(View arg0) {
		loadmoreButton.setText("正在加载");
		getNewData();
		
	}

	
}
