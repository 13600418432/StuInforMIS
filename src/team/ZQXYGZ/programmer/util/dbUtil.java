package team.ZQXYGZ.programmer.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接Util
 */

public class dbUtil {
 private String dbUrl = "jdbc:mysql://localhost:3306/db_student_manager_web?useUnicode=true&characterEncoding=utf8";
 private String dbUser = "root";
 private String dbPassword = "root_12root";
 private String jdbcName = "com.mysql.jdbc.Driver";
 private Connection connection = null;
 public Connection getConnection(){
  try {
   Class.forName(jdbcName);
   connection = DriverManager.getConnection(dbUrl,dbUser,dbPassword);
   System.out.println("成功");
  } catch (Exception e) {
   System.out.println("失败");
   e.printStackTrace();
  }
 return connection;
 }
 public void closeCon(){
  if(connection != null) {
   try {
    connection.close();
   } catch (SQLException e) {
    e.printStackTrace();
   }
  }
 }
public static void main(String[] args){
  dbUtil dbUtil = new dbUtil();
  dbUtil.getConnection();
}
}
