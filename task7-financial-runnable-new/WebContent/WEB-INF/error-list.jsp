<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<c:if test="${not empty errors}">
	<div class="alert alert-danger" role="alert">
 
		<c:forEach var="error" items="${errors}">
		  <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
  ${error} 
  
		  </br>
		</c:forEach>
	</div>
</c:if>
<c:if test="${not empty success}">
	<div class="alert alert-success" role="alert">
		<c:forEach var="suc" items="${success}">
		  ${suc} 
	 </br>
		</c:forEach>
	</div>
</c:if>