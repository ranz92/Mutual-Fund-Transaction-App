<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h2>Transaction History</h2>
<table class="table table-striped">
	<thead>
		<tr>
			<th>#</th>
			<th>Customer Name</th>
			<th>Transaction Type</th>
			<th>Fund Name</th>
			<th># of Shares</th>
			<th>Share Price</th>
			<th>Amount ($)</th>
			<th>Status</th>
			<th>Completion Date</th>
			</tr>
	</thead>
		<% int index=1; %>
	   <c:forEach var="item" items="${transactions}">
		  
        <tr>
			<td><%= index %><% index++; %></td>
			<td>${ item.customerName }</td>
			<td>${ item.transactionType }</td>
			<td>${ item.fundName }</th>
			<td>${ item.numShares }</th>
			<td>${ item.sharePrice }</th>
			 <td>${item.amount }</td> 
			<td>${ item.status }</td>
			<td>${ item.executeDate }</td>
			</tr>
	</c:forEach>
</table>

<jsp:include page="template-bottom.jsp" />
