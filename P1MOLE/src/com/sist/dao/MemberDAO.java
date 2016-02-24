package com.sist.dao;
import java.sql.*;
import java.util.*;

public class MemberDAO {
    private Connection conn;
    private PreparedStatement ps;
    private static MemberDAO dao;
    private final String URL="jdbc:oracle:thin:@211.238.142.72:1521:ORCL";
    
    public MemberDAO()
    {
    	try
    	{
    		Class.forName("oracle.jdbc.driver.OracleDriver");
    	}catch(Exception ex)
    	{
    		System.out.println(ex.getMessage());
    	}
    }
    public static MemberDAO newInstance()
    {
    	if(dao==null)
    		dao=new MemberDAO();
    	return dao;
    }
    public void getConnection()
    {
    	try
    	{
    		conn=DriverManager.getConnection(URL,"scott","tiger");
    		//  conn scott/tiger
    	}catch(Exception ex){}
    }
    public void disConnection()
    {
    	try
    	{
    		if(ps!=null) ps.close();
    		// InputStream,OutputStream
    		if(conn!=null) conn.close();
    		// socket.close()
    		// exit
    	}catch(Exception ex){}
    }
    public String isLogin(String id,String pwd)
    {
    	String res="";
    	try
    	{
    		// 연결
    		getConnection();
    		String sql="SELECT COUNT(*) FROM member WHERE id=?";
    		ps=conn.prepareStatement(sql);
    		ps.setString(1, id);
    		ResultSet rs=ps.executeQuery();
    		rs.next();
    		int count=rs.getInt(1);
    		if(count==0)
    		{
    			res="NOID";
    		}
    		else
    		{
    			sql="SELECT pwd FROM member WHERE id=?";
    			ps=conn.prepareStatement(sql);
    			ps.setString(1, id);
    			rs=ps.executeQuery();
    			rs.next();
    			String db_pwd=rs.getString(1);
    			if(db_pwd.equals(pwd))
    			{
    				res="OK";
    			}
    			else
    			{
    				res="NOPWD";
    			}
    		}
    	}catch(Exception ex)
    	{
    		System.out.println(ex.getMessage());
    	}
    	finally
    	{
    		disConnection();
    	}
    	return res;
    }
    public MemberDTO memberInfoData(String id)
    {
    	MemberDTO d=new MemberDTO();
    	try
    	{
    		getConnection();
    		String sql="SELECT * FROM member WHERE id=?";
    		ps=conn.prepareStatement(sql);
    		ps.setString(1, id);
    		ResultSet rs=ps.executeQuery();
    		rs.next();
    		d.setId(rs.getString(1));
    		d.setPwd(rs.getString(2));
    		d.setName(rs.getString(3));
    		d.setAvata(rs.getInt(4));
    		d.setSex(rs.getString(5));
    		d.setType(rs.getInt(6));
    	}catch(Exception ex)
    	{
    		System.out.println(ex.getMessage());
    	}
    	finally
    	{
    		disConnection();
    	}
    	return d;
    }
    public void memberUpdate(String id,int type)
    {
    	try
    	{
    		getConnection();
    		String sql="UPDATE member SET "
    			      +"type=? "
    				  +"WHERE id=?";
    		ps=conn.prepareStatement(sql);
    		ps.setInt(1, type);
    		ps.setString(2, id);
    		ps.executeUpdate();
    	}catch(Exception ex)
    	{
    		System.out.println(ex.getMessage());
    	}
    	finally
    	{
    		disConnection();
    	}
    }
    public void memberAllUpdate()
    {
    	try
    	{
    		getConnection();
    		String sql="UPDATE member SET "
    			      +"type=0";	  
    		ps=conn.prepareStatement(sql);
    		
    		ps.executeUpdate();
    	}catch(Exception ex)
    	{
    		System.out.println(ex.getMessage());
    	}
    	finally
    	{
    		disConnection();
    	}
    }
}






