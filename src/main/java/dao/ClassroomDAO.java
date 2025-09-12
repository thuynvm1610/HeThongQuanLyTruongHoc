package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Classroom;
import util.DBConnect;

public class ClassroomDAO {

	public List<Classroom> findAll() {
		String sql = "select * from classroom";
		DBConnect dbConn = new DBConnect();
		List<Classroom> classroomList = new ArrayList<>();
		try {
			Connection conn = dbConn.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Classroom classroom = new Classroom();
				classroom.setClassroomID(rs.getString("classroomID"));
				classroom.setName(rs.getString("name"));
				classroom.setTeacherID(rs.getString("teacherID"));
				classroomList.add(classroom);
			}
			conn.close();
			stmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return classroomList;
	}

	public Classroom findByID(String classroomID) {
		String sql = "select * from classroom where classroomID = ?";
		DBConnect dbConn = new DBConnect();
		Classroom classroom = null;
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, classroomID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				classroom = new Classroom();
				classroom.setClassroomID(rs.getString("classroomID"));
				classroom.setName(rs.getString("name"));
				classroom.setTeacherID(rs.getString("teacherID"));
			}
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return classroom;
	}

	public boolean insert(Classroom classroom) {
		String sql = "insert into classroom values (?, ?, ?)";
		DBConnect dbConn = new DBConnect();
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, classroom.getClassroomID());
			pstmt.setString(2, classroom.getName());
			pstmt.setString(3, classroom.getTeacherID());
			pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean update(Classroom classroom) {
		String sql = "update classroom set name = ?, teacherID = ? where classroomID = ?";
		DBConnect dbConn = new DBConnect();
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, classroom.getName());
			pstmt.setString(2, classroom.getTeacherID());
			pstmt.setString(3, classroom.getClassroomID());
			pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean delete(String classroomID) {
		String sql = "delete from classroom where classroomID = ?";
		DBConnect dbConn = new DBConnect();
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, classroomID);
			pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public int totalClassroom() {
		String sql = "select count(*) from classroom";
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

	public List<Classroom> findByTeacherID(String teacherID) {
		String sql = "select * from classroom where teacherID = ?";
		DBConnect dbConn = new DBConnect();
		List<Classroom> classroomList = new ArrayList<Classroom>();
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, teacherID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Classroom classroom = new Classroom();
				classroom.setClassroomID(rs.getString("classroomID"));
				classroom.setName(rs.getString("name"));
				classroom.setTeacherID(rs.getString("teacherID"));
				classroomList.add(classroom);
			}
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return classroomList;
	}
	
	public String getClassroomName(String classroomID) {
		String sql = "select name from classroom where classroomID = ?";
		DBConnect dbConn = new DBConnect();
		String classroomName = null;
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, classroomID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				classroomName = rs.getString("name");
			}
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classroomName;
	}
	
	public List<Classroom> getClassroomsPaginated(int start, int limit) {
	    List<Classroom> classroomList = new ArrayList<>();
	    String sql = "select * from classroom limit ?, ?";
	    DBConnect dbConn = new DBConnect();
	    try {
	    	Connection conn = dbConn.getConnection();
	    	PreparedStatement pstmt = conn.prepareStatement(sql);
	    	pstmt.setInt(1, start);
	    	pstmt.setInt(2, limit);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	Classroom classroom = new Classroom();
				classroom.setClassroomID(rs.getString("classroomID"));
				classroom.setName(rs.getString("name"));
				classroom.setTeacherID(rs.getString("teacherID"));
				classroomList.add(classroom);
	        }
	        conn.close();
			pstmt.close();
			rs.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return classroomList;
	}

	public int countClassrooms() {
	    String sql = "select count(*) from classroom";
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
