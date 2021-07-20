package team.ZQXYGZ.programmer.dao;

import team.ZQXYGZ.programmer.model.Page;
import team.ZQXYGZ.programmer.model.clazz;
import team.ZQXYGZ.programmer.util.StringUitl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 班级信息数据库操作封装
 */
public class ClazzDao extends BaseDao{
 public List<clazz> getClazzList(clazz clazz, Page page){
  List<clazz> cla = new ArrayList<clazz>();
  String sql = "select * from s_clazz ";
  if(!StringUitl.isEmpty(clazz.getName())){
   sql += "where name like '%" + clazz.getName() + "%'";
  }
  sql += " limit " + page.getStart() + "," + page.getPageSize();
  ResultSet resultSet = query(sql);
  try {
   while (resultSet.next()){
    clazz cl = new clazz();
    cl.setId(resultSet.getInt("id"));
    cl.setName(resultSet.getString("name"));
    cl.setInfo(resultSet.getString("info"));
    cla.add(cl);
   }
  }catch (Exception e){
   e.printStackTrace();
  }
  return cla;
 }
 public int getClazzListTotal(clazz clazz){
  int total = 0;
  String sql = "select count(*)as total from s_clazz ";
  if(!StringUitl.isEmpty(clazz.getName())){
   sql += "where name like '%" + clazz.getName() + "%'";
  }
  ResultSet resultSet = query(sql);
  try {
   while (resultSet.next()){
     total = resultSet.getInt("total");
   }
  }catch (Exception e){
   e.printStackTrace();
  }
   return total;
 }
public boolean addClazz(clazz clazz){
  String sql = "insert into s_clazz values(null,'"+clazz.getName()+"','"+clazz.getInfo()+"')";
  return updata(sql);
 }
 public boolean deleteClazz(int id){
  String sql = "delete from s_clazz where id = "+id;
  return updata(sql);
 }
 public boolean editClazz(clazz clazz){
  String sql = "update s_clazz set name = '" + clazz.getName() +"',info = '" + clazz.getInfo() +"' where id = "+clazz.getId();
  return updata(sql);
 }
}
