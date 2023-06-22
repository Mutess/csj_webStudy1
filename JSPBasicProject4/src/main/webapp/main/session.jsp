<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
	request / response / session => 웹개발의 핵심
	------------------   ------- 프로그램의 실행하는 중에만 유지함
	session은 서버에 저장이 됨 : 사용자의 정보를 지속적으로 관리
	------- 1) 장바구니, 결제, 예약, 추천...
			2) session에 저장이 되면 모든 JSP에서 사용이 가능 (전역변수)
	클래스명 => HttpSession
			 클라이언트마다 1개 생성 => id가 보여 (구분자)
			 					 --- sessionId => 채팅, 상담...
	주요메소드
			String getId() : 세션마다 저장 구분자
			setMaxinactiveInterval() => 저장 기간을 설정
				=> 기본 Default => 1800 (초단위 : 30분)
				=> 경매시간도 session저장해서 돌리는 것
			isNew() : ID가 할당이 된것인지 여부
				=> 장바구니
			invalidate() : session에 저장된 모든 내용을 지운다.
							로그아웃
			setAttribute() : session에 정보 저장
			getAttribute() : 저장된 데이터 읽기
			removeAttribute() : 저장된 데이터 일부를 지울 용도로 사용
 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>HttpSession(session):177page</h1>
</body>
</html>