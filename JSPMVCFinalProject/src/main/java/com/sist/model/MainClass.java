package com.sist.model;

import java.lang.reflect.Method;
import java.util.Scanner;

import com.sist.controller.RequestMapping;

class Board {
	@RequestMapping("list.do")
	public void boardList() {
		System.out.println("목록 출력~~");
	}
	@RequestMapping("insert.do")
	public void boardInsert() {
		System.out.println("데이터 추가~~");
	}
	@RequestMapping("update.do")
	public void boardUpdate() {
		System.out.println("데이터 수정~~");
	}
	@RequestMapping("delete.do")
	public void boardDelete() {
		System.out.println("데이터 삭제~~");
	}
	@RequestMapping("find.do")
	public void boardFind() {
		System.out.println("데이터 찾기~~");
	}
	
}
public class MainClass {
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("URL 입력:");
		//list.do => boardList가 호출
		String url=scan.next();
		
		//찾기
/*		Board board=new Board();
		if (url.equals("list.do")) {
			board.boardList();
		}
		if (url.equals("insert.do")) {
			board.boardInsert();
		}
		if (url.equals("update.do")) {
			board.boardUpdate();
		}
		if (url.equals("delete.do")) {
			board.boardDelete();
		}
		if (url.equals("find.do")) {
			board.boardFind();
		} */
		try {
			Class clsName=Class.forName("com.sist.model.Board");
			// class의 정보 읽기
			Object obj=clsName.getDeclaredConstructor().newInstance();
			//Board board=new Board(); 
			
			Method[] methods=clsName.getDeclaredMethods();
			// class안에 정의된 모든 메소드를 읽는다
			for(Method m:methods) {
				//메소드위에 어노테이션 확인
				RequestMapping rm=m.getAnnotation(RequestMapping.class);
				if(rm.value().equals(url)) {
					m.invoke(obj, null);
					// invoke 메소드를 찾아줌
					// 나중에 메소드 자리가 request, response자리가 됨
				}
			}
		} catch (Exception e) {}
	}
}
