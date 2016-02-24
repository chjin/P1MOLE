package com.client;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Created by sist on 2016-02-11.
 */
public class MoleGameView extends JPanel implements Runnable, MouseMotionListener{

    NotiBar notiMyBar=new NotiBar();

    Rectangle[] rectangles=new Rectangle[9];
    Image[] molesImage=new Image[5];
    Image[] molesHitImage=new Image[5];
    Image[] combosImage=new Image[11];
    Image moleImage, groundImage,hammerImage,comboImage;
    //콤보 조건 만족시 보너스 점수 추가 클래스.
    Bonus bonus=new Bonus();

    String[] stringsImage={
        "image/mole1.png","image/mole2.png",
        "image/mole3.png","image/mole4.png",
        ""
    };
    
    String[] stringsImageHit={
    	"image/mole1Hit.png","image/mole2Hit.png",
    	"image/mole3Hit.png","image/mole4Hit.png",
    	""
    };

    String[] stringsCombo={
        "image/0.png","image/1.png",
        "image/2.png","image/3.png",
        "image/4.png","image/5.png",
        "image/6.png","image/7.png",
        "image/8.png","image/9.png",
        "image/10.png",
    };

    int c_combo=0;      //보통 게임에서 콤보는 연속성을 지니는 기술이나 행위를 말한다.
                         //Normal Mole, Luck Mole을 miss없이 가격하면 콤보가 증가합니다
                         //두더지를 놓치지 않고 맞추면 콤보가 올라 갑니다.
    int m_combo=0;      //콤보 조건 확인시 필요.
    int m_score=0;		//hit시에 게임 점수 계산시 사용할 변수.

    int left,top,width,height;
    int stringsNumber2=0;
    int count=0;
    int timerVar=600;

    JLabel jLabel;
    int hammerX,hammerY;

    //게임 시간 체크용도
    Timer timer=new Timer(10,null);

    //MoleGameView 스레드를 실행(run())하기 위한 용도로 스레드 추가
    Thread thread;

    public MoleGameView(){
        this.addMouseMotionListener(this);

        groundImage=Toolkit.getDefaultToolkit().getImage("image/gameMap.png");
        hammerImage=Toolkit.getDefaultToolkit().getImage("image/01.png");

        for(int i=0;i<9;i++){
            rectangles[i]=new Rectangle();
        }

        //두더지 위치 설정. 두더지 크기는 100x100 가로여백 20, 세로여백 5
        rectangles[0].setRect(30,100,130,200);	// 1번 행 1번 여백 x로 20씩
        rectangles[1].setRect(150,100,250,200); // 1번 행 2번
        rectangles[2].setRect(270,100,370,200);	// 1번 행 3번
        rectangles[3].setRect(30,205,130,310);	// 2번 행 1번 
        rectangles[4].setRect(150,205,250,310);	// 2번 행 2번
        rectangles[5].setRect(270,205,370,310);	// 2번 행 3번
        rectangles[6].setRect(30,310,130,420); 	// 3번 행 1번
        rectangles[7].setRect(150,310,250,420); // 3번 행 2번
        rectangles[8].setRect(270,310,370,420);	// 3번 행 3번

        jLabel=new JLabel();
        jLabel.setBounds(20,20,100,50);
        jLabel.setText(0+"");
        this.add(jLabel);

        for(int i=0;i<5;i++){
            molesImage[i]=Toolkit.getDefaultToolkit().getImage(stringsImage[i]);
        }
        
        for(int j=0;j<5;j++){
            molesHitImage[j]=Toolkit.getDefaultToolkit().getImage(stringsImageHit[j]);
        }

        for(int k=0;k<11;k++){
            combosImage[k]=Toolkit.getDefaultToolkit().getImage(stringsCombo[k]);
        }

    }

    @Override
    public void paint(Graphics g) {
    	notiMyBar.jProgressBar.setValue(timerVar);
    	notiMyBar.jProgressBar.setString(timerVar+" ");
    	notiMyBar.jProgressBar.setStringPainted(false);
        g.drawImage(groundImage,0,0,400,450,this);
        g.drawImage(moleImage,left,top,width,height,this);
        g.drawImage(hammerImage,hammerX-35,hammerY-35,70,70,this);
        g.drawImage(comboImage,30,10,60,60,this);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.setCursor(this.getToolkit().createCustomCursor(
                new BufferedImage(3,3,BufferedImage.TYPE_INT_ARGB),new Point(0,0),"null"));
        hammerX=e.getX();
        hammerY=e.getY();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.setCursor(this.getToolkit().createCustomCursor(
                new BufferedImage(3,3,BufferedImage.TYPE_INT_ARGB),new Point(0,0),"null"));
        hammerX=e.getX();
        hammerY=e.getY();
        repaint();

    }

    //두더지 나왔다 들어갔다하는 부분 스레드로 구현.
    @Override
    public void run() {
        try{
            while(true){
                setImage();             //콤보 이미지, 큰 망치 이미지 출력
                if(timerVar>2000)
                    Thread.sleep(1100);		//1100  
                else if(timerVar>800)
                    Thread.sleep(1000);		//1000
                else
                    Thread.sleep(900);		//900

                if(c_combo>0){
                    if(m_combo !=c_combo){
                        m_combo=0;
                        c_combo=0;
                    }
                }

                //연속해서 3번 두더지 hit시에 보너스 점수 부여함.
                if(c_combo==3 && timerVar>100){		//1000
                    c_combo=0;
                    
                    bonus.timer.start();
                    moleImage=molesImage[4];
                    repaint();
                    bonus.setVisible(true);
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setImage(){
        //랜덤 두더지 이미지
        int no=(int)(Math.random()*4);
        moleImage=molesImage[no];

        if(moleImage==molesImage[0] || moleImage==molesImage[1] || moleImage==molesImage[2]){
            c_combo++;
        }
        //랜덤 이미지 위치
        int i= (int)(Math.random()*9);//(int)(Math.random()*3);////////////////////////////////
        left=rectangles[i].getLeft();
        top=rectangles[i].getTop();
        width=rectangles[i].getWidth();
        height=rectangles[i].getHeight();

        switch(m_combo){
            case 0:{
                comboImage=combosImage[0];          //콤보숫자가 나타내기 위해 초기화.
                break;
            }
            case 1:{
                comboImage=combosImage[1];
                break;
            }
            case 2:{
                comboImage=combosImage[2];
                break;
            }
            case 3:{
                comboImage=combosImage[3];
                break;
            }
            case 4:{
                comboImage=combosImage[4];
                break;
            }
            case 5:{
                comboImage=combosImage[5];
                break;
            }
            case 6:{
                comboImage=combosImage[6];
                break;
            }
            case 7:{
                comboImage=combosImage[7];
                break;
            }
            case 8:{
                comboImage=combosImage[8];
                break;
            }
            case 9:{
                comboImage=combosImage[9];
                break;
            }
            case 10:{
                comboImage=combosImage[10];
                break;
            }
        }
        repaint();
    }


}