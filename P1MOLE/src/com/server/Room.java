package com.server;
import java.util.*;
public class Room {
   String roomName,roomState,roomPwd,roomBang;
   int inwon;
   int current;
   Vector<Server.ClientThread> userVc=
		   new Vector<Server.ClientThread>();
   public Room(String rn,String rs,String rp,int i)
   {
	   roomName=rn;
	   roomState=rs;
	   roomPwd=rp;
	   inwon=i;
	   current=1;
   }
}
