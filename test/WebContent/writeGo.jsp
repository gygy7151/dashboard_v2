<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="list.ListDAO"%>
<%@ page import="java.io.PrintWriter"%>
<jsp:useBean id="list" class="list.Board" scope="page" />
<jsp:setProperty name="list" property="listTitle" />
<jsp:setProperty name="list" property="listContent" />
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
	} else {
		if (list.getListTitle() == null || list.getListContent() == null) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안된 사항이 있습니다.')");
			script.println("history.back()");
			script.println("</script>");
		} else {
			ListDAO listDAO = new ListDAO();
			int result = listDAO.write(list.getListTitle(), userID, list.getListContent());
			
			if (result == -1) {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('글쓰기에 실패했습니다.');");
				
				script.println("alert('"+list.getListTitle().toString()+"');");
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