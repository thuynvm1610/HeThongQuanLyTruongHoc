package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccountDAO;
import dao.LoginDAO;
import model.Account;

@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");

		String action = req.getParameter("action");

		if (action.equals("loginForm")) {
			req.getRequestDispatcher("view/login/loginForm.jsp").forward(req, resp);
			return;
		} else if (action.equals("signUpForm")) {
			req.getRequestDispatcher("view/login/signUpForm.jsp").forward(req, resp);
			return;
		} else if (action.equals("loginRequest")) {
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			LoginDAO loginDAO = new LoginDAO();

			StringBuilder message = new StringBuilder();

			if (!loginDAO.isUsernameExists(username)) {
				message.append("Tên tài khoản không tồn tại<br>");
			} else if (!loginDAO.isPasswordValid(username, password)) {
				message.append("Mật khẩu sai<br>");
			}

			if (message.length() > 0) {
				req.getSession().setAttribute("username", username);
				req.getSession().setAttribute("password", password);
				req.setAttribute("message", message.toString());
				req.getRequestDispatcher("view/login/loginForm.jsp").forward(req, resp);
				return;
			}
			
			String role = loginDAO.getRole(username);
			String studentName = loginDAO.getStudentName(username);
			String studentID = loginDAO.getStudentID(username);
			if (role.equals("admin")) {		
				resp.sendRedirect("admin?action=dashboard");
			} else if (role.equals("student")) {
				req.getSession().setAttribute("studentName", studentName);
				req.getSession().setAttribute("studentID", studentID);
				resp.sendRedirect("student?action=personalInformation");
			}
			return;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");

		String action = req.getParameter("action");
		
		if (action.equals("addStudentAccount")) {
			LoginDAO loginDAO = new LoginDAO();
			AccountDAO accountDAO = new AccountDAO();
			StringBuilder message = new StringBuilder();

			if (loginDAO.isStudentIDUsed(req.getParameter("studentID"))) {
				message.append("Mã sinh viên đã được sử dụng<br>");
			} else if (!loginDAO.isStudentExists(req.getParameter("studentID"))) {
				message.append("Mã sinh viên không tồn tại<br>");
			} else if (loginDAO.isUsernameExists(req.getParameter("username"))) {
				message.append("Tên tài khoản đã được sử dụng<br>");
			} else if (!loginDAO.isConfirmPasswordValid(req.getParameter("confirmPassword"), req.getParameter("password"))) {
				message.append("Xác nhận mật khẩu sai<br>");
			}

			String accountID = loginDAO.generateNextAccountID();
			Account account = new Account();
			account.setAccountID(accountID);
			account.setUsername(req.getParameter("username"));
			account.setPassword(req.getParameter("password"));
			account.setRole("student");
			account.setStudentID(req.getParameter("studentID"));

			if (message.length() > 0) {
				req.setAttribute("account", account);
				req.setAttribute("message", message.toString());
				req.setAttribute("studentID", req.getParameter("studentID"));
				req.setAttribute("username", req.getParameter("username"));
				req.setAttribute("password", req.getParameter("password"));
				req.getRequestDispatcher("view/login/signUpForm.jsp").forward(req, resp);
				return;
			}
			
			accountDAO.insert(account);
			req.setAttribute("succeedAddMessage", "Đăng ký tài khoản thành công");
			req.getRequestDispatcher("view/login/signUpForm.jsp").forward(req, resp);
			return;
		}
	}
	
}
