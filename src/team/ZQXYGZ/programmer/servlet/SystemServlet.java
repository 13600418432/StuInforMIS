package team.ZQXYGZ.programmer.servlet;

import team.ZQXYGZ.programmer.dao.AdminDao;
import team.ZQXYGZ.programmer.dao.StudentDao;
import team.ZQXYGZ.programmer.dao.TeacherDao;
import team.ZQXYGZ.programmer.model.admin;
import team.ZQXYGZ.programmer.model.student;
import team.ZQXYGZ.programmer.model.teacher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***
 * 登录后主界面
 */
public class SystemServlet extends HttpServlet {
 public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
  doPost(request,response);
 }
 public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
  String method = request.getParameter("method");
  if("EditPasswod".equals(method)){
   editPassword(request,response);
   return;
  }else if("toPersonalView".equals(method)){
   personalView(request,response);
   return;
  }
  try {
   request.getRequestDispatcher("view/system.jsp").forward(request,response);
  } catch (ServletException e) {
   e.printStackTrace();
  }
 }
 private void editPassword(HttpServletRequest request,HttpServletResponse response){
   String password = request.getParameter("password");
   String newpassword = request.getParameter("newpassword");
   int userType = Integer.parseInt(request.getSession().getAttribute("userType").toString());
   response.setCharacterEncoding("UTF-8");
   if(userType == 1){
    //管理员
    admin admin = (admin) request.getSession().getAttribute("user");
    if(!admin.getPassword().equals(password)){
     try {
      response.getWriter().write("原密码错误！");
     } catch (IOException e) {
      e.printStackTrace();
     }
     return;
    }
    AdminDao adminDao = new AdminDao();
    if(adminDao.editPassword(admin,newpassword)){
     try {
      response.getWriter().write("success");
     } catch (IOException e) {
      e.printStackTrace();
     }
    }else {
     try {
      response.getWriter().write("数据库修改错误");
     } catch (IOException e) {
      e.printStackTrace();
     }
    }
    adminDao.closeCon();
   }
   if(userType == 2){
   //学生
   student student = (student) request.getSession().getAttribute("user");
   if(!student.getPassword().equals(password)){
    try {
     response.getWriter().write("原密码错误！");
    } catch (IOException e) {
     e.printStackTrace();
    }
    return;
   }
    StudentDao studentDao = new StudentDao();
   if(studentDao.editPassword(student,newpassword)){
    try {
     response.getWriter().write("success");
    } catch (IOException e) {
     e.printStackTrace();
    }
   }else {
    try {
     response.getWriter().write("数据库修改错误");
    } catch (IOException e) {
     e.printStackTrace();
    }
   }
   studentDao.closeCon();
  }
  if(userType == 3){
   //管理员
   teacher teacher = (teacher) request.getSession().getAttribute("user");
   if(!teacher.getPassword().equals(password)){
    try {
     response.getWriter().write("原密码错误！");
    } catch (IOException e) {
     e.printStackTrace();
    }
    return;
   }
   TeacherDao teacherDao = new TeacherDao();
   if(teacherDao.editPassword(teacher,newpassword)){
    try {
     response.getWriter().write("success");
    } catch (IOException e) {
     e.printStackTrace();
    }
   }else {
    try {
     response.getWriter().write("数据库修改错误");
    } catch (IOException e) {
     e.printStackTrace();
    }
   }
   teacherDao.closeCon();
  }
 }
 private void personalView(HttpServletRequest request, HttpServletResponse response) {
  try {
   request.getRequestDispatcher("view/personalView.jsp").forward(request, response);
  } catch (ServletException e) {
   e.printStackTrace();
  } catch (IOException e) {
   e.printStackTrace();
  }
 }
}
