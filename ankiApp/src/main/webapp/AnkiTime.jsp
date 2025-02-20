<%@page contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.*, javax.servlet.*, javax.servlet.http.*"%>

 <jsp:useBean id="userbean" scope="session" class="bean.UserBean"/>
 <jsp:useBean id="fileofdata" scope="session" class="bean.FileOfData"/>
 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="/css/style.css">
	<title>Insert title here</title>
</head>
<body>
	<form action="/FileEditerController" method="post">
		<button type="submit" name="action" value="back">ファイル編集画面に戻る</button>
	</form>
	<p><%=userbean.getName() %>の<%=fileofdata.getFileName() %></p>
	<% 
		String question_answer = (String)request.getAttribute("question_answer");
		int id = ((int)request.getAttribute("id") != 0) ? (int)request.getAttribute("id") : fileofdata.getMinId();
		if(fileofdata.isElement(id) && "question".equals(question_answer)){
	%>
			<h3><%=fileofdata.getQuestionById(id) %></h3>

	<%
		}else if(fileofdata.isElement(id) && "answer".equals(question_answer)){
	%>
			<h3><%=fileofdata.getAnswerById(id) %></h3>
	<%
		}
	%>
	<form action="/AnkiTime" method="post">
		<button type="submit" name="action" value="back">戻る</button>
		<%
			if("answer".equals(question_answer)){
		%>		
				<button type="submit" name="action" value="convert_question">質問へ</button>
		<%	
			}else if("question".equals(question_answer)){
		%>
				<button type="submit" name="action" value="convert_answer">答えへ</button>
		<%
			}
		%>
		<button type="submit" name="action" value="next">次へ</button>
		
		<input type="hidden" name="question_answer" value=<%=question_answer %>>
		<input type="hidden" name="id" value=<%=id %>>
	</form>
</body>
</html>