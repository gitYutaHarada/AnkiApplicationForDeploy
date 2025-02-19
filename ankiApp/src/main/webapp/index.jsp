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
    <link rel="stylesheet" href="/ankiApp/css/style.css">
    <title>ログイン画面</title>
</head>
<body>
<%
Object miss_obj = request.getAttribute("miss");
String miss_str = (miss_obj instanceof String) ? (String)miss_obj : "notMiss";
if("miss".equals(miss_str)){
%>
<p>名前もしくはパスワードに間違いがあります</p>
<%
}
%>
    <p>名前とパスワードを入力してください</p>
    <form action="/ankiApp/LoginController" method="post">
        名前<input type="text" name="name"/><br/>
        パスワード<input type="password" name="pass"/><br/>
        <input type="submit" value="送信">
    </form>
    <br/><br/>
	<form action="/ankiApp/CreateUserController" method="post">
		<p>名前とパスワードをお持ちでない方</p>
		<input type="submit" value="新規登録">
	</form>
    

</body>
</html>