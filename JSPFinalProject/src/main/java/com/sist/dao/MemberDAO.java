package com.sist.dao;

import java.sql.*;
import java.util.*;
import com.sist.common.*;
import com.sist.vo.MemberVO;
import com.sist.vo.zipcodeVO;

public class MemberDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db=new CreateDataBase();
	private static MemberDAO dao;
	
	// 싱글톤
	public static MemberDAO newInstance() {
		if (dao==null)
			dao=new MemberDAO();
		return dao;
	}
	// 회원가입
	// 1. 아이디 중복체크
	public int memberIdCheck(String id) {
		int count=0;
		try {
			conn=db.getConnection();
			String sql="SELECT COUNT(*) "
					+ "FROM project_member "
					+ "WHERE id=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs=ps.executeQuery();
			rs.next();
			count=rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return count;
	}
	// 2. 이메일 중복체크
	public int memberEmailCheck(String email) {
		int count=0;
		try {
			conn=db.getConnection();
			String sql="SELECT COUNT(*) "
					+ "FROM project_member "
					+ "WHERE email=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs=ps.executeQuery();
			rs.next();
			count=rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return count;
	}
	// 3. 우편번호 검색
	public int postFindCount(String dong) {
		int count=0;
		try {
			conn=db.getConnection();
			String sql="SELECT COUNT(*) "
					+ "FROM zipcode "
					+ "WHERE dong LIKE '%'||?||'%'";
			ps=conn.prepareStatement(sql);
			ps.setString(1, dong);
			ResultSet rs=ps.executeQuery();
			rs.next();
			count=rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return count;
	}
	
	public List<zipcodeVO> postFindData(String dong) {
		List<zipcodeVO> list=new ArrayList<zipcodeVO>();
		try {
			conn=db.getConnection();
			String sql="SELECT zipcode, sido, gugun, dong, NVL(bunji,' ') "
					+ "FROM zipcode "
					+ "WHERE dong LIKE '%'||?||'%'";
			ps=conn.prepareStatement(sql);
			ps.setString(1, dong);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				zipcodeVO vo=new zipcodeVO();
				vo.setZipcode(rs.getString(1));
				vo.setSido(rs.getString(2));
				vo.setGugun(rs.getString(3));
				vo.setDong(rs.getString(4));
				vo.setBunji(rs.getString(5));
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
	// 4. 전화번호 중복체크
	public int memberPhoneCheck(String phone) {
		int count=0;
		try {
			conn=db.getConnection();
			String sql="SELECT COUNT(*) "
					+ "FROM project_member "
					+ "WHERE phone=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, phone);
			ResultSet rs=ps.executeQuery();
			rs.next();
			count=rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return count;
	}
	// 5. 회원 가입
	public void memberInsert(MemberVO vo) {
		try {
			conn=db.getConnection();
			String sql="INSERT INTO project_member VALUES("
					+ "?,?,?,?,?,?,?,?,?,?,?,'n',SYSDATE)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getId());
			ps.setString(2, vo.getPwd());
			ps.setString(3, vo.getName());
			ps.setString(4, vo.getSex());
			ps.setString(5, vo.getBirthday());
			ps.setString(6, vo.getEmail());
			ps.setString(7, vo.getPost());
			ps.setString(8, vo.getAddr1());
			ps.setString(9, vo.getAddr2());
			ps.setString(10, vo.getPhone());
			ps.setString(11, vo.getContent());
			ps.executeUpdate();
			ps.setString(11, vo.getPwd());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
	}
	// 회원 수정
	public MemberVO memberUpdateData(String id) {
		MemberVO vo=new MemberVO();
		try {
			conn=db.getConnection();
			String sql="SELECT id,name,sex,birthday,email,post,addr1,addr2,phone,content "
					+ "FROM project_member "
					+ "WHERE id=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setId(rs.getString(1));
			vo.setName(rs.getString(2));
			vo.setSex(rs.getString(3));
			vo.setBirthday(rs.getString(4));
			vo.setEmail(rs.getString(5));
			vo.setPost(rs.getString(6));
			vo.setAddr1(rs.getString(7));
			vo.setAddr2(rs.getString(8));
			String phone=rs.getString(9);
			phone=phone.substring(4);
			vo.setPhone(phone);
			vo.setContent(rs.getString(10));
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
	// 회원 탈퇴
	public String memberDeleteOk(String id, String pwd) {
		String result="no";
		try {
			conn=db.getConnection();
			conn.setAutoCommit(false);
			String sql="SELECT pwd FROM project_member "
					+ "WHERE id=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs=ps.executeQuery();
			rs.next();
			String db_pwd=rs.getString(1);
			rs.close();
			
			if(db_pwd.equals(pwd)) {
				try {
					sql="DELETE FROM food_jjim "
							  + "WHERE id=?";
					ps=conn.prepareStatement(sql);
					ps.setString(1, id);
					ps.executeUpdate();
					
					sql="DELETE FROM food_like "
							  + "WHERE id=?";
					ps=conn.prepareStatement(sql);
					ps.setString(1, id);
					ps.executeUpdate();
					
					sql="DELETE FROM project_freeboard_reply "
							  + "WHERE id=?";
					ps=conn.prepareStatement(sql);
					ps.setString(1, id);
					ps.executeUpdate();
					
//					sql="DELETE FROM PROJECT_FREEBOARD "
//							  + "WHERE id=?";
//					ps=conn.prepareStatement(sql);
//					ps.setString(1, id);
//					ps.executeUpdate();
					
					sql="DELETE FROM RESERVE_INFO "
							  + "WHERE id=?";
					ps=conn.prepareStatement(sql);
					ps.setString(1, id);
					ps.executeUpdate();
					
					sql="DELETE FROM reply_all "
							  + "WHERE id=?";
					ps=conn.prepareStatement(sql);
					ps.setString(1, id);
					ps.executeUpdate();
					
					sql="DELETE FROM project_member "
					  + "WHERE id=?";
					ps=conn.prepareStatement(sql);
					ps.setString(1, id);
					ps.executeUpdate();
					
					result="yes";
					conn.commit();
				} catch (Exception e) {
					e.printStackTrace();
					try {
						conn.rollback();
					} catch (Exception e2) {}
				} finally {
					db.disConnection(conn, ps);
					try {
						conn.setAutoCommit(true);
					} catch (Exception e2) {}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return result;
	}
	// 아이디 찾기
	public String memberIdEmailFind(String email) {
		String result="";
		try {
			conn=db.getConnection();
			String sql="SELECT COUNT(*) FROM project_member "
					+ "WHERE email=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs=ps.executeQuery();
			rs.next();
			int count=rs.getInt(1);
			rs.close();
			
			if (count==0) { //email이 없는 상태
				result="NO";
			}else { // email이 존재
				sql="SELECT RPAD(SUBSTR(id,1,1),LENGTH(id),'*') "
				  + "FROM project_member "
				  + "WHERE email=?";
				// s***
				ps=conn.prepareStatement(sql);
				ps.setString(1, email);
				rs=ps.executeQuery();
				rs.next();
				result=rs.getString(1);
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return result;
	}
	// 비밀번호 찾기
	public String memberPasswordFind(String name,String email) {
		String result="";
		try {
			conn=db.getConnection();
			String sql="SELECT COUNT(*) "
					+ "FROM project_member "
					+ "WHERE name=? AND email=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, email);
			ResultSet rs=ps.executeQuery();
			rs.next();
			int count=rs.getInt(1);
			rs.close();
			if(count==0) {
				result="NO";
			}else {
				sql="SELECT RPAD(SUBSTR(pwd,1,1),LENGTH(pwd),'*') "
				  + "FROM project_member "
				  + "WHERE name=? AND email=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, name);
				ps.setString(2, email);
				rs=ps.executeQuery();
				rs.next();
				result=rs.getString(1);
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return result;
	}
	// 비밀번호 변경
	// 로그인
	public MemberVO memberLogin(String id, String pwd) {
		MemberVO vo=new MemberVO();
		try {
			conn=db.getConnection();
			String sql="SELECT COUNT(*) "
					+ "FROM project_member "
					+ "WHERE id=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs=ps.executeQuery();
			rs.next();
			int count=rs.getInt(1);
			rs.close();
			/////////////////// ID 존재여부 확인
			if (count==0) { //ID(X)
				vo.setMsg("NOID");
			}else { //ID(O)
				sql="SELECT pwd, name, admin, sex "
						+ "FROM project_member "
						+ "WHERE id=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, id);
				rs=ps.executeQuery();
				rs.next();
				String db_pwd=rs.getString(1);
				String name=rs.getString(2);
				String admin=rs.getString(3);
				String sex=rs.getString(4);
				rs.close();
				
				if(db_pwd.equals(pwd)) { //로그인
					vo.setId(id);
					vo.setName(name);
					vo.setAdmin(admin);
					vo.setSex(sex);
					vo.setMsg("OK");
				}else {
					vo.setMsg("NOPWD");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
	//실제 회원 수정
	public MemberVO memberUpdate(MemberVO vo) {
		MemberVO mvo=new MemberVO();
		try {
			conn=db.getConnection();
			String sql="SELECT pwd "
					+ "FROM project_member "
					+ "WHERE id=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getId());
			ResultSet rs=ps.executeQuery();
			rs.next();
			String db_pwd=rs.getString(1);
			rs.close();
			
			if(db_pwd.equals(vo.getPwd())) {
				mvo.setMsg("yes");
				mvo.setName(vo.getName());
				// 실제 수정
				sql="UPDATE project_member SET "
				  + "name=?,email=?,phone=?,content=?,birthday=?,post=?,addr1=?,addr2=? "
				  + "WHERE id=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, vo.getName());
				ps.setString(2, vo.getEmail());
				ps.setString(3, vo.getPhone());
				ps.setString(4, vo.getContent());
				ps.setString(5, vo.getBirthday());
				ps.setString(6, vo.getPost());
				ps.setString(7, vo.getAddr1());
				ps.setString(8, vo.getAddr2());
				ps.setString(9, vo.getId());
				ps.executeUpdate();
			}else {
				mvo.setMsg("no");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return mvo;
	}
	
}
