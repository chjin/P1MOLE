package com.client;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sist on 2016-02-11.
 */
public class MoleGamePlay extends JPanel {
    MoleGameView moleGameMyView=new MoleGameView();
    //MoleGameView moleGameYourView=new MoleGameView();
    // 아바타
    JPanel[] pan=new JPanel[2];
    // 접속 여부 확인
    boolean[] sw=new boolean[2];
    // 접속 ID
    JTextField[] idtf=new JTextField[2];

	JTextField tf;
	JTextArea ta;
	JScrollBar bar;
	
	// 각종 게임 구성부
    JButton jButtonStn, jButtonRdy, jButtonPause, jButtonCancel, jButtonExit;
    NotiBar notiMyBar=moleGameMyView.notiMyBar;
    JTextPane jTextPane;
    JLabel scoreJLabel;
    Image image,cursorImage;
    Cursor cursor;

    public MoleGamePlay(){
    	for(int i=0;i<2;i++)
    	{
    		pan[i]=new JPanel();
    		pan[i].setBackground(Color.black);
    		idtf[i]=new JTextField();
    		idtf[i].setEditable(false);
    		idtf[i].setHorizontalAlignment(JLabel.CENTER);
    	}
    	// 게임내 채팅창
    	ta = new JTextArea();
    	ta.setEditable(true);
    	JScrollPane js = new JScrollPane(ta);
    	bar=js.getVerticalScrollBar();
    	tf=new JTextField();
    	
    	pan[0].setBounds(455, 15, 150, 150);
    	add(pan[0]);
    	idtf[0].setBounds(455, 170, 150, 30);
    	add(idtf[0]);
    	
    	pan[1].setBounds(610, 15, 150, 150);
    	add(pan[1]);
    	idtf[1].setBounds(610, 170, 150, 30);
    	add(idtf[1]);
    	
    	// 게임내 채팅창 위치
    	js.setBounds(455,220, 305, 160);
    	tf.setBounds(455,390, 305, 30);
    	
    	// 채팅창
    	add(js);
    	add(tf);
    	
    	scoreJLabel=new JLabel(new ImageIcon("image/score.png"));	//스코어 이미지 위치
    	scoreJLabel.setBounds(10,10,144,44);
    	
    	
    	
    	
        jTextPane=new JTextPane();							//스코어  위치
        jTextPane.setEditable(false);
        jTextPane.setBounds(154,10,180,43);
        jTextPane.setText("0");
        jTextPane.setFont(new Font("Pompadour",50,35));

        jButtonStn=new JButton(new ImageIcon("image/start.png"));
        jButtonStn.setBounds(10,20,144,44);
        jButtonStn.setBorderPainted(false);
        jButtonStn.setContentAreaFilled(false);

        jButtonRdy=new JButton(new ImageIcon("image/ready.png"));
        jButtonRdy.setBounds(184,20,144,44);
        jButtonRdy.setBorderPainted(false);
        jButtonRdy.setContentAreaFilled(false);

        jButtonPause=new JButton(new ImageIcon("image/pause.png"));
        jButtonPause.setBounds(10,74,144,44);
        jButtonPause.setBorderPainted(false);
        jButtonPause.setContentAreaFilled(false);

        jButtonCancel=new JButton(new ImageIcon("image/cancel.png"));
        jButtonCancel.setBounds(184,74,144,44);
        jButtonCancel.setBorderPainted(false);
        jButtonCancel.setContentAreaFilled(false);
        
        jButtonExit=new JButton(new ImageIcon("image/exit.png"));
        jButtonExit.setBounds(10,128,144,44);
        jButtonExit.setBorderPainted(false);
        jButtonExit.setContentAreaFilled(false);

        JLabel gamemenuJLabel=new JLabel();
        gamemenuJLabel.setLayout(null);
        gamemenuJLabel.setBounds(440,400,338,192);
        gamemenuJLabel.add(jButtonStn);	gamemenuJLabel.add(jButtonRdy);
        gamemenuJLabel.add(jButtonPause);	gamemenuJLabel.add(jButtonCancel);
        gamemenuJLabel.add(jButtonExit);

        image=Toolkit.getDefaultToolkit().getImage("image/back.png");
        cursorImage=Toolkit.getDefaultToolkit().getImage("image/01.png");
        cursor=Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0,0), "null");
        this.setCursor(cursor);
        this.setLayout(null);
        moleGameMyView.setBounds(26,70,400,450);
        notiMyBar.setBounds(15,530,420,30);			//타이머 노티 영역 

        this.add(notiMyBar);
        this.add(scoreJLabel);
        this.add(jTextPane);
        this.add(moleGameMyView);
        this.add(gamemenuJLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(image,0,0,getWidth(),getHeight(),this);
    }
}