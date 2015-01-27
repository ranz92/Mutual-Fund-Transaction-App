<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h2>Buy Fund</h2>
<h4 class="text-primary">Total Balance : $<fmt:formatNumber value="${customer.cash }" type="currency" pattern="#,##0.00" /> </h4>
<h4 class="text-primary">Pending Balance : $${pendingAmount}</h4>
<h4 class="text-primary">Available Balance : $${availableAmount}</h4>
<table class="table table-striped">
	<thead>
		<tr>
			<th>Product</th>
			<th>Description</th>
			<th>Amount</th>
			<th>Operation</th>
		</tr>
	</thead>
	
		   
        <c:forEach var="item" items="${fundList}">
		   <form action="confirmbuy.do" method="POST">
         
        <tr>
			<td>${ item.name }</td>
			<td>${ item.symbol }</td>
			<td><input type="text" name="amount" /></td>
			<td><input type="submit" class="btn btn-success" value="Buy" /></td>
		</tr>
		
		<input type="hidden" name="fundId" value="${ item.id }" />
        
		
		        			
        </form>
		
	</c:forEach>
	
</table>
<h2>Pending Buy</h2>
<table class="table table-striped">
	<thead>
		<tr>
			<th>Product</th>
			<th>Description</th>
			<th>Amount</th>
			</tr>
	</thead>
	   <c:forEach var="item" items="${mFundList}">
		  
        <tr>
			<td>${ item.name }</td>
			<td>${ item.symbol }</td>
			<td>${ item.amount }</td>
			</tr>
		
		 
		
		
	</c:forEach>
	
</table>
<jsp:include page="template-bottom.jsp" />