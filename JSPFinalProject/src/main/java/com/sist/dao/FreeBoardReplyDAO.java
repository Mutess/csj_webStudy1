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
	/*
							gi 	gs 	gt
		aaaaaaaaaaa			1	0	0
			=>kkkkkk		1	1	1
			=>dddddd		1 	2	1
			=>bbbbbbbb		1	3	1
				=> ccccc	1	4	2
			
	 */
	public void replyReplyInsert(int pno, FreeboardReplyVO vo) {
		try {
			conn=db.getConnection();
			//오토커밋 해제
			conn.setAutoCommit(false);
			// 처리 => SQL문장 여러개 수행 => INSERT / UPDATE / DELETE
			String sql="SELECT group_id, group_step, group_tab "
					+ "FROM project_freeboard_reply "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, pno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			int gi=rs.getInt(1);
			int gs=rs.getInt(2);
			int gt=rs.getInt(3);
			rs.close();
			
			// group_step+1 => 업데이트 
			sql="UPDATE project_freeboard_reply SET "
			  + "group_step=group_step+1 "
			  + "WHERE group_id=? AND group_step>?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, gi);
			ps.setInt(2, gs);
			ps.executeUpdate();
			// insert => insert
			sql="INSERT INTO project_freeboard_reply VALUES("
					+ "pfr_no_seq.nextval,?,?,?,?,SYSDATE,?,?,?,?,0)";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, vo.getBno());
			ps.setString(2, vo.getId());
			ps.setString(3, vo.getName());
			ps.setString(4, vo.getMsg());
			ps.setInt(5, gi);
			ps.setInt(6, gs+1);
			ps.setInt(7, gt+1);
			ps.setInt(8, pno);
			ps.executeUpdate();
			// depth => update
			
			sql="UPDATE project_freeboard_reply SET "
					+ "depth=depth+1 "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, pno);
			ps.executeUpdate();
			conn.commit();
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e2) {}
				e.printStackTrace();
		} finally {
			try {
				//conn의 원래기능으로 돌려놈
				conn.setAutoCommit(true);
			} catch (Exception e3) {}
				db.disConnection(conn, ps);
		}
	}
	//댓글삭제
	public void replyDelete(int no) {
		try {
			conn=db.getConnection();
			conn.setAutoCommit(false);
			// SQL문장이 여러개 수행
			String sql="SELECT root, depth "
					+ "FROM project_freeboard_reply "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs=ps.executeQuery();
			rs.next();
			int root=rs.getInt(1);
			int depth=rs.getInt(2);
			rs.close();
			
			if(depth==0) {
				sql="DELETE FROM project_freeboard_reply "
				  + "WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, no);
				ps.executeUpdate();
			}else {
				String msg="관리자가 삭제한 댓글입니다.";
				sql="UPDATE project_freeboard_reply SET "
				  + "msg=? "
				  + "WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, msg);
				ps.setInt(2, no);
				ps.executeUpdate();
			}
			// depth감소
			sql="UPDATE project_freeboard_reply SET "
			  + "depth=depth-1 "
			  + "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, root);
			ps.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e2) {}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {}
			db.disConnection(conn, ps);
		}
	}
}
