package chuangbang.adapter;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import chuangbang.activity.R;
import chuangbang.app.ChuangApp;
import chuangbang.entity.Project;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SeeProAdapter extends BaseAdapter{
	private List<Project> data;
	private LayoutInflater inflater;
	private ChuangApp app;
	private DisplayImageOptions options;
	private ImageLoader io;
	
	public SeeProAdapter(List<Project> data,Context context){
		if (data == null) {
			this.data = new ArrayList<Project>();
		} else {
			this.data = data;
		}
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_launcher) // 设置图片下载期间显示的图片
		.showImageForEmptyUri(R.drawable.ic_launcher) // 设置图片Uri为空或是错误的时候显示的图片
		.showImageOnFail(R.drawable.ic_launcher) // 设置图片加载或解码过程中发生错误显示的图片
		.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
		.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
		.displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build(); // 构建完成
		app=ChuangApp.getApp();//获取app对象
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.item_see_project, null);
			holder.tvProName=(TextView)convertView.findViewById(R.id.tv_see_project_proName);
			holder.tvProDescription=(TextView)convertView.findViewById(R.id.tv_see_project_proDescription);
			holder.tvProLocation=(TextView)convertView.findViewById(R.id.tv_see_project_proLocation);
			holder.ivAvater=(ImageView)convertView.findViewById(R.id.iv_see_project_avater);
			holder.ivComment=(ImageView) convertView.findViewById(R.id.img_comment);
			holder.ivClick=(ImageView) convertView.findViewById(R.id.img_click);
			convertView.setTag(holder);
			
			
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		holder.tvProName.setText(data.get(position).getProName());
		holder.tvProDescription.setText(data.get(position).getProDescription());
		holder.tvProLocation.setText(data.get(position).getProLoaction()+".."+data.get(position).getProState());
		app.getImageLoader().displayImage(data.get(position).getProLogo(), holder.ivAvater, options);
		return convertView;
	}
	private class ViewHolder{
		ImageView ivAvater,ivComment,ivClick;
		TextView tvProName,tvProDescription,tvProLocation;
	}

	

}
