package controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccountDAO;
import dao.TeacherDAO;
import model.Account;
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
		} else if (action.equals("updateEmailForm")) {
			String teacherID = (String) req.getSession().getAttribute("teacherID");
			TeacherDAO teacherDAO = new TeacherDAO();
			Teacher teacher = teacherDAO.findById(teacherID);
			req.setAttribute("teacher", teacher);
			req.getRequestDispatcher("view/teacher/updateEmail.jsp").forward(req, resp);
		} else if (action.equals("accountInformation")) {
			String teacherID = (String) req.getSession().getAttribute("teacherID");
			AccountDAO accoutDAO = new AccountDAO();
			String accountID = accoutDAO.getTeacherAccountID(teacherID);
			Account account = accoutDAO.findByID(accountID);
			req.setAttribute("account", account);
			String succeedAddMessage = (String) req.getSession().getAttribute("succeedAddMessage");
			if (succeedAddMessage != null) {
			    req.setAttribute("succeedAddMessage", succeedAddMessage);
			    req.getSession().removeAttribute("succeedAddMessage");
			}
			req.getRequestDispatcher("view/teacher/accountInformation.jsp").forward(req, resp);
		} else if (action.equals("changePasswordForm")) {
			String teacherID = (String) req.getSession().getAttribute("teacherID");
			AccountDAO accoutDAO = new AccountDAO();
			String accountID = accoutDAO.getTeacherAccountID(teacherID);
			Account account = accoutDAO.findByID(accountID);
			req.setAttribute("account", account);
			req.getRequestDispatcher("view/teacher/changePassword.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");

		String action = req.getParameter("action");
		
		if (action.equals("updateEmail")) {
			TeacherDAO teacherDAO = new TeacherDAO();
			StringBuilder message = new StringBuilder();

			if (teacherDAO.isEmailExists(req.getParameter("email"), req.getParameter("teacherID"))) {
				message.append("Email đã được sử dụng<br>");
			}

			Teacher teacher = new Teacher();
			teacher.setTeacherID(req.getParameter("teacherID"));
			teacher.setName(req.getParameter("name"));
			teacher.setGender(req.getParameter("gender"));
			teacher.setDob(Date.valueOf(req.getParameter("dob")));
			teacher.setHometown(req.getParameter("hometown"));
			if (message.length() > 0) {
				String teacherID = (String) req.getSession().getAttribute("teacherID");
				Teacher originalTeacher = teacherDAO.findById(teacherID);
				String originalEmail = originalTeacher.getEmail();
				teacher.setEmail(originalEmail);
			} else {
				teacher.setEmail(req.getParameter("email"));
			}

			req.setAttribute("teacher", teacher);
			if (message.length() > 0) {
				req.setAttribute("message", message.toString());
				req.getRequestDispatcher("view/teacher/updateEmail.jsp").forward(req, resp);
				return;
			}

			teacherDAO.update(teacher);
			req.getSession().setAttribute("succeedAddMessage", "Cập nhật email thành công");
			req.getRequestDispatcher("view/teacher/personalInformation.jsp").forward(req, resp);
			return;
		} else if (action.equals("changePassword")) {
			AccountDAO accountDAO = new AccountDAO();
			
			Account account = new Account();
			account.setAccountID(req.getParameter("accountID"));
			account.setUsername(req.getParameter("username"));
			account.setRole(req.getParameter("role"));
			account.setTeacherID(req.getParameter("teacherID"));
			account.setPassword(req.getParameter("password"));
			
			req.setAttribute("account", account);
			
			accountDAO.update(account);
			req.getSession().setAttribute("succeedAddMessage", "Cập nhật mật khẩu thành công");
			req.getRequestDispatcher("view/teacher/accountInformation.jsp").forward(req, resp);
			return;
		}
	}
}
