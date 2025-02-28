<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*, javax.servlet.*, javax.servlet.http.*"%>
<%@page import="utils.PageUtils" %>

<jsp:useBean id="userBean" scope="session" class="bean.UserBean" />
<jsp:useBean id="dataOfFile" scope="session" class="bean.DataOfFile" />

<%
request.setCharacterEncoding("utf-8");
int selectId = 0;
if (request.getAttribute("selectId") != null)
	selectId = (Integer) request.getAttribute("selectId");
String msg = Objects.toString(request.getAttribute("msg"), "");
List<Integer> searchWords = (List<Integer>)request.getAttribute("searchWords");
List<Integer> pageElementIds = (List<Integer>)request.getAttribute("pageElementIds");
if(pageElementIds == null) {
	PageUtils pageUtils = new PageUtils();
	pageElementIds = pageUtils.getPageElementIdsByPageNum(dataOfFile, 1);
}
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
		<% 
			if (dataOfFile.getMaxId() == 0) {
		%>
				<p id="edit-action-msg">Ankiカードがありません</p>
	<div id="divide-thirds">
		<div id="file-content">
			<table>
				<thead>
					<tr id="question-answer">
						<th>質問</th>
						<th>解答</th>
					</tr>
				</thead>
			</table>
		</div>
		<%
			} else {
				if(!"".equals(msg)){
		%>
					<p id="edit-action-msg"><%= msg %></p>
		<%			
				}else if(searchWords != null){
		%>
					<p id="edit-action-msg">検索結果</p>
		<%
					if(searchWords.size() < 1){
		%>			
						<p id="edit-action-msg">検索した結果その内容は見つかりませんでした</p>					
		<%			
					}
				}
		%>
	<div id="divide-thirds">
		<div id="file-content">
		<table>
			<thead>
				<tr id="question-answer">
					<th>質問</th>
					<th>解答</th>
				</tr>
			</thead>
		<%
			if(searchWords != null){
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
								<td id="edit-button">
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
				for(int elementNum = 0; elementNum < pageElementIds.size(); elementNum++) {
		%>
					<tbody>
						<tr>
							<form action="/FileEditerController" method="post">
								<td>
									<%=dataOfFile.getQuestionById(pageElementIds.get(elementNum))%>
									<%
										if (selectId == pageElementIds.get(elementNum)) { 
									%>
											<input type="text" name="editQuestion"> 
									<%
										}
									%>
								</td>
								<td>
									<%=dataOfFile.getAnswerById(pageElementIds.get(elementNum))%> 
									<%
										if (selectId == pageElementIds.get(elementNum)) { 
									%> 
											<input type="text" name="editAnswer"> 
									<%
										}
									%>
								</td> 
									<input type="hidden" name="selectQuestion" value="<%=dataOfFile.getQuestionById(pageElementIds.get(elementNum))%>">
									<input type="hidden" name="selectAnswer" value="<%=dataOfFile.getAnswerById(pageElementIds.get(elementNum))%>"> 
									<input type="hidden" name="selectId" value="<%=pageElementIds.get(elementNum)%>">
								<td>
									<%
										if (selectId == pageElementIds.get(elementNum)) {
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
				}
		%>
			</table>	
		<%	
		//firstElementId を返す。
				for(int pageNum = 1; pageNum < (dataOfFile.getDataOfFileSize() / 5) + 1; pageNum++){
		%>
					<form action="/FileEditerController" method="post">
						<input type="hidden" name="pageNum" value="<%=pageNum %>">
						<button type="submit" name="action" value="pageTransition"><%=pageNum %></button>
					</form>
		<%
				}
		}
		%>
		</div>
		<div id="search-word">
			<form action="/FileEditerController" method="post">
				<input type="text" name="searchWord" placeholder="単語を検索してみる">
				<button type="submit" name="action" value="search">検索</button>
				<button type="submit" name="action" value="complateSearch">検索終了</button>
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
