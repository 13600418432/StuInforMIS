package team.ZQXYGZ.programmer.servlet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import team.ZQXYGZ.programmer.dao.StudentDao;
import team.ZQXYGZ.programmer.model.Page;
import team.ZQXYGZ.programmer.model.student;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学生Servlet
 */
public class StudentServlet extends HttpServlet {
 public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
  doPost(request,response);
 }
 public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
  String method = request.getParameter("method");
  if ("toStudentListView".equals(method)){
     Student(request,response);
  }else if("AddStudent".equals(method)){
     addStudent(request,response);
  }else if("StudentList".equals(method)){
   studentList(request,response);
  }else if("EditStudent".equals(method)){
   editStudent(request,response);
  }else if("DeleteStudent".equals(method)){
   deleteStudent(request,response);
  }
 }

 private void deleteStudent(HttpServletRequest request, HttpServletResponse response) {
  String[] ids = request.getParameterValues("ids[]");
  String idStr = "";
  for(String id : ids){
   idStr += id + ",";
  }
  idStr = idStr.substring(0,idStr.length()-1);
  StudentDao studentDao = new StudentDao();
  if(studentDao.deleteStudent(idStr)){
   try {
    response.getWriter().write("success");
   } catch (IOException e) {
    e.printStackTrace();
   }finally {
    studentDao.closeCon();
   }
  }
 }

 private void editStudent(HttpServletRequest request,HttpServletResponse response) {
  String name = request.getParameter("name");
  int id = Integer.parseInt(request.getParameter("id"));
  String sex = request.getParameter("sex");
  String sn = request.getParameter("sn");
  String mobile = request.getParameter("mobile");
  String qq = request.getParameter("qq");
  int clazzId = Integer.parseInt(request.getParameter("clazzid"));
  student student = new student();
  student.setClazzId(clazzId);
  student.setMobile(mobile);
  student.setName(name);
  student.setSn(sn);
  student.setId(id);
  student.setQq(qq);
  student.setSex(sex);
  StudentDao studentDao = new StudentDao();
  if(studentDao.editStudent(student)){
   try {
    response.getWriter().write("success");
   } catch (IOException e) {
    e.printStackTrace();
   }finally{
    studentDao.closeCon();
   }
  }
 }

 private void studentList(HttpServletRequest request, HttpServletResponse response) {
  String name = request.getParameter("studentName");
  int userType = Integer.parseInt(request.getSession().getAttribute("userType").toString());
  Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
  Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
  Integer clazz = request.getParameter("clazzid") == null ? 0 : Integer.parseInt(request.getParameter("clazzid"));
  student student = new student();
  student.setName(name);
  student.setClazzId(clazz);
  if(userType == 2){
   //如果是学生，只能查看自己的信息
   student currentUser = (student)request.getSession().getAttribute("user");
   student.setId(currentUser.getId());
  }
  StudentDao studentDao = new StudentDao();
  List<student> studentList = studentDao.getStudentList(student, new Page(currentPage,pageSize));
  int total = studentDao.getStudentListTotal(student);
  studentDao.closeCon();
  response.setCharacterEncoding("UTF-8");
  Map<String,Object> ret = new HashMap<String, Object>();
  ret.put("total",total);
  ret.put("rows",studentList);
  try {
   String from = request.getParameter("from");
   if("combox".equals(from)){
    response.getWriter().write(JSONArray.fromObject(studentList).toString());
   }else{
    response.getWriter().write(JSONObject.fromObject(ret).toString());
   }
  } catch (IOException e) {
   e.printStackTrace();
  }
 }

 private void addStudent(HttpServletRequest request, HttpServletResponse response) {
  String name = request.getParameter("name");
  String nd = request.getParameter("nd");
  String password = request.getParameter("password");
  String sex = request.getParameter("sex");
  String mobile = request.getParameter("mobile");
  String qq = request.getParameter("qq");
  Integer clazzId = Integer.parseInt(request.getParameter("clazzid"));
  student student = new student();
  student.setName(name);
  student.setSn(nd);
  student.setPassword(password);
  student.setSex(sex);
  student.setMobile(mobile);
  student.setQq(qq);
  student.setClazzId(clazzId);
  StudentDao studentDao = new StudentDao();
  if(studentDao.addStudent(student)){
   try {
    response.getWriter().write("success");
   } catch (IOException e) {
    e.printStackTrace();
   }finally {
    studentDao.closeCon();
   }
  }
 }
 private void Student(HttpServletRequest request, HttpServletResponse response){
  try {
   request.getRequestDispatcher("view/studentList.jsp").forward(request,response);
  } catch (ServletException | IOException e) {
   e.printStackTrace();
  }
 }
}
