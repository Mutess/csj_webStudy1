package com.sist.dao;

import java.util.*;
import java.sql.*;
import com.sist.common.*;
import com.sist.vo.*;

public class FreeBoardReplyDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db=new CreateDataBase();
	private static FreeBoardReplyDAO dao;
	
	//싱글톤
	public static FreeBoardReplyDAO newInstance() {
		if (dao==null)
			dao=new FreeBoardReplyDAO();
		return dao;
	}
	// 목록 출력
	public List<FreeboardReplyVO> replyListData(int bno) { // 게시물 번호에 해당 되는 댓글 불러오기
		List<FreeboardReplyVO> list=new ArrayList<FreeboardReplyVO>();
		try {
			conn=db.getConnection();
			String sql="SELECT no, bno, id, name, msg, TO_CHAR(regdate,'yyyy-MM-dd HH24:MI:SS'), "
					+ "group_tab "
					+ "FROM project_freeboard_reply "
					+ "WHERE bno=? "
					+ "ORDER BY group_id DESC, group_step ASC";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, bno);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				FreeboardReplyVO vo=new FreeboardReplyVO();
				vo.setNo(rs.getInt(1));
				vo.setBno(rs.getInt(2));
				vo.setId(rs.getString(3));
				vo.setName(rs.getString(4));
				vo.setMsg(rs.getString(5));
				vo.setDbday(rs.getString(6));
				vo.setGroup_tab(rs.getInt(7));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return list;
	}
	// 수정
	public void replyUpdate(int no, String msg) {
		try {
			conn=db.getConnection();
			String sql="UPDATE project_freeboard_reply SET "
					+ "msg=? "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, msg);
			ps.setInt(2, no);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
	}
	// 삭제 => 트랜잭션 적용 (일괄처리) => commit(), catch => rollback()
	// 댓글 입력
	public void replyInsert(FreeboardReplyVO vo) {
		try {
			conn=db.getConnection();
			String sql="INSERT INTO project_freeboard_reply(no,bno,id,name,msg,group_id) "
					+ "VALUES(pfr_no_seq.nextval,?,?,?,?, "
					+ "(SELECT NVL(MAX(group_id)+1,1) FROM project_freeboard_reply))";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, vo.getBno());
			ps.setString(2, vo.getId());
			ps.setString(3, vo.getName());
			ps.setString(4, vo.getMsg());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
	}
	// 대댓글 입력 => 트랜잭션 적용 (일괄처리) => commit(), catch => rollback()
}
