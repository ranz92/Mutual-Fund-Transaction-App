<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h2>Transaction History of customer: </h2>
<table class="table table-striped">
	<thead>
		<tr>
			<th align="left">#</th>
<!-- 			<th align="left">Customer Name</th> -->
			<th align="left">Transaction Type</th>
			<th align="left">Fund Name</th>
			<th align="right"># of Shares</th>
			<th align="right">Share Price</th>
			<th align="right">Amount ($)</th>
			<th align="left">Status</th>
			<th align="left">Completion Date</th>
			</tr>
	</thead>
		<% int index=1; %>
	   <c:forEach var="item" items="${transactions}">
		  
        <tr>
			<td align="left"><%= index %><% index++; %></td>
<%-- 			<td align="left">${ item.customerName }</td> --%>
			<td align="left">${ item.transactionType }</td>
			<td align="left">${ item.fundName }</td>
			<td align="right">${ item.numShares }</td>
			<td align="right">${ item.sharePrice }</td>
			<td align="right">${ item.amount }</td>
			<td align="left">${ item.status }</td>
			<td align="left"><fmt:formatDate value="${ item.executeDate }" type="date"/></td>
			</tr>
	</c:forEach>
</table>

<jsp:include page="template-bottom.jsp" />
