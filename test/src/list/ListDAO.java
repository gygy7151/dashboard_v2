package list;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ListDAO {
	private Connection conn;
	private ResultSet rs;

	
	public ListDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/nvrg";
			String dbID = "nvsg";
			String dbPassword = "1234";
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
	
	public String getDate() {
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getNString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ""; //데이터베이스 오류
	}
	
	public int getNext() {
		String SQL = "SELECT listID FROM LIST ORDER BY listID DESC";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) + 1;
			}
			return 1; // 첫번째 게시물인 경우
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	public int write(String listTitle, String userID, String listContent) {
		String SQL = "INSERT INTO LIST VALUES (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,  getNext());
			pstmt.setString(2,  listTitle);
			pstmt.setString(3,  userID);
			pstmt.setString(4,  getDate());
			pstmt.setString(5,  listContent);
			pstmt.setInt(6,  1);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류		
	}
	
	public ArrayList<Board> getList(int pageNumber) {
		String SQL = "SELECT * FROM LIST WHERE listID < ? AND listAvailable = 1 ORDER BY listID DESC LIMIT 10";
		ArrayList<Board> list = new ArrayList<Board>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,  getNext() - (pageNumber - 1) * 10);
			rs =pstmt.executeQuery();
			while ( rs.next()) {
				Board text = new Board();
				text.setListID(rs.getInt(1));
				text.setListTitle(rs.getString(2));
				text.setUserID(rs.getString(3));
				text.setListDate(rs.getString(4));
				text.setListContent(rs.getString(5));
				text.setListAvailable(rs.getInt(6));
				list.add(text);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list; //데이터베이스 오류		
	}
	
	public boolean nextPage(int pageNumber) {
		String SQL = "SELECT * FROM LIST WHERE listID < ? AND listAvailable = 1";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,  getNext() - (pageNumber - 1) * 10);
			rs =pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false; 	
	}
	
	public Board getBbs (int listID) {
		String SQL = "SELECT * FROM LIST WHERE listID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,  listID);
			rs =pstmt.executeQuery();
			if (rs.next()) {
				Board bbs = new Board();
				bbs.setListID(rs.getInt(1));
				bbs.setListTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setListDate(rs.getString(4));
				bbs.setListContent(rs.getString(5));
				bbs.setListAvailable(rs.getInt(6));
				return bbs;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; 			
	}
	
	public int update(int listID, String listTitle, String listContent) {
		String SQL = "UPDATE LIST SET listTitle = ?, listContent = ? WHERE listID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, listTitle);
			pstmt.setString(2,  listContent);
			pstmt.setInt(3,  listID);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류			
	}
	
	public int delete(int listID) {
		// 글을 삭제하더라도 글의 정보가 남을 수 있도록 listAvailable값만 1에서 0으로 바꿔서 delete함수 구현이 가능하다.
		String SQL = "UPDATE LIST SET listAvailable = 0 WHERE listID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, listID);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류		
	}
}
