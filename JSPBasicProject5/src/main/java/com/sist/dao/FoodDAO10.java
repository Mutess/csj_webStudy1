package com.sist.dao;


import java.sql.*;
import java.util.*;
//DBCP
import javax.sql.*;
import javax.naming.*;

public class FoodDAO10 {
	// 연결 객체 얻기
	private Connection conn;
	// SQL 송수신
	private PreparedStatement ps;
	//싱글톤
	private static FoodDAO10 dao;
	// 츨력 갯수
	private final int ROW_SIZE=20;
	//pool영역에서 Connection객체를 얻어 온다
	public void getConnection() {
		// Connection 객체 주소를 => 메모리에 저장
		// 저장 공간 => POOL 영역 (디렉토리형식으로 저장) => JNDI
		// 루트 => 저장공간
		// java://env/comp => C드라이버 => jdbc/oracle
		
		try {
			// 1. 탐색기를 연다
			Context init = new InitialContext();
			// 2. C드라이브 연결
			Context cdriver=(Context)init.lookup("java://comp/env");
			// lookup => 문자열(key) => 객체 찾기 (RMI) => Socket
			// 3. Connection 객체 찾기
			DataSource ds=(DataSource)cdriver.lookup("jdbc/oracle");
			// 4. Connection주소를 연결
			conn=ds.getConnection();
			// 282page
			// => 오라클 연결 시간을 줄인다
			// => Connection 객체가 일정하게 생성 => 관리
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	// Connection객체 사용후에 반환
	public void disConnection() {
		try {
			if (ps!=null) ps.close();
			if (conn!=null) conn.close();
		} catch (Exception ex) {}
		
	}
	// 싱글톤 객체 만들기
	public static FoodDAO10 newInstance() {
		if(dao==null) 
			dao=new FoodDAO10();
		return dao;
	}
	// 동일 => 오라클 연결 후 SQL문장 실행
	public List<FoodBean> foodListData(int page){
		List<FoodBean> list = new ArrayList<FoodBean>();
		try {
			// Connection의 주소를 얻어 온다
			getConnection();
			String sql="SELECT fno,poster,name,num "
					+ "FROM (SELECT fno,poster,name,rownum as num "
					+ "FROM (SELECT /*+ INDEX_ASC(food_location fl_fno_pk)*/fno,poster,name "
					+ "FROM food_location)) "
					+ "WHERE num Between ? AND ?";
			ps=conn.prepareStatement(sql);
			int rowSize=ROW_SIZE;
			int start=(page-1)*rowSize+1; //(rowSize*page)-(rowSize-1);
			int end=rowSize*page;
			// ?에 값을 채운다
			ps.setInt(1, start);
			ps.setInt(2, end);
			// 실행요청
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				FoodBean vo = new FoodBean();
				vo.setFno(rs.getInt(1));
				String poster=rs.getString(2);
				poster=poster.substring(0,poster.indexOf("^"));
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setName(rs.getString(3));
				list.add(vo);
			}
			rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			disConnection(); //반환
		}
		return list;
	}
	public int foodTotalpage() {
		int totalpage=0;
		try {
			getConnection();
			String sql="SELECT CEIL(COUNT(*)/"+ROW_SIZE+") "
					+ "FROM food_location";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			totalpage=rs.getInt(1);
			rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			disConnection();
		}
		return totalpage;
	}
	
	
}
