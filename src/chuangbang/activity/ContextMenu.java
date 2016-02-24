package chuangbang.activity;

import com.easemob.chat.EMMessage;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class ContextMenu extends BaseActivity {
	//职位描述
	private int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		int txtValue = EMMessage.Type.TXT.ordinal();
		int type = getIntent().getIntExtra("type", -1);
		if (type == EMMessage.Type.TXT.ordinal()) {
			setContentView(R.layout.context_menu_tex);
		} else if (type == EMMessage.Type.IMAGE.ordinal()) {
			setContentView(R.layout.context_menu_img);
		}
		position = getIntent().getIntExtra("position", -1);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	public void copy(View view) {
		setResult(ChatActivity.RESULT_CODE_COPY,
				new Intent().putExtra("position", position));
		finish();
	}

	public void delete(View view) {
		setResult(ChatActivity.RESULT_CODE_DELETE,
				new Intent().putExtra("position", position));
		finish();
	}

	public void forward(View view) {
		setResult(ChatActivity.RESULT_CODE_FORWARD,
				new Intent().putExtra("position", position));
		finish();
	}

	public void open(View v) {
		setResult(ChatActivity.RESULT_CODE_OPEN,
				new Intent().putExtra("position", position));
		finish();
	}

	public void download(View v) {
		setResult(ChatActivity.RESULT_CODE_DWONLOAD,
				new Intent().putExtra("position", position));
		finish();
	}

	public void toCloud(View v) {
		setResult(ChatActivity.RESULT_CODE_TO_CLOUD,
				new Intent().putExtra("position", position));
		finish();
	}

}
