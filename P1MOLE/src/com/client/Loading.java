package com.client;

import java.awt.*;
import java.awt.event.*;

import javax.print.DocFlavor.STRING;
import javax.swing.*;
// Loading Page 로딩화면
public class Loading extends JPanel implements MouseListener {
	Image loadImage, backImage, img, img1;		// 추가이미지, 배경화면, 마우스 커서 이미지
	JLabel loadTitle, loadContext1, loadContext2;	// Loading!!! 출력용
	JButton loadConfirm;			// Start Button 스타트 버튼
	JProgressBar percentBar;		// Loading Bar 진행도 나타내는 막대
	boolean loadFinish;				// 로딩 완료됐는지 확인
	
	// 마우스 커서용
	Cursor cursor,cursor1;
	
	// 마우스 커서 음성
	SoundSet MouseClickSound;
	
	// 이미지 불러오고, 각종 컴포넌트 초기화
	public Loading() {
		
		backImage = Toolkit.getDefaultToolkit().getImage("image/loadBack.png");		// 배경이미지

		// 마우스용 이미지
		img=Toolkit.getDefaultToolkit().getImage("image/01.png");
		cursor=Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(0,0), "null");
				
		img1=Toolkit.getDefaultToolkit().getImage("image/02.png");
		cursor1=Toolkit.getDefaultToolkit().createCustomCursor(img1, new Point(0,0), "null");
		
		// 마우스 클릭 사운드
		MouseClickSound = new SoundSet();
		
		percentBar = new JProgressBar(JProgressBar.HORIZONTAL,0,100);
		loadTitle = new JLabel("Loading!!!");
		
		loadContext1 = new JLabel("어느날 밤, 평화로운 숲속에 두더지 악당이 나타나 나무들에게 당장 이곳을 떠나라고 말했어요.");
		loadContext2 = new JLabel("곧 나무들은 두더지에게 자신들의 숲을 빼앗길 운명이란 것을 알게 되었죠.");
		loadConfirm = new JButton("입장");
		loadFinish = false;
		
		setLayout(null);			// ClientMain의 레이아웃을 해제시키고, 새로 위치를 배치할 수 있도록 함
		loadTitle.setFont(new Font("맑은고딕", Font.BOLD, 24));		// 폰트 지정 맑은고딕, 굵게, 크기 24
		loadContext1.setFont(new Font("맑은고딕", Font.BOLD, 15));
		loadContext1.setForeground(Color.black);
		loadContext2.setFont(new Font("맑은고딕", Font.BOLD, 15));
		loadContext2.setForeground(Color.black);
		loadContext1.setBounds(90, 480, 750, 30);
		loadContext2.setBounds(90, 510, 750, 30);
		loadConfirm.setBounds(720, 490, 70, 50);
		percentBar.setBounds(10, 550, 775, 30);
		
		add(loadTitle);
		add(loadContext1);
		add(loadContext2);
		add(loadConfirm);
		add(percentBar);
		
		// 마우스 이벤트 추가
		addMouseListener(this);
	}
	
	@Override	// 그림 출력부
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backImage, 0, 0, getWidth(), getHeight(), this);
		
	}
	
	// Thread 쓰레드
	class Move extends Thread {
		public void run() {
			try {										// 예외처리 num이 0~100으로 증가하면서 게이지바를 그려줌
				int num = 0;
				while (true) {
					System.out.println(num);			// 콘솔창에 게이지가 잘 작동하는지 출력
					percentBar.setValue(num);			// 화면에 게이지 0% -> 100% 출력
					percentBar.setStringPainted(true);	// 게이지 차오르는 것 출력
					num++;
					Thread.sleep(30);					// millisecond 단위  1초는 1000 // 출력 1번당 70만큼 지연을 줌
					if (num > 100){						// 100% 도달시
						Thread.interrupted();			// Thread death 쓰레드 소멸
						loadFinish = true;
						break;
					}
				}
			} catch (Exception e) {}
		}
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


