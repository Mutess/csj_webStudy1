package com.sist.dao;
// div.info_inner_wrap span.title
// div.info_inner_wrap p.desc
// ul.list-toplist-slider img.center-croping
// ul.list-toplist-slider a
import java.sql.*;
import java.util.*;
import com.sist.vo.*;
/*
	1. 드라이버 등록
	   ------- 오라클 연결하는 라이브러리 (ojdbc8.jar)
	    OracleDriver => 메모리 할당
	2. 오라클 연결
		Connection
	3. SQL문장 전송
		PreparedStatement
	4. SQL문장 실행 요청
		= executeUpdate() => INSERT, UPDATE, DELETE
		  --------------- COMMIT (AutoCommit)
		= executeQuery() => SELECT
		  ------------ 결과값을 가지고 온다
		  			   ----
		  			   ResultSet = 가지고 온 결과값을 여기에 담아줌
		  ResultSet sql = "SELECT id, name, sex, age"
		  ---------------------------------------
		   id		name 		sex 		age
		  ---------------------------------------
		   aaa		홍길동		남자			20	| first() => next() ==> 커서위치 바꿀때 쓰는 명령어
		   										  위치변경	 	 위치 변경후 데이터 읽는 매소드
		getString(1) getString(2) getString(3) getInt(4)
		getString("id") => mybatis = 컬럼명을 읽음
		  --------------------------------------
		   bbb		심청이		여자			23
		getString(1) getString(2) getString(3) getInt(4)
		  ---------------------------------------
		   ccc		박문수		남자			27	| last() => previous()
		getString(1) getString(2) getString(3) getInt(4)
		  ---------------------------------------
		  | 커서 위치
	5. 닫기 
		생성 반대로 닫는다.
		rs.close(), ps.close, conn.close()
		---------------------------------- 오라클 연결 (Servlet => JSP)
 */
public class FoodDAO {
	// 기능 => INSERT => 데이터 수집 (파일)
	private Connection conn; //오라클 연결 객체 (데이터베이스 연결)
	private PreparedStatement ps; // SQL문장 전송 / 결과값 읽기
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	// MySQL => jdbc:mysql://localhost/mydb
	private static FoodDAO dao; //(싱글톤 패턴) == 메모리 저장 공간 1개로 작동하는 것
	// DAO객체를 한개만 사용이 가능하게 만든다. 
	// 드라이버 설치 => 소프트웨어 (메모리 할당 요청) Class.forName()
	// 클래스의 정보를 전송
	// 드라이버 설치는 1번만 수행
	public FoodDAO() {
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
	public static FoodDAO newInstance() {
		// newInstance(), getInstance() => 싱글턴
		if (dao==null)
			dao=new FoodDAO();
		return dao;
	}
	//=============== 기본 세팅 (모든 DAO)
	// 기능 
	// 1. 데이터수집 (INSERT) insert, update, delete는 리턴형이 없음
	
	/*
		Statement => SQL => 생성과 동시에 데이터 추가
					"'"+name+"','"+
		PreparedStatement => 미리 SQL문장을 만들고 나중에 값을 채운다
		CallableStatement => Procedure 호출
	 */
	public void foodCategoryInsert(CategoryVO vo) {
		try {
			//1. 연결
			getConnection();
			//2. SQL문장 작성
			String sql ="INSERT INTO food_category VALUES("
						+"fc_cno_seq.nextval,?,?,?,?)"; //여기서 ?는 미리 ?채워주고 나중에 값을 채우는 목적
										//   1,2,3,4번
			/*
					"'"+vo.getTitle()+"','"
			 */
			//3. SQL문장을 오라클에 전송
			ps = conn.prepareStatement(sql);
			//3-1 => ?에 값을 채워줌
			ps.setString(1, vo.getTitle()); // 이것 자동으로 "'"+vo.getTitle()+"'"이것을 만들어줌
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getPoster());
			ps.setString(4, vo.getLink());
			// 이것이 단점은 번호가 잘못되면 오류발생, 데이터형이 잘못되면 오류발생
			// IN,OUT이라고 에러가 나면 번호에서 에러가 난거임
			//4. SQL문장을 실행 명령 => SQL문장을 작성하고 => 실행 : execute
			ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace(); //에러확인
		} finally {
			disconnection(); //오라클 연결 해제 => 무조건
		}
	}
	//1-1 실제 맛집정보 저장
	/*
		fno NUMBER,
		cno NUMBER,
		name VARCHAR2(100) CONSTRAINT fh_name_nn NOT NULL,
		score NUMBER(2,1),
		address VARCHAR2(300) CONSTRAINT fh_addr_nn NOT NULL,
		phone VARCHAR2(20) CONSTRAINT fh_phone_nn NOT NULL, 
		TYPE VARCHAR2(30) CONSTRAINT fh_type_nn NOT NULL,
		price VARCHAR2(30),
		parking VARCHAR2(30),
		time VARCHAR2(20),
		menu CLOB,
		good NUMBER,
		soso NUMBER,
		bad NUMBER,
		poster VARCHAR2(4000) CONSTRAINT fh_poster_nn NOT NULL,
	 */
	public void foodDataInsert(FoodVO vo) {
		try {
			// 1. 오라클 연결
			getConnection();
			// 2.SQL문장 제작
			String sql = "INSERT INTO food_house VALUES("
						+"fn_fno_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			// 3. 오라클 전송
			ps=conn.prepareStatement(sql);
			//4. ?에 값을 채운다
			ps.setInt(1, vo.getCno());
			ps.setString(2, vo.getName());
			ps.setDouble(3, vo.getScore());
			ps.setString(4, vo.getAddress());
			ps.setString(5, vo.getPhone());
			ps.setString(6, vo.getType());
			ps.setString(7, vo.getPrice());
			ps.setString(8, vo.getParking());
			ps.setString(9, vo.getTime());
			ps.setString(10, vo.getMenu());
			ps.setInt(11, vo.getGood());
			ps.setInt(12, vo.getSoso());
			ps.setInt(13, vo.getBad());
			ps.setString(14, vo.getPoster());
			
			//실행요청
			ps.executeUpdate(); //commit()
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			disconnection();
		}
	}
	// 2. SELECT => 전체 데이터 읽기 => 30개씩 전송
	// =>
	// 브라우저 <==> 오라클(X)
	// 브라우저 <==> 자바 <==> 오라클 ==> Spring Cloud
	public List<CategoryVO> foodCategoryData() {
		List<CategoryVO> list = new ArrayList<CategoryVO>();
		try {
			//1. 오라클 연결
			getConnection();
			//2. SQL문장
			String sql = "SELECT cno, title, subtitle, poster, link "
					   + "FROM food_category";
			//3. 오라클 전송
			ps=conn.prepareStatement(sql);
			//4. 실행 후 결과값 받기
			ResultSet rs = ps.executeQuery();
			//rs에 있는 데이터들을 list에 저장
			while(rs.next()) {
				CategoryVO vo = new CategoryVO();
				vo.setCno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setSubject(rs.getString(3));
				String poster=rs.getString(4);
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setLink("https://www.mangoplate.com"+rs.getString(5));
				list.add(vo);
			}
			rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			disconnection();
		}
		return list;
	}
	// 3. 상세보기 => WHERE
	
	
}
