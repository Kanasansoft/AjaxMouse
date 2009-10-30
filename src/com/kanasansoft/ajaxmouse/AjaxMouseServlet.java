package com.kanasansoft.ajaxmouse;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjaxMouseServlet extends HttpServlet {

	protected void doGet(
		HttpServletRequest request, 
		HttpServletResponse response
	) throws ServletException, IOException {

		Robot robot;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
			return;
		}

		String eventType = request.getParameter("eventtype");

		if("mousemove".equals(eventType)){

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int minX = 0;
			int minY = 0;
			int maxX = screenSize.width;
			int maxY = screenSize.height;

			int moveX = 0;
			int moveY = 0;
			try{
				moveX = Integer.parseInt(request.getParameter("x"));
			}catch(Exception e){}
			try{
				moveY = Integer.parseInt(request.getParameter("y"));
			}catch(Exception e){}

			Point point = MouseInfo.getPointerInfo().getLocation();
			int currentX = point.x;
			int currentY = point.y;

			int nextX = currentX + moveX;
			int nextY = currentY + moveY;

			if(minX > nextX){ nextX = minX; }
			if(minY > nextY){ nextY = minY; }
			if(maxX < nextX){ nextX = maxX; }
			if(maxY < nextY){ nextY = maxY; }

			robot.mouseMove(nextX, nextY);

		}else if("mousedown".equals(eventType)){
			String buttonType = request.getParameter("buttontype");
			if("left".equals(buttonType)){
				robot.mousePress(InputEvent.BUTTON1_MASK);
			}else if("right".equals(buttonType)){
				robot.mousePress(InputEvent.BUTTON3_MASK);
			}
		}else if("mouseup".equals(eventType)){
			String buttonType = request.getParameter("buttontype");
			if("left".equals(buttonType)){
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
			}else if("right".equals(buttonType)){
				robot.mouseRelease(InputEvent.BUTTON3_MASK);
			}
		}else if("mousewheel".equals(eventType)){
			int wheel = 0;
			try{
				wheel = Integer.parseInt(request.getParameter("wheel"));
			}catch(Exception e){}
			robot.mouseWheel(-wheel);
		}

		response.setContentType("text/plain");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println("ok");
/*
		String status = "";
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()){
			String paramName = paramNames.nextElement();
			String paramValue = request.getParameter(paramName);
			status+=paramName+":"+paramValue+"\r\n";
		}
		response.getWriter().println(status);
*/
	}
}
