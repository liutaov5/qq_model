//�ͻ��˵��߳�
package com.lt.ConnectServer;

import java.net.*;
import java.io.*;

import Common.*;

public class ClientThread extends Thread {

	private Socket s;
	private MessageListener ml;
	private boolean isStart;
	ObjectInputStream ois;
	


	public MessageListener getMl() {
		return ml;
	}

	public void setMl(MessageListener ml) {
		this.ml = ml;
	}

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
	}

	public ClientThread(Socket s)
	{
		this.s=s;
	}
	
	public void run()
	{
		isStart=true;
		while(isStart)
		{
			try {
				ois = new ObjectInputStream(s.getInputStream());
				Message m=(Message)ois.readObject();
				//ͨ���ӿڽ����յ�����Ϣ���ݳ�ȥ��������Ϣ���ͽ��в�ͬ�Ĵ���	
				ml.Message(m);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		try {
			if (ois != null)
				ois.close();
			if(s!=null)
				s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
