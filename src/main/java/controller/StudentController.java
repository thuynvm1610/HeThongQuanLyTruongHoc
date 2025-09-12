package controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccountDAO;
import dao.ClassroomDAO;
import dao.StudentDAO;
import dao.Student_classroomDAO;
import model.Account;
import model.Classroom;
import model.Student;
import model.Student_classroom;

@WebServlet(urlPatterns = "/student")
public class StudentController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");

		String action = req.getParameter("action");

		if (action.equals("personalInformation")) {
			String studentName = (String) req.getSession().getAttribute("studentName");
			req.setAttribute("studentName", studentName);
			
			String studentID = (String) req.getSession().getAttribute("studentID");
			
			StudentDAO studentDAO = new StudentDAO();
			Student student = studentDAO.findByID(studentID);
			
			req.setAttribute("student", student);
			String succeedAddMessage = (String) req.getSession().getAttribute("succeedAddMessage");
			if (succeedAddMessage != null) {
			    req.setAttribute("succeedAddMessage", succeedAddMessage);
			    req.getSession().removeAttribute("succeedAddMessage");
			}
			req.getRequestDispatcher("view/student/personalInformation.jsp").forward(req, resp);
			return;
		} else if (action.equals("accountInformation")) {
			String studentID = (String) req.getSession().getAttribute("studentID");
			AccountDAO accoutDAO = new AccountDAO();
			String accountID = accoutDAO.getAccountID(studentID);
			Account account = accoutDAO.findByID(accountID);
			req.setAttribute("account", account);
			String succeedAddMessage = (String) req.getSession().getAttribute("succeedAddMessage");
			if (succeedAddMessage != null) {
			    req.setAttribute("succeedAddMessage", succeedAddMessage);
			    req.getSession().removeAttribute("succeedAddMessage");
			}
			req.getRequestDispatcher("view/student/accountInformation.jsp").forward(req, resp);
			
		} else if (action.equals("updateEmailForm")) {
			String studentID = (String) req.getSession().getAttribute("studentID");
			StudentDAO studentDAO = new StudentDAO();
			Student student = studentDAO.findByID(studentID);
			req.setAttribute("student", student);
			req.getRequestDispatcher("view/student/updateEmail.jsp").forward(req, resp);
		} else if (action.equals("changePasswordForm")) {
			String studentID = (String) req.getSession().getAttribute("studentID");
			AccountDAO accoutDAO = new AccountDAO();
			String accountID = accoutDAO.getAccountID(studentID);
			Account account = accoutDAO.findByID(accountID);
			req.setAttribute("account", account);
			req.getRequestDispatcher("view/student/changePassword.jsp").forward(req, resp);
		} else if (action.equals("searchClassroomListByStudentID")) {
			String studentID = (String) req.getSession().getAttribute("studentID");
			Student_classroomDAO student_classroomDAO = new Student_classroomDAO();
			List<Student_classroom> student_classroomList = student_classroomDAO.findByID(null, studentID);
			List<Classroom> classroomList = new ArrayList<>();
			ClassroomDAO classroomDAO = new ClassroomDAO();
			for (int i = 0; i < student_classroomList.size(); i++) {
				String classroomID = student_classroomList.get(i).getClassroomID();
				String classroomName = classroomDAO.getClassroomName(classroomID);
				Classroom classroom = new Classroom();
				classroom.setClassroomID(classroomID);
				classroom.setName(classroomName);
				classroom.setTeacherID(null);
				classroomList.add(classroom);
			}
			req.setAttribute("classroomList", classroomList);
			if (student_classroomList.isEmpty()) {
				req.setAttribute("message", "Bạn hiện chưa học lớp nào");
			}
			req.getRequestDispatcher("view/student/classroomListByStudentID.jsp").forward(req, resp);
			return;
		} else if (action.equals("searchClassroomListByOtherStudentID")) {
			String studentID = req.getParameter("studentID");
			req.setAttribute("studentID", studentID);
			Student_classroomDAO student_classroomDAO = new Student_classroomDAO();
			List<Student_classroom> student_classroomList = student_classroomDAO.findByID(null, studentID);
			List<Classroom> classroomList = new ArrayList<>();
			ClassroomDAO classroomDAO = new ClassroomDAO();
			for (int i = 0; i < student_classroomList.size(); i++) {
				String classroomID = student_classroomList.get(i).getClassroomID();
				String classroomName = classroomDAO.getClassroomName(classroomID);
				Classroom classroom = new Classroom();
				classroom.setClassroomID(classroomID);
				classroom.setName(classroomName);
				classroom.setTeacherID(null);
				classroomList.add(classroom);
			}
			req.setAttribute("classroomList", classroomList);
			req.getRequestDispatcher("view/student/classroomListByStudentID.jsp").forward(req, resp);
			return;
		} else if (action.equals("searchStudentListByClassroomID")) {
			String classroomID = req.getParameter("classroomID");
			req.setAttribute("classroomID", classroomID);
			Student_classroomDAO student_classroomDAO = new Student_classroomDAO();
			List<Student_classroom> student_classroomList = student_classroomDAO.findByID(classroomID, null);
			List<Student> studentList = new ArrayList<>();
			StudentDAO studentDAO = new StudentDAO();
			for (int i = 0; i < student_classroomList.size(); i++) {
				String studentID = student_classroomList.get(i).getStudentID();
				String studentName = studentDAO.getStudentName(studentID);
				Student student = new Student();
				student.setStudentID(studentID);
				student.setName(studentName);
				student.setDob(null);
				student.setEmail(null);
				student.setGender(null);
				studentList.add(student);
			}
			req.setAttribute("studentList", studentList);
			req.getRequestDispatcher("view/student/studentListByClassroomID.jsp").forward(req, resp);
			return;
		} else if (action.equals("logout")) {
			HttpSession session = req.getSession(false);
		    if (session != null) {
		        session.invalidate();
		    }
			req.getRequestDispatcher("view/login/loginForm.jsp").forward(req, resp);
			return;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");

		String action = req.getParameter("action");

		if (action.equals("updateEmail")) {
			StudentDAO studentDAO = new StudentDAO();
			StringBuilder message = new StringBuilder();

			if (studentDAO.isEmailExists(req.getParameter("email"), req.getParameter("studentID"))) {
				message.append("Email đã được sử dụng<br>");
			}

			Student student = new Student();
			student.setStudentID(req.getParameter("studentID"));
			student.setName(req.getParameter("name"));
			student.setGender(req.getParameter("gender"));
			student.setDob(Date.valueOf(req.getParameter("dob")));
			student.setHometown(req.getParameter("hometown"));
			if (message.length() > 0) {
				String studentID = (String) req.getSession().getAttribute("studentID");
				Student originalStudent = studentDAO.findByID(studentID);
				String originalEmail = originalStudent.getEmail();
				student.setEmail(originalEmail);
			} else {
				student.setEmail(req.getParameter("email"));
			}

			req.setAttribute("student", student);
			if (message.length() > 0) {
				req.setAttribute("message", message.toString());
				req.getRequestDispatcher("view/student/updateEmail.jsp").forward(req, resp);
				return;
			}

			studentDAO.update(student);
			req.getSession().setAttribute("succeedAddMessage", "Cập nhật email thành công");
			req.getRequestDispatcher("view/student/personalInformation.jsp").forward(req, resp);
			return;
		} else if (action.equals("changePassword")) {
			AccountDAO accountDAO = new AccountDAO();
			
			Account account = new Account();
			account.setAccountID(req.getParameter("accountID"));
			account.setUsername(req.getParameter("username"));
			account.setRole(req.getParameter("role"));
			account.setStudentID(req.getParameter("studentID"));
			account.setPassword(req.getParameter("password"));
			
			req.setAttribute("account", account);
			
			accountDAO.update(account);
			req.getSession().setAttribute("succeedAddMessage", "Cập nhật mật khẩu thành công");
			req.getRequestDispatcher("view/student/accountInformation.jsp").forward(req, resp);
			return;
		}
	}

}
