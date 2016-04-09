<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Restful Test</title>
</head>
<body>
<h1>Welcome!</h1>
<a href="users/greeting/Steven">add</a>
<a href="<c:url value="/logout"/>">Logout</a>
</body>
</html>