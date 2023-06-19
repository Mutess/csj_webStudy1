package com.sist.dao;

import java.util.*;
import java.sql.*;
public class MemberDAO {
	private Connection conn; //오라클 연결 객체 (데이터베이스 연결)
	private PreparedStatement ps; // SQL문장 전송 / 결과값 읽기
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	// MySQL => jdbc:mysql://localhost/mydb
	private static MemberDAO dao; //(싱글톤 패턴) == 메모리 저장 공간 1개로 작동하는 것
	// DAO객체를 한개만 사용이 가능하게 만든다. 
	// 드라이버 설치 => 소프트웨어 (메모리 할당 요청) Class.forName()
	// 클래스의 정보를 전송
	// 드라이버 설치는 1번만 수행
	public MemberDAO() {
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
	public static MemberDAO newInstance() {
		// newInstance(), getInstance() => 싱글턴
		if (dao==null)
			dao=new MemberDAO();
		return dao;
	}
	//5. 우편번호 검색
	public List<ZipcodeVO> postfind(String dong) { //동으로 찾음
		List<ZipcodeVO> list = new ArrayList<ZipcodeVO>();
		try {
			getConnection();
			String sql="SELECT zipcode,sido,gugun,dong,NVL(bunji,' ') "
					+ "FROM zipcode "
					+ "WHERE dong LIKE '%'||?||'%'";
			ps=conn.prepareStatement(sql);
			ps.setString(1, dong);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				ZipcodeVO vo = new ZipcodeVO();
				vo.setZipcode(rs.getString(1));
				vo.setSido(rs.getString(2));
				vo.setGugun(rs.getString(3));
				vo.setDong(rs.getString(4));
				vo.setBunji(rs.getString(5));
				list.add(vo);
			}
			rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			disconnection();
		}
		return list;
	}
	public int postfindcount(String dong) { //동으로 찾음
		int count=0;
		try {
			getConnection();
			String sql="SELECT COUNT(*) "
					+ "FROM zipcode "
					+ "WHERE dong LIKE '%'||?||'%'";
			ps=conn.prepareStatement(sql);
			ps.setString(1, dong);
			ResultSet rs=ps.executeQuery();
			rs.next();
			count=rs.getInt(1);
			rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			disconnection();
		}
		return count;
	}
}
