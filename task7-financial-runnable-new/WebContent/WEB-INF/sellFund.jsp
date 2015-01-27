<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h2>Sell Fund</h2>
<h4 class="text-primary">Total Balance: $<fmt:formatNumber value="${customer.cash }" type="currency" pattern="#,##0.00" /> </h4>
<h4 class="text-primary">Pending Balance: $${pendingAmount }</h4>
<h4 class="text-primary">Available Balance: $${availableAmount }</h4>

<table class="table table-striped">
	<thead>
		<tr>
			<th>Product</th>
			<th>Current Price</th>
			<th>Share</th>
			<th>Amount</th>
			<th>Operation</th>
		</tr>
	</thead>
	
		   
        <c:forEach var="item" items="${posList}">
		   <form action="confirmsell.do" method="POST">
         
        <tr>
			<td>${ item.name }</td>
			<td>${ item.price }</td>
			<td>${ item.shares }</td>
			<td><input type="text" name="amount" /></td>
			<td><input type="submit" class="btn btn-success" value="Sell" /></td>
		</tr>
		
		<input type="hidden" name="fundId" value="${ item.id }" />
        
		
		        			
        </form>
		
	</c:forEach>
	
</table>
<jsp:include page="template-bottom.jsp" /> 