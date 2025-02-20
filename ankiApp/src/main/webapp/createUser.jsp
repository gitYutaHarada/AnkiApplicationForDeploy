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
Integer isSuccessInsert_Integer = (Integer)request.getAttribute("isSuccessInsert");
Integer isName = (Integer) request.getAttribute("isValidString_name");
Integer isPass = (Integer) request.getAttribute("isValidString_pass");
//nullだったら2を代入それ以外ならisSuccessInsert_Integer.intValue()を代入
int isSuccessInsert = (isSuccessInsert_Integer != null) ? isSuccessInsert_Integer.intValue() : 2;
int isName_int = (isName != null) ? isName : 2;
int isPass_int = (isPass != null) ? isPass : 2;

System.out.println(isSuccessInsert);
System.out.println(isName_int);
System.out.println(isPass_int);

if (isSuccessInsert == 1 && isName_int == 1 && isPass_int == 1) { %>
	<p>おめでとうございます！新規登録成功です！戻るボタンを押してもう一度ログインしましょう！</p>

	<% } else if (isName_int == 0 || isPass_int == 0) { %>
	<p>新しい名前もしくはパスワードに英数字以外が含まれています</p>

	<% } else if (isName_int == 2 || isPass_int == 2){ %>
	<p>新しい名前とパスワードを英数字のみで記入してください</p>

	<% } else if (isSuccessInsert == 0 && isName_int == 1 && isPass_int == 1) { %>
	<p>既に名前が使われています。ほかの名前で登録してください</p>
	<% } %>



	<form action="/CreateSuccessController" method="post">
		新しい名前：<input type="text" name="name"><br /> 新しいパスワード<input type="password" name="pass"><br />
		<button type="submit">新規登録</button>
	</form><br/><br/>
	<form action="/LoginBackController" method="post">
		<button type="submit">ログイン画面に戻る</button>
	</form>
</body>
</html>