package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Student_classroom;
import util.DBConnect;

public class Student_classroomDAO {

	public List<Student_classroom> findByID(String classroomID, String studentID) {
		List<Student_classroom> student_classroomList = new ArrayList<>();
		if (classroomID != null && studentID == null) {
			String sql = "select * from student_classroom where classroomID = ?";
			DBConnect dbConn = new DBConnect();
			try {
				Connection conn = dbConn.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, classroomID);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					Student_classroom student_classroom = new Student_classroom();
					student_classroom.setClassroomID(rs.getString("classroomID"));
					student_classroom.setStudentID(rs.getString("studentID"));
					student_classroomList.add(student_classroom);
				}
				conn.close();
				pstmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else if (classroomID == null && studentID != null) {
			String sql = "select * from student_classroom where studentID = ?";
			DBConnect dbConn = new DBConnect();
			try {
				Connection conn = dbConn.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, studentID);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					Student_classroom student_classroom = new Student_classroom();
					student_classroom.setClassroomID(rs.getString("classroomID"));
					student_classroom.setStudentID(rs.getString("studentID"));
					student_classroomList.add(student_classroom);
				}
				conn.close();
				pstmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else if (classroomID != null && studentID != null) {
			String sql = "select * from student_classroom where classroomID = ? and studentID = ?";

			DBConnect dbConn = new DBConnect();
			try {
				Connection conn = dbConn.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, classroomID);
				pstmt.setString(2, studentID);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					Student_classroom student_classroom = new Student_classroom();
					student_classroom.setClassroomID(rs.getString("classroomID"));
					student_classroom.setStudentID(rs.getString("studentID"));
					student_classroomList.add(student_classroom);
				}
				conn.close();
				pstmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return student_classroomList;
	}

	public boolean insert(Student_classroom student_classroom) {
		String sql = "insert into student_classroom values (?, ?)";
		DBConnect dbConn = new DBConnect();
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, student_classroom.getClassroomID());
			pstmt.setString(2, student_classroom.getStudentID());
			pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean update(Student_classroom student_classroom, String oldClassroomID, String oldStudentID) {
		String sql = "update student_classroom set classroomID = ?, studentID = ? where classroomID = ? and studentID = ?";
		DBConnect dbConn = new DBConnect();
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, student_classroom.getClassroomID());
			pstmt.setString(2, student_classroom.getStudentID());
			pstmt.setString(3, oldClassroomID);
			pstmt.setString(4, oldStudentID);
			pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean delete(String classroomID, String studentID) {
		String sql = "delete from student_classroom where classroomID = ? and studentID = ?";
		DBConnect dbConn = new DBConnect();
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, classroomID);
			pstmt.setString(2, studentID);
			pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public int studentCount(String classroomID) {
		String sql = "select count(*) from student_classroom where classroomID = ?";
		DBConnect dbConn = new DBConnect();
		int count = 0;
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, classroomID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			return 70;
		}
		return count;
	}
	
}
