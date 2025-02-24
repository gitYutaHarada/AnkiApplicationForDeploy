<%@page contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.*, javax.servlet.*, javax.servlet.http.*"%>
<%@page import="bean.*"%>

<jsp:useBean id="userbean" scope="session" class="bean.UserBean" />

<%
request.setCharacterEncoding("utf-8");
if (userbean.getName() == null) {
	String name = (String) request.getAttribute("name");
	userbean.setName(name != null ? name : "miss");
}
int deleteFile_count = 0;
if (request.getAttribute("deleteFile_count") != null)
	deleteFile_count = (Integer) request.getAttribute("deleteFile_count");
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
	<p>こんにちは <strong><%=userbean.getName()%> </strong>さん!</p>
	<br />
	<table id="file_editer">
					<tr>
		<%
			for (int i = 1; i <= userbean.getFileNamesSize(); i++) {
		%>
				
						<th>
							<form action="/FileEditerJspController" method="post">
								<figcaption><%=userbean.getFileName(i)%></figcaption>
								<button type="submit">
									<img src="/images/file.jpg" alt="Image <%=i + 1%>" name="" width="100" height="100">
								</button>
						
								<input type="hidden" name="fileName" value="<%=userbean.getFileName(i)%>"> 
								<input type="hidden" name="userName" value="<%=userbean.getName()%>">
						
							</form>
							<br/>
							<form action="/MyPageController" method="post" onsubmit="return confirmDelete()">
								<button type="submit" name="action" value="remove">ファイルの削除</button>
						
								<input type="hidden" name="remove_fileName" value="<%=userbean.getFileName(i)%>">
							</form>
						</th>
		<%
				if( (i != 1) &&  ( (i % 4 == 0) || (i == userbean.getFileNamesSize()) ) ){
		%>
					</tr>
		<%			
				}
				Systm.out.println(i);
			}
		%>

	</table>
	<br/>
	<form action="/MyPageController" method="post">
		<label for="create_imageName">作成するファイル名:</label>
		<input type="text" id="create_imageName" name="create_fileName" placeholder="ファイル名を入力">
		<button type="submit" name="action" value="create">新しいファイルの作成</button>

		<input type="hidden" name="userName" value="<%=userbean.getName()%>">
	</form>

	<script>
		function confirmDelete() {
		    return confirm("本当にこのファイルを削除しますか？");
		}
	</script>
</body>
</html>
