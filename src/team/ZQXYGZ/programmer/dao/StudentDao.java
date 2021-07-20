package team.ZQXYGZ.programmer.dao;

import team.ZQXYGZ.programmer.model.Page;
import team.ZQXYGZ.programmer.model.student;
import team.ZQXYGZ.programmer.util.StringUitl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDao extends BaseDao {
 public boolean addStudent(student student) {
  String sql = "insert into s_student values(null,'" + student.getSn() + "','" + student.getName() + "'";
  sql += ",'" + student.getPassword() + "'," + student.getClazzId();
  sql += ",'" + student.getSex() + "','" + student.getMobile() + "'";
  sql += ",'" + student.getQq() + "',null)";
  return updata(sql);
 }
 public boolean deleteStudent(String ids) {
  String sql = "delete from s_student where id in("+ids+")";
  return updata(sql);
 }
 public boolean editStudent(student student) {
  String sql = "update s_student set name = '"+student.getName()+"'";
  sql += ",sex = '" + student.getSex() + "'";
  sql += ",mobile = '" + student.getMobile() + "'";
  sql += ",qq = '" + student.getQq() + "'";
  sql += ",sn = '" + student.getSn() + "'";
  sql += ",clazz_id = " + student.getClazzId();
  sql += " where id = " + student.getId();
  return updata(sql);
 }
 public boolean setStudentPhoto(student student) {
  // TODO Auto-generated method stub
  String sql = "update s_student set photo = ? where id = ?";
  Connection connection = getConnection();
  try {
   PreparedStatement prepareStatement = connection.prepareStatement(sql);
   prepareStatement.setBinaryStream(1, student.getPhoto());
   prepareStatement.setInt(2, student.getId());
   return prepareStatement.executeUpdate() > 0;
  } catch (SQLException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
  return updata(sql);
 }
 public List<student> getStudentList(student student, Page page){
  List<student> ret = new ArrayList<student>();
  String sql = "select * from s_student,s_clazz where s_student.clazz_id = s_clazz.id ";
  if(!StringUitl.isEmpty(student.getName())){
   sql += "and name like '%" + student.getName() + "%'";
  }
  if(student.getClazzId()!=0){
   sql += " and clazz_id = " + student.getClazzId() ;
  }
  if(student.getId()!=0){
   sql +=" and s_student.id = " + student.getId();
  }
  sql += " limit "+ page.getStart() + "," +page.getPageSize();
  ResultSet resultSet = query(sql);
  try {
   while(resultSet.next()){
     student s = new student();
     s.setName(resultSet.getString("name"));
     s.setClazz_name(resultSet.getString("s_clazz.name"));
     s.setSn(resultSet.getString("sn"));
     s.setPassword(resultSet.getString("password"));
     s.setClazzId(resultSet.getInt("clazz_id"));
     s.setQq(resultSet.getString("qq"));
     s.setMobile(resultSet.getString("mobile"));
     s.setSex(resultSet.getString("sex"));
     s.setId(resultSet.getInt("id"));
     ret.add(s);
   }
  } catch (SQLException e) {
   e.printStackTrace();
  }
  return  ret;
 }
 public student getStudent(int id){
  String sql ="select * from s_student where id = "+ id;
  ResultSet resultSet = query(sql);
  student student = null;
  try {
   if(resultSet.next()){
    student = new student();
    student.setName(resultSet.getString("name"));
    student.setSn(resultSet.getString("sn"));
    student.setPassword(resultSet.getString("password"));
    student.setClazzId(resultSet.getInt("clazz_id"));
    student.setQq(resultSet.getString("qq"));
    student.setMobile(resultSet.getString("mobile"));
    student.setSex(resultSet.getString("sex"));
    student.setPhoto(resultSet.getAsciiStream("photo"));
    return student;
   }
  } catch (SQLException e) {
   e.printStackTrace();
  }
  return student;
 }
 public int getStudentListTotal(student student){
  int total = 0;
  String sql = "select count(*)as total from s_student ";
  if(!StringUitl.isEmpty(student.getName())){
   sql += "and name like '%" + student.getName() + "%'";
  }
  if(student.getClazzId() != 0){
   sql += " and clazz_id = " + student.getClazzId();
  }
  if(student.getId() != 0){
   sql += " and id = " + student.getId();
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
 public student login(String name ,String password){
  String sql = "select * from s_student where name = '" + name + "' and password = '" + password + "'";
  ResultSet resultSet = query(sql);
  try {
   if(resultSet.next()){
    student student = new student();
    student.setId(resultSet.getInt("id"));
    student.setName(resultSet.getString("name"));
    student.setPassword(resultSet.getString("password"));
    student.setClazzId(resultSet.getInt("clazz_id"));
    student.setMobile(resultSet.getString("mobile"));
    student.setPhoto(resultSet.getBinaryStream("photo"));
    student.setQq(resultSet.getString("qq"));
    student.setSex(resultSet.getString("sex"));
    student.setSn(resultSet.getString("sn"));
    return student;
   }
  } catch (SQLException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
  return null;
 }
 public boolean editPassword(student student, String newpassword){
  String sql = "update s_student set password = " + newpassword + " where id = " + student.getId();
  return updata(sql);
 }
}
