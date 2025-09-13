package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TeacherDAO;
import model.Teacher;

@WebServlet(urlPatterns = "/teacher")
public class TeacherController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");

		String action = req.getParameter("action");
		
		if (action.equals("personalInformation")) {
			String teacherName = (String) req.getSession().getAttribute("teacherName");
			req.setAttribute("teacherName", teacherName);
			
			String teacherID = (String) req.getSession().getAttribute("teacherID");
			
			TeacherDAO teacherDAO = new TeacherDAO();
			Teacher teacher = teacherDAO.findById(teacherID);
			
			req.setAttribute("teacher", teacher);
			String succeedAddMessage = (String) req.getSession().getAttribute("succeedAddMessage");
			if (succeedAddMessage != null) {
			    req.setAttribute("succeedAddMessage", succeedAddMessage);
			    req.getSession().removeAttribute("succeedAddMessage");
			}
			req.getRequestDispatcher("view/teacher/personalInformation.jsp").forward(req, resp);
			return;
		}
	}
}
