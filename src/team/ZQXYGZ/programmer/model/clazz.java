package team.ZQXYGZ.programmer.model;

import javax.print.attribute.standard.PrinterURI;

/**
 * 班级数据库实体表
 */
public class clazz {
 private int id;
 private String name;
 private String info;

 public int getId() {
  return id;
 }

 public void setId(int id) {
  this.id = id;
 }

 public String getName() {
  return name;
 }

 public void setName(String name) {
  this.name = name;
 }

 public String getInfo() {
  return info;
 }

 public void setInfo(String info) {
  this.info = info;
 }
}
