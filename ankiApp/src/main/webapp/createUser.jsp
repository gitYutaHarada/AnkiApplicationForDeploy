<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/css/style.css">
<title>新規登録</title>
</head>
<body>

	<%
Integer isSuccessInsertInteger = (Integer)request.getAttribute("isSuccessInsert");
Integer isName = (Integer) request.getAttribute("isValidStringName");
Integer isPass = (Integer) request.getAttribute("isValidStringPass");
//nullだったら2を代入それ以外ならisSuccessInsert_Integer.intValue()を代入
int isSuccessInsert = (isSuccessInsertInteger != null) ? isSuccessInsertInteger.intValue() : 2;
int isNameInt = (isName != null) ? isName : 2;
int isPassInt = (isPass != null) ? isPass : 2;

if (isSuccessInsert == 1 && isNameInt == 1 && isPassInt == 1) { %>
	<p>おめでとうございます！新規登録成功です！戻るボタンを押してもう一度ログインしましょう！</p>

	<% } else if (isNameInt == 0 || isPassInt == 0) { %>
	<p>新しい名前もしくはパスワードに英数字以外が含まれています</p>

	<% } else if (isNameInt == 2 || isPassInt == 2){ %>
	<p>新しい名前とパスワードを<Strong>英数字のみ</Strong>で記入してください</p>

	<% } else if (isSuccessInsert == 0 && isNameInt == 1 && isPassInt == 1) { %>
	<p>既に名前が使われています。ほかの名前で登録してください</p>
	<% } %>



	<form action="/CreateSuccessController" method="post">
		新しい名前：<input type="text" name="name"><br /> 新しいパスワード<input type="password" name="pass"><br />
		<button type="submit">新規登録</button>
	</form><br/><br/>
	<form id="back-createUser" action="/LoginBackController" method="post">
		<button type="submit">ログイン画面に戻る</button>
	</form>
</body>
</html>