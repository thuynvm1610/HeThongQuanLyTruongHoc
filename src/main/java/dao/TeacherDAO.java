package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Teacher;
import util.DBConnect;

public class TeacherDAO {

	public List<Teacher> findAll() {
		String sql = "select * from teacher";
		DBConnect dbConn = new DBConnect();
		List<Teacher> teacherList = new ArrayList<>();
		try {
			Connection conn = dbConn.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Teacher teacher = new Teacher();
				teacher.setTeacherID(rs.getString("teacherID"));
				teacher.setName(rs.getString("name"));
				teacher.setGender(rs.getString("gender"));
				teacher.setDob(rs.getDate("dob"));
				teacher.setEmail(rs.getString("email"));
				teacher.setHometown(rs.getString("hometown"));
				teacherList.add(teacher);
			}
			conn.close();
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return teacherList;
	}

	public Teacher findById(String teacherID) {
		String sql = "select * from teacher where teacherID = ?";
		DBConnect dbConn = new DBConnect();
		Teacher teacher = null;
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, teacherID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				teacher = new Teacher();
				teacher.setTeacherID(rs.getString("teacherID"));
				teacher.setName(rs.getString("name"));
				teacher.setGender(rs.getString("gender"));
				teacher.setDob(rs.getDate("dob"));
				teacher.setEmail(rs.getString("email"));
				teacher.setHometown(rs.getString("hometown"));
			}
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return teacher;
	}

	public boolean insert(Teacher teacher) {
		String sql = "insert into teacher values (?, ?, ?, ?, ?, ?)";
		DBConnect dbConn = new DBConnect();
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, teacher.getTeacherID());
			pstmt.setString(2, teacher.getName());
			pstmt.setString(3, teacher.getGender());
			pstmt.setDate(4, teacher.getDob());
			pstmt.setString(5, teacher.getEmail());
			pstmt.setString(6, teacher.getHometown());
			pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean update(Teacher teacher) {
		String sql = "update teacher set name = ?, gender = ?, dob = ?, email = ?, hometown = ? where teacherID = ?";
		DBConnect dbConn = new DBConnect();
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, teacher.getName());
			pstmt.setString(2, teacher.getGender());
			pstmt.setDate(3, teacher.getDob());
			pstmt.setString(4, teacher.getEmail());
			pstmt.setString(5, teacher.getHometown());
			pstmt.setString(6, teacher.getTeacherID());
			pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean delete(String teacherID) {
		String sql = "delete from teacher where teacherID = ?";
		DBConnect dbConn = new DBConnect();
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, teacherID);
			pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean isEmailExists(String email, String teacherID) {
		String sql = "select email from teacher where email = ? and teacherID != ? " + "union "
				+ "select email from student where email = ?";
		DBConnect dbConn = new DBConnect();
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, teacherID);
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

	public int totalTeacher() {
		String sql = "select count(*) from teacher";
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

	public List<Teacher> teacherListByFilter(String gender, String yob, String hometown) {
		StringBuilder sql = new StringBuilder("select * from teacher where 1=1");
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
		List<Teacher> teacherList = new ArrayList<>();
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			for (int i = 0; i < params.size(); i++) {
	            pstmt.setObject(i + 1, params.get(i));
	        }
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Teacher teacher = new Teacher();
				teacher.setTeacherID(rs.getString("teacherID"));
				teacher.setName(rs.getString("name"));
				teacher.setGender(rs.getString("gender"));
				teacher.setDob(rs.getDate("dob"));
				teacher.setEmail(rs.getString("email"));
				teacher.setHometown(rs.getString("hometown"));
				teacherList.add(teacher);
			}
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return teacherList;
	}
	
	public List<Integer> getListOfYear() {
		String sql = "select year(dob) from teacher group by year(dob)";
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
		String sql = "select hometown from teacher group by hometown";
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
	
	public List<Teacher> getTeachersPaginated(int start, int limit) {
	    List<Teacher> teacherList = new ArrayList<>();
	    String sql = "select * from teacher limit ?, ?";
	    DBConnect dbConn = new DBConnect();
	    try {
	    	Connection conn = dbConn.getConnection();
	    	PreparedStatement pstmt = conn.prepareStatement(sql);
	    	pstmt.setInt(1, start);
	    	pstmt.setInt(2, limit);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	Teacher teacher = new Teacher();
				teacher.setTeacherID(rs.getString("teacherID"));
				teacher.setName(rs.getString("name"));
				teacher.setGender(rs.getString("gender"));
				teacher.setDob(rs.getDate("dob"));
				teacher.setEmail(rs.getString("email"));
				teacher.setHometown(rs.getString("hometown"));
				teacherList.add(teacher);
	        }
	        conn.close();
			pstmt.close();
			rs.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return teacherList;
	}

	public int countTeachers() {
	    String sql = "select count(*) from teacher";
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
