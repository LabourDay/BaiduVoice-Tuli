package com.example.baiduspeech;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.tts.client.SpeechSynthesizer;
import com.example.baiduspeech.R;
import com.example.baiduspeech.SpeechRecognizerTool;
import com.example.baiduspeech.bean.ChatMessage;
import com.example.baiduspeech.bean.ChatMessage.Type;
import com.example.baiduspeech.utils.HttpUtils;
import com.example.baiduspeech.utils.VoiceUtils;
import com.example.baiduspeech.adapter.ChatMessageAdapter;


public class MainActivity extends Activity implements SpeechRecognizerTool.ResultsCallback{

	private ListView mMsgs;
	private ChatMessageAdapter mAdapter;
	private List<ChatMessage> mDatas;
	
	private ImageView iv;
	private ImageView con;
	
	private EditText mInputMsg;
	private Button mSendMsg;
	
	private boolean voice_switch;
	
	private SpeechRecognizerTool mSpeechRecognizerTool = new SpeechRecognizerTool(this);
	private SpeechSynthesizer mSpeechSynthesizer;
	private VoiceUtils utils=new VoiceUtils();

	private Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			// 等待接收，子线程完成数据的返回
//			VoiceUtils utils=new VoiceUtils();
//	        utils.init(MainActivity.this,0);
//	        mSpeechSynthesizer=utils.getSyntheszer();
			ChatMessage fromMessge = (ChatMessage) msg.obj;
			mDatas.add(fromMessge);
			mAdapter.notifyDataSetChanged();
			mMsgs.setSelection(mDatas.size()-1);
			String text = fromMessge.getMsg();
			if(MainActivity.this.voice_switch){
				MainActivity.this.mSpeechSynthesizer.speak(text);
			}
		};

	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		initView();
		initDatas();
		// 初始化事件
		initListener();
	}

	private void initListener()
	{
		mSendMsg.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				final String toMsg = mInputMsg.getText().toString();
				if (TextUtils.isEmpty(toMsg))
				{
					Toast.makeText(MainActivity.this, "发送消息不能为空！",
							Toast.LENGTH_SHORT).show();
					return;
				}
				
				ChatMessage toMessage = new ChatMessage();
				toMessage.setDate(new Date());
				toMessage.setMsg(toMsg);
				toMessage.setType(Type.OUTCOMING);
				mDatas.add(toMessage);
				mAdapter.notifyDataSetChanged();
				mMsgs.setSelection(mDatas.size()-1);
				
				mInputMsg.setText("");
				
				new Thread()
				{
					public void run()
					{
						ChatMessage fromMessage = HttpUtils.sendMessage(toMsg);
						Message m = Message.obtain();
						m.obj = fromMessage;
						mHandler.sendMessage(m);
					};
				}.start();

			}
		});
	}

	private void initDatas()
	{
		mDatas = new ArrayList<ChatMessage>();
		mDatas.add(new ChatMessage("你好，图灵为您服务", Type.INCOMING, new Date()));
		mAdapter = new ChatMessageAdapter(this, mDatas);
		mMsgs.setAdapter(mAdapter);
	}

	private void initView()
	{
		voice_switch = false;
		mMsgs = (ListView) findViewById(R.id.id_listview_msgs);
		mInputMsg = (EditText) findViewById(R.id.id_input_msg);
		iv = (ImageView)findViewById(R.id.id_send_voice);
		con = (ImageView)findViewById(R.id.id_control_voice);
		mSendMsg = (Button) findViewById(R.id.id_send_msg);
		MainActivity.this.utils.init(MainActivity.this,0);
		MainActivity.this.mSpeechSynthesizer=utils.getSyntheszer();
		if(voice_switch){
			MainActivity.this.mSpeechSynthesizer.speak("你好，图灵为您服务");
		}
		iv.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int action = event.getAction();
				switch (action) {
				case MotionEvent.ACTION_DOWN:
					mSpeechRecognizerTool.startASR(MainActivity.this);
					break;
				case MotionEvent.ACTION_UP:
					mSpeechRecognizerTool.stopASR();
					break;
				default:
					return false;
				}
				return true;
			}
		});
		
		con.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				voice_switch = !voice_switch;
				if(voice_switch){
					con.setImageResource(R.drawable.on);
				}else{
					con.setImageResource(R.drawable.off);
				}
			}
		});
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mSpeechRecognizerTool.createTool(); 
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mSpeechRecognizerTool.destroyTool(); 
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		this.mSpeechSynthesizer.release();
		super.onDestroy();
		
	}
	
	@Override
	public void onResults(String result) {
		final String finalResult = result;
		ChatMessage toMessage = new ChatMessage();
		toMessage.setDate(new Date());
		toMessage.setMsg(finalResult);
		toMessage.setType(Type.OUTCOMING);
		mDatas.add(toMessage);
		mAdapter.notifyDataSetChanged();
		mMsgs.setSelection(mDatas.size()-1);
		new Thread()
		{
			public void run()
			{
				ChatMessage fromMessage = HttpUtils.sendMessage(finalResult);
				Message m = Message.obtain();
				m.obj = fromMessage;
				mHandler.sendMessage(m);
			};
		}.start();
	}

}
