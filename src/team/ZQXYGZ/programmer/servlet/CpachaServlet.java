package team.ZQXYGZ.programmer.servlet;

import team.ZQXYGZ.programmer.util.CpachaUtil;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码Servlet
 */
public class CpachaServlet extends HttpServlet {
 public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
  doPost(request,response);
 }
  public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
 String method = request.getParameter("method");
 if("loginCapcha".equals(method)){
   generateLoginCpacha(request,response);
   return;
 }
 response.getWriter().write("error mrthod");
  }

 private void generateLoginCpacha(HttpServletRequest request,HttpServletResponse response) throws IOException {
  CpachaUtil cpachaUtil = new CpachaUtil();
  String generatorVCode = cpachaUtil.generatorVCode();
  request.getSession().setAttribute("loginCapcha",generatorVCode);
  BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);
  ImageIO.write(generatorRotateVCodeImage,"gif",response.getOutputStream());
 }
}
