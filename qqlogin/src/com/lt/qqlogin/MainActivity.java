package com.lt.qqlogin;

import java.util.*;

import Common.*;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost.TabSpec;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.lt.ConnectServer.*;
import java.io.*;

public class MainActivity extends Activity {

	private EditText userId;
	private EditText password;
	private ProgressBar progressBar;
	private TextView textView;
	private Handler handler;
	private Intent intent;
	private AlertDialog alertDialog;
	String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ����Ϊ�ޱ�����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		userId = (EditText) findViewById(R.id.user_id);
		password = (EditText) findViewById(R.id.password);
		progressBar = (ProgressBar) findViewById(R.id.login_progress);
		textView = (TextView) findViewById(R.id.login_text);

	}

	// �������һ��Ҫд
	public void show(View v) {
		progressBar.setVisibility(View.VISIBLE);
		textView.setVisibility(View.VISIBLE);
		// ������������������֤
		intent = new Intent(this, friendList.class);
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					User user = new User();
					user.setUserId(userId.getText().toString());
					user.setPasswd(password.getText().toString());
					conServer cs = new conServer();
					name = cs.SendInfo(user);
					if (name!=null) {
						Message ms = handler.obtainMessage(1, "1");
						//System.out.println("������ȷ");
						handler.sendMessage(ms);
					} else {
						Message ms = handler.obtainMessage(0, "0");
						//System.out.println("�������");
						handler.sendMessage(ms);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();

		handler = new Handler() {
			public void handleMessage(Message ms) {
				progressBar.setVisibility(View.GONE);
				textView.setVisibility(View.GONE);
				//System.out.println("handler����");
				if (ms.obj.toString().equals("1")) {
					System.out.println("true");
					intent = new Intent(MainActivity.this, friendList.class);
					intent.putExtra("myId", userId.getText().toString());
					
					startActivity(intent);
				} else  {
					alertDialog = new AlertDialog.Builder(MainActivity.this)
							.setMessage("��������ȷ����").create();
					Window window = alertDialog.getWindow();
					// ���Ϸ�λ��
					window.setGravity(Gravity.TOP);
					WindowManager.LayoutParams lp = window.getAttributes();
					// ����͸����
					lp.alpha = 0.6f;
					window.setAttributes(lp);
					alertDialog.show();
					System.out.println("false");
				}
			}
		};

		// if(isSuccess)
		// {
		// System.out.println("�ɹ�");
		// Intent intent=new Intent(this, friendList.class);
		// startActivity(intent);
		// }else
		// {
		// System.out.println("ʧ��");
		// //����һ��dialog
		// AlertDialog alertDialog=new
		// AlertDialog.Builder(this).setMessage("��������ȷ����").create();
		// Window window=alertDialog.getWindow();
		// //���Ϸ�λ��
		// window.setGravity(Gravity.TOP);
		// WindowManager.LayoutParams lp=window.getAttributes();
		// //����͸����
		// lp.alpha=0.6f;
		// window.setAttributes(lp);
		// alertDialog.show();
		// }

	}

	// ���������ʱ��������˷���������Ϣ
	@Override
	protected void onDestroy() {
		try {

			//���͵ǳ���Ϣ
			ObjectOutputStream oos = new ObjectOutputStream(conServer.ct.getS()
					.getOutputStream());
			Common.Message m = new Common.Message();
			m.setMesType(MessageType.message_logout);
			oos.writeObject(m);
			oos.close();
			conServer.ct.getS().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
