/**
 * �����������������
 */
package com.lt.ConnectServer;

import java.net.*;
import java.io.*;

import Common.*;

public class conServer {

	public Socket s;
	public static ClientThread ct;
	private String name=null;
	public String SendInfo(Object user){
		
		try {
			s=new Socket("192.168.1.121", 9999);
			System.out.println("���ӳɹ�");
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(user);
			System.out.println("���ͳɹ�");
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			Message m=(Message)ois.readObject();
			System.out.println("���ճɹ�");
			System.out.println(m.getMesType());
			if(m.getMesType().equals(MessageType.message_login_success))
			{
				//�����߳�
				ct=new ClientThread(s);
				ct.start();
				name=m.getCon();
				return name;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}
}
