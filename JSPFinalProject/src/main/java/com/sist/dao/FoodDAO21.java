package com.sist.dao;

import java.util.*;
import java.sql.*;
import com.sist.vo.*;
import com.sist.common.*;
public class FoodDAO21 {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db=new CreateDataBase();
	private static FoodDAO21 dao;
	
	//싱글톤
	public static FoodDAO21 newInstance() {
		if (dao==null)
			dao=new FoodDAO21();
		return dao;
	}
	
	//기능 => 카테고리
	public List<CategoryVO> foodCategoryListData(){
		List<CategoryVO> list=new ArrayList<CategoryVO>();
		try {
			conn=db.getConnection();
			String sql="SELECT /*+INDEX_ASC(food_category fc_cno_pk)*/cno, title, subtitle, poster "
					+ "FROM food_category";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				CategoryVO vo=new CategoryVO();
				vo.setCno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setSubject(rs.getString(3));
				vo.setPoster(rs.getString(4));
				list.add(vo);
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
		return list;
	}
	//지역별 맛집 찾기
	public List<FoodVO> foodLocationFindData(String fd, int page){
		List<FoodVO> list=new ArrayList<FoodVO>();
		try {
			conn=db.getConnection();
			String sql="SELECT fno,poster,name, num "
					+ "FROM (SELECT fno,poster,name, rownum as num "
					+ "FROM (SELECT /*+INDEX_ASC(food_location fl_fno_pk)*/fno,poster,name "
					+ "FROM food_location WHERE address LIKE '%'||?||'%')) "
					+ "WHERE num BETWEEN ? AND ?";
			int rowSize=12;
			int start=(page-1)*rowSize+1;
			int end=page*rowSize;
			ps=conn.prepareStatement(sql);
			ps.setString(1, fd);
			ps.setInt(2, start);
			ps.setInt(3, end);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				FoodVO vo = new FoodVO();
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
			db.disConnection(conn, ps);
		}
		return list;
	}
	//지역별 맛집 총페이지
	public int foodlocationTotalPage(String fd) {
		int total=0;
		try {
			conn=db.getConnection();
			String sql="SELECT CEIL(COUNT(*)/12.0) "
					+ "FROM food_location "
					+ "WHERE address LIKE '%'||?||'%'";
			ps=conn.prepareStatement(sql);
			ps.setString(1, fd);
			ResultSet rs=ps.executeQuery();
			rs.next();
			total=rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
		return total;
	}
	// 카테고리별 맛집
	// 1. 카테고리 정보
	public CategoryVO foodCategoryInfoData(int cno) {
		CategoryVO vo=new CategoryVO();
		try {
			conn=db.getConnection();
			String sql="SELECT title,subtitle "
					+ "FROM food_category "
					+ "WHERE cno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, cno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setTitle(rs.getString(1));
			vo.setSubject(rs.getString(2));
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
	// 2. 실제 맛집 목록
	public List<FoodVO> foodCategoryListData(int cno){
		List<FoodVO> list=new ArrayList<FoodVO>();
		try {
			conn=db.getConnection();
			String sql="SELECT fno, cno, poster, name, score, address, NVL(foodReplyData(fno),' ') as msg, NVL(foodReplyName(fno),' ') as rname "
					+ "FROM food_house "
					+ "WHERE cno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, cno);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				FoodVO vo=new FoodVO();
				vo.setFno(rs.getInt("fno"));
				vo.setCno(rs.getInt("cno")); //MyBatis가 이런 방식으로 사용
				String poster=rs.getString("poster");
				poster=poster.substring(0,poster.indexOf("^"));
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setName(rs.getString("name"));
				vo.setScore(rs.getDouble("score"));
				String addr=rs.getString("address");
				addr=addr.substring(0,addr.indexOf("지번"));
				vo.setAddress(addr.trim());
				vo.setMsg(rs.getString("msg"));
				vo.setRname(rs.getString("rname"));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
		return list;
	}
	// 맛집 상세보기
	public FoodVO foodDetailData(int fno) {
		FoodVO vo=new FoodVO();
		try {
			conn=db.getConnection();
			String sql="UPDATE food_house SET "
					+ "hit=hit+1 "
					+ "WHERE fno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, fno);
			ps.executeUpdate();
			
			sql="SELECT fno, cno, name, score, address, phone, type, time, parking, price, menu, poster "
					+ "FROM food_house "
					+ "WHERE fno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, fno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setFno(rs.getInt(1));
			vo.setCno(rs.getInt(2));
			vo.setName(rs.getString(3));
			vo.setScore(rs.getDouble(4));
			vo.setAddress(rs.getString(5));
			vo.setPhone(rs.getString(6));
			vo.setType(rs.getString(7));
			vo.setTime(rs.getString(8));
			vo.setParking(rs.getString(9));
			vo.setPrice(rs.getString(10));
			vo.setMenu(rs.getString(11));
			vo.setPoster(rs.getString(12));
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
	// 맛집 => 인근 명소 => 레시피 (가격비교)
	public List<FoodVO> foodTop7(){
		List<FoodVO> list=new ArrayList<FoodVO>();
		try {
			conn=db.getConnection();
			String sql="SELECT fno,name,hit, rownum "
					+ "FROM (SELECT fno,name,hit "
					+ "FROM food_house ORDER BY hit DESC) "
					+ "WHERE rownum<=7";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				FoodVO vo=new FoodVO();
				vo.setFno(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setHit(rs.getInt(3));
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
	//해당 레시피
	public List<RecipeVO> foodRecipeData(String type) {
		List<RecipeVO> list=new ArrayList<RecipeVO>();
		try {
			conn=db.getConnection();
			String sql="SELECT no,title,chef,poster,rownum "
					+ "FROM recipe "
					+ "WHERE REGEXP_LIKE(title,?) "
					+ "AND rownum <=5";
			ps=conn.prepareStatement(sql);
			ps.setString(1, type);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				RecipeVO vo=new RecipeVO();
				vo.setNo(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setChef(rs.getString(3));
				vo.setPoster(rs.getString(4));
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
}
