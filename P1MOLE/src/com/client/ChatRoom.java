package com.client;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;
public class ChatRoom extends JPanel{
    JPanel[] pan=new JPanel[6];
    // 클래스의 배열 선언 ==> null
    JTextField[] idtf=new JTextField[6];
    boolean[] sw=new boolean[6];
    JTextArea ta;
    JTextField tf;
    JComboBox box;
    JTable table;
    DefaultTableModel model;
    JButton b1,b2,b3;
    // 포함 클래스 ==> 익명의 클래스(재정의)
    public ChatRoom()
    {
    	for(int i=0;i<6;i++)
    	{
    		pan[i]=new JPanel();
    		pan[i].setBackground(Color.black);
    		idtf[i]=new JTextField();
    		idtf[i].setEditable(false);
    		idtf[i].setHorizontalAlignment(JLabel.CENTER);
    	}
    	ta=new JTextArea();
    	ta.setEnabled(false);
    	JScrollPane js1=new JScrollPane(ta);
    	tf=new JTextField();
    	box=new JComboBox();
    	b1=new JButton("강퇴");
    	b2=new JButton("초대");
    	b3=new JButton("나가기");
    	
    	String[] col={"ID","대화명","성별"};
    	String[][] row=new String[0][3];
    	model=new DefaultTableModel(row, col);
    	table=new JTable(model);
    	JScrollPane js2=new JScrollPane(table);
    	
    	// 배치
    	setLayout(null);
    	for(int i=0;i<6;i++)
    	{
    		if(i<3)
    		{
    			pan[i].setBounds(10+(i*190), 15, 180, 150);
    			add(pan[i]);
    			idtf[i].setBounds(10+(i*190), 170, 180, 30);
    			add(idtf[i]);
    		}
    		else
    		{
    			pan[i].setBounds(10+((i-3)*190), 210, 180, 150);
    			add(pan[i]);
    			idtf[i].setBounds(10+((i-3)*190), 365, 180, 30);
    			add(idtf[i]);
    		}
    	}
    	js1.setBounds(10, 400, 560, 120);
    	tf.setBounds(10,525,455, 30);
    	box.setBounds(470, 525, 100, 30);
    	js2.setBounds(580, 15, 190,350);
    	JPanel p=new JPanel();
    	p.setLayout(new GridLayout(3, 1,5,5));
    	p.add(b1);p.add(b2);p.add(b3);
    	p.setBounds(580, 370, 190, 185);
    	add(js1);
    	add(tf);
    	add(box);
    	add(js2);
    	add(p);
    	
    }
	

}
