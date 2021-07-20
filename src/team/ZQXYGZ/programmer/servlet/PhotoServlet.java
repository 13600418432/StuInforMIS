package team.ZQXYGZ.programmer.servlet;

import com.lizhou.exception.FileFormatException;
import com.lizhou.exception.NullFileException;
import com.lizhou.exception.ProtocolException;
import com.lizhou.exception.SizeException;
import com.lizhou.fileload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import team.ZQXYGZ.programmer.dao.StudentDao;
import team.ZQXYGZ.programmer.dao.TeacherDao;
import team.ZQXYGZ.programmer.model.student;
import team.ZQXYGZ.programmer.model.teacher;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PhotoServlet extends HttpServlet {
 public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
  doPost(request,response);
 }
 public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
  String method = request.getParameter("method");
  if("getPhoto".equals(method)){
   getPhoto(request,response);
  }if("SetPhoto".equals(method)){
   uploadPhoto(request,response);
  }
 }

 private void uploadPhoto(HttpServletRequest request,HttpServletResponse response) {
  // TODO Auto-generated method stub
  int sid = request.getParameter("sid") == null ? 0 : Integer.parseInt(request.getParameter("sid"));
  int tid = request.getParameter("tid") == null ? 0 : Integer.parseInt(request.getParameter("tid"));
  FileUpload fileUpload = new FileUpload(request);
  fileUpload.setFileFormat("jpg");
  fileUpload.setFileFormat("png");
  fileUpload.setFileFormat("jpeg");
  fileUpload.setFileFormat("gif");
  fileUpload.setFileSize(2048);
  response.setCharacterEncoding("UTF-8");
  try {
   InputStream uploadInputStream = fileUpload.getUploadInputStream();
   if(sid != 0){
    student student = new student();
    student.setId(sid);
    student.setPhoto(uploadInputStream);
    StudentDao studentDao = new StudentDao();
    if(studentDao.setStudentPhoto(student)){
     response.getWriter().write("<div id='message'>上传成功！</div>");
    }else{
     response.getWriter().write("<div id='message'>上传失败！</div>");
    }
   }
   if(tid != 0){
    teacher teacher = new teacher();
    teacher.setId(tid);
    teacher.setPhoto(uploadInputStream);
    TeacherDao teacherDao = new TeacherDao();
    if(teacherDao.setTeacherPhoto(teacher)){
     response.getWriter().write("<div id='message'>上传成功！</div>");
    }else{
     response.getWriter().write("<div id='message'>上传失败！</div>");
    }
   }
  } catch (ProtocolException e) {
   // TODO Auto-generated catch block
   try {
    response.getWriter().write("<div id='message'>上传协议错误！</div>");
   } catch (IOException e1) {
    // TODO Auto-generated catch block
    e1.printStackTrace();
   }
   e.printStackTrace();
  }catch (NullFileException e1) {
   // TODO: handle exception
   try {
    response.getWriter().write("<div id='message'>上传的文件为空!</div>");
   } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
   e1.printStackTrace();
  }
  catch (SizeException e2) {
   // TODO: handle exception
   try {
    response.getWriter().write("<div id='message'>上传文件大小不能超过"+fileUpload.getFileSize()+"！</div>");
   } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
   e2.printStackTrace();
  }
  catch (IOException e3) {
   // TODO: handle exception
   try {
    response.getWriter().write("<div id='message'>读取文件出错！</div>");
   } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
   e3.printStackTrace();
  }
  catch (FileFormatException e4) {
   // TODO: handle exception
   try {
    response.getWriter().write("<div id='message'>上传文件格式不正确，请上传 "+fileUpload.getFileFormat()+" 格式的文件！</div>");
   } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
   e4.printStackTrace();
  }
  catch (FileUploadException e5) {
   // TODO: handle exception
   try {
    response.getWriter().write("<div id='message'>上传文件失败！</div>");
   } catch (IOException e6) {
    // TODO Auto-generated catch block
    e6.printStackTrace();
   }
   e5.printStackTrace();
  }
 }

 private void getPhoto(HttpServletRequest request, HttpServletResponse response) {
   int sid = request.getParameter("sid") == null? 0:Integer.parseInt(request.getParameter("sid"));
   int tid = request.getParameter("tid") == null? 0:Integer.parseInt(request.getParameter("tid"));
   if(sid !=0){
    //学生
    StudentDao studentDao = new StudentDao();
    student student = studentDao.getStudent(sid);
    studentDao.closeCon();
    if(student!=null){
     InputStream photo = student.getPhoto();
     if(photo!= null){
      try {
       byte[] b = new byte[photo.available()];
       photo.read(b);
       response.getOutputStream().write(b,0,b.length);
      } catch (IOException e) {
       e.printStackTrace();
      }
      return;
     }
    }
   }
   if(tid != 0){
    //老师
    TeacherDao teacherDao = new TeacherDao();
    teacher teacher = teacherDao.getTeacher(tid);
    teacherDao.closeCon();
    if(teacher!=null){
     InputStream photo = teacher.getPhoto();
     if(photo!= null){
      try {
       byte[] b = new byte[photo.available()];
       photo.read(b);
       response.getOutputStream().write(b,0,b.length);
      } catch (IOException e) {
       e.printStackTrace();
      }
      return;
     }
    }
   }
   String path = request.getSession().getServletContext().getRealPath("");
  File file = new File(path+"\\file\\logo.jpg");
  try {
   FileInputStream fis = new FileInputStream(file);
   byte[] b = new byte[fis.available()];
   fis.read(b);
   response.getOutputStream().write(b,0,b.length);
  } catch (Exception e) {
   e.printStackTrace();
  }
 }
}
