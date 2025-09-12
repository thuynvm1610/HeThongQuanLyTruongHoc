package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.DBConnect;

public class LoginDAO {
	
	public boolean isUsernameExists(String username) {
		String sql = "select * from account where username = ?";
		DBConnect dbConn = new DBConnect();
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
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
	
	public boolean isPasswordValid(String username, String password) {
		String sql = "select password from account where username = ? and password = ?";
		DBConnect dbConn = new DBConnect();
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
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
	
	public String getRole(String username) {
		String sql = "select role from account where username = ?";
		DBConnect dbConn = new DBConnect();
		try {
			Connection conn = dbConn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			String role = null;
			if (rs.next()) {
			    role = rs.getString("role");
			}
			conn.close();
			pstmt.close();
			rs.close();
			return role;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean isStudentIDUsed(String studentID) {
		String sql = "select studentID from account where studentID = ?";
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
			return true;
		}
	}

	public boolean isStudentExists(String studentID) {
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
	
	public String getLastAccountID() {
	    String sql = "select accountID from account order by accountID desc limit 1";
	    DBConnect dbConn = new DBConnect();
	    try {
	        Connection conn = dbConn.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getString("accountID");
	        }
	        conn.close();
	        pstmt.close();
	        rs.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	public String generateNextAccountID() {
	    String lastID = getLastAccountID();
	    if (lastID != null) {
	        int number = Integer.parseInt(lastID.substring(3));
	        number++;
	        return String.format("TK_%04d", number);
	    } else {
	        return "TK_0001";
	    }
	}

	public boolean isConfirmPasswordValid(String confirmPassword, String password) {
		if (confirmPassword.equals(password)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getStudentID(String username) {
		String sql = "select studentID from account where username = ?";
	    DBConnect dbConn = new DBConnect();
	    String studentID = null;
	    try {
	        Connection conn = dbConn.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, username);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            studentID = rs.getString("studentID");
	        }
	        conn.close();
	        pstmt.close();
	        rs.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return studentID;
	}
	
	public String getStudentName(String username) {
		String sql = "select name from student where studentID = ?";
	    DBConnect dbConn = new DBConnect();
	    String studentID = this.getStudentID(username);
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
	
}
