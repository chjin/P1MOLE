package com.client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class GameRule extends JPanel implements MouseListener{
	
	Image back, img, img1;	// 배경, 마우스 커서 이미지
	JButton b1;//버튼  
	
	// 마우스 커서용
	Cursor cursor,cursor1;
	
	// 마우스 커서 음성
	SoundSet MouseClickSound;
	
	public GameRule()
	{	
		back = Toolkit.getDefaultToolkit().getImage("image/gr.png");		// 배경이미지

		// 마우스용 이미지
		img=Toolkit.getDefaultToolkit().getImage("image/01.png");
		cursor=Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(0,0), "null");
				
		img1=Toolkit.getDefaultToolkit().getImage("image/02.png");
		cursor1=Toolkit.getDefaultToolkit().createCustomCursor(img1, new Point(0,0), "null");
		
		// 마우스 클릭 사운드
		MouseClickSound = new SoundSet();
		
		b1=new JButton("돌아가기 >>");
		
		setLayout(null);
		
		b1.setBounds(630,450,100,50);
		b1.setOpaque(true);
	
		add(b1);
		
		// 마우스 이벤트 추가
		addMouseListener(this);
		
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(back, 0, 0, getWidth(), getHeight(), this);
	}
	
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