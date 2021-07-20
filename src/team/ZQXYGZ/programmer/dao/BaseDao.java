package team.ZQXYGZ.programmer.dao;
import team.ZQXYGZ.programmer.util.dbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 分装基础操作
 */
public class BaseDao {
 private dbUtil dbUtil = new dbUtil();
 public void closeCon(){
  dbUtil.closeCon();
 }

 /**
  * 多条查询
  * @param sql
  * @return
  */
 public ResultSet query(String sql){
  try {
   PreparedStatement preparedStatement=dbUtil.getConnection().prepareStatement(sql);
   return preparedStatement.executeQuery();
  } catch (SQLException e) {
   e.printStackTrace();
  }
  return null;
 }
 /**
  * 数据更新
  */
 public boolean updata(String sql){
  try {
   return dbUtil.getConnection().prepareStatement(sql).executeUpdate() > 0;
  } catch (SQLException e) {
   e.printStackTrace();
  }
  return false;
 }
 public Connection getConnection(){
  return dbUtil.getConnection();
 }
}
