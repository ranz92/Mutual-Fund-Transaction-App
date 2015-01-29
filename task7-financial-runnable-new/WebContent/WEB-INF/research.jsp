<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.*"%>
<h2>Research Fund</h2>
<jsp:include page="error-list.jsp" />
<table class="table table-striped">
	<thead>
		<tr>
			<th>Product</th>
			<th>Description</th>
			
			<th>Operation</th>
		</tr>
	</thead>
	
      <c:forEach var="item" items="${fundList}">
		   <form action="showPerformance.do" method="POST">
         
        <tr>
			<td>${ item.name }</td>
			<td>${ item.symbol }</td>
			
			<td><input type="submit" class="btn btn-success" value="Show Performance" /></td>
		</tr>
		
		<input type="hidden" name="fundId" value="${ item.id }" />
        
		
		        			
        </form>
		
	</c:forEach>
	
</table>

<jsp:include page="template-bottom.jsp" />
