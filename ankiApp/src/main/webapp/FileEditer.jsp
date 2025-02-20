<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*, javax.servlet.*, javax.servlet.http.*"%>

<jsp:useBean id="userbean" scope="session" class="bean.UserBean" />
<jsp:useBean id="fileofdata" scope="session" class="bean.FileOfData" />

<%
request.setCharacterEncoding("utf-8");
int select_id = 0;
if (request.getAttribute("select_id") != null)
	select_id = (Integer) request.getAttribute("select_id");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="/css/style.css">
	<title><%=userbean.getName()%>の<%=fileofdata.getFileName()%></title>
</head>
<body>
	<p><%=userbean.getName()%>の<%=fileofdata.getFileName()%>という名前のファイル</p>
	<%
		int i = 0;
		if (fileofdata.getMaxId() == 0) {
	%>
	<p>Ankiカードがありません</p>
	<table id="file_content">
		<thead>
			<tr id="question_answer">
				<th>質問</th>
				<th>解答</th>
			</tr>
		</thead>
	</table>
	<%
		} else {
	%>
		<table id="file_content">
			<thead>
				<tr id="question_answer">
					<th>質問</th>
					<th>解答</th>
				</tr>
			</thead>
		<%
			while (i != (fileofdata.getMaxId() + 1)) {
				if (fileofdata.isElement(i)) {
		%>
		<tbody>
			<tr>
				<form action="/FileEditerController" method="post">
					<td>
						<%=fileofdata.getQuestionById(i)%>
						<%
							if (select_id == i) { 
						%>
								<input type="text" name="edit_question"> 
						<%
							}
						%>
					</td>
					<td>
						<%=fileofdata.getAnswerById(i)%> 
						<%
							if (select_id == i) { 
						%> 
								<input type="text" name="edit_answer"> 
						<%
							}
						%>
					</td> 
						<input type="hidden" name="select_question" value="<%=fileofdata.getQuestionById(i)%>">
						<input type="hidden" name="select_answer" value="<%=fileofdata.getAnswerById(i)%>"> 
						<input type="hidden" name="select_id" value="<%=i%>">
					<td>
						<%
							if (select_id == i) {
						%>
								<button type="submit" name="action" value="complete_edit">編集完了</button>
						<%
							} else {
						%>
								<button type="submit" name="action" value="edit">編集</button> 
						<%
							}
						%>
						<button type="submit" name="action" value="delete">削除</button>
					</td>
				</form>
			</tr>
		</tbody>
		<%
				}
				i++;
			}
		%>
	</table>
		
	<%	
		}
	%>
	<div id="create_anki">
		<form action="/FileEditerController" method="post">
			<input type="text" name="create_question" placeholder="質問を入力">
			<input type="text" name="create_answer" placeholder="答えを入力"><br/>
	
			<button type="submit" name="action" value="create">Ankiカードの作成</button>
		</form><br/><br/>
		<form action="/AnkiTime" method="post">
			<button type="submit">Anki開始！</button>
			<input type="hidden" name="id" value="0">
			<input type="hidden" name="question_answer" value="question">		
		</form>
	</div>
</body>
</html>
