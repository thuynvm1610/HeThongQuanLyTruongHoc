package controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
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
import dao.TeacherDAO;
import model.Account;
import model.Classroom;
import model.Student;
import model.Student_classroom;
import model.Teacher;

@WebServlet(urlPatterns = "/admin")
public class AdminController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");

		String action = req.getParameter("action");

		if (action.equals("teacherList")) {
			TeacherDAO teacherDAO = new TeacherDAO();
			
			int recordsPerPage = 5;
			int currentPage = 1;

			String pageStr = req.getParameter("page");
			if (pageStr != null) {
			    currentPage = Integer.parseInt(pageStr);
			}
			int start = (currentPage - 1) * recordsPerPage;

			List<Teacher> teacherList = new ArrayList<Teacher>();
			Boolean isSearchTeacherFunctionUse = (Boolean) req.getSession().getAttribute("isSearchTeacherFunctionUse");
			Boolean isTeacherFilterFunctionUse = (Boolean) req.getSession().getAttribute("isTeacherFilterFunctionUse");
		    if (isSearchTeacherFunctionUse != null && isSearchTeacherFunctionUse) {
		    	teacherList = (List<Teacher>) req.getSession().getAttribute("teacherList");
		        req.getSession().removeAttribute("isSearchTeacherFunctionUse");
		    } else if (isTeacherFilterFunctionUse != null && isTeacherFilterFunctionUse) {
		    	teacherList = (List<Teacher>) req.getSession().getAttribute("teacherList");
		        req.getSession().removeAttribute("isTeacherFilterFunctionUse");
		    } else {
				teacherList = teacherDAO.getTeachersPaginated(start, recordsPerPage);
		    }
			
		    int totalRecords = teacherDAO.countTeachers();
			int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

			req.setAttribute("teacherList", teacherList);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("totalPages", totalPages);
			
			req.getSession().setAttribute("teacherList", teacherList);
			req.getSession().setAttribute("currentPage", currentPage);
			req.getSession().setAttribute("totalPages", totalPages);
			
			String succeedAddMessage = (String) req.getSession().getAttribute("succeedAddMessage");
			if (succeedAddMessage != null) {
			    req.setAttribute("succeedAddMessage", succeedAddMessage);
			    req.getSession().removeAttribute("succeedAddMessage");
			}
			String succeedUpdateMessage = (String) req.getSession().getAttribute("succeedUpdateMessage");
			if (succeedUpdateMessage != null) {
			    req.setAttribute("succeedUpdateMessage", succeedUpdateMessage);
			    req.getSession().removeAttribute("succeedUpdateMessage");
			}
			String succeedDeleteMessage = (String) req.getSession().getAttribute("succeedDeleteMessage");
			if (succeedDeleteMessage != null) {
			    req.setAttribute("succeedDeleteMessage", succeedDeleteMessage);
			    req.getSession().removeAttribute("succeedDeleteMessage");
			}
			String infoMessage = (String) req.getSession().getAttribute("infoMessage");
			if (infoMessage != null) {
			    req.setAttribute("infoMessage", infoMessage);
			    req.getSession().removeAttribute("infoMessage");
			}
			List<Integer> yobList = teacherDAO.getListOfYear();
			req.setAttribute("yobList", yobList);
			List<String> hometownList = teacherDAO.getListOfHometown();
			req.setAttribute("hometownList", hometownList);
			req.getRequestDispatcher("view/admin/teacherList.jsp").forward(req, resp);
			return;
		} else if (action.equals("teacherFilter")) {
			String gender = req.getParameter("gender");
			String yob = req.getParameter("yob");
			String hometown = req.getParameter("hometown");
			TeacherDAO teacherDAO = new TeacherDAO();
			List<Teacher> teacherList = new ArrayList<>();
			
			teacherList = teacherDAO.teacherListByFilter(gender, yob, hometown);
			List<Integer> yobList = teacherDAO.getListOfYear();
			req.setAttribute("yobList", yobList);
			List<String> hometownList = teacherDAO.getListOfHometown();
			req.setAttribute("hometownList", hometownList);
			req.getSession().setAttribute("teacherList", teacherList);
			boolean isTeacherFilterFunctionUse = true;
			req.getSession().setAttribute("isTeacherFilterFunctionUse", isTeacherFilterFunctionUse);
			resp.sendRedirect("admin?action=teacherList");
		} else if (action.equals("exportTeachers")) {
			HttpSession session = req.getSession();
	        List<Teacher> teacherList = (List<Teacher>) session.getAttribute("teacherList");

	        if (teacherList == null || teacherList.isEmpty()) {
	            req.getSession().setAttribute("infoMessage", "Không có dữ liệu để xuất file");
	            resp.sendRedirect("admin?action=teacherList");
	        } else {
	        	resp.setContentType("text/csv");
		        resp.setHeader("Content-Disposition", "attachment;filename=teacher_list.csv");

		        OutputStream out = resp.getOutputStream();
			    // Ghi BOM để Excel hiểu UTF-8
			    out.write(0xEF);
			    out.write(0xBB);
			    out.write(0xBF);

			    PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8), true);

		        writer.println("Mã GV;Họ và tên;Giới tính;Ngày sinh;Email;Quê quán");

		        for (Teacher t : teacherList) {
		            writer.printf("%s;%s;%s;%s;%s;%s\n",
		                    t.getTeacherID(), t.getName(), t.getGender(), t.getDob(), t.getEmail(), t.getHometown());
		        }

		        writer.flush();
		        writer.close();
	        }
	        return;
		} else if (action.equals("classroomList")) {
			ClassroomDAO classroomDAO = new ClassroomDAO();
			
			int recordsPerPage = 5;
			int currentPage = 1;

			String pageStr = req.getParameter("page");
			if (pageStr != null) {
			    currentPage = Integer.parseInt(pageStr);
			}
			int start = (currentPage - 1) * recordsPerPage;

			List<Classroom> classroomList = new ArrayList<Classroom>();
			Boolean isSearchClassroomFunctionUse = (Boolean) req.getSession().getAttribute("isSearchClassroomFunctionUse");
		    if (isSearchClassroomFunctionUse != null && isSearchClassroomFunctionUse) {
		    	classroomList = (List<Classroom>) req.getSession().getAttribute("classroomList");
		        req.getSession().removeAttribute("isSearchClassroomFunctionUse");
		    } else {
				classroomList = classroomDAO.getClassroomsPaginated(start, recordsPerPage);
		    }
			
			int totalRecords = classroomDAO.countClassrooms();
			int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

			req.setAttribute("classroomList", classroomList);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("totalPages", totalPages);
			
			req.getSession().setAttribute("classroomList", classroomList);
			req.getSession().setAttribute("currentPage", currentPage);
			req.getSession().setAttribute("totalPages", totalPages);
			
			String succeedAddMessage = (String) req.getSession().getAttribute("succeedAddMessage");
			if (succeedAddMessage != null) {
			    req.setAttribute("succeedAddMessage", succeedAddMessage);
			    req.getSession().removeAttribute("succeedAddMessage");
			}
			String succeedUpdateMessage = (String) req.getSession().getAttribute("succeedUpdateMessage");
			if (succeedUpdateMessage != null) {
			    req.setAttribute("succeedUpdateMessage", succeedUpdateMessage);
			    req.getSession().removeAttribute("succeedUpdateMessage");
			}
			String succeedDeleteMessage = (String) req.getSession().getAttribute("succeedDeleteMessage");
			if (succeedDeleteMessage != null) {
			    req.setAttribute("succeedDeleteMessage", succeedDeleteMessage);
			    req.getSession().removeAttribute("succeedDeleteMessage");
			}
			String infoMessage = (String) req.getSession().getAttribute("infoMessage");
			if (infoMessage != null) {
			    req.setAttribute("infoMessage", infoMessage);
			    req.getSession().removeAttribute("infoMessage");
			}
			req.getRequestDispatcher("view/admin/classroomList.jsp").forward(req, resp);
			return;
		} else if (action.equals("exportClassrooms")) {
			HttpSession session = req.getSession();
	        List<Classroom> classroomList = (List<Classroom>) session.getAttribute("classroomList");

	        if (classroomList == null || classroomList.isEmpty()) {
	            req.getSession().setAttribute("infoMessage", "Không có dữ liệu để xuất");
	            resp.sendRedirect("admin?action=classroomList");
	        } else {
	        	resp.setContentType("text/csv");
		        resp.setHeader("Content-Disposition", "attachment;filename=classroom_list.csv");

		        OutputStream out = resp.getOutputStream();
			    // Ghi BOM để Excel hiểu UTF-8
			    out.write(0xEF);
			    out.write(0xBB);
			    out.write(0xBF);

			    PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8), true);

		        writer.println("Mã lớp;Tên lớp;Mã GV");

		        for (Classroom c : classroomList) {
		            writer.printf("%s;%s;%s\n",
		                    c.getClassroomID(), c.getName(), c.getTeacherID());
		        }

		        writer.flush();
		        writer.close();
	        }
	        return;
		} else if (action.equals("studentList")) {
			StudentDAO studentDAO = new StudentDAO();
			int recordsPerPage = 30;
			int currentPage = 1;

			String pageStr = req.getParameter("page");
			if (pageStr != null) {
			    currentPage = Integer.parseInt(pageStr);
			}
			int start = (currentPage - 1) * recordsPerPage;

			List<Student> studentList = new ArrayList<Student>();
			Boolean isSearchStudentFunctionUse = (Boolean) req.getSession().getAttribute("isSearchStudentFunctionUse");
			Boolean isStudentFilterFunctionUse = (Boolean) req.getSession().getAttribute("isStudentFilterFunctionUse");
		    if (isSearchStudentFunctionUse != null && isSearchStudentFunctionUse) {
		    	studentList = (List<Student>) req.getSession().getAttribute("studentList");
		        req.getSession().removeAttribute("isSearchStudentFunctionUse");
		    } else if (isStudentFilterFunctionUse != null && isStudentFilterFunctionUse) {
		    	studentList = (List<Student>) req.getSession().getAttribute("studentList");
		        req.getSession().removeAttribute("isStudentFilterFunctionUse");
		    } else {
		    	studentList = studentDAO.getStudentsPaginated(start, recordsPerPage);
		    }
			
			int totalRecords = studentDAO.countStudents();
			int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

			req.setAttribute("studentList", studentList);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("totalPages", totalPages);
			
			req.getSession().setAttribute("studentList", studentList);
			req.getSession().setAttribute("currentPage", currentPage);
			req.getSession().setAttribute("totalPages", totalPages);
			
			String succeedAddMessage = (String) req.getSession().getAttribute("succeedAddMessage");
			if (succeedAddMessage != null) {
			    req.setAttribute("succeedAddMessage", succeedAddMessage);
			    req.getSession().removeAttribute("succeedAddMessage");
			}
			String succeedUpdateMessage = (String) req.getSession().getAttribute("succeedUpdateMessage");
			if (succeedUpdateMessage != null) {
			    req.setAttribute("succeedUpdateMessage", succeedUpdateMessage);
			    req.getSession().removeAttribute("succeedUpdateMessage");
			}
			String succeedDeleteMessage = (String) req.getSession().getAttribute("succeedDeleteMessage");
			if (succeedDeleteMessage != null) {
			    req.setAttribute("succeedDeleteMessage", succeedDeleteMessage);
			    req.getSession().removeAttribute("succeedDeleteMessage");
			}
			String infoMessage = (String) req.getSession().getAttribute("infoMessage");
			if (infoMessage != null) {
			    req.setAttribute("infoMessage", infoMessage);
			    req.getSession().removeAttribute("infoMessage");
			}
			List<Integer> yobList = studentDAO.getListOfYear();
			req.setAttribute("yobList", yobList);
			List<String> hometownList = studentDAO.getListOfHometown();
			req.setAttribute("hometownList", hometownList);
			req.getRequestDispatcher("view/admin/studentList.jsp").forward(req, resp);
			return;
		} else if (action.equals("studentFilter")) {
			String gender = req.getParameter("gender");
			String yob = req.getParameter("yob");
			String hometown = req.getParameter("hometown");
			StudentDAO studentDAO = new StudentDAO();
			List<Student> studentList = new ArrayList<Student>();
			
			studentList = studentDAO.studentListByFilter(gender, yob, hometown);
			List<Integer> yobList = studentDAO.getListOfYear();
			req.setAttribute("yobList", yobList);
			List<String> hometownList = studentDAO.getListOfHometown();
			req.setAttribute("hometownList", hometownList);
			req.getSession().setAttribute("studentList", studentList);
			boolean isStudentFilterFunctionUse = true;
			req.getSession().setAttribute("isStudentFilterFunctionUse", isStudentFilterFunctionUse);
			resp.sendRedirect("admin?action=studentList");
		} else if (action.equals("exportStudents")) {
		    HttpSession session = req.getSession();
		    List<Student> studentList = (List<Student>) session.getAttribute("studentList");

		    if (studentList == null || studentList.isEmpty()) {
		        req.getSession().setAttribute("infoMessage", "Không có dữ liệu để xuất file");
		        resp.sendRedirect("admin?action=studentList");
		    } else {
		    	resp.setContentType("text/csv; charset=UTF-8");
			    resp.setHeader("Content-Disposition", "attachment;filename=student_list.csv");

			    OutputStream out = resp.getOutputStream();
			    // Ghi BOM để Excel hiểu UTF-8
			    out.write(0xEF);
			    out.write(0xBB);
			    out.write(0xBF);

			    PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8), true);

			    writer.println("Mã SV;Họ và tên;Giới tính;Ngày sinh;Email;Quê quán");

			    for (Student s : studentList) {
			        writer.printf("%s;%s;%s;%s;%s;%s\n",
			                s.getStudentID(),
			                s.getName(),
			                s.getGender(),
			                s.getDob(),
			                s.getEmail(),
			                s.getHometown());
			    }

			    writer.flush();
			    writer.close();
		    }
		    return;
		} else if (action.equals("student_classroom")) {
			req.getRequestDispatcher("view/admin/student_classroom.jsp").forward(req, resp);
			return;
		} else if (action.equals("exportClassroomsByStudent")) {
		    HttpSession session = req.getSession();
		    List<Classroom> classroomList = (List<Classroom>) session.getAttribute("classroomList");

		    if (classroomList == null || classroomList.isEmpty()) {
		        req.getSession().setAttribute("infoMessage", "Không có dữ liệu để xuất file");
		        resp.sendRedirect("admin?action=studentList");
		    } else {
		    	resp.setContentType("text/csv; charset=UTF-8");
			    resp.setHeader("Content-Disposition", "attachment;filename=classroom_list_by_student.csv");

			    OutputStream out = resp.getOutputStream();
			    // Ghi BOM để Excel hiểu UTF-8
			    out.write(0xEF);
			    out.write(0xBB);
			    out.write(0xBF);

			    PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8), true);

			    writer.println("Mã lớp;Tên lớp");

			    for (Classroom c : classroomList) {
			        writer.printf("%s;%s\n",
			                c.getClassroomID(),
			                c.getName());
			    }

			    writer.flush();
			    writer.close();
		    }
		    return;
		} else if (action.equals("exportClassroomsByTeacher")) {
		    HttpSession session = req.getSession();
		    List<Classroom> classroomList = (List<Classroom>) session.getAttribute("classroomList");

		    if (classroomList == null || classroomList.isEmpty()) {
		        req.getSession().setAttribute("infoMessage", "Không có dữ liệu để xuất file");
		        resp.sendRedirect("admin?action=studentList");
		    } else {
		    	resp.setContentType("text/csv; charset=UTF-8");
			    resp.setHeader("Content-Disposition", "attachment;filename=classroom_list_by_teacher.csv");

			    OutputStream out = resp.getOutputStream();
			    // Ghi BOM để Excel hiểu UTF-8
			    out.write(0xEF);
			    out.write(0xBB);
			    out.write(0xBF);

			    PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8), true);

			    writer.println("Mã lớp;Tên lớp");

			    for (Classroom c : classroomList) {
			        writer.printf("%s;%s\n",
			                c.getClassroomID(),
			                c.getName());
			    }

			    writer.flush();
			    writer.close();
		    }
		    return;
		} else if (action.equals("exportStudentsByClassroom")) {
		    HttpSession session = req.getSession();
		    List<Student> studentList = (List<Student>) session.getAttribute("studentList");

		    if (studentList == null || studentList.isEmpty()) {
		        req.getSession().setAttribute("infoMessage", "Không có dữ liệu để xuất file");
		        resp.sendRedirect("admin?action=classroomList");
		    } else {
		    	resp.setContentType("text/csv; charset=UTF-8");
			    resp.setHeader("Content-Disposition", "attachment;filename=student_list_by_classroom.csv");

			    OutputStream out = resp.getOutputStream();
			    // Ghi BOM để Excel hiểu UTF-8
			    out.write(0xEF);
			    out.write(0xBB);
			    out.write(0xBF);

			    PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8), true);

			    writer.println("Mã SV;Tên SV");

			    for (Student s : studentList) {
			        writer.printf("%s;%s\n",
			                s.getStudentID(),
			                s.getName());
			    }

			    writer.flush();
			    writer.close();
		    }
		    return;
		} else if (action.equals("accountList")) {
			AccountDAO accountDAO = new AccountDAO();		
			int recordsPerPage = 30;
			int currentPage = 1;

			String pageStr = req.getParameter("page");
			if (pageStr != null) {
			    currentPage = Integer.parseInt(pageStr);
			}
			int start = (currentPage - 1) * recordsPerPage;
			
			List<Account> accountList = new ArrayList<Account>();
			Boolean isSearchAccFunctionUse = (Boolean) req.getSession().getAttribute("isSearchAccFunctionUse");
			Boolean isAccFilterFunctionUse = (Boolean) req.getSession().getAttribute("isAccFilterFunctionUse");
		    if (isSearchAccFunctionUse != null && isSearchAccFunctionUse) {
		        accountList = (List<Account>) req.getSession().getAttribute("accountList");
		        req.getSession().removeAttribute("isSearchAccFunctionUse");
		    } else if (isAccFilterFunctionUse != null && isAccFilterFunctionUse) {
		        accountList = (List<Account>) req.getSession().getAttribute("accountList");
		        req.getSession().removeAttribute("isAccFilterFunctionUse");
		    } else {
		        accountList = accountDAO.getAccountsPaginated(start, recordsPerPage);
		    }
			int totalRecords = accountDAO.countAccounts();
			int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

			req.setAttribute("accountList", accountList);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("totalPages", totalPages);
			
			req.getSession().setAttribute("accountList", accountList);
			req.getSession().setAttribute("currentPage", currentPage);
			req.getSession().setAttribute("totalPages", totalPages);

			String succeedAddMessage = (String) req.getSession().getAttribute("succeedAddMessage");
			if (succeedAddMessage != null) {
			    req.setAttribute("succeedAddMessage", succeedAddMessage);
			    req.getSession().removeAttribute("succeedAddMessage");
			}
			String succeedUpdateMessage = (String) req.getSession().getAttribute("succeedUpdateMessage");
			if (succeedUpdateMessage != null) {
			    req.setAttribute("succeedUpdateMessage", succeedUpdateMessage);
			    req.getSession().removeAttribute("succeedUpdateMessage");
			}
			String succeedDeleteMessage = (String) req.getSession().getAttribute("succeedDeleteMessage");
			if (succeedDeleteMessage != null) {
			    req.setAttribute("succeedDeleteMessage", succeedDeleteMessage);
			    req.getSession().removeAttribute("succeedDeleteMessage");
			}
			String infoMessage = (String) req.getSession().getAttribute("infoMessage");
			if (infoMessage != null) {
			    req.setAttribute("infoMessage", infoMessage);
			    req.getSession().removeAttribute("infoMessage");
			}
			req.getRequestDispatcher("view/admin/accountList.jsp").forward(req, resp);
			return;
		} else if (action.equals("accountFilter")) {
			String role = req.getParameter("role");
			AccountDAO accountDAO = new AccountDAO();
			List<Account> accountList = new ArrayList<>();
			
			accountList = accountDAO.accountListByRole(role);
			req.getSession().setAttribute("accountList", accountList);
			boolean isAccFilterFunctionUse = true;
			req.getSession().setAttribute("isAccFilterFunctionUse", isAccFilterFunctionUse);
			resp.sendRedirect("admin?action=accountList");
		} else if (action.equals("exportAccounts")) {
			HttpSession session = req.getSession();
	        List<Account> accountList = (List<Account>) session.getAttribute("accountList");

	        if (accountList == null || accountList.isEmpty()) {
				req.getSession().setAttribute("infoMessage", "Không có dữ liệu để xuất file");
				resp.sendRedirect("admin?action=accountList");
	        } else {
	        	resp.setContentType("text/csv");
		        resp.setHeader("Content-Disposition", "attachment;filename=account_list.csv");

		        OutputStream out = resp.getOutputStream();
			    // Ghi BOM để Excel hiểu UTF-8
			    out.write(0xEF);
			    out.write(0xBB);
			    out.write(0xBF);

			    PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8), true);

		        writer.println("Mã TK;Tên TK;Mật khẩu;Quyền đăng nhập;Mã SV");

		        for (Account acc : accountList) {
		            writer.printf("%s;%s;%s;%s;%s\n",
		                    acc.getAccountID(), acc.getUsername(), acc.getPassword(), acc.getRole(), acc.getStudentID());
		        }

		        writer.flush();
		        writer.close();
	        }
	        return;
		} else if (action.equals("searchTeacher")) {
			String teacherID = req.getParameter("teacherID");
			TeacherDAO teacherDAO = new TeacherDAO();
			Teacher teacher = teacherDAO.findById(teacherID);
			if (teacher != null) {
				req.getSession().setAttribute("teacherList", List.of(teacher));
			} else {
				req.getSession().setAttribute("infoMessage", "Không tìm thấy giáo viên " + teacherID);
				req.getSession().setAttribute("teacherList", List.of());
			}
			boolean isSearchTeacherFunctionUse = true;
			req.getSession().setAttribute("isSearchTeacherFunctionUse", isSearchTeacherFunctionUse);
			resp.sendRedirect("admin?action=teacherList");
			return;
		} else if (action.equals("searchClassroom")) {
			String classroomID = req.getParameter("classroomID");
			ClassroomDAO classroomDAO = new ClassroomDAO();
			Classroom classroom = classroomDAO.findByID(classroomID);
			if (classroom != null) {
				req.getSession().setAttribute("classroomList", List.of(classroom));
			} else {
				req.getSession().setAttribute("infoMessage", "Không tìm thấy lớp học " + classroomID);
				req.getSession().setAttribute("classroomList", List.of());
			}
			boolean isSearchClassroomFunctionUse = true;
			req.getSession().setAttribute("isSearchClassroomFunctionUse", isSearchClassroomFunctionUse);
			resp.sendRedirect("admin?action=classroomList");
			return;
		} else if (action.equals("searchStudent")) {
			String studentID = req.getParameter("studentID");
			StudentDAO studentDAO = new StudentDAO();
			Student student = studentDAO.findByID(studentID);
			if (student != null) {
				req.getSession().setAttribute("studentList", List.of(student));
			} else {
				req.getSession().setAttribute("infoMessage", "Không tìm thấy sinh viên " + studentID);
				req.getSession().setAttribute("studentList", List.of());
			}
			boolean isSearchStudentFunctionUse = true;
			req.getSession().setAttribute("isSearchStudentFunctionUse", isSearchStudentFunctionUse);
			resp.sendRedirect("admin?action=studentList");
			return;
		} else if (action.equals("searchClassroomListByTeacherID")) {
			req.getSession().removeAttribute("classroomList");
			String teacherID = req.getParameter("teacherID");
			ClassroomDAO classroomDAO = new ClassroomDAO();
			List<Classroom> classroomList = classroomDAO.findByTeacherID(teacherID);
			if (classroomList.isEmpty()) {	
				req.setAttribute("message", "Giáo viên " + teacherID + " hiện chưa dạy lớp nào");
				req.getRequestDispatcher("view/admin/classroomListByTeacherID.jsp").forward(req, resp);
			} else {
				req.getSession().setAttribute("classroomList", classroomList);
				req.setAttribute("teacherID", teacherID);
				req.getRequestDispatcher("view/admin/classroomListByTeacherID.jsp").forward(req, resp);
			}
			return;
		} else if (action.equals("searchClassroomListByStudentID")) {
			req.getSession().removeAttribute("classroomList");
			String studentID = req.getParameter("studentID");
			Student_classroomDAO student_classroomDAO = new Student_classroomDAO();
			List<Student_classroom> student_classroomList = student_classroomDAO.findByID(null, studentID);
			if (student_classroomList.isEmpty()) {
				req.setAttribute("message", "Sinh viên " + req.getParameter("studentID") + " hiện chưa học lớp nào");
				req.getRequestDispatcher("view/admin/classroomListByStudentID.jsp").forward(req, resp);
			} else {
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
				req.getSession().setAttribute("classroomList", classroomList);
				req.setAttribute("studentID", studentID);
				req.getRequestDispatcher("view/admin/classroomListByStudentID.jsp").forward(req, resp);
			}
			return;
		} else if (action.equals("searchStudentListByClassroomID")) {
			req.getSession().removeAttribute("studentList");
			String classroomID = req.getParameter("classroomID");
			Student_classroomDAO student_classroomDAO = new Student_classroomDAO();
			List<Student_classroom> student_classroomList = student_classroomDAO.findByID(classroomID, null);
			if (student_classroomList.isEmpty()) {
				req.setAttribute("message", "Lớp " + req.getParameter("classroomID") + " hiện chưa có sinh viên nào");
				req.getRequestDispatcher("view/admin/studentListByClassroomID.jsp").forward(req, resp);
			} else {
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
				req.getSession().setAttribute("studentList", studentList);
				req.setAttribute("classroomID", classroomID);
				req.getRequestDispatcher("view/admin/studentListByClassroomID.jsp").forward(req, resp);
			}
			return;
		} else if (action.equals("searchAccount")) {
			String accountID = req.getParameter("accountID");
			AccountDAO accountDAO = new AccountDAO();
			Account account = accountDAO.findByID(accountID);
			if (account != null) {
				req.getSession().setAttribute("accountList", List.of(account));
			} else {
				req.getSession().setAttribute("infoMessage", "Không tìm thấy tài khoản " + accountID);
				req.getSession().setAttribute("accountList", List.of());
			}
			boolean isSearchAccFunctionUse = true;
			req.getSession().setAttribute("isSearchAccFunctionUse", isSearchAccFunctionUse);
			resp.sendRedirect("admin?action=accountList");
			return;
		} else if (action.equals("addTeacherForm")) {
			List<Teacher> teacherList = (List<Teacher>) req.getSession().getAttribute("teacherList");
			TeacherDAO teacherDAO = new TeacherDAO();
			
			int currentPage = (int) req.getSession().getAttribute("currentPage");
			int totalPages = (int) req.getSession().getAttribute("totalPages");
			List<Integer> yobList = teacherDAO.getListOfYear();
			List<String> hometownList = teacherDAO.getListOfHometown();
			String message = (String) req.getSession().getAttribute("message");
			req.getSession().removeAttribute("message");
			Teacher teacher = (Teacher) req.getSession().getAttribute("teacher");
			req.getSession().removeAttribute("teacher");
			
			req.setAttribute("teacherList", teacherList);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("totalPages", totalPages);
			req.setAttribute("hometownList", hometownList);
			req.setAttribute("yobList", yobList);
			req.setAttribute("message", message);
			req.setAttribute("teacher", teacher);
			
			req.getRequestDispatcher("view/admin/addTeacher.jsp").forward(req, resp);
			return;
		} else if (action.equals("addClassroomForm")) {
			List<Classroom> classroomList = (List<Classroom>) req.getSession().getAttribute("classroomList");
			int currentPage = (int) req.getSession().getAttribute("currentPage");
			int totalPages = (int) req.getSession().getAttribute("totalPages");
			String message = (String) req.getSession().getAttribute("message");
			req.getSession().removeAttribute("message");
			Classroom classroom = (Classroom) req.getSession().getAttribute("classroom");
			req.getSession().removeAttribute("classroom");
			
			req.setAttribute("classroomList", classroomList);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("totalPages", totalPages);
			req.setAttribute("message", message);
			req.setAttribute("classroom", classroom);
			
			req.getRequestDispatcher("view/admin/addClassroom.jsp").forward(req, resp);
			return;
		} else if (action.equals("addStudentForm")) {
			List<Student> studentList = (List<Student>) req.getSession().getAttribute("studentList");
			StudentDAO studentDAO = new StudentDAO();
			List<String> hometownList = studentDAO.getListOfHometown();
			List<Integer> yobList = studentDAO.getListOfYear();
			int currentPage = (int) req.getSession().getAttribute("currentPage");
			int totalPages = (int) req.getSession().getAttribute("totalPages");
			String message = (String) req.getSession().getAttribute("message");
			req.getSession().removeAttribute("message");
			Student student = (Student) req.getSession().getAttribute("student");
			req.getSession().removeAttribute("student");
			
			req.setAttribute("studentList", studentList);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("totalPages", totalPages);
			req.setAttribute("yobList", yobList);
			req.setAttribute("hometownList", hometownList);
			req.setAttribute("message", message);
			req.setAttribute("student", student);
			
			req.getRequestDispatcher("view/admin/addStudent.jsp").forward(req, resp);
			return;
		} else if (action.equals("addAccountForm")) {
			List<Account> accountList = (List<Account>) req.getSession().getAttribute("accountList");
			int currentPage = (int) req.getSession().getAttribute("currentPage");
			int totalPages = (int) req.getSession().getAttribute("totalPages");
			String message = (String) req.getSession().getAttribute("message");
			req.getSession().removeAttribute("message");
			Account account = (Account) req.getSession().getAttribute("account");
			req.getSession().removeAttribute("account");
			
			req.setAttribute("accountList", accountList);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("totalPages", totalPages);
			req.setAttribute("message", message);
			req.setAttribute("account", account);
			
			req.getRequestDispatcher("view/admin/addAccount.jsp").forward(req, resp);
			return;
		} else if (action.equals("updateTeacherForm")) {
			String message = (String) req.getSession().getAttribute("message");
			req.getSession().removeAttribute("message");
			TeacherDAO teacherDAO = new TeacherDAO();

			if (message == null) {
				String teacherID = req.getParameter("teacherID");
				Teacher teacher = teacherDAO.findById(teacherID);
				req.setAttribute("teacher", teacher);
			} else {
				Student student = (Student) req.getSession().getAttribute("student");
				req.getSession().removeAttribute("student");
				req.setAttribute("student", student);
			}
			
			List<Teacher> teacherList = (List<Teacher>) req.getSession().getAttribute("teacherList");
			List<Integer> yobList = teacherDAO.getListOfYear();
			List<String> hometownList = teacherDAO.getListOfHometown();
			int currentPage = (int) req.getSession().getAttribute("currentPage");
			int totalPages = (int) req.getSession().getAttribute("totalPages");
			
			req.setAttribute("teacherList", teacherList);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("totalPages", totalPages);
			req.setAttribute("yobList", yobList);
			req.setAttribute("hometownList", hometownList);
			req.setAttribute("message", message);
			
			req.getRequestDispatcher("view/admin/updateTeacher.jsp").forward(req, resp);
			return;
		} else if (action.equals("updateClassroomForm")) {
			String message = (String) req.getSession().getAttribute("message");
			req.getSession().removeAttribute("message");
			ClassroomDAO classroomDAO = new ClassroomDAO();

			if (message == null) {
				String classroomID = req.getParameter("classroomID");
				Classroom classroom = classroomDAO.findByID(classroomID);
				req.setAttribute("classroom", classroom);
			} else {
				Classroom classroom = (Classroom) req.getSession().getAttribute("classroom");
				req.getSession().removeAttribute("classroom");
				req.setAttribute("classroom", classroom);
			}
			
			List<Classroom> classroomList = (List<Classroom>) req.getSession().getAttribute("classroomList");
			int currentPage = (int) req.getSession().getAttribute("currentPage");
			int totalPages = (int) req.getSession().getAttribute("totalPages");
			
			req.setAttribute("classroomList", classroomList);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("totalPages", totalPages);
			req.setAttribute("message", message);
			
			req.getRequestDispatcher("view/admin/updateClassroom.jsp").forward(req, resp);
			return;
		} else if (action.equals("updateStudentForm")) {
			StudentDAO studentDAO = new StudentDAO();
			String message = (String) req.getSession().getAttribute("message");
			req.getSession().removeAttribute("message");
			
			if (message == null) {
				String studentID = req.getParameter("studentID");
				Student student = studentDAO.findByID(studentID);
				req.setAttribute("student", student);
			} else {
				Student student = (Student) req.getSession().getAttribute("student");
				req.getSession().removeAttribute("student");
				req.setAttribute("student", student);
			}
			
			List<Student> studentList = (List<Student>) req.getSession().getAttribute("studentList");
			int currentPage = (int) req.getSession().getAttribute("currentPage");
			int totalPages = (int) req.getSession().getAttribute("totalPages");
			List<Integer> yobList = studentDAO.getListOfYear();
			List<String> hometownList = studentDAO.getListOfHometown();
			
			req.setAttribute("studentList", studentList);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("totalPages", totalPages);
			req.setAttribute("hometownList", hometownList);
			req.setAttribute("yobList", yobList);
			req.setAttribute("message", message);
			
			req.getRequestDispatcher("view/admin/updateStudent.jsp").forward(req, resp);
			return;
		} else if (action.equals("updateAccountForm")) {
			String message = (String) req.getSession().getAttribute("message");
			req.getSession().removeAttribute("message");
			
			if (message == null) {
				String accountID = req.getParameter("accountID");
				AccountDAO accountDAO = new AccountDAO();
				Account account = accountDAO.findByID(accountID);
				req.setAttribute("account", account);
			} else {
				Account account = (Account) req.getSession().getAttribute("account");
				req.getSession().removeAttribute("account");
				req.setAttribute("account", account);
			}
			
			List<Account> accountList = (List<Account>) req.getSession().getAttribute("accountList");
			int currentPage = (int) req.getSession().getAttribute("currentPage");
			int totalPages = (int) req.getSession().getAttribute("totalPages");
			
			req.setAttribute("accountList", accountList);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("totalPages", totalPages);
			req.setAttribute("message", message);
			
			req.getRequestDispatcher("view/admin/updateAccount.jsp").forward(req, resp);
			return;
		} else if (action.equals("deleteTeacherForm")) {
			String teacherID = req.getParameter("teacherID");
			req.setAttribute("teacherID", teacherID);
			TeacherDAO teacherDAO = new TeacherDAO();
			List<Integer> yobList = teacherDAO.getListOfYear();
			req.setAttribute("yobList", yobList);
			List<String> hometownList = teacherDAO.getListOfHometown();
			req.setAttribute("hometownList", hometownList);
			
			List<Teacher> teacherList = (List<Teacher>) req.getSession().getAttribute("teacherList");
			int currentPage = (int) req.getSession().getAttribute("currentPage");
			int totalPages = (int) req.getSession().getAttribute("totalPages");
			
			req.setAttribute("teacherList", teacherList);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("totalPages", totalPages);
			
			req.getRequestDispatcher("view/admin/deleteTeacher.jsp").forward(req, resp);
			return;
		} else if (action.equals("deleteClassroomForm")) {
			String classroomID = req.getParameter("classroomID");
			req.setAttribute("classroomID", classroomID);
			
			List<Classroom> classroomList = (List<Classroom>) req.getSession().getAttribute("classroomList");
			int currentPage = (int) req.getSession().getAttribute("currentPage");
			int totalPages = (int) req.getSession().getAttribute("totalPages");
			
			req.setAttribute("classroomList", classroomList);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("totalPages", totalPages);
			
			req.getRequestDispatcher("view/admin/deleteClassroom.jsp").forward(req, resp);
			return;
		} else if (action.equals("deleteStudentForm")) {
			String studentID = req.getParameter("studentID");
			req.setAttribute("studentID", studentID);
			StudentDAO studentDAO = new StudentDAO();
			List<Integer> yobList = studentDAO.getListOfYear();
			req.setAttribute("yobList", yobList);
			List<String> hometownList = studentDAO.getListOfHometown();
			req.setAttribute("hometownList", hometownList);
			
			List<Student> studentList = (List<Student>) req.getSession().getAttribute("studentList");
			int currentPage = (int) req.getSession().getAttribute("currentPage");
			int totalPages = (int) req.getSession().getAttribute("totalPages");
			
			req.setAttribute("studentList", studentList);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("totalPages", totalPages);
			
			req.getRequestDispatcher("view/admin/deleteStudent.jsp").forward(req, resp);
			return;
		} else if (action.equals("deleteAccountForm")) {
			String accountID = req.getParameter("accountID");
			req.setAttribute("accountID", accountID);
			
			List<Account> accountList = (List<Account>) req.getSession().getAttribute("accountList");
			int currentPage = (int) req.getSession().getAttribute("currentPage");
			int totalPages = (int) req.getSession().getAttribute("totalPages");
			
			req.setAttribute("accountList", accountList);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("totalPages", totalPages);
			
			req.getRequestDispatcher("view/admin/deleteAccount.jsp").forward(req, resp);
			return;
		} else if (action.equals("dashboard")) {
			StudentDAO studentDAO = new StudentDAO();
			int totalStudent = studentDAO.totalStudent();
			ClassroomDAO classroomDAO = new ClassroomDAO();
			int totalClassroom = classroomDAO.totalClassroom();
			TeacherDAO teacherDAO = new TeacherDAO();
			int totalTeacher = teacherDAO.totalTeacher();
			AccountDAO accountDAO = new AccountDAO();
			int totalAccount = accountDAO.totalAccount();
			req.setAttribute("totalStudent", totalStudent);
			req.setAttribute("totalClassroom", totalClassroom);
			req.setAttribute("totalTeacher", totalTeacher);
			req.setAttribute("totalAccount", totalAccount);
			req.getRequestDispatcher("view/admin/dashboard.jsp").forward(req, resp);
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

		if (action.equals("addTeacher")) {
			TeacherDAO teacherDAO = new TeacherDAO();
			StringBuilder message = new StringBuilder();

			if (teacherDAO.findById(req.getParameter("teacherID")) != null) {
				message.append("Mã giáo viên đã tồn tại<br>");
			} else if (teacherDAO.isEmailExists(req.getParameter("email"), req.getParameter("teacherID"))) {
				message.append("Email đã được sử dụng<br>");
			}

			Teacher teacher = new Teacher();
			teacher.setTeacherID(req.getParameter("teacherID"));
			teacher.setName(req.getParameter("name"));
			teacher.setGender(req.getParameter("gender"));
			teacher.setDob(Date.valueOf(req.getParameter("dob")));
			teacher.setEmail(req.getParameter("email"));
			teacher.setHometown(req.getParameter("hometown"));

			if (message.length() > 0) {
				req.getSession().setAttribute("teacher", teacher);
				req.getSession().setAttribute("message", message.toString());
				resp.sendRedirect("admin?action=addTeacherForm");
				return;
			}

			teacherDAO.insert(teacher);
			req.getSession().setAttribute("succeedAddMessage", "Thêm giáo viên thành công");
			resp.sendRedirect("admin?action=teacherList");
			return;
		} else if (action.equals("addClassroom")) {
			ClassroomDAO classroomDAO = new ClassroomDAO();
			TeacherDAO teacherDAO = new TeacherDAO();
			StringBuilder message = new StringBuilder();

			if (classroomDAO.findByID(req.getParameter("classroomID")) != null) {
				message.append("Mã lớp học đã tồn tại<br>");
			} else if (teacherDAO.findById(req.getParameter("teacherID")) == null) {
				message.append("Mã giáo viên không tồn tại<br>");
			}

			Classroom classroom = new Classroom();
			classroom.setClassroomID(req.getParameter("classroomID"));
			classroom.setName(req.getParameter("name"));
			classroom.setTeacherID(req.getParameter("teacherID"));

			if (message.length() > 0) {
				req.getSession().setAttribute("classroom", classroom);
				req.getSession().setAttribute("message", message.toString());
				resp.sendRedirect("admin?action=addClassroomForm");
				return;
			}

			classroomDAO.insert(classroom);
			req.getSession().setAttribute("succeedAddMessage", "Thêm lớp học thành công");
			resp.sendRedirect("admin?action=classroomList");
			return;
		} else if (action.equals("addStudent")) {
			StudentDAO studentDAO = new StudentDAO();
			StringBuilder message = new StringBuilder();

			if (studentDAO.findByID(req.getParameter("studentID")) != null) {
				message.append("Mã sinh viên đã tồn tại<br>");
			} else if (studentDAO.isEmailExists(req.getParameter("email"), req.getParameter("studentID"))) {
				message.append("Email đã được sử dụng<br>");
			}
			Student student = new Student();
			student.setStudentID(req.getParameter("studentID"));
			student.setName(req.getParameter("name"));
			student.setGender(req.getParameter("gender"));
			student.setDob(Date.valueOf(req.getParameter("dob")));
			student.setEmail(req.getParameter("email"));
			student.setHometown(req.getParameter("hometown"));

			if (message.length() > 0) {
				req.getSession().setAttribute("student", student);
				req.getSession().setAttribute("message", message.toString());
				resp.sendRedirect("admin?action=addStudentForm");
				return;
			}

			studentDAO.insert(student);
			req.getSession().setAttribute("succeedAddMessage", "Thêm sinh viên thành công");
			resp.sendRedirect("admin?action=studentList");
			return;
		} else if (action.equals("addStudent_classroom")) {
			Student_classroomDAO student_classroomDAO = new Student_classroomDAO();
			StudentDAO studentDAO = new StudentDAO();
			ClassroomDAO classroomDAO = new ClassroomDAO();
			StringBuilder message = new StringBuilder();

			if (classroomDAO.findByID(req.getParameter("classroomID")) == null) {
				message.append("Mã lớp không tồn tại<br>");
			} else if (studentDAO.findByID(req.getParameter("studentID")) == null) {
				message.append("Mã sinh viên không tồn tại<br>");
			} else if (!student_classroomDAO.findByID(req.getParameter("classroomID"), req.getParameter("studentID")).isEmpty()) {
				message.append("Sinh viên " + req.getParameter("studentID") + " đã học lớp " + req.getParameter("classroomID"));
			} else if (student_classroomDAO.studentCount(req.getParameter("classroomID")) >= 70) {
				message.append("Lớp " + req.getParameter("classroomID") + " đã đủ 70 sinh viên");
			}

			Student_classroom student_classroom = new Student_classroom();
			student_classroom.setClassroomID(req.getParameter("classroomID"));
			student_classroom.setStudentID(req.getParameter("studentID"));

			if (message.length() > 0) {
				req.setAttribute("student_classroom", student_classroom);
				req.setAttribute("message", message.toString());
				req.setAttribute("classroomID", req.getParameter("classroomID"));
			    req.setAttribute("studentID", req.getParameter("studentID"));
				req.getRequestDispatcher("view/admin/student_classroom.jsp").forward(req, resp);
				return;
			}

			student_classroomDAO.insert(student_classroom);
			StringBuilder succeedAddMessage = new StringBuilder();
			succeedAddMessage.append("Thêm sinh viên vào lớp thành công<br>");
			req.setAttribute("succeedAddMessage", succeedAddMessage.toString());
			req.getRequestDispatcher("view/admin/student_classroom.jsp").forward(req, resp);
			return;
		} else if (action.equals("addAccount")) {
			AccountDAO accountDAO = new AccountDAO();
			StudentDAO studentDAO = new StudentDAO();
			StringBuilder message = new StringBuilder();

			if (accountDAO.findByID(req.getParameter("accountID")) != null) {
				message.append("Mã tài khoản đã tồn tại<br>");
			} else if (accountDAO.isUsernameExists(req.getParameter("username"), req.getParameter("accountID"))) {
				message.append("Tên tài khoản đã được sử dụng<br>");
			} else if (accountDAO.isStudentIDUsed(req.getParameter("studentID"), null, req.getParameter("role"))) {
				message.append("Mã sinh viên đã được sử dụng<br>");
			} else if (!studentDAO.isStudentExists(req.getParameter("studentID"), req.getParameter("role"))) {
				message.append("Mã sinh viên không tồn tại<br>");
			}

			Account account = new Account();
			account.setAccountID(req.getParameter("accountID"));
			account.setUsername(req.getParameter("username"));
			account.setPassword(req.getParameter("password"));
			account.setRole(req.getParameter("role"));
			account.setStudentID(req.getParameter("role").equals("student") ? req.getParameter("studentID") : null);

			if (message.length() > 0) {
				req.getSession().setAttribute("account", account);
				req.getSession().setAttribute("message", message.toString());
				resp.sendRedirect("admin?action=addAccountForm");
				return;
			}

			accountDAO.insert(account);
			req.getSession().setAttribute("succeedAddMessage", "Thêm tài khoản thành công");
			resp.sendRedirect("admin?action=accountList");
			return;
		} else if (action.equals("updateTeacher")) {
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
			teacher.setEmail(req.getParameter("email"));
			teacher.setHometown(req.getParameter("hometown"));

			if (message.length() > 0) {
				req.getSession().setAttribute("teacher", teacher);
				req.getSession().setAttribute("message", message.toString());
				resp.sendRedirect("admin?action=updateTeacherForm");
				return;
			}

			teacherDAO.update(teacher);
			req.getSession().setAttribute("succeedUpdateMessage", "Sửa thông tin giáo viên thành công");
			resp.sendRedirect("admin?action=teacherList");
			return;
		} else if (action.equals("updateClassroom")) {
			ClassroomDAO classroomDAO = new ClassroomDAO();
			TeacherDAO teacherDAO = new TeacherDAO();
			StringBuilder message = new StringBuilder();

			if (teacherDAO.findById(req.getParameter("teacherID")) == null) {
				message.append("Mã giáo viên không tồn tại<br>");
			}

			Classroom classroom = new Classroom();
			classroom.setClassroomID(req.getParameter("classroomID"));
			classroom.setName(req.getParameter("name"));
			classroom.setTeacherID(req.getParameter("teacherID"));

			if (message.length() > 0) {
				req.getSession().setAttribute("classroom", classroom);
				req.getSession().setAttribute("message", message.toString());
				resp.sendRedirect("admin?action=updateClassroomForm");
				return;
			}

			classroomDAO.update(classroom);
			req.getSession().setAttribute("succeedUpdateMessage", "Sửa thông tin lớp học thành công");
			resp.sendRedirect("admin?action=classroomList");
			return;
		} else if (action.equals("updateStudent")) {
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
			student.setEmail(req.getParameter("email"));
			student.setHometown(req.getParameter("hometown"));

			if (message.length() > 0) {
				req.getSession().setAttribute("student", student);
				req.getSession().setAttribute("message", message.toString());
				resp.sendRedirect("admin?action=updateStudentForm");
				return;
			}

			studentDAO.update(student);
			req.getSession().setAttribute("succeedUpdateMessage", "Sửa thông tin sinh viên thành công");
			resp.sendRedirect("admin?action=studentList");
			return;
		} else if (action.equals("updateAccount")) {
			AccountDAO accountDAO = new AccountDAO();
			StringBuilder message = new StringBuilder();

			if (accountDAO.isUsernameExists(req.getParameter("username"), req.getParameter("accountID"))) {
				message.append("Tên tài khoản đã được sử dụng<br>");
			}

			Account account = new Account();
			account.setAccountID(req.getParameter("accountID"));
			account.setUsername(req.getParameter("username"));
			account.setPassword(req.getParameter("password"));
			account.setRole(req.getParameter("role"));
			account.setStudentID(req.getParameter("studentID"));

			if (message.length() > 0) {
				req.getSession().setAttribute("account", account);
				req.getSession().setAttribute("message", message.toString());
				resp.sendRedirect("admin?action=updateAccountForm");
				return;
			}

			accountDAO.update(account);
			req.getSession().setAttribute("succeedUpdateMessage", "Sửa thông tin tài khoản thành công");
			resp.sendRedirect("admin?action=accountList");
			return;
		} else if (action.equals("deleteTeacher")) {
			TeacherDAO teacherDAO = new TeacherDAO();
			teacherDAO.delete(req.getParameter("teacherID"));
			req.getSession().setAttribute("succeedDeleteMessage", "Xóa giáo viên thành công");
			resp.sendRedirect("admin?action=teacherList");
			return;
		} else if (action.equals("deleteTeachers")) {
			String deleteCodeConfirm = req.getParameter("deleteCodeConfirm");
			String randomCode = req.getParameter("randomCode");
			if (deleteCodeConfirm == null || randomCode == null || !deleteCodeConfirm.trim().equals(randomCode.trim())) {
				req.getSession().setAttribute("infoMessage", "Nhập mã xác nhận sai");
			    resp.sendRedirect("admin?action=teacherList");
			} else {
				String[] teacherIds = req.getParameterValues("teacherIds");
			    if (teacherIds != null) {
			      TeacherDAO teacherDAO = new TeacherDAO();
			      for (String teacherID : teacherIds) {
			    	  teacherDAO.delete(teacherID);
			      }
			      req.getSession().setAttribute("succeedDeleteMessage", "Xóa giáo viên thành công");
			      resp.sendRedirect("admin?action=teacherList");
			    }
			    else {
			    	req.getSession().setAttribute("infoMessage", "Chưa có giáo viên nào được chọn");
				    resp.sendRedirect("admin?action=teacherList");
			    }
			}
		} else if (action.equals("deleteClassroom")) {
			ClassroomDAO classroomDAO = new ClassroomDAO();
			classroomDAO.delete(req.getParameter("classroomID"));
			req.getSession().setAttribute("succeedDeleteMessage", "Xóa lớp học thành công");
			resp.sendRedirect("admin?action=classroomList");
			return;
		} else if (action.equals("deleteClassrooms")) {
			String deleteCodeConfirm = req.getParameter("deleteCodeConfirm");
			String randomCode = req.getParameter("randomCode");
			if (deleteCodeConfirm == null || randomCode == null || !deleteCodeConfirm.trim().equals(randomCode.trim())) {
				req.getSession().setAttribute("infoMessage", "Nhập mã xác nhận sai");
			    resp.sendRedirect("admin?action=classroomList");
			} else {
				String[] classroomIds = req.getParameterValues("classroomIds");
			    if (classroomIds != null) {
			      ClassroomDAO classroomDAO = new ClassroomDAO();
			      for (String classroomID : classroomIds) {
			    	  classroomDAO.delete(classroomID);
			      }
			      req.getSession().setAttribute("succeedDeleteMessage", "Xóa lớp học thành công");
			      resp.sendRedirect("admin?action=classroomList");
			    }
			    else {
			    	req.getSession().setAttribute("infoMessage", "Chưa có lớp học nào được chọn");
				    resp.sendRedirect("admin?action=classroomList");
			    }
			}
		} else if (action.equals("deleteStudent")) {
			StudentDAO studentDAO = new StudentDAO();
			studentDAO.delete(req.getParameter("studentID"));
			req.getSession().setAttribute("succeedDeleteMessage", "Xóa sinh viên thành công");
			resp.sendRedirect("admin?action=studentList");
			return;
		} else if (action.equals("deleteStudents")) {
			String deleteCodeConfirm = req.getParameter("deleteCodeConfirm");
			String randomCode = req.getParameter("randomCode");
			if (deleteCodeConfirm == null || randomCode == null || !deleteCodeConfirm.trim().equals(randomCode.trim())) {
				req.getSession().setAttribute("infoMessage", "Nhập mã xác nhận sai");
			    resp.sendRedirect("admin?action=studentList");
			} else {
				String[] studentIds = req.getParameterValues("studentIds");
			    if (studentIds != null) {
			      StudentDAO studentDAO = new StudentDAO();
			      for (String studentID : studentIds) {
			    	  studentDAO.delete(studentID);
			      }
			      req.getSession().setAttribute("succeedDeleteMessage", "Xóa sinh viên thành công");
			      resp.sendRedirect("admin?action=studentList");
			    }
			    else {
			    	req.getSession().setAttribute("infoMessage", "Chưa có sinh viên nào được chọn");
				    resp.sendRedirect("admin?action=studentList");
			    }
			}
		} else if (action.equals("deleteStudent_classroom")) {
			Student_classroomDAO student_classroomDAO = new Student_classroomDAO();
			StudentDAO studentDAO = new StudentDAO();
			ClassroomDAO classroomDAO = new ClassroomDAO();
			StringBuilder message = new StringBuilder();

			if (classroomDAO.findByID(req.getParameter("classroomID")) == null) {
				message.append("Mã lớp không tồn tại<br>");
			} else if (studentDAO.findByID(req.getParameter("studentID")) == null) {
				message.append("Mã sinh viên không tồn tại<br>");
			} else if (student_classroomDAO.findByID(req.getParameter("classroomID"), req.getParameter("studentID")).isEmpty()) {
				message.append("Sinh viên " + req.getParameter("studentID") + " không học lớp " + req.getParameter("classroomID"));
			}
			
			if (message.length() > 0) {
				req.setAttribute("message", message.toString());
				req.setAttribute("classroomID", req.getParameter("classroomID"));
			    req.setAttribute("studentID", req.getParameter("studentID"));
				req.getRequestDispatcher("view/admin/student_classroom.jsp").forward(req, resp);
				return;
			}
			
			student_classroomDAO.delete(req.getParameter("classroomID"), req.getParameter("studentID"));
			StringBuilder succeedDeleteMessage = new StringBuilder();
			succeedDeleteMessage.append("Xóa sinh viên khỏi lớp thành công<br>");
			req.setAttribute("succeedDeleteMessage", succeedDeleteMessage.toString());
			req.getRequestDispatcher("view/admin/student_classroom.jsp").forward(req, resp);
			return;
		} else if (action.equals("deleteAccount")) {
			AccountDAO accountDAO = new AccountDAO();
			accountDAO.delete(req.getParameter("accountID"));
			req.getSession().setAttribute("succeedDeleteMessage", "Xóa tài khoản thành công");
			resp.sendRedirect("admin?action=accountList");
			return;
		} else if (action.equals("deleteAccounts")) {
			String deleteCodeConfirm = req.getParameter("deleteCodeConfirm");
			String randomCode = req.getParameter("randomCode");
			if (deleteCodeConfirm == null || randomCode == null || !deleteCodeConfirm.trim().equals(randomCode.trim())) {
				req.getSession().setAttribute("infoMessage", "Nhập mã xác nhận sai");
			    resp.sendRedirect("admin?action=accountList");
			} else {
				String[] accountIds = req.getParameterValues("accountIds");
			    if (accountIds != null) {
			      AccountDAO accountDAO = new AccountDAO();
			      for (String accountID : accountIds) {
			    	  accountDAO.delete(accountID);
			      }
			      req.getSession().setAttribute("succeedDeleteMessage", "Xóa tài khoản thành công");
			      resp.sendRedirect("admin?action=accountList");
			    }
			    else {
			    	req.getSession().setAttribute("infoMessage", "Chưa có tài khoản nào được chọn");
				    resp.sendRedirect("admin?action=accountList");
			    }
			}
		}
	}

}
