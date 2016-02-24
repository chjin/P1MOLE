package com.server;
import java.util.*;

import com.common.Function;
import com.sist.dao.MemberDAO;
import com.sist.dao.MemberDTO;

import java.net.*;
import java.io.*;

public class Server implements Runnable {
	Vector<ClientThread> waitVc = new Vector<ClientThread>();
	Vector<Room>  roomVc=new Vector<Room>();	// 방에 입장해 있는 Client 저장용
	Vector<Rank> rankVc=new Vector<Rank>();
	ServerSocket ss = null;	// 서버에서 접속시 처리 (교환  소켓)
	
	public Server(){
		try {
			ss = new ServerSocket(5000);
			System.out.println("Server Start...");
		    } 
		catch (Exception ex) 
		{
			System.out.println(ex.getMessage());
		}
	}
	
	public void run(){
		//접속 처리
		while(true){
			try {
				// 클라이언트의 정보 => ip, port(Socket)
				Socket s = ss.accept();	// 클라이언트가 접속할때만 호출됨. 접속을 기다리고 있음
				// s => client
				ClientThread ct = new ClientThread(s);	// 전화기를 넘겨줌
				ct.start();	// 통신 시작
			} catch (Exception ex) {}
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 서버 가동
		Server server = new Server();
		new Thread(server).start();
	}
	
	class ClientThread extends Thread {
		String id, name, sex, pos ;
		int avata, rank, total;
		
		Socket s;
		BufferedReader in;	// 받을 때는 2byte	(Reader)	client 요청값을 읽어온다
		OutputStream out;	// 보낼 때는 byte	(Stream)	client로 결과값을 응답할 때
		
		public ClientThread(Socket s){
			try {
				this.s = s;
				in = new BufferedReader(new InputStreamReader(s.getInputStream()));	// byte --> 2byte로 변환 하여 값을 받는다.
				out = s.getOutputStream();		// 클라이언트가 원하는 값을 보낸다.
				
			} catch (Exception ex) {
				// TODO: handle exception
			}
		}
		
		// 통신 부분
		public void run(){
			while(true){
				try {
					String msg = in.readLine();		// 한줄씩 읽어들임	\n으로 구분한 이유	클라이언트가 보낸 값을 읽었다.
					System.out.println("Client=>" + msg);
					// 100|id|name|sex
					StringTokenizer st = new StringTokenizer(msg, "|");		// 구분해서 잘라넴
					int protocol = Integer.parseInt(st.nextToken());	// 번호 100번 잘라냄
					switch (protocol) {
					  case Function.LOGIN: {
						    id=st.nextToken();
						    //아래는 고정값으로..
						    pos="메인";
						    rank=10;
						    total=5000;
	    					
						    messageAll(Function.LOGIN +"|"+ id +"|"+ pos +"|"+rank+ "|" +total);
						    waitVc.addElement(this);
						    
						    messageTo(Function.MYLOG +"|"+ id);
						    
						    for(int i=0;i<waitVc.size();i++){
						    	ClientThread clientThread=waitVc.elementAt(i);
						    	if(!clientThread.id.equals(id))
						    		messageTo(Function.LOGIN +"|"+ clientThread.id +"|"+ clientThread.pos +"|"+clientThread.rank+ "|" +clientThread.total);
						    }
						    
						    for(int i=0;i<rankVc.size();i++){
						    	Rank rank=rankVc.elementAt(i);
						    	messageTo(Function.OVER +"|"+ rank.id +"|"+ rank.rank +"|"+ rank.total);
						    }   	
	    				}
	    				break;
	    				
					  case Function.LOGIN1: {
						  id=st.nextToken();
						  //아래는 고정값으로..
						  pos="메인";
						  rank=15;
						  total=6000;
						  
						  messageTo(Function.LOGIN1 +"|"+ rank +"|"+ id +"|"+ pos +"|"+ total);
						  
						  for(int i=0; i<waitVc.size();i++){
						    	ClientThread clientThread=waitVc.elementAt(i);
						    	if(!clientThread.id.equals(id))
						    		messageTo(Function.LOGIN1 +"|"+ clientThread.rank +"|"+ clientThread.id +"|"+clientThread.pos+ "|" +clientThread.total);
						  }
					  }
					  break;
	    				
					  //case Function.LOGIN2  여기서부터~~~~~
	    				
						case Function.WAITCHAT:	// 채팅
						{
							String data = st.nextToken();
							messageAll(Function.WAITCHAT + "|[" + name + "]" + data);
						}
						break;
						
						
						case Function.MAKEROOM:
						{
	    					Room room=new Room(st.nextToken(),
	    							  st.nextToken(),st.nextToken(),
	    							  Integer.parseInt(st.nextToken()));
	    					
	    					messageAll(Function.MAKEROOM+"|"
	    							  +room.roomName+"|"+
	    							  room.roomState+"|"+
	    							  room.current+"/"+room.inwon);
	    					
	    					room.roomBang=id;
	    					pos=room.roomName;
	    					room.userVc.addElement(this);
	    					roomVc.addElement(room);

	    				}
	    				break;
					}
				}
				
				
					
				 catch (Exception ex) {}
			}
				 
		}
		
			
		
		
		// 개인적으로
	public synchronized void messageTo(String msg)
	{
		try
		{
			out.write((msg+"\n").getBytes());
		}catch(Exception ex)
		{
			for(int i=0;i<waitVc.size();i++)
			{
				ClientThread client=waitVc.elementAt(i);
				if(id.equals(client.id))
				{
					waitVc.removeElementAt(i);
					break;
				}
			}
		}
	}
	// 전체적으로 전송 
	public synchronized void messageAll(String msg)
	{
		for(int i=0;i<waitVc.size();i++)
		{
			ClientThread client=waitVc.elementAt(i);
			try
			{
				client.messageTo(msg);
			}catch(Exception ex)
			{
				waitVc.removeElementAt(i);
			}
		}
	}
}
}
