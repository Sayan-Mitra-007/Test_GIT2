package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.model.Register;

public class RegisterDao {

	Connection con=null;
	private String Driver="oracle.jdbc.OracleDriver";
	private String url="jdbc:oracle:thin:@localhost:1521:Orcl";
	private String username="system";
	private String password="Sayan123";
	
	public Connection MyConnection()
	{
		try{
		Class.forName(Driver);
		con=DriverManager.getConnection(url,username,password);
		System.out.println("Connection Established....");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Connection Failed!!!");
		}
		return con;
		
	}
	public int createData(Register r)
	{
		int i=0;
		try {
			
			Connection conn=MyConnection();
			PreparedStatement ps=conn.prepareStatement("insert into ltidb values(?,?,?,?,?)");
			ps.setInt(1,r.getRegno());
			ps.setString(2, r.getFname());
			ps.setString(3, r.getUname());
			ps.setString(4, r.getPass());
			ps.setFloat(5, r.getBal());
			
			i=ps.executeUpdate();
			
	}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return i;
	}
	
	public Register retrieveRecord(int regno)
	{
		Connection conn;
		Register r=null;
		try {
			conn=MyConnection();
			PreparedStatement ps=conn.prepareStatement("select * from ltidb where reg_no=?");
			ps.setInt(1, regno);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				r=new Register(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getFloat(5));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return r;
	}
	
	public int updateRecord(Register r)
	{
		Connection conn=MyConnection();
		int i=0;
		try {
			PreparedStatement ps=conn.prepareStatement("update ltidb set firstname=?,username=?,password=?,bal=? where reg_no=?");
			ps.setString(1,r.getFname());
			ps.setString(2,r.getUname());
			ps.setString(3,r.getPass());
			ps.setFloat(4,r.getBal());
			ps.setInt(5,r.getRegno());
			
			i=ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return i;
	}
	
	public int deleteRecord(int regno)
	{
		Connection conn=MyConnection();
		int result=0;
		try {
			PreparedStatement ps=conn.prepareStatement("delete from ltidb where reg_no=?");
			ps.setInt(1, regno);
			result=ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean loginCheck(String uname,String pass)
	{
		Connection conn=MyConnection();
		try
		{
			PreparedStatement ps=conn.prepareStatement("select * from ltidb where username=?");
			ps.setString(1, uname);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				if(pass.compareTo(rs.getString(4))==0)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
}
