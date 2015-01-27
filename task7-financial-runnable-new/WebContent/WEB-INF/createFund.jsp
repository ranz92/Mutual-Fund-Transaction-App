<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<h2>Create Fund</h2>
<jsp:include page="error-list.jsp" />
<table class="table table-striped">
	<thead>
		<tr>
			<th>Fund Name</th>
			<th>Ticker</th>
			<th>Operation</th>
			
		</tr>
	</thead>
	
		   
       
		   <form action="createFund.do" method="POST">
         
        <tr>
			
			<td><input type="text" name="fundName" value="${form.fundName}"/></td>
			<td><input type="text" name="ticker" value="${form.ticker}"/></td>
			<td><input type="submit" class="btn btn-success" value="Create Fund" /></td>
		</tr>
   			
        </form>
		

	
</table>
<h2>All Funds</h2>
<table class="table table-striped">
	<thead>
		<tr>
			<th>Product</th>
			<th>Ticker</th>
			
		</tr>
	</thead>
	
		   
        <c:forEach var="item" items="${fundList}">

			<td>${ item.name }</td>
			<td>${ item.symbol }</td>
			</tr>
	
	</c:forEach>
	
</table>
<jsp:include page="template-bottom.jsp" />