<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.*"%>
<%@page import="hjx.shop.vo.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<body>
<%
		Map<String,Product> entry = new HashMap<>();
		entry.put("1", new Product("1", "dddddd"));
		entry.put("2", new Product("1", "tttttttt"));
		pageContext.setAttribute("entry", entry);
	%>
	<c:forEach items="${entry }" var="entry">
		${entry.key}<br>
		${entry.value.pname }<br>
	</c:forEach>
</body>
</body>
</html>