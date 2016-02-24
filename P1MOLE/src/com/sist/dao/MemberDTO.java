package com.sist.dao;

public class MemberDTO {
    private String id;
    private String pwd;
    private String name;
    private int avata;
    private String sex;
    private int type;
    
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAvata() {
		return avata;
	}
	public void setAvata(int avata) {
		this.avata = avata;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
   
}
