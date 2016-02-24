package com.client;

import java.awt.*; // layout
import java.awt.event.*;
import java.net.Socket;

import javax.swing.*; // window
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import com.client.Loading.Move;
import com.common.Function;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientMainForm extends JFrame implements ActionListener, Runnable, MouseListener {
	CardLayout card = new CardLayout();

	// 게임창 객체
	MoleGamePlay moleGamePlay = new MoleGamePlay();
	MoleGameView moleGameView = moleGamePlay.moleGameMyView;
	
	//먹물표시
	IndianInk indianInk=new IndianInk();
	
	//보너스 객체
	Bonus bonus= moleGameView.bonus;
	
	//게임창의 시간 알림바
	NotiBar notibar=moleGamePlay.notiMyBar;
	
	//제한시간 초과시 게임오버표시 객체
	GameOver gameOver;

	Login login = new Login();
	WaitRoom wr = new WaitRoom();
	Loading loading = new Loading();
	MakeRoom mr = new MakeRoom();
	ChatRoom cr=new ChatRoom();
	// GetNewone no=new GetNewone();
	GameRule gr = new GameRule();
	boolean b = false;

	// 마우스 커서 음성
	SoundSet MouseClickSound;
	
	// 게임창 레디 및 스타트 확인용
	boolean readyConfirm = false;
	boolean startConfirm = false;

	// id|대화명|성별
	Socket s;
	BufferedReader in;// 서버에서 값을 읽는다
	OutputStream out; // 서버로 요청값을 보낸다
	String myId,myRoom;
	
	public ClientMainForm() {
		setLayout(card); // BoarderLayout => CardLayout

		// 마우스 클릭 사운드
		MouseClickSound = new SoundSet();
		
		add("LOG", login); // 로그인창
		add("LOADING", loading);// 로딩화면
		add("WR", wr); // 대기실
		add("GAMERULE", gr); // 정보보기
		add("GAMEROOM", moleGamePlay); // 게임창

		// 윈도우창 제목과 크기 지정
		setTitle("잡아라두더지");
		setSize(800, 600);
		setVisible(true);
		setResizable(false);

		// 버튼 이벤트 등록

		// 로그인 창
		// login.getNew.addActionListener(this); // 회원가입
		login.login.addActionListener(this); // 로그인

		// 대기실 창
	    wr.table1.addMouseListener(this);
	    wr.table2.addMouseListener(this);
		wr.tf.addActionListener(this);
		wr.b1.addActionListener(this);
		wr.b2.addActionListener(this);
		wr.b5.addActionListener(this);
		wr.b6.addActionListener(this);
/*		cr.b1.addActionListener(this);
	    cr.b2.addActionListener(this);
	    cr.b3.addActionListener(this);
	    cr.tf.addActionListener(this);*/

		// 방만들기 창
		mr.b1.addActionListener(this);
		mr.b2.addActionListener(this);
		
		// 게임 리스너 추가
	    moleGamePlay.tf.addActionListener(this);
		moleGamePlay.jButtonStn.addActionListener(this);
		moleGamePlay.jButtonRdy.addActionListener(this);
		moleGamePlay.jButtonCancel.addActionListener(this);
		moleGamePlay.jButtonPause.addActionListener(this);
		moleGamePlay.jButtonExit.addActionListener(this);

		// 게임창 리스너 추가
		moleGameView.addMouseListener(this);
		moleGameView.timer.addActionListener(this);
		
		// 게임창 두더지 쓰레드
		moleGameView.thread = new Thread(moleGameView);
		
		// 먹물 이벤트 리스너 연결
		indianInk.timer.addActionListener(this);
		
		// 보너스 이미지 아이콘 버튼 리스너 추가
		bonus.jButton.addActionListener(this);
		

		// 게임규칙(정보보기) 창
		gr.b1.addActionListener(this);

		// 로딩 창
		loading.loadConfirm.addActionListener(this);
		
		// 윈도우 종료버튼 선택시 아무 것도 안함
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

	}

	// 서버와 연결
	public void connection(String id, String pwd, String sex) {
		try {
			s = new Socket("211.238.142.72", 9469);
			// s=>server
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = s.getOutputStream();
			out.write((Function.LOGIN + "|" + id + "|" + pwd + "|" + sex + "\n").getBytes());
		} catch (Exception ex) {}

		// 서버로부터 응답값을 받아서 처리
		new Thread(this).start();// run()
		wr.tf.setEnabled(true);// chat
	}

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// 예외처리
				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				ClientMainForm cm = new ClientMainForm();
			}			
		});
	}

	// 패널바꾸기
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == wr.tf) {

			String msg=wr.tf.getText().trim();
			if(msg.length()<1)
			return;
			
			try
			{
				out.write((Function.WAITCHAT+"|"+msg+"\n").getBytes());
			}catch(Exception ex){}
			wr.tf.setText("");
		}
		/*
		 * else if(e.getSource() == login.getNew){ card.show(getContentPane(),
		 * "GetNewOne"); no.setBounds(275,200,550,330); no.setVisible(true); }
		 */
		else if (e.getSource() == login.login) {
			MouseClickSound.SoundSet();
			MouseClickSound.clip1.play();

			String id = login.IDField.getText().trim();
			if (id.length() < 1) {
				JOptionPane.showMessageDialog(this, "ID를 입력하세요");
				login.IDField.requestFocus();
				return;
			}
			String pwd = String.valueOf(login.PWField.getPassword()).trim();
			if (pwd.length() < 1) {
				JOptionPane.showMessageDialog(this, "비밀번호를 입력하세요");
				login.nf.requestFocus();
				return;
			}
			String sex = "";
			if (login.M.isSelected())
				sex = "남자";
			else
				sex = "여자";
			connection(id, pwd, sex);
			
		} else if (e.getSource() == loading.loadConfirm && loading.loadFinish == true) {

			MouseClickSound.SoundSet();
			MouseClickSound.clip1.play();
			setTitle("대기실");
			card.show(getContentPane(), "WR");
			
		} else if (e.getSource() == wr.b1) {
			MouseClickSound.SoundSet();
			MouseClickSound.clip1.play();

			mr.tf.setText("");
			mr.rb1.setSelected(true);
			mr.box.setSelectedIndex(0);
			mr.la3.setVisible(false);
			mr.pf.setVisible(false);
			mr.setVisible(true);
			
		} else if (e.getSource() == wr.b2) {
			MouseClickSound.SoundSet();
			MouseClickSound.clip1.play();
			setTitle("게임방");
			//card.show(getContentPane(), "GAMEROOM");
		} else if (e.getSource() == wr.b6) {
			int confirmPopup=JOptionPane.showConfirmDialog(this, "정말로 나가시는거에요?", "선택", JOptionPane.YES_NO_OPTION);
			if(confirmPopup==JOptionPane.YES_OPTION){
				MouseClickSound.SoundSet();
				MouseClickSound.clip1.play();
				try
				{
					//out.write((Function.EXIT+"|\n").getBytes());
				}catch(Exception ex){
					ex.printStackTrace();
				}
				dispose();
				System.exit(0);
			}
			if(confirmPopup==JOptionPane.NO_OPTION){
				card.show(getContentPane(), "WR");
			}

		} else if(e.getSource() == mr.b1){
			String rn=mr.tf.getText().trim();
			if(rn.length()<1) {
				JOptionPane.showMessageDialog(this, "방이름을 입력하세요");
				mr.tf.requestFocus();
				return;
			}
			String temp="";
			for(int i=0;i<wr.model1.getRowCount();i++)
			{
				temp=wr.model1.getValueAt(i, 0).toString();
				if(rn.equals(temp))
				{
					JOptionPane.showMessageDialog(this, "이미 존재하는 방입니다\n다른 이름을 입력하세요");
					mr.tf.setText("");
					mr.tf.requestFocus();
					return;
				}
			}
			String state="",pwd="";
			if(mr.rb1.isSelected())
			{
				state="공개";
				pwd=" ";
			}
			else
			{
				state="비공개";
				pwd=String.valueOf(mr.pf.getPassword());
			}
			int inwon=mr.box.getSelectedIndex()+2;
			// 서버로 전송 
			try
			{
				out.write((Function.MAKEROOM+"|"+rn+"|"+state+"|"+pwd+"|"+inwon+"\n").getBytes());
			}catch(Exception ex){}
			mr.setVisible(false);
		}
		
		else if(e.getSource()==mr.b2)
		{
			mr.setVisible(false);
		}
/*		else if(e.getSource()==cr.b3)
		{
			try
			{
				out.write((Function.ROOMOUT+"|"+myRoom+"\n").getBytes());
			}catch(Exception ex){}
		}*/ else if (e.getSource() == wr.b5) {			// 정보보기
			MouseClickSound.SoundSet();
			MouseClickSound.clip1.play();
			card.show(getContentPane(), "GAMERULE");
		} else if (e.getSource() == gr.b1) {			// 정보보기 내부의 확인 버튼
			MouseClickSound.SoundSet();
			MouseClickSound.clip1.play();
			card.show(getContentPane(), "WR");
		}
		
		//////////////// 게임창 버튼 ////////////////////////////
		else if (e.getSource() == moleGamePlay.jButtonRdy) { // gameready
			MouseClickSound.SoundSet();
			MouseClickSound.clip1.play();
			
			// 사용자가 레디하면 레디값이 true로 변환
			moleGamePlay.jButtonRdy.setEnabled(false);
			readyConfirm = true;
		} else if(e.getSource() == moleGamePlay.jButtonCancel){	// gamecancel
			// 취소하면 레디가 풀리고 레디값이 false로 변환
			readyConfirm = false;
			moleGamePlay.jButtonRdy.setEnabled(true);
		} else if (e.getSource() == moleGamePlay.jButtonStn && (readyConfirm == true)) { // gamestart
			// 레디 하지 않으면 게임을 시작할 수 없다
			MouseClickSound.SoundSet();
			MouseClickSound.clip1.play();
			
			// 게임이 시작되면 스타트값이 true로 바뀌며, 게임시작을 알게된다
			startConfirm = true;
			
			moleGameView.thread=new Thread(moleGameView);	//스레드 생성 추가.
			moleGameView.thread.start();
			moleGameView.timer.start(); // 시간 제한 적용 구현중....
			moleGamePlay.jButtonStn.setEnabled(false);
			moleGamePlay.jButtonPause.setEnabled(true);
			moleGamePlay.jButtonExit.setEnabled(false);
			moleGamePlay.jButtonCancel.setEnabled(false);
		} else if(e.getSource()== moleGamePlay.jButtonPause && (startConfirm == true)){  // gamepause
			// 게임 도중에만 일시정지 할 수 있다
			moleGameView.moleImage=moleGameView.molesImage[4];
			moleGameView.repaint();
			
			moleGameView.thread.stop();
			moleGameView.timer.stop();
			moleGamePlay.jButtonStn.setEnabled(true);
			moleGamePlay.jButtonPause.setEnabled(false);
			// 일시정지 중에 방을 나갈 수 있다
			moleGamePlay.jButtonExit.setEnabled(true);
		} else if (e.getSource() == moleGamePlay.jButtonExit) { // gameexit
			//add 팝업
			int confirmPopup=JOptionPane.showConfirmDialog(this, "게임을 끝내시겠습니까?", "선택", JOptionPane.YES_NO_OPTION);
			if(confirmPopup==JOptionPane.YES_OPTION){
				try{
					out.write((Function.ROOMOUT +"|" +myRoom+ "\n").getBytes());
				}catch(Exception ex){
					ex.printStackTrace();
				}
				moleGameView.m_score=0;
				moleGameView.m_combo=0;
				moleGamePlay.jTextPane.setText(String.valueOf("0"));
				moleGameView.timerVar=3000;
				
				card.show(getContentPane(), "WR");
			}
			
			if(confirmPopup==JOptionPane.NO_OPTION){
				
			}
		}
		else if(e.getSource()==moleGamePlay.tf)
		{
			String msg=moleGamePlay.tf.getText().trim();
			if(msg.length()<1)
			return;
			
			try
			{
				//out.write((Function.GAMECHAT+"|"+msg+"\n").getBytes());
			}catch(Exception ex){}
			moleGamePlay.tf.setText("");
		}
		
		//먹물 히트시  이벤트 핸들러 내용 추가 
		if(indianInk.timer==e.getSource()){
			if(0<indianInk.m_timer){
				System.out.println(indianInk.m_timer);
				indianInk.m_timer--;
				indianInk.repaint();
			}else if(0==indianInk.m_timer){
				indianInk.timer.stop();
				indianInk.m_timer=2;	//초기 threadhold값으로 재설정.
				indianInk.setVisible(false);
			}
		}
		
		//NotiBar 표시갱신
		if(moleGameView.timer == e.getSource()){
			if(0<moleGameView.timerVar){
				moleGameView.timerVar--;
				moleGameView.repaint();
			}else if(0==moleGameView.timerVar){
				moleGameView.timer.stop();
				moleGameView.thread.interrupt();
				moleGameView.moleImage=moleGameView.molesImage[4];
				moleGameView.repaint();
				
				
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				String msg = in.readLine();
				//System.out.println("Server=>" + msg);
				StringTokenizer st = new StringTokenizer(msg, "|");
				// 100|id|sex|name
				int protocol = Integer.parseInt(st.nextToken());
				switch (protocol) {
				case Function.LOGIN:
				{
					String[] data={
							st.nextToken(),	 
							st.nextToken(),
							st.nextToken()
					};
					wr.model2.addRow(data);
				}
				break;
				
				case Function.MYLOG: {
					String id = st.nextToken();
					setTitle("Loading");
					myId=id;
					loading.new Move().start(); // thread working! 게이지바 차는 것을
												// 시작함
					card.show(getContentPane(), "LOADING");
				}
				break;
				
				case Function.WAITCHAT: {
					wr.ta.append(st.nextToken() + "\n");
					wr.bar.setValue(wr.bar.getMaximum());
				}
				break;
				
/*				case Function.GAMECHAT1: {
					moleGamePlay.ta.append(st.nextToken() + "\n");
					moleGamePlay.bar.setValue(moleGamePlay.bar.getMaximum());
				}
				break;*/
				

				
				case Function.MYCHATEND:
				{
					dispose();
					System.exit(0);
				}
				




				
				case Function.MAKEROOM:
				{
					String[] data={
						st.nextToken(),	
						st.nextToken(),	
						st.nextToken()
					};
					wr.model1.addRow(data);
				}
				break;


				case Function.ROOMIN:
				{
					String id=st.nextToken();
					String name=st.nextToken();
					String sex=st.nextToken();
					String avata=st.nextToken();
					String rb=st.nextToken();
					card.show(getContentPane(), "GAMEROOM");
					String[] data={id,name,sex};
					//cr.model.addRow(data);
					for(int i=0;i<2;i++)
					{
						if(!moleGamePlay.sw[i])
						{
							moleGamePlay.sw[i]=true;
							moleGamePlay.pan[i].setLayout(new BorderLayout());
							moleGamePlay.pan[i].removeAll();
							moleGamePlay.pan[i].add("Center",
									new JLabel(new ImageIcon("c:\\image\\"
							        +(sex.equals("남자")?"m":"w")+avata+".gif")));
							moleGamePlay.idtf[i].setText(name);
							if(id.equals(rb))
							{
								moleGamePlay.idtf[i].setForeground(Color.red);
							}
							moleGamePlay.pan[i].validate();
							break;
						}
					}
				}
				break;
				
				case Function.WAITUPDATE:
				{
					/*
					 *  +room.roomName+"|"
    									+room.current+"|"
    									+room.inwon+"|"
    									+id+"|"
    									+pos
					 */
					String rn=st.nextToken();
					String rc=st.nextToken();
					String ri=st.nextToken();
					String id=st.nextToken();
					String pos=st.nextToken();
					for(int i=0;i<wr.model1.getRowCount();i++)
					{
						String temp=wr.model1.getValueAt(i, 0).toString();
						if(rn.equals(temp))
						{
							if(Integer.parseInt(rc)<1)
							{
								wr.model1.removeRow(i);
							}
							else
							{
								wr.model1.setValueAt(rc+"/"+ri, i, 2);
							}
							break;
						}
					}
					for(int i=0;i<wr.model2.getRowCount();i++)
					{
						String temp=wr.model2.getValueAt(i,0).toString();
						if(id.equals(temp))
						{
							wr.model2.setValueAt(pos, i, 3);
							break;
						}
					}
				}
				break;

				case Function.ROOMOUT:
				{
					String id=st.nextToken();
					String name=st.nextToken();
					for(int i=0;i<2;i++)
					{
						String temp=moleGamePlay.idtf[i].getText();
						if(temp.equals(name))
						{
    						moleGamePlay.sw[i]=false;
    						moleGamePlay.idtf[i].setText("");
    						moleGamePlay.pan[i].removeAll();
    						moleGamePlay.pan[i].setLayout(new BorderLayout());
    						moleGamePlay.pan[i].add("Center",new JLabel(
    								new ImageIcon("c:\\image\\def.png")));
    						moleGamePlay.pan[i].validate();
    						break;
						}
					}
				}
				break;
				case Function.MYROOMOUT:
				{
					for(int i=0;i<2;i++)
					{
						moleGamePlay.sw[i]=false;
						moleGamePlay.idtf[i].setText("");
						moleGamePlay.pan[i].removeAll();
						moleGamePlay.pan[i].setLayout(new BorderLayout());
						moleGamePlay.pan[i].add("Center",new JLabel(
								new ImageIcon("c:\\image\\def.png")));
						moleGamePlay.pan[i].validate();
					}
					moleGamePlay.ta.setText("");
					moleGamePlay.tf.setText("");

					card.show(getContentPane(), "WR");
				}
				}
			}catch (Exception ex) {}
		}	
	}
		
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==wr.table2)
		{
			int row=wr.table2.getSelectedRow();
			String id=wr.model2.getValueAt(row, 0).toString();
			if(myId.equals(id))
			{
				wr.b3.setEnabled(false);
				wr.b4.setEnabled(false);
			}
			else
			{
				wr.b3.setEnabled(true);
				wr.b4.setEnabled(true);
			}
		}
		else if(e.getSource()==wr.table1)
		{
			if(e.getClickCount()==2)
			{
				int row=wr.table1.getSelectedRow();
				String rn=wr.model1.getValueAt(row, 0).toString();
				String rs=wr.model1.getValueAt(row, 1).toString();
				String ri=wr.model1.getValueAt(row, 2).toString();
				StringTokenizer st=
						new StringTokenizer(ri, "/");
				// 1/6  => 6/6
				if(Integer.parseInt(st.nextToken())== Integer.parseInt(st.nextToken()))
				{
					JOptionPane.showMessageDialog(this,
							"더이상 입장이 불가능 합니다");
					return;
				}
				try
				{
					//out.write((Function.MYROOMIN+"|"+rn+"\n").getBytes());
				}catch(Exception ex){}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

		// 게임뷰moleGameView에서 두더지 hit시에.
		System.out.println("mole is hited!");

		int x, y;
		x = e.getX();
		y = e.getY();

		// ground이미지 내에서 마우스 이벤트 발생하는 경우임
		if (y >= moleGameView.top && y <= (moleGameView.height + moleGameView.top)) {
			if (x >= moleGameView.left && x <= (moleGameView.left + moleGameView.width)) {
				// mole1.png 클릭시임.
				if (moleGameView.moleImage == moleGameView.molesImage[0]) {
					// mole1.png가 mole1Hit.png로 바뀜.
					moleGameView.moleImage = moleGameView.molesHitImage[0];
					//점수 반영
					if(moleGameView.m_combo<20){		//콤보점수 디폴드 0임.
						moleGameView.m_score+=100;		//두더지 한번 히트시 기본 100점 추가됨.
						moleGameView.m_combo++;			//콤보 점수 1증가.
						moleGamePlay.jTextPane.setText(String.valueOf(moleGameView.m_score));	//해당 점수 출력
					}else{
						//콤보점수가 20점 이상이면 아래 점수 반영
						moleGameView.m_score+=200;		//두더지  hit시에 기본 200점씩 추가됨.
						moleGameView.m_combo++;			//콤보 점수 1증가.
						moleGamePlay.jTextPane.setText(String.valueOf(moleGameView.m_score)); 	//해당 점수 출력
					}
					
					moleGameView.repaint();

					// mole2.png 클릭시임.
				} else if (moleGameView.moleImage == moleGameView.molesImage[1]) {
					// mole2.png가 mole2Hit.png로 바뀜.
					moleGameView.moleImage = moleGameView.molesHitImage[1];
					//점수 반영
					if(moleGameView.m_combo<20){		//콤보점수 디폴드 0임.
						moleGameView.m_score+=200;		//두더지  히트시 기본 200점 추가됨.
						moleGameView.m_combo++;			//콤보 점수 1증가.
						moleGamePlay.jTextPane.setText(String.valueOf(moleGameView.m_score));	//해당 점수 출력
					}else{
						//콤보점수가 20점 이상이면 클릭시부터 아래 점수 반영
						moleGameView.m_score+=400;		//두더지  hit시에 기본 400점씩 추가됨.
						moleGameView.m_combo++;			//콤보 점수 1증가.
						moleGamePlay.jTextPane.setText(String.valueOf(moleGameView.m_score)); 	//해당 점수 출력
					}
					moleGameView.repaint();

					// mole3.png 클릭시임.
				} else if (moleGameView.moleImage == moleGameView.molesImage[2]) {
					// mole2.png가 mole3Hit.png로 바뀜.
					moleGameView.moleImage = moleGameView.molesHitImage[2];
					//점수 반영
					if(moleGameView.m_combo<20){		//콤보점수 디폴드 0임.
						moleGameView.m_score+=300;		//두더지  히트시 기본 300점 추가됨.
						moleGameView.m_combo++;			//콤보 점수 1증가.
						moleGamePlay.jTextPane.setText(String.valueOf(moleGameView.m_score));	//해당 점수 출력
					}else{
						//콤보점수가 20점 이상이면 아래 점수 반영
						moleGameView.m_score+=600;		//두더지  hit시에 기본 600점씩 추가됨.
						moleGameView.m_combo++;			//콤보 점수 1증가.
						moleGamePlay.jTextPane.setText(String.valueOf(moleGameView.m_score)); 	//해당 점수 출력
					}
					
					moleGameView.repaint();

					// mole4.png 클릭시임.==> 두더지 아님.
				} else if (moleGameView.moleImage == moleGameView.molesImage[3]) {
					// mole4.png가 muk.jpg로 바뀜.==> 먹물 표시 이미지 표시되게 함.
					indianInk.setVisible(true);
					indianInk.timer.start();		//타이머 스레드 시작
					if(moleGameView.m_score<100){	//점수가 100미만인경우
						moleGameView.m_score=0;
					}else{							//100이상인 경우
						moleGameView.m_score-=100;	//100점씩 감점.
						moleGameView.m_combo=0;		//콤보 점수는 0.
					}							
					moleGameView.repaint();
					moleGamePlay.jTextPane.setText(String.valueOf(moleGameView.m_score));     //해당 점수 출력
				}

			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
