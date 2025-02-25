<%@page contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.*, javax.servlet.*, javax.servlet.http.*"%>
<%@page import="bean.*"%>

<jsp:useBean id="userBean" scope="session" class="bean.UserBean" />

<%
request.setCharacterEncoding("utf-8");
if (userBean.getName() == null) {
	String name = (String) request.getAttribute("name");
	userBean.setName(name != null ? name : "miss");
}
int deleteFileCount = 0;
if (request.getAttribute("deleteFileCount") != null)
	deleteFileCount = (Integer) request.getAttribute("deleteFileCount");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<link rel="stylesheet" href="/css/style.css">
	<title>ログイン成功！</title>
</head>
<body>
	<form id="back" action="/LoginController" method="post">
		<button type="submit" name="action" value="logout">ログアウト</button>
	</form>
	<p>ログイン成功！</p>
	<p>こんにちは <strong><%=userBean.getName()%> </strong>さん!</p>
	<br />
	<table id="file-editer">
					<tr>
		<%
			for (int i = 1; i <= userBean.getFileNamesSize(); i++) {
		%>
				
						<th>
							<form action="/FileEditerJspController" method="post">
								<figcaption><%=userBean.getFileName(i-1)%></figcaption>
								<button type="submit">
									<img src="/images/file.jpg" alt="Image <%=i - 1%>" name="" width="100" height="100">
								</button>
						
								<input type="hidden" name="fileName" value="<%=userBean.getFileName(i-1)%>"> 						
							</form>
							<br/>
							<form action="/MyPageController" method="post" onsubmit="return confirmDelete()">
								<button type="submit" name="action" value="remove">ファイルの削除</button>
						
								<input type="hidden" name="removeFileName" value="<%=userBean.getFileName(i-1)%>">
							</form>
						</th>
		<%
				if( (i != 1) &&  ( (i % 4 == 0) || (i == userBean.getFileNamesSize()) ) ){
		%>
					</tr>
		<%			
				}
			}
		%>

	</table>
	<br/>
	<form action="/MyPageController" method="post">
		<label for="createImageName">作成するファイル名:</label>
		<input type="text" id="createImageName" name="createFileName" placeholder="ファイル名を入力">
		<button type="submit" name="action" value="create">新しいファイルの作成</button>

		<input type="hidden" name="userName" value="<%=userBean.getName()%>">
	</form>

	<script>
		function confirmDelete() {
		    return confirm("本当にこのファイルを削除しますか？");
		}
	</script>
</body>
</html>
