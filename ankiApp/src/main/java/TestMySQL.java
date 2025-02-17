import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestMySQL {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/userinformation";
        String user = "root";  // 適切なユーザー名
        String password = "mysql";  // 適切なパスワード
        String sql = "insert into user values(1, 'yuta', 'pass')";

        try {
//            Class.forName("com.mysql.cj.jdbc.Driver"); // 手動ロード (JDBC 4.0 以降は不要)
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            int count = stmt.executeUpdate(sql);
            System.out.println(count + "件更新しました！");
            System.out.println("接続成功！");
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("SQLエラー: " + e.getMessage());
        }
    }
}
