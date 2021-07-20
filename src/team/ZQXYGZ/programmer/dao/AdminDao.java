package team.ZQXYGZ.programmer.dao;

import team.ZQXYGZ.programmer.model.admin;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 管理员数据库操作封装
 */
public class AdminDao extends BaseDao {
 public admin login(String name,String password){
  String sql = "select * from s_admin where name = '" + name + "' and password = '" + password+ "'";
  ResultSet resultSet = query(sql);
  try {
   if(resultSet.next()){
    admin admin = new admin();
    admin.setId(resultSet.getInt("id"));
    admin.setName(resultSet.getString("name"));
    admin.setPassword(resultSet.getString("password"));
    admin.setStatus(resultSet.getInt("status"));
    return admin;
   }
  } catch (SQLException e) {
   e.printStackTrace();
  }
  return null;
 }
 public boolean editPassword(admin admin,String newpassword){
  String sql = "update s_admin set password = " + newpassword + " where id = " + admin.getId();
  return updata(sql);
 }
}
