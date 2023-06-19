package com.sist.dao;

import java.util.*;
import java.sql.*;
import com.sist.dbconn.*;

public class SeoulDAO1 {
	private String[] tables = {
		"seoul_location",
		"seoul_nature",
		"seoul_shop",
		"seoul_hotel"
	};
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db = new CreateDataBase();
	private static SeoulDAO1 dao;
	
	// 1. 기능
	public List<SeoulVO> seoulListData(int page, int type) { //type은 table안에 들어있는 배열값 1이면 location, 2면 nature, 3은 shop이런식으로
		List<SeoulVO> list = new ArrayList<SeoulVO>();
		try {
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			
		}
		return list;
	}
	//2. 총 페이지 구하기
	public int seoulTotalPage(int type) {
		int total = 0;
		try {
			db.getConnection();
			String sql="SELECT CEIL(COUNT(*)/12.0) "
					+ "FROM "+tables[type];//페이지 구할떄는 CEIL
			// 만약 + "FROM ?"; 이렇게 주면
			// ps.setString로 값을 넣음
			// 근데 이렇게 주면 FROM 'seoul_location'이렇게 들어감 테이블명에 ''가 들어가면 안됨
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return total;
	}
	//3. 상세보기
	public SeoulVO seoulDetailData(int no, int type) {
		SeoulVO vo = new SeoulVO();
		try {
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			
		}
		return vo;
	}
}
