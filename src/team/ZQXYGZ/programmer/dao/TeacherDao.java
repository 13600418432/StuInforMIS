package team.ZQXYGZ.programmer.dao;

import team.ZQXYGZ.programmer.model.Page;
import team.ZQXYGZ.programmer.model.teacher;
import team.ZQXYGZ.programmer.util.StringUitl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDao extends BaseDao {
 public boolean addTeacher(teacher teacher){
  String sql = "insert into s_teacher values(null,'"+teacher.getSn()+"','"+teacher.getName()+"'";
  sql += ",'" + teacher.getPassword() + "'," + teacher.getClazzId();
  sql += ",'" + teacher.getSex() + "','" + teacher.getMobile() + "'";
  sql += ",'" + teacher.getQq() + "',null)";
  return updata(sql);
 }
 public boolean deleteTeacher(String ids) {
  String sql = "delete from s_teacher where id in("+ids+")";
  return updata(sql);
 }
 public boolean editStudent(teacher teacher) {
  String sql = "update s_teacher set name = '"+teacher.getName()+"'";
  sql += ",sex = '" + teacher.getSex() + "'";
  sql += ",mobile = '" + teacher.getMobile() + "'";
  sql += ",qq = '" + teacher.getQq() + "'";
  sql += ",sn = '" +teacher.getSn() + "'";
  sql += ",clazz_id = " + teacher.getClazzId();
  sql += " where id = " + teacher.getId();
  return updata(sql);
 }
 public boolean setTeacherPhoto(teacher teacher){
  String sql = "update s_teacher set photo = ? where id = ?";
  Connection connection = getConnection();
  try {
   PreparedStatement prepareStatement = connection.prepareStatement(sql);
   prepareStatement.setBinaryStream(1, teacher.getPhoto());
   prepareStatement.setInt(2, teacher.getId());
   return prepareStatement.executeUpdate() > 0;
  } catch (SQLException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
  return updata(sql);
 }
 public teacher getTeacher(int id){
  String sql ="select * from s_teacher where id = "+ id;
  ResultSet resultSet = query(sql);
  teacher teacher = null;
  try {
   if(resultSet.next()){
    teacher = new teacher();
    teacher.setName(resultSet.getString("name"));
    teacher.setSn(resultSet.getString("sn"));
    teacher.setPassword(resultSet.getString("password"));
    teacher.setClazzId(resultSet.getInt("clazz_id"));
    teacher.setQq(resultSet.getString("qq"));
    teacher.setMobile(resultSet.getString("mobile"));
    teacher.setSex(resultSet.getString("sex"));
    teacher.setPhoto(resultSet.getAsciiStream("photo"));
    return teacher;
   }
  } catch (SQLException e) {
   e.printStackTrace();
  }
  return teacher;
 }
 public List<teacher> getTeacherList(teacher teacher, Page page){
  List<teacher> ret = new ArrayList<teacher>();
  String sql = "select * from s_teacher,s_clazz where s_teacher.clazz_id = s_clazz.id ";
  if(!StringUitl.isEmpty(teacher.getName())){
   sql +="and name like '%" + teacher.getName() + "%'";
  }
  if(teacher.getClazzId() != 0){
   sql += " and clazz_id = " + teacher.getClazzId();
  }
  if(teacher.getId() != 0){
   sql += " and s_teacher.id = " + teacher.getId();
  }
  sql += " limit " + page.getStart() + "," + page.getPageSize();
  ResultSet resultSet = query(sql);
   try {
    while (resultSet.next()){
     teacher t = new teacher();
     t.setId(resultSet.getInt("id"));
     t.setClazzId(resultSet.getInt("clazz_id"));
     t.setMobile(resultSet.getString("mobile"));
     t.setName(resultSet.getString("name"));
     t.setClazz_name(resultSet.getString("s_clazz.name"));
     t.setPassword(resultSet.getString("password"));
     t.setPhoto(resultSet.getBinaryStream("photo"));
     t.setQq(resultSet.getString("qq"));
     t.setSex(resultSet.getString("sex"));
     t.setSn(resultSet.getString("sn"));
     ret.add(t);
    }
   } catch (SQLException e) {
    e.printStackTrace();
   }
   return ret;
 }
 public int getStudentListTotal(teacher teacher){
  int total = 0;
  String sql = "select count(*)as total from s_teacher ";
  if(!StringUitl.isEmpty(teacher.getName())){
   sql += "and name like '%" + teacher.getName() + "%'";
  }
  if(teacher.getClazzId() != 0){
   sql += " and clazz_id = " + teacher.getClazzId();
  }
  if(teacher.getId() != 0){
   sql += " and id = " + teacher.getId();
  }
  ResultSet resultSet = query(sql.replaceFirst("and", "where"));
  try {
   while(resultSet.next()){
    total = resultSet.getInt("total");
   }
  } catch (SQLException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
  return total;
 }
 public teacher login(String name ,String password){
  String sql = "select * from s_teacher where name = '" + name + "' and password = '" + password + "'";
  ResultSet resultSet = query(sql);
  try {
   if(resultSet.next()){
    teacher teacher = new teacher();
    teacher.setId(resultSet.getInt("id"));
    teacher.setName(resultSet.getString("name"));
    teacher.setPassword(resultSet.getString("password"));
    teacher.setClazzId(resultSet.getInt("clazz_id"));
    teacher.setMobile(resultSet.getString("mobile"));
    teacher.setPhoto(resultSet.getBinaryStream("photo"));
    teacher.setQq(resultSet.getString("qq"));
    teacher.setSex(resultSet.getString("sex"));
    teacher.setSn(resultSet.getString("sn"));
    return teacher;
   }
  } catch (SQLException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
  return null;
 }
 public boolean editPassword(teacher teacher, String newpassword){
  String sql = "update s_teacher set password = " + newpassword + " where id = " + teacher.getId();
  return updata(sql);
 }
}
