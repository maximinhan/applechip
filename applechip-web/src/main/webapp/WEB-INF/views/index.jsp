<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %> --%>
<%@ page session="false"%>
<html>
<head>
<title>${projetName}</title>
</head>
<body>
	<h1>Hello ${projectName}</h1>
	<P>The time on the server is ${serverTime}.</P>
</body>
</html>
