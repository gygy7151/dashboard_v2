package list;

public class Board {
	
	public static final String System = null;
	private int listID;
	private String listTitle;
	private String userID;
	private String listDate;
	private String listContent;
	private int listAvailable;
	
	
	public int getListID() {
		return listID;
	}
	public void setListID(int listID) {
		this.listID = listID;
	}
	public String getListTitle() {
		return listTitle;
	}
	public void setListTitle(String listTitle) {
		this.listTitle = listTitle;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getListDate() {
		return listDate;
	}
	public void setListDate(String listDate) {
		this.listDate = listDate;
	}
	public String getListContent() {
		return listContent;
	}
	public void setListContent(String listContent) {
		this.listContent = listContent;
	}
	public int getListAvailable() {
		return listAvailable;
	}
	public void setListAvailable(int listAvailable) {
		this.listAvailable = listAvailable;
	}	
}
