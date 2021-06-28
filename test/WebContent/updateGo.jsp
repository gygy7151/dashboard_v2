<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="list.ListDAO"%>
<%@ page import="list.Board"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width" , initial-scale="1">
<title>BluevulpeBoard</title>
</head>
<body style="background-color: lightblue;">
	<%
		String userID = null;
	if (session.getAttribute("userID") != null) {
		userID = (String) session.getAttribute("userID");
	}
	if (userID == null) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인을 하세요.')");
		script.println("");
		script.println("location.href = 'login.jsp'");
		script.println("</script>");
	} 
	
	int listID = 0;
	if (request.getParameter("listID") != null) {
		listID = Integer.parseInt(request.getParameter("listID"));
	}
	if (listID == 0) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('유효하지 않은 글입니다.')");
		script.println("location.href = 'list.jsp'");
		script.println("</script>");		
	}
	//현재 넘어온 id값을 가지고 세션에 있는 값과 이 글을 작성한 사람의 값을 서로 비교및 id값 동일여부 체크
	Board bbs = new ListDAO().getBbs(listID);
	if (!userID.equals(bbs.getUserID())) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('권한이 없습니다.')");
		script.println("location.href = 'list.jsp'");
		script.println("</script>");		
	} else { //listTitle이라는 이름으로 매개변수로 넘어온 값들을 확인한다. 빈값이나 null값이 입력된 경우 체크한다.
		if (request.getParameter("listTitle") == null || request.getParameter("listContent") == null
		|| request.getParameter("listTitle").equals("") || request.getParameter("listContent").equals("")) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안된 사항이 있습니다.')");
			script.println("history.back()");
			script.println("</script>");
		} else { //모든내용이 다 잘 작성되었는지 확인하는 예외처리문
			ListDAO listDAO = new ListDAO();
			int result = listDAO.update(listID, request.getParameter("listTitle"), request.getParameter("listContent"));
			
			if (result == -1) {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('글수정에 실패했습니다.');");
				
				script.println("alert('"+bbs.getListTitle().toString()+"');");
				script.println("history.back();");
				script.println("</script>");
			} else {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("location.href = 'list.jsp'");
				script.println("</script>");
			}
		}
		
	}
	%>
</body>
</html>