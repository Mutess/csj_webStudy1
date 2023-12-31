<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Dongle&display=swap" rel="stylesheet">
<style type="text/css">
.container{
	margin-top: 50px
}
.row {
	margin: 0px auto;
	width: 700px;
}
h1 {
	text-align: center;
	font-family: 'Dongle', sans-serif;
}
</style>
<script type="text/javascript">
function board_write(){
	// 유효성 검사
	// form를 읽기
	let frm=document.frm; //frm이라는 이름을 가지고 와라
	
	if (frm.name.value=="") { // 오라클 => NOT NULL
		frm.name.focus();
		return;
	}
	if (frm.subject.value=="") { // 오라클 => NOT NULL
		frm.subject.focus();
		return;
	}
	if (frm.content.value=="") { // 오라클 => NOT NULL
		frm.content.focus();
		return;
	}
	if (frm.pwd.value=="") { // 오라클 => NOT NULL
		frm.pwd.focus();
		return;
	}
	frm.submit();//submit버튼을 함수로 만듬
}
</script>
</head>
<body>
	<div class="container">
		<h1>글쓰기</h1>
		<div class="row">
			<form method="post" action="insert_ok.jsp" name=frm
			 enctype="multipart/form-data"
			> <!-- 모양 잡아주는 jsp랑 form을 보내주는 jsp를 나눠줘야함 --> <!-- 업로드 하려면 enc로 작성 -->
			<!-- 
				multipart/form-data ; fileupload 프로토콜
				=> POST
			 -->
			
			<table class="table">
				<tr>
					<th class="text-center danger" width=15%>이름</th>
					<td width=85%>
						<input type=text name=name class="input-sm" size=15>
					</td>
				</tr>
				<tr>
					<th class="text-center danger" width=15%>제목</th>
					<td width=85%>
						<input type=text name=subject class="input-sm" size=50>
					</td>
				</tr>
				<tr>
					<th class="text-center danger" width=15%>내용</th>
					<td width=85%>
						<textarea rows="10" cols="50" name=content></textarea>
					</td>
				</tr>
				<tr>
					<th class="text-center danger" width=15%>첨부파일</th>
					<td width=85%>
						<input type=file name=upload class="input-sm" size=20>
					</td>
				</tr>
				<tr>
					<th class="text-center danger" width=15%>비밀번호</th>
					<td width=85%>
						<input type=password name=pwd class="input-sm" size=10>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="text-center">
						<input type=button value="글쓰기" class="btn btn-sm btn-primary" onclick="board_write()">
						<input type=button value="취소" class="btn btn-sm btn-info" onclick="javascript:history.back()">
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
</body>
</html>