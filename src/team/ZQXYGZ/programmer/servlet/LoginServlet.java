package team.ZQXYGZ.programmer.servlet;

import team.ZQXYGZ.programmer.dao.AdminDao;
import team.ZQXYGZ.programmer.dao.StudentDao;
import team.ZQXYGZ.programmer.dao.TeacherDao;
import team.ZQXYGZ.programmer.model.admin;
import team.ZQXYGZ.programmer.model.student;
import team.ZQXYGZ.programmer.model.teacher;
import team.ZQXYGZ.programmer.util.StringUitl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录验证Servlrt
 */
public class LoginServlet extends HttpServlet {
 public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
  doPost(request,response);
 }
 public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
  String method = request.getParameter("method");
  if ("logout".equals(method)){
   logout(request,response);
   return;
  }
  String vcode = request.getParameter("vcode");
  String name = request.getParameter("account");
  String password = request.getParameter("password");
  int type = Integer.parseInt(request.getParameter("type"));
  String loginCapcha = request.getSession().getAttribute("loginCapcha").toString();
  if(StringUitl.isEmpty(vcode)){
    response.getWriter().write("vcodeError");
    return;
  }
  if(!vcode.toUpperCase().equals(loginCapcha.toUpperCase())){
   response.getWriter().write("vcodeError");
   return;
  }
  //验证码正确，判断密码和用户名
  String loginStatus = "loginFaild";
  switch (type){
   //管理员权限登录
   case 1:{
    AdminDao adminDao = new AdminDao();
    admin admin = adminDao.login(name,password);
    adminDao.closeCon();
    if(admin == null){
     response.getWriter().write("loginError");
     return;
    }
    HttpSession session = request.getSession();
    session.setAttribute("user",admin);
    session.setAttribute("userType",type);
    loginStatus = "loginSuccess";
    break;
   }
   //学生权限登录
   case 2:{
    StudentDao studentDao = new StudentDao();
    student student = studentDao.login(name,password);
    studentDao.closeCon();
    if(student == null){
     response.getWriter().write("loginError");
     return;
    }
    HttpSession session = request.getSession();
    session.setAttribute("user",student);
    session.setAttribute("userType",type);
    loginStatus = "loginSuccess";
    break;
   }
   //教师权限登录
   case 3:{
    TeacherDao teacherDao = new TeacherDao();
    teacher teacher = teacherDao.login(name,password);
    teacherDao.closeCon();
    if(teacher == null){
     response.getWriter().write("loginError");
     return;
    }
    HttpSession session = request.getSession();
    session.setAttribute("user",teacher);
    session.setAttribute("userType",type);
    loginStatus = "loginSuccess";
    break;
   }
  }
   response.getWriter().write(loginStatus);
 }
 private void logout(HttpServletRequest request,HttpServletResponse response) throws IOException {
   request.getSession().removeAttribute("user");
   request.getSession().removeAttribute("userType");
   response.sendRedirect("index.jsp");
 }
}
