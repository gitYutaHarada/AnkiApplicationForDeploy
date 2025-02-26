<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*, javax.servlet.*, javax.servlet.http.*"%>

<jsp:useBean id="userBean" scope="session" class="bean.UserBean" />
<jsp:useBean id="dataOfFile" scope="session" class="bean.DataOfFile" />

<%
request.setCharacterEncoding("utf-8");
int selectId = 0;
if (request.getAttribute("selectId") != null)
	selectId = (Integer) request.getAttribute("selectId");
String msg = Objects.toString(request.getAttribute("msg"), "");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="/css/style.css">
	<title><%=userBean.getName()%>の<%=dataOfFile.getFileName()%></title>
</head>
<body>
	<form id="back" action="/MyPageController" method="post">
		<button type="submit" name="action" value="back">マイページに戻る</button>
	</form>
	<p><strong><%=userBean.getName()%></strong>の<strong><%=dataOfFile.getFileName()%></strong>という名前のファイルの編集画面</p>
	<div id="divide-thirds">
		<% 
			int i = 0;
			if (dataOfFile.getMaxId() == 0) {
		%>
				<p>Ankiカードがありません</p>
				<table id="file-content">
					<thead>
						<tr id="question-answer">
							<th>質問</th>
							<th>解答</th>
						</tr>
					</thead>
				</table>
		<%
			} else {
				if(!"".equals(msg)){
		%>
					<p><%= msg %></p>
		<%			
				}
		%>
				<table id="file-content">
					<thead>
						<tr id="question-answer">
							<th>質問</th>
							<th>解答</th>
						</tr>
					</thead>
			<%
				List<Integer> searchWords = (List<Integer>)request.getAttribute("searchWords");
				if(searchWords != null && searchWords.size() > 0){
					for(int id : searchWords){
			%>
						<tbody>
							<tr>
								<form action="/FileEditerController" method="post">
									<td>
										<%=dataOfFile.getQuestionById(id)%>
										<%
											if (selectId == id) { 
										%>
												<input type="text" name="editQuestion"> 
										<%
											}
										%>
									</td>
									<td>
										<%=dataOfFile.getAnswerById(id)%> 
										<%
											if (selectId == id) { 
										%> 
												<input type="text" name="editAnswer"> 
										<%
											}
										%>
									</td> 
										<input type="hidden" name="selectQuestion" value="<%=dataOfFile.getQuestionById(id)%>">
										<input type="hidden" name="selectAnswer" value="<%=dataOfFile.getAnswerById(id)%>"> 
										<input type="hidden" name="selectId" value="<%=id%>">
									<td>
										<%
											if (selectId == id) {
										%>
												<button type="submit" name="action" value="completeEdit">編集完了</button>
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
			%>	</table><%	
				}else{
					while (i != (dataOfFile.getMaxId() + 1)) {
						if (dataOfFile.isElement(i)) {
			%>
						<tbody>
							<tr>
								<form action="/FileEditerController" method="post">
									<td>
										<%=dataOfFile.getQuestionById(i)%>
										<%
											if (selectId == i) { 
										%>
												<input type="text" name="editQuestion"> 
										<%
											}
										%>
									</td>
									<td>
										<%=dataOfFile.getAnswerById(i)%> 
										<%
											if (selectId == i) { 
										%> 
												<input type="text" name="editAnswer"> 
										<%
											}
										%>
									</td> 
										<input type="hidden" name="selectQuestion" value="<%=dataOfFile.getQuestionById(i)%>">
										<input type="hidden" name="selectAnswer" value="<%=dataOfFile.getAnswerById(i)%>"> 
										<input type="hidden" name="selectId" value="<%=i%>">
									<td>
										<%
											if (selectId == i) {
										%>
												<button type="submit" name="action" value="completeEdit">編集完了</button>
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
			}
		%>
		
		<div id="search-word">
			<form action="/FileEditerController" method="post">
				<input type="text" name="searchWord" placeholder="単語を検索してみる">
				<button type="submit" name="action" value="search">検索</button>
			</form><br/><br/>
		</div>
		
		<div id="create-anki">
			<form action="/FileEditerController" method="post">
				<input type="text" name="createQuestion" placeholder="質問を入力">
				<input type="text" name="createAnswer" placeholder="解答を入力"><br/>
		
				<button type="submit" name="action" value="create">Ankiカードの作成</button>
			</form><br/><br/>
			<form action="/AnkiTime" method="post">
				<button type="submit">Anki開始！</button>
				<input type="hidden" name="id" value="0">
				<input type="hidden" name="questionAnswer" value="question">		
			</form>
		</div>
	</div>
</body>
</html>
