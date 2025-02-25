<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/style.css">
    <title>ログイン画面</title>
</head>
<body>
<%
Object missObj = request.getAttribute("miss");
String missStr = (missObj instanceof String) ? (String)missObj : "notMiss";
if("miss".equals(missStr)){
%>
<p>名前もしくはパスワードに間違いがあります</p>
<%
}
%>
    <p>名前とパスワードを入力してください</p>
    <form action="/LoginController" method="post">
        名前<input type="text" name="name"/><br/>
        パスワード<input type="password" name="pass"/><br/>
        <button type="submit" name="action" value="login">ログイン</button>
    </form>
    <br/><br/>
	<form action="/CreateUserController" method="post">
		<p>名前とパスワードをお持ちでない方</p>
		<input type="submit" value="新規登録">
	</form>
    

</body>
</html>