package com.sist.dao;

//DBCP
import java.util.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
public class replyBoardDAO {
	//연결 객체
	private Connection conn;
	//송수신 객체
	private PreparedStatement ps;
	//싱글톤
	private static replyBoardDAO dao;
	
	public static replyBoardDAO newInstance() {
		if(dao==null)
			dao=new replyBoardDAO();
		return dao;
	}
	
	//주소값을 얻기
	public void getConnection() {
		try {
			Context init=new InitialContext();
			Context c=(Context)init.lookup("java://comp/env");
			DataSource ds=(DataSource)c.lookup("jdbc/oracle");
			conn=ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//반환
	public void disConnection() {
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		} catch (Exception e) {
			
		}
	}
	//기능수행
	// 1.목록
	public List<replyBoardVO> boardListData(int page){
		List<replyBoardVO> list=new ArrayList<replyBoardVO>();
		try {
			getConnection();
			String sql="SELECT no, subject, content, name, TO_CHAR(regdate, 'YYYY-MM-DD'), hit, group_tab, num "
					+ "FROM (SELECT no, subject, content, name, regdate, hit, group_tab, rownum as num "
					+ "FROM (SELECT no, subject, content, name, regdate, hit, group_tab "
					+ "FROM replyBoard ORDER BY group_id DESC, group_step ASC)) "
					+ "WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			int rowSize=10;
			int start=(page-1)*rowSize+1;
			int end=page*rowSize;
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				replyBoardVO vo = new replyBoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setHit(rs.getInt(6));
				vo.setGroup_tab(rs.getInt(7));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return list;
	}
	// 1-1 총페이지
	public int boardRowCount() {
		int count=0;
		try {
			getConnection();
			String sql="SELECT COUNT(*) FROM replyBoard";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			count=rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return count;
	}
	// 2.상세보기
	// 3.추가
	// 4.수정
	// 5.삭제
	// 6.답변
	// 7.검색
	
}
