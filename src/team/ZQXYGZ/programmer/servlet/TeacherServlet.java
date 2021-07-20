package team.ZQXYGZ.programmer.servlet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import team.ZQXYGZ.programmer.dao.TeacherDao;
import team.ZQXYGZ.programmer.model.Page;
import team.ZQXYGZ.programmer.model.teacher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherServlet extends HttpServlet {
 public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
  doPost(request,response);
 }
 public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
  String method = request.getParameter("method");
  if ("toTeacherListView".equals(method)){
   Teacher(request,response);
  }else if("AddTeacher".equals(method)){
   addTeacher(request,response);
  }else if("TeacherList".equals(method)){
   teacherList(request,response);
  }else if("EditTeacher".equals(method)){
   editTeacher(request,response);
  }else if("DeleteTeacher".equals(method)){
   deleteTeacher(request,response);
  }
 }

 private void deleteTeacher(HttpServletRequest request, HttpServletResponse response) {
  String[] ids = request.getParameterValues("ids[]");
  String idStr = "";
  for(String id : ids){
   idStr += id + ",";
  }
  idStr = idStr.substring(0,idStr.length()-1);
  TeacherDao teacherDao = new TeacherDao();
  if(teacherDao.deleteTeacher(idStr)){
   try {
    response.getWriter().write("success");
   } catch (IOException e) {
    e.printStackTrace();
   }finally {
    teacherDao.closeCon();
   }
  }
 }

 private void editTeacher(HttpServletRequest request, HttpServletResponse response) {
 String name = request.getParameter("name");
 int id = Integer.parseInt(request.getParameter("id"));
 String sex = request.getParameter("sex");
 String mobile = request.getParameter("mobile");
 String qq = request.getParameter("qq");
 String sn = request.getParameter("sn");
 int clazzid = Integer.parseInt(request.getParameter("clazzid"));
 teacher teacher = new teacher();
 teacher.setSex(sex);
 teacher.setQq(qq);
 teacher.setMobile(mobile);
 teacher.setSn(sn);
 teacher.setClazzId(clazzid);
 teacher.setName(name);
 teacher.setId(id);
 TeacherDao teacherDao = new TeacherDao();
 if(teacherDao.editStudent(teacher)){
  try {
   response.getWriter().write("success");
  } catch (IOException e) {
   e.printStackTrace();
  }finally {
   teacherDao.closeCon();
  }
 }
 }

 private void teacherList(HttpServletRequest request, HttpServletResponse response) {
  String name = request.getParameter("studentName");
  int userType = Integer.parseInt(request.getSession().getAttribute("userType").toString());
  Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
  Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
  Integer clazz = request.getParameter("clazzid") == null ? 0 : Integer.parseInt(request.getParameter("clazzid"));
  teacher teacher = new teacher();
  teacher.setName(name);
  teacher.setClazzId(clazz);
  if(userType == 3){
   //如果是老师，只能查看自己的信息
   teacher currentUser = (teacher) request.getSession().getAttribute("user");
   teacher.setId(currentUser.getId());
  }
  TeacherDao teacherDao = new TeacherDao();
  List<teacher> teacherList = teacherDao.getTeacherList(teacher,new Page(currentPage,pageSize));
  int total = teacherDao.getStudentListTotal(teacher);
  teacherDao.closeCon();
  response.setCharacterEncoding("UTF-8");
  Map<String,Object> ret = new HashMap<String, Object>();
  ret.put("total",total);
  ret.put("rows",teacherList);
  try {
   String from = request.getParameter("from");
   if("combox".equals(from)){
    response.getWriter().write(JSONArray.fromObject(teacherList).toString());
   }else{
    response.getWriter().write(JSONObject.fromObject(ret).toString());
   }
  } catch (IOException e) {
   e.printStackTrace();
  }
 }

 private void addTeacher(HttpServletRequest request, HttpServletResponse response) {
  String name = request.getParameter("name");
  String password = request.getParameter("password");
  String sex = request.getParameter("sex");
  int clazzid = Integer.parseInt(request.getParameter("clazzid"));
  String sn = request.getParameter("sn");
  String qq = request.getParameter("qq");
  String phone = request.getParameter("mobile");
  teacher teacher = new teacher();
  teacher.setClazzId(clazzid);
  teacher.setName(name);
  teacher.setMobile(phone);
  teacher.setPassword(password);
  teacher.setQq(qq);
  teacher.setSex(sex);
  teacher.setSn(sn);
  TeacherDao teacherDao = new TeacherDao();
  if (teacherDao.addTeacher(teacher)){
   try {
    response.getWriter().write("success");
   } catch (IOException e) {
    e.printStackTrace();
   }finally {
    teacherDao.closeCon();
   }
  }
 }

 private void Teacher(HttpServletRequest request, HttpServletResponse response) {
  try {
   request.getRequestDispatcher("view/teacherList.jsp").forward(request,response);
  } catch (ServletException e) {
   e.printStackTrace();
  } catch (IOException e) {
   e.printStackTrace();
  }
 }
}
