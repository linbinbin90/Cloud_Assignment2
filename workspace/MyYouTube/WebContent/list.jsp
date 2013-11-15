<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.jsp.MyYouTube.*" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
    <%
    RDSManager DBmanager = new RDSManager();
    Map<String, Double> movieList = DBmanager.getVideoList();
	%>
	<form action="/UpdateRatingServlet" method="POST">
	<table border="1">
	<tr>
	<th>Movie name</th>
	<th>Average rate</th>
	</tr>
	<%
	Set<Map.Entry<String, Double>> entries = movieList.entrySet();
	for(Map.Entry<String, Double> entry : entries) {
	%>
	<tr>
	<td><%=entry.getKey() %></td>
	<td><%=entry.getValue()  %></td>
	</tr>
	<%} %>
	<input type="submit" value="Rate" />
	</table>
	</form>
</body>
</html>