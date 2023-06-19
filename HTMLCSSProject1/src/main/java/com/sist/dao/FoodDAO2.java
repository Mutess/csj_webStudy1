package com.sist.dao;

import java.util.*;
import java.sql.*;
public class FoodDAO2 {
	//연결객체
		private Connection conn;
		//송수신
		private PreparedStatement ps;
		//오라클 URL주소 설정
		private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
		//싱글턴
		private static FoodDAO2 dao;
		//1. 드라이버 등록 => 한번만 수행 => 시작과 동시에 한번 수행, 멤버변수 초기화할때 사용 (생성자)
		public FoodDAO2() {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				//ClassNotFoundException => 체크예외처리 => 반드시 예외처리
				//java.io, java.net, java.sql => 체크 예외처리
			} catch (Exception ex) {}
		}
		//2. 오라클 연결
		public void getConnection () {
			try {
				conn=DriverManager.getConnection(URL,"hr","happy");
				// conn hr/happy => 명령어를 오라클 전송
			} catch (Exception ex) {}
		}
		//3. 오라클 해제
		public void disConnection() {
			try {
				if (ps!=null) ps.close(); //통신이 열러있으면 닫는다
				if (conn!=null) conn.close(); // 폰이 열려있으면 닫는다
				// exit() => 오라클 닫기
			} catch (Exception ex) {}
		}
		// 4. 싱글턴 설정 => 한개의 폰으로만 통신 => 그래서 static으로 설정 => 메모리가 1개만 가지고 있음
		// 메모리누수를 방지하기 위해 사용
		// DAO => new를 이용해서 생성 => 사용하지 않는 DAO가 오라클을 연결하고 있다
		// 싱글턴을 데이터베이스에서 필수 조건
		// 프로그래머!
		public static FoodDAO2 newInsatance() {
			if (dao==null)
				dao=new FoodDAO2();
			return dao;
		}
		public List<FoodVO> foodAllData() {
			List<FoodVO> list = new ArrayList<FoodVO>();
			try {
				getConnection();
				String sql="SELECT name,address,poster,phone,type "
						+ "FROM food_house "
						+ "ORDER BY fno ASC";
				ps=conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					FoodVO vo = new FoodVO();
					String name = rs.getString(1);
					vo.setName(name);
					String addr=rs.getString(2);
					addr=addr.substring(0,addr.lastIndexOf("지번"));
					vo.setAddress(addr.trim());
					String poster = rs.getString(3);
					poster=poster.substring(0,poster.indexOf("^"));
					//원자값
					poster=poster.replace("#", "&");
					vo.setPoster(poster);
					vo.setTel(rs.getString(4));
					vo.setType(rs.getString(5));
					list.add(vo);
				}
				rs.close();
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}finally {
				disConnection();
			}
			return list;
		}
}
