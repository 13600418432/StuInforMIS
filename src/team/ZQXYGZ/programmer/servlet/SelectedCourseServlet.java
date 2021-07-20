package team.ZQXYGZ.programmer.servlet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import team.ZQXYGZ.programmer.dao.CourseDao;
import team.ZQXYGZ.programmer.dao.SelectedCourseDao;
import team.ZQXYGZ.programmer.model.Page;
import team.ZQXYGZ.programmer.model.SelectedCourse;
import team.ZQXYGZ.programmer.model.student;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectedCourseServlet extends HttpServlet {
 public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
  doPost(request, response);
 }
 public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
  String method = request.getParameter("method");
  if("toSelectedCourseListView".equals(method)){
   try {
    request.getRequestDispatcher("view/selectedCourseList.jsp").forward(request, response);
   } catch (ServletException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
  }else if("AddSelectedCourse".equals(method)){
   addSelectedCourse(request,response);
  }else if("SelectedCourseList".equals(method)){
   getSelectedCourseList(request,response);
  }else if("DeleteSelectedCourse".equals(method)){
   deleteSelectedCourse(request,response);
  }
 }

 private void addSelectedCourse(HttpServletRequest request, HttpServletResponse response) {
  int studentId = request.getParameter("studentid") == null ? 0 : Integer.parseInt(request.getParameter("studentid").toString());
  int courseId = request.getParameter("courseid") == null ? 0 : Integer.parseInt(request.getParameter("courseid").toString());
  CourseDao courseDao = new CourseDao();
  String msg = "success";
  if(courseDao.isFull(courseId)){
   msg = "courseFull";
   try {
    response.getWriter().write(msg);
   } catch (IOException e) {
    e.printStackTrace();
   }
   courseDao.closeCon();
   return;
  }
  SelectedCourseDao selectedCourseDao = new SelectedCourseDao();
  if(selectedCourseDao.isSelected(studentId, courseId)){
   msg = "courseSelected";
   try {
    response.getWriter().write(msg);
   } catch (IOException e) {
    e.printStackTrace();
   }
   selectedCourseDao.closeCon();
   return;
  }
  SelectedCourse selectedCourse = new SelectedCourse();
  selectedCourse.setStudentId(studentId);
  selectedCourse.setCourseId(courseId);
  if(selectedCourseDao.addSelectedCourse(selectedCourse)){
   msg = "success";
  }
  courseDao.updateCourseSelectedNum(courseId, 1);
  courseDao.closeCon();
  selectedCourseDao.closeCon();
  try {
   response.getWriter().write(msg);
  } catch (IOException e) {
   e.printStackTrace();
  }
 }

 private void getSelectedCourseList(HttpServletRequest request, HttpServletResponse response) {
  int studentId = request.getParameter("studentid") == null ? 0 : Integer.parseInt(request.getParameter("studentid").toString());
  int courseId = request.getParameter("courseid") == null ? 0 : Integer.parseInt(request.getParameter("courseid").toString());
  Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
  Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
  SelectedCourse selectedCourse = new SelectedCourse();
  //获取当前登录用户类型
  int userType = Integer.parseInt(request.getSession().getAttribute("userType").toString());
  if(userType == 2){
   //如果是学生，只能查看自己的信息
   student currentUser = (student)request.getSession().getAttribute("user");
   studentId = currentUser.getId();
  }
  selectedCourse.setCourseId(courseId);
  selectedCourse.setStudentId(studentId);
  SelectedCourseDao selectedCourseDao = new SelectedCourseDao();
  List<SelectedCourse> courseList = selectedCourseDao.getSelectedCourseList(selectedCourse, new Page(currentPage, pageSize));
  int total = selectedCourseDao.getSelectedCourseListTotal(selectedCourse);
  selectedCourseDao.closeCon();
  response.setCharacterEncoding("UTF-8");
  Map<String, Object> ret = new HashMap<String, Object>();
  ret.put("total", total);
  ret.put("rows", courseList);
  try {
   String from = request.getParameter("from");
   if("combox".equals(from)){
    response.getWriter().write(JSONArray.fromObject(courseList).toString());
   }else{
    response.getWriter().write(JSONObject.fromObject(ret).toString());
   }
  } catch (IOException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
 }

 private void deleteSelectedCourse(HttpServletRequest request, HttpServletResponse response) {
  int id = Integer.parseInt(request.getParameter("id"));
  SelectedCourseDao selectedCourseDao = new SelectedCourseDao();
  SelectedCourse selectedCourse = selectedCourseDao.getSelectedCourse(id);
  String msg = "success";
  if(selectedCourse == null){
   msg = "not found";
   try {
    response.getWriter().write(msg);
   } catch (IOException e) {
    e.printStackTrace();
   }
   selectedCourseDao.closeCon();
   return;
  }
  if(selectedCourseDao.deleteSelectedCourse(selectedCourse.getId())){
   CourseDao courseDao = new CourseDao();
   courseDao.updateCourseSelectedNum(selectedCourse.getCourseId(), -1);
   courseDao.closeCon();
  }else{
   msg = "error";
  }
  selectedCourseDao.closeCon();
  try {
   response.getWriter().write(msg);
  } catch (IOException e) {
   e.printStackTrace();
  }
 }
}
