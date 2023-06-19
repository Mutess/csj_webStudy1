package com.sist.dao;
// = 카테고리 => 카테고리별 맛집 => 맛집에 대한 상세보기 => 지도출력, 검색(화면이 바뀌면 안됨(Ajax))

import java.util.*;
import java.sql.*;

public class FoodDAO1 {
	//연결객체
	private Connection conn;
	//송수신
	private PreparedStatement ps;
	//오라클 URL주소 설정
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	//싱글턴
	private static FoodDAO1 dao;
	//1. 드라이버 등록 => 한번만 수행 => 시작과 동시에 한번 수행, 멤버변수 초기화할때 사용 (생성자)
	public FoodDAO1() {
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
	public static FoodDAO1 newInsatance() {
		if (dao==null)
			dao=new FoodDAO1();
		return dao;
	}
	//5. 기능
	//5-1. 카테고리 출력
	public List<CategoryVO> food_category_list() { //결과를 30개출력하면 list, 1개 나오면 VO
		List<CategoryVO> list = new ArrayList<CategoryVO>();
		//카테고리 1개의 정보 (번호, 이미지, 제목, 부제목) => CategoryVO
		try {
			//1. 오라클 연결
			getConnection();
			//2. SQL문 작성
			String sql="SELECT cno,title, subtitle, poster "
					+ "FROM food_category "
					+ "ORDER BY cno ASC";
//			String sql="SELECT /*+ INDEX_ASC(food_Category fc_cno_pf)*/cno, title, subtitle, poster "
//					+ "FROM food_category";
//			=> 자동 (PRIMARY, UNIQUE)
//			String sql="SELECT --+ INDEX_ASC(food_Category fc_cno_pf) cno, title, subtitle, poster "
//					+ "FROM food_category";
//			=> 자동 (PRIMARY, UNIQUE)
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CategoryVO vo = new CategoryVO();
				vo.setCno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setSubtitle(rs.getString(3));
				vo.setPoster(rs.getString(4));
				list.add(vo);
			}
			rs.close();
			// list => 받아서 브라우저로 전송 실행
			//				--------- Servlet, JSP
			//				Spring => servlet을 만들어서 브라우저로 전송 => DispatcherServlet
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			//연결해제
			disConnection();
		}
		return list;
	}
	//5-2. 카테고리별 맛집 출력
	//List<FoodVO>
	public CategoryVO food_category_info(int cno) {
		CategoryVO vo = new CategoryVO();
		try {
			getConnection();
			String sql = "SELECT title, subtitle FROM food_category "
					+ "WHERE cno="+cno;
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setTitle(rs.getString(1));
			vo.setSubtitle(rs.getString(2));
			rs.close();
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			disConnection();
		}
		return vo;
	}
	
	public List<FoodVO> food_category_data(int cno) {
		List<FoodVO> list = new ArrayList<FoodVO>();
		try {
			getConnection();
			String sql = "SELECT fno,name,poster,address,phone,type,score "
					+ "FROM food_house "
					+ "WHERE cno="+cno;
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				FoodVO vo = new FoodVO();
				vo.setFno(rs.getInt(1));
				vo.setName(rs.getString(2));
				String poster = rs.getString(3);
				poster=poster.substring(0,poster.indexOf("^"));
				//^로 사진 하나하나를 구분해 놨기 때문에 ^를 구분자로 사용하고 ^앞에 있는 사진 하나를 잘라옴
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				String address = rs.getString(4);
				address=address.substring(0,address.lastIndexOf("지"));
				vo.setAddress(address.trim());
				vo.setPhone(rs.getString(5));
				vo.setType(rs.getString(6));
				vo.setScore(rs.getDouble(7));
				list.add(vo);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			disConnection();
		}
		return list;
	}
	//5-3. 맛집 상세보기
	//FoodVO
	public FoodVO foodDetailData(int fno) {
		FoodVO vo = new FoodVO();
		try {
			getConnection();
			String sql = "SELECT fno,cno,name,poster,phone,type,address,time,parking,menu,price,score "
					+ "FROM food_house "
					+ "WHERE fno=?";
			ps=conn.prepareStatement(sql);
			// ?에 값을 채움 ==> JSP/프로젝트는 지금 처음 다 해야함
			// 2차 => MyBatis, 보안(비밀번호 암호화), 실시간 처리(Betch)
			// 3차 => MySQL, JPA
			// 기반 => MSA 기반 CI(지속적인 통합)/CD(지속적인 개발) => 설치 및 모니티링(젠킨스)
			ps.setInt(1, fno);
			//실행요청
			ResultSet rs=ps.executeQuery();
			rs.next(); //커서 위치 이동 => 데이터가 출력할 위치로 이동
			vo.setFno(rs.getInt(1));
			vo.setCno(rs.getInt(2));
			vo.setName(rs.getString(3));
			vo.setPoster(rs.getString(4));
			vo.setPhone(rs.getString(5));
			vo.setType(rs.getString(6));
			vo.setAddress(rs.getString(7));
			vo.setTime(rs.getString(8));
			vo.setParking(rs.getString(9));
			vo.setMenu(rs.getString(10));
			vo.setPrice(rs.getString(11));
			vo.setScore(rs.getDouble(12));
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			disConnection();
		}
		return vo;
	}
	
	//5-4. 맛집 검색 => Ajax
	//List<FoodVO>
	public List<FoodVO> FoodFindData(String addr, int page) {
		List<FoodVO> list = new ArrayList<FoodVO>();
		try {
			getConnection();
/*			String sql="SELECT fno,name,poster,score "
					+ "FROM food_location "
					+ "WHERE address LIKE '%'||?||'%'";  //오라클 LIKE문은 문자열 결합을 시켜줘야 작동을 함
			//mysql => LIKE CONCAT('%',?,?) */
			String sql="SELECT fno,name,poster,score,num "
					+ "FROM (SELECT fno,name,poster,score,rownum as num "
					+ "FROM (SELECT fno,name,poster,score "
					+ "FROM food_location "
					+ "WHERE address LIKE '%'||?||'%')) "
					+ "WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			int rowSize=12;
//			int start=(rowSize*page)-(rowSize-1);
			int start=(page-1)*rowSize+1;
			int end = rowSize*page;
			//?
			ps.setString(1, addr);
			ps.setInt(2, start);
			ps.setInt(3, end);
			
			//결과값 읽기
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				FoodVO vo = new FoodVO();
				vo.setFno(rs.getInt(1));
				vo.setName(rs.getString(2));
				String poster=rs.getString(3);
				poster=poster.substring(0,poster.indexOf("^"));
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setScore(rs.getDouble(4));
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
	//5-4-1 총페이지 ==> 데이터(오라클)
	public int foodRowCount(String addr) {
		int count=0;
		try {
			getConnection();
			String sql="SELECT COUNT(*) FROM food_location "
					+ "WHERE address LIKE '%'||?||'%'";
			ps=conn.prepareStatement(sql);
			ps.setString(1, addr);
			ResultSet rs=ps.executeQuery();
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
	
	//5-5 댓글 (CURD) ==> 로그인
	
}
