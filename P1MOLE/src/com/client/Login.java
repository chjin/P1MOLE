package com.client;
import javax.swing.*;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.MalformedURLException;

public class Login extends JPanel implements MouseListener{
	//그림정보를 가져온다.
	Image loadImage, backImage, img, img1;	// 로고, 배경, 마우스 커서 이미지
	
	int lx=80; //로고시작위치
	int ly=90;
	int logoWidth=610;
	int logoHeight=300;
	
	//ID & PW & 로그인 & 회원가입버튼
	JLabel la1,IDLabel,PWLabel,la4;
	JTextField IDField,nf;
	JPasswordField PWField;
	JButton getNew,login;
	
	// 성별
	JRadioButton  M;
	JRadioButton  W;
	
	// 캐릭터
	
	
	// 마우스 커서용
	Cursor cursor,cursor1;
	
	// 마우스 커서 음성
	SoundSet MouseClickSound;
	
	public Login()
	{
		backImage=Toolkit.getDefaultToolkit().getImage("image/back.png");
		loadImage=Toolkit.getDefaultToolkit().getImage("image/logo.png");
		
		// 마우스용 이미지
		img=Toolkit.getDefaultToolkit().getImage("image/01.png");
		cursor=Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(0,0), "null");
		
		img1=Toolkit.getDefaultToolkit().getImage("image/02.png");
		cursor1=Toolkit.getDefaultToolkit().createCustomCursor(img1, new Point(0,0), "null");
		
		setLayout(null);
		
		//버튼들 메모리 할당
		
		IDLabel=new JLabel("ID");
		PWLabel=new JLabel("PW");
		la4=new JLabel("성별");
		
		
		IDField=new JTextField();
		PWField=new JPasswordField();
		
		M=new JRadioButton("남자");
		W=new JRadioButton("여자");
		
		// 성별 버튼 그룹
		ButtonGroup  group = new ButtonGroup();
		group.add(M);  group.add(W);
		M.setOpaque(false);
		W.setOpaque(false);
		M.setSelected(true);
		
		// 성별
		JPanel p = new JPanel();
		p.add(la4);p.add(M); p.add(W);
		
		//getNew=new JButton("회원가입");
		login=new JButton("Login");

		// 마우스 클릭 사운드
		MouseClickSound = new SoundSet();
		
		//배치 
		setLayout(null);
		
		IDLabel.setBounds(280,415,30,30);
		IDField.setBounds(315,415,150,30);
		PWLabel.setBounds(280,450,30,30);
		PWField.setBounds(315,450,150,30);
		p.setBounds(315,485,150,30);
		p.setOpaque(false);
		
		//getNew.setBounds(315,485,100,30);
		login.setBounds(470,415,65,65);

		//추가
		
		add(IDLabel);add(IDField);add(login);
		add(PWLabel);add(PWField);
		add(p);
		//add(getNew);
		
		// 마우스 이벤트 추가
		addMouseListener(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(backImage, 0, 0, getWidth(),getHeight(),this);
		g.drawImage(loadImage, lx,ly,logoWidth,logoHeight,this);	
	}
	
	// 마우스 이벤트
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {	// 마우스가 컴포넌트 위에서 눌렸을 때
		// TODO Auto-generated method stub
		setCursor(cursor1);
		MouseClickSound.SoundSet();
		MouseClickSound.clip1.play();
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {	// 마우스가 컴포넌트 위에서 눌리지 않았을 때
		// TODO Auto-generated method stub
		setCursor(cursor);
		repaint();
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
