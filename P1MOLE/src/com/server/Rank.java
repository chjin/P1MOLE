package com.server;

import java.util.Vector;

public class Rank {
	String id;
	int rank;
	int total;
	
	Vector<Server.ClientThread> userVc=new Vector<Server.ClientThread>();
	
	public Rank(int rank, String id, int total){
		this.rank=rank;
		this.id=id;
		this.total=total;
	}
}


























