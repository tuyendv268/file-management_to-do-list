
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.MySQLConnUtils;
public class Demo {

  public static void main(String[] args) throws ClassNotFoundException,
          SQLException {

      // Lấy ra đối tượng Connection kết nối vào DB.
      Connection connection = MySQLConnUtils.getMySQLConnection();

      // Tạo đối tượng Statement.
      Statement statement = connection.createStatement();

      String sql = "Select * from user";

      // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
      ResultSet rs = statement.executeQuery(sql);

      // Duyệt trên kết quả trả về.
      while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
          int id = rs.getInt(1);
          String userName = rs.getString(2);
          String password = rs.getString(3);
          String role = rs.getString(4);
          System.out.println("--------------------");
          System.out.println("EmpId:" + id);
          System.out.println("EmpNo:" + userName);
          System.out.println("EmpName:" + password);
      }
      // Đóng kết nối
      connection.close();
  }

}