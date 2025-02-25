<%@page contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.*, javax.servlet.*, javax.servlet.http.*"%>

 <jsp:useBean id="userBean" scope="session" class="bean.UserBean"/>
 <jsp:useBean id="dataOfFile" scope="session" class="bean.DataOfFile"/>
 
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
	<p><%=userBean.getName() %>の<%=dataOfFile.getFileName() %></p>
	<% 
		String questionAnswer = (String)request.getAttribute("questionAnswer");
		int id = ((int)request.getAttribute("id") != 0) ? (int)request.getAttribute("id") : dataOfFile.getMinId();
		if(dataOfFile.isElement(id) && "question".equals(questionAnswer)){
	%>
			<h3><%=dataOfFile.getQuestionById(id) %></h3>

	<%
		}else if(dataOfFile.isElement(id) && "answer".equals(questionAnswer)){
	%>
			<h3><%=dataOfFile.getAnswerById(id) %></h3>
	<%
		}else if("last".equals(questionAnswer)){
	%>
			<h3>最後まで頑張りました！</h3>
	<%
		}
	%>
	<form action="/AnkiTime" method="post">
		<%
			if(id != dataOfFile.getMinId()){	
		%>
				<button type="submit" name="action" value="back">戻る</button>		
		<%
			}
			if("answer".equals(questionAnswer)){
		%>		
				<button type="submit" name="action" value="convertQuestion">質問へ</button>
		<%	
			}else if("question".equals(questionAnswer)){
		%>
				<button type="submit" name="action" value="convertAnswer">答えへ</button>
		<%
			}
			
			if("last".equals(questionAnswer)){
		%>
				<button type="submit" name="action" value="convertFirst">最初から</button>
		<%
			}else{
		%>
				<button type="submit" name="action" value="next">次へ</button>
		<%
			}
		%>
		<input type="hidden" name="questionAnswer" value=<%=questionAnswer %>>
		<input type="hidden" name="id" value=<%=id %>>
	</form>
</body>
</html>