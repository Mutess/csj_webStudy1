package com.sist.dao;

import java.util.*;
import java.sql.*;
public class SeoulDAO {
	//연결객체
		private Connection conn;
		//송수신
		private PreparedStatement ps;
		//오라클 URL주소 설정
		private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
		//싱글턴
		private static SeoulDAO dao;
		//1. 드라이버 등록 => 한번만 수행 => 시작과 동시에 한번 수행, 멤버변수 초기화할때 사용 (생성자)
		public SeoulDAO() {
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
		public static SeoulDAO newInsatance() {
			if (dao==null)
				dao=new SeoulDAO();
			return dao;
		}
		//5. 기능
		//목록 => SQL(인라인뷰 => 페이징 기법)
		public List<SeoulVO> locationListData(int page) { //그림12개를 하나의 List로 모아서 전송
			List<SeoulVO> list = new ArrayList<SeoulVO>();
			try {
				getConnection();
				String sql = "SELECT no,title, poster, num "
						+ "FROM (SELECT no,title, poster, rownum as num "
						+ "FROM (SELECT no,title, poster "
						+ "FROM seoul_location ORDER BY no ASC)) "
						+ "WHERE num BETWEEN ? AND ?";
				// rownum => 가상컬럼 (오라클에서 지원)
				// 단점 => 중간에 데이터를 추출할 수 없다.
				// 그래서 가상컬럼에 값을 저장한 후 가상컬럼에서 불러오기
				// sql문장 전송
				ps=conn.prepareStatement(sql);
				int rowSize = 12;
				int start = (page-1)*rowSize+1;
				int end = page*rowSize;
				// ?에 값을 채움 => IN, OUT 입출력 에러
				ps.setInt(1, start);
				ps.setInt(2, end);
				// 실행 요청
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					SeoulVO vo = new SeoulVO();
					vo.setNo(rs.getInt(1));
					vo.setTitle(rs.getString(2));
					vo.setPoster(rs.getString(3));
					
					list.add(vo);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				disConnection();
			}
			return list;
		}
		//5-1
		public List<SeoulVO> natureListData(int page) {
			List<SeoulVO> list = new ArrayList<SeoulVO>();
			try {
				getConnection();
				String sql = "SELECT no, title, poster, num "
						+ "FROM (SELECT no, title, poster, rownum as num "
						+ "FROM (SELECT no, title, poster "
						+ "FROM seoul_nature ORDER BY no ASC)) "
						+ "WHERE num BETWEEN ? AND ?";
				ps=conn.prepareStatement(sql);
				int rowSize=12;
				int start=(page-1)*rowSize+1;
				int end = page*rowSize;
				//?
				ps.setInt(1, start);
				ps.setInt(2, end);
				//실행요청
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					SeoulVO vo = new SeoulVO();
					vo.setNo(rs.getInt(1));
					vo.setTitle(rs.getString(2));
					vo.setPoster(rs.getString(3));
					list.add(vo);
				}
				rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				disConnection();
			}
			return list;
		}
		//5-1-1 총페이지
		public int seoulRowCount () {
			int count=0;
			try {
				getConnection();
				String sql = "SELECT COUNT(*) FROM seoul_slocation";
				ps=conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				rs.next();
				count=rs.getInt(1);
				rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				disConnection();
			}
			return count;
		}
}
