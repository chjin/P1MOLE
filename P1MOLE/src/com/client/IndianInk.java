package com.client;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.Timer;

public class IndianInk extends JDialog{
	Image indianInkImage;
	
	int m_timer=2;			//threadhold용도 .
	Timer timer=new Timer(400,null);		//타이머
	
	public IndianInk(){
		indianInkImage=Toolkit.getDefaultToolkit().getImage("image/IndianInk.jpg");
		this.setUndecorated(true); 			//다이올로그 비활성화.
		setSize(350,350);
		setLocation(60,140);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(indianInkImage, 0, 0, getWidth(),getHeight(),this);
	}
	
	
}





























