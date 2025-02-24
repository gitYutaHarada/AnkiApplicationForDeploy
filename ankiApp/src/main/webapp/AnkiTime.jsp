<%@page contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.*, javax.servlet.*, javax.servlet.http.*"%>

 <jsp:useBean id="userbean" scope="session" class="bean.UserBean"/>
 <jsp:useBean id="dataoffile" scope="session" class="bean.DataOfFile"/>
 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="/css/style.css">
	<title>Insert title here</title>
</head>
<body>
	<form id="back" action="/FileEditerController" method="post">
		<button type="submit" name="action" value="back">ファイル編集画面に戻る</button>
	</form>
	<p><%=userbean.getName() %>の<%=dataoffile.getFileName() %></p>
	<% 
		String question_answer = (String)request.getAttribute("question_answer");
		int id = ((int)request.getAttribute("id") != 0) ? (int)request.getAttribute("id") : dataoffile.getMinId();
		if(dataoffile.isElement(id) && "question".equals(question_answer)){
	%>
			<h3><%=dataoffile.getQuestionById(id) %></h3>

	<%
		}else if(dataoffile.isElement(id) && "answer".equals(question_answer)){
	%>
			<h3><%=dataoffile.getAnswerById(id) %></h3>
	<%
		}else if("last".equals(question_answer)){
	%>
			<h3>最後まで頑張りました！</h3>
	<%
		}
	%>
	<form action="/AnkiTime" method="post">
		<%
			if(id != dataoffile.getMinId()){	
		%>
				<button type="submit" name="action" value="back">戻る</button>		
		<%
			}
			if("answer".equals(question_answer)){
		%>		
				<button type="submit" name="action" value="convert_question">質問へ</button>
		<%	
			}else if("question".equals(question_answer)){
		%>
				<button type="submit" name="action" value="convert_answer">答えへ</button>
		<%
			}
			
			if("last".equals(question_answer)){
		%>
				<button type="submit" name="action" value="convert_first">最初から</button>
		<%
			}else{
		%>
				<button type="submit" name="action" value="next">次へ</button>
		<%
			}
		%>
		<input type="hidden" name="question_answer" value=<%=question_answer %>>
		<input type="hidden" name="id" value=<%=id %>>
	</form>
</body>
</html>