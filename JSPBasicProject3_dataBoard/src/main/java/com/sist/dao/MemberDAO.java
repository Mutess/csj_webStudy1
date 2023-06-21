package com.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MemberDAO {
		//연결 객체 => Socket
		private Connection conn;
		//송수신 (SQL => 결과값(데이터값))
		private PreparedStatement ps;
		//URL
		private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
		//싱글톤
		private static MemberDAO dao;
		//static => 저장공간의 한계
		//드라이버 등록\
		public MemberDAO() {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (Exception ex) {}
		}
		//오라클 연결
		public void getConnection() {
			try {
				conn=DriverManager.getConnection(URL,"hr","happy");
			} catch (Exception ex) {}
		}
		//오라클 해제
		public void disConnection() {
			try {
				if(ps!=null) ps.close();
				if(conn!=null) conn.close();
			} catch (Exception ex) {}
		}
		//싱글턴 처리
		public static MemberDAO newInstance() {
			if (dao == null) 
				dao=new MemberDAO();
			return dao;
		}
		//기능
		
}
