<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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


<%--  <c:if test="${not empty updateFileVO}"> --%>
<!--    <select id="s3-bucket"> -->
<%--    <c:forEach var="data" items="${updateFileVO }" varStatus="status"> --%>
<%--      <option value="${data }" label="${data }"></option> --%>
<%--    </c:forEach> --%>
<!--    </select> -->
<%--  </c:if> --%>
<!-- $('#s3-bucket :selected').val() -->

<!--     $('#s3-bucket').change( function() { -->
<!--       $s3ServerTree.dynatree( 'option', 'initAjax', { -->
<!--         url : '../update_file/updateFileList', -->
<!--         data : { -->
<!--           'bucket' : $( '#s3-bucket').val() -->
<!--         }, -->
<!--       }); -->
<!--       $s3ServerTree.dynatree( 'getTree').reload(); -->
<!--     }); -->

