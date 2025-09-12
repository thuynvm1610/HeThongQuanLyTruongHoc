package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Student;
import util.DBConnect;

public class StudentDAO {

	public List<Student> findAll() {
		String sql = "select * from student";
		DBConnect dbConn = new DBConnect();
		List<Student> studentList = new ArrayList<>();
		try {
			Connection conn = dbConn.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Student student = new Student();
				student.setStudentID(rs.getString("studentID"));
				student.setName(rs.getString("name"));
				student.setGender(rs.getString("gender"));
				student.setDob(rs.getDate("dob"));
				student.setEmail(rs.getString("email"));
				student.setHometown(rs.getString("hometown"));
				studentList.add(student);
			}
			conn.close();
			stmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return studentList;
	}

	public Student findByID(String studentID) {
		String sql = "select * from student where studentID = ?";
		DBConnect dbConn = new DBConnect();
		Student student = null;
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, studentID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				student = new Student();
				student.setStudentID(rs.getString("studentID"));
				student.setName(rs.getString("name"));
				student.setGender(rs.getString("gender"));
				student.setDob(rs.getDate("dob"));
				student.setEmail(rs.getString("email"));
				student.setHometown(rs.getString("hometown"));
			}
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return student;
	}

	public boolean insert(Student student) {
		String sql = "insert into student values (?, ?, ?, ?, ?, ?)";
		DBConnect dbConn = new DBConnect();
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, student.getStudentID());
			pstmt.setString(2, student.getName());
			pstmt.setString(3, student.getGender());
			pstmt.setDate(4, student.getDob());
			pstmt.setString(5, student.getEmail());
			pstmt.setString(6, student.getHometown());
			pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean update(Student student) {
		String sql = "update student set name = ?, gender = ?, dob = ?, email = ?, hometown = ? where studentID = ?";
		DBConnect dbConn = new DBConnect();
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, student.getName());
			pstmt.setString(2, student.getGender());
			pstmt.setDate(3, student.getDob());
			pstmt.setString(4, student.getEmail());
			pstmt.setString(5, student.getHometown());
			pstmt.setString(6, student.getStudentID());
			pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean delete(String studentID) {
		String sql = "delete from student where studentID = ?";
		DBConnect dbConn = new DBConnect();
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, studentID);
			pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean isEmailExists(String email, String studentID) {
		String sql = "select email from student where email = ? and studentID != ? " + "union "
				+ "select email from teacher where email = ?";
		DBConnect dbConn = new DBConnect();

		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, studentID);
			pstmt.setString(3, email);
			ResultSet rs = pstmt.executeQuery();
			boolean result = rs.next();
			conn.close();
			pstmt.close();
			rs.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}

	public boolean isStudentExists(String studentID, String role) {
		if (role.equals("admin"))
			return true;
		else {
			String sql = "select studentID from student where studentID = ?";
			DBConnect dbConn = new DBConnect();
			try {
				Connection conn = dbConn.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, studentID);
				ResultSet rs = pstmt.executeQuery();
				boolean result = rs.next();
				conn.close();
				pstmt.close();
				rs.close();
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	public int totalStudent() {
		String sql = "select count(*) from student";
		DBConnect dbConn = new DBConnect();
		try {
			Connection conn = dbConn.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			int result = 0;
			if (rs.next()) {
				result = rs.getInt(1);
			}
			conn.close();
			stmt.close();
			rs.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public String getStudentName(String studentID) {
		String sql = "select name from student where studentID = ?";
		DBConnect dbConn = new DBConnect();
		String studentName = null;
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, studentID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				studentName = rs.getString("name");
			}
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentName;
	}

	public List<Student> studentListByFilter(String gender, String yob, String hometown) {
		StringBuilder sql = new StringBuilder("select * from student where 1=1");
		List<Object> params = new ArrayList<>();
		if (!gender.isEmpty()) {
			sql.append(" and gender = ?");
			params.add(gender);
		}
		if (!yob.isEmpty()) {
			sql.append(" and year(dob) = ?");
			params.add(yob);
		}
		if (!hometown.isEmpty()) {
			sql.append(" and hometown = ?");
			params.add(hometown);
		}
		DBConnect dbConn = new DBConnect();
		List<Student> studentList = new ArrayList<>();
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			for (int i = 0; i < params.size(); i++) {
	            pstmt.setObject(i + 1, params.get(i));
	        }
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Student student = new Student();
				student.setStudentID(rs.getString("studentID"));
				student.setName(rs.getString("name"));
				student.setGender(rs.getString("gender"));
				student.setDob(rs.getDate("dob"));
				student.setEmail(rs.getString("email"));
				student.setHometown(rs.getString("hometown"));
				studentList.add(student);
			}
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return studentList;
	}
	
	public List<Integer> getListOfYear() {
		String sql = "select year(dob) from student group by year(dob)";
		DBConnect dbConn = new DBConnect();
		List<Integer> yobList = new ArrayList<>();
		try {
			Connection conn = dbConn.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Integer yob = rs.getInt(1);
				yobList.add(yob);
			}
			conn.close();
			stmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return yobList;
	}

	public List<String> getListOfHometown() {
		String sql = "select hometown from student group by hometown";
		DBConnect dbConn = new DBConnect();
		List<String> hometownList = new ArrayList<>();
		try {
			Connection conn = dbConn.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String hometown = rs.getString(1);
				hometownList.add(hometown);
			}
			conn.close();
			stmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return hometownList;
	}

	public List<Student> getStudentsPaginated(int start, int limit) {
	    List<Student> studentList = new ArrayList<>();
	    String sql = "select * from student limit ?, ?";
	    DBConnect dbConn = new DBConnect();
	    try {
	    	Connection conn = dbConn.getConnection();
	    	PreparedStatement pstmt = conn.prepareStatement(sql);
	    	pstmt.setInt(1, start);
	    	pstmt.setInt(2, limit);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	Student student = new Student();
				student.setStudentID(rs.getString("studentID"));
				student.setName(rs.getString("name"));
				student.setGender(rs.getString("gender"));
				student.setDob(rs.getDate("dob"));
				student.setEmail(rs.getString("email"));
				student.setHometown(rs.getString("hometown"));
				studentList.add(student);
	        }
	        conn.close();
			pstmt.close();
			rs.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return studentList;
	}

	public int countStudents() {
	    String sql = "select count(*) from student";
	    DBConnect dbConn = new DBConnect();
	    int result = 0;
	    try {
	    	Connection conn = dbConn.getConnection();
	    	Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(sql);
	        if (rs.next()) {
	            result = rs.getInt(1);
	        }
	        conn.close();
			rs.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}

}
