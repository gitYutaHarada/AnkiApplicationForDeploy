anki-app 

JAWSDB_URL: mysql://d6uoc91fueyqgpyr:ht52jrafaw0f8qlh@nba02whlntki5w2p.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/yngwlvduglko28ms

mysql	プロトコル（MySQLを指定）
d6uoc91fueyqgpyr	ユーザー名（username）
ht52jrafaw0f8qlh	パスワード（password）
nba02whlntki5w2p.cbetxkdyhwsb.us-east-1.rds.amazonaws.com	ホスト名（host）
3306	ポート番号（port）（MySQLのデフォルトは3306）
yngwlvduglko28ms	データベース名（database）

heroku config:add CLEARDB_DATABASE_URL="mysql://d6uoc91fueyqgpyr:ht52jrafaw0f8qlh@nba02whlntki5w2p.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/yngwlvduglko28ms?reconnect=true^^^&useSSL=false^^^&useUnicode=ture^^^&characterEncoding=utf8^^^&characterSetResults=utf8" --app anki-app
mysql -h nba02whlntki5w2p.cbetxkdyhwsb.us-east-1.rds.amazonaws.com -u d6uoc91fueyqgpyr -p yngwlvduglko28ms -P 3306



create table user(
	id int primary key,
	name varchar(50),
	password varchar(255)
	);