package chuangbang.adapter;

import com.easemob.chat.EMConversation;


import android.content.Context;
import android.provider.Telephony.Sms.Conversations;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ChatAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	private EMConversation conversation;
	
	public ChatAdapter(Context context){
		inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return conversation.getAllMessages().size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return conversation.getAllMessages().get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}
	private class ViewHolder{
		
	}

}
