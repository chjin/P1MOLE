package com.client;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.Timer;

public class Bonus extends JDialog implements ActionListener{
	
	JPanel jPanel;
	JButton jButton;
	
	ImageIcon imageIcon;
	
	int m_timer=3;
	Timer timer=new Timer(1000,this);
	
	public Bonus(){
		setLayout(null);
		jButton=new JButton(new ImageIcon(".\\image\\bigMole.jpg"));
		jButton.setBounds(100,10,10,10);
		jButton.setMargin(new Insets(0,0,0,0));
		
		jButton.setContentAreaFilled(false);
		this.add(jButton);
		
		imageIcon=new ImageIcon(".\\image\\bonus.jpg");
		
		this.setSize(350,350);
		this.setLocation(60,140);
		this.setModal(true);
		this.setUndecorated(true);
		
	}
	
	//보너스 이미지 그리기
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(imageIcon.getImage(),0,0,getWidth(),getHeight(),this);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if(timer==e.getSource()){
			if(0<m_timer){
				System.out.println(m_timer);
				m_timer --;
			}else if(0==m_timer){
				timer.stop();
				m_timer=3;
				setVisible(false);
			}
		}
		
	}

}























