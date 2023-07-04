package com.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sist.dao.*;
import java.util.*;
public class FoodDAO23 {
	// 기능 => INSERT => 데이터 수집 (파일)
		private Connection conn; //오라클 연결 객체 (데이터베이스 연결)
		private PreparedStatement ps; // SQL문장 전송 / 결과값 읽기
		private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
		// MySQL => jdbc:mysql://localhost/mydb
		private static FoodDAO23 dao; //(싱글톤 패턴) == 메모리 저장 공간 1개로 작동하는 것
		// DAO객체를 한개만 사용이 가능하게 만든다. 
		// 드라이버 설치 => 소프트웨어 (메모리 할당 요청) Class.forName()
		// 클래스의 정보를 전송
		// 드라이버 설치는 1번만 수행
		public FoodDAO23() {
			try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (Exception e) {}
		}
		// 오라클 연결
		public void getConnection() {
			try {
				conn = DriverManager.getConnection(URL,"hr","happy");
				// => 오라클 전송 : conn hr/happy
			} catch (Exception e) {}
		}
		// 오라클 연결 종료
		public void disconnection() {
			try {
				if (ps!=null) ps.close();
				if (conn!=null) conn.close();
				// => 오라클 전송 : exit
			} catch (Exception e) {}
		}
		// DAO객체를 1개만 생성해서 사용 => 메모리 누수현상을 방지 (싱글톤 패턴)
		// 싱글턴 / 팩토리 => 면접 (스프링 : 패턴 8개)
		public static FoodDAO23 newInstance() {
			// newInstance(), getInstance() => 싱글턴
			if (dao==null)
				dao=new FoodDAO23();
			return dao;
		}
		public List<FoodVO> foodListData(){
			List<FoodVO> list=new ArrayList<FoodVO>();
			try {
				getConnection();
				String sql="SELECT fno, poster, name, rownum "
						+ "FROM food_location "
						+ "WHERE rownum <= 20";
				ps=conn.prepareStatement(sql);
				ResultSet rs=ps.executeQuery();
				while(rs.next()) {
					FoodVO vo=new FoodVO();
					vo.setFno(rs.getInt(1));
					String poster=rs.getString(2);
					poster=poster.substring(0,poster.indexOf("^"));
					poster=poster.replace("#", "&");
					vo.setPoster(poster);
					vo.setName(rs.getString(3));
					list.add(vo);
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				disconnection();
			}
			return list;
		}
		public FoodVO fooddetailData(int fno) {
			FoodVO vo=new FoodVO();
			try {
				getConnection();
				String sql="SELECT fno, name, score, poster, address, type, parking, time, menu, tel ,price "
						+ "FROM food_location "
						+ "WHERE fno=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, fno);
				ResultSet rs=ps.executeQuery();
				rs.next();
				vo.setFno(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setScore(rs.getDouble(3));
				vo.setPoster(rs.getString(4));
				vo.setAddress(rs.getString(5));
				vo.setType(rs.getString(6));
				vo.setParking(rs.getString(7));
				vo.setTime(rs.getString(8));
				vo.setMenu(rs.getString(9));
				vo.setPhone(rs.getString(10));
				vo.setPrice(rs.getString(11));
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				disconnection();
			}
			return vo;
		}
}
