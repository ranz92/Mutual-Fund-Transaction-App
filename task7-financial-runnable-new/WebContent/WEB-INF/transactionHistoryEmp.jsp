<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<h2>Transaction History for customer ${param.username}</h2>


<table class="table table-striped">
	<thead>
		<tr>
			<th>#</th>
			<th>Transaction Type</th>
			<th>Fund Name</th>
			<th># of Shares</th>
			<th>Share Price</th>
			<th>Amount ($)</th>
			<th>Status</th>
			<th>Completion Date</th>
		</tr>
	</thead>
	<c:forEach var="item" items="${transactions}">

		<%
			int index = 1;
		%>

		<tr>
			<td align="left"><%=index%> <%index++;%></td>
			<td align="left">${ item.transactionType }</td>
			<td align="left">${ item.fundName }</td>
			<td align="right"><fmt:formatNumber value="${ item.numShares }" type="number" pattern="#,##0.000" /></td>
			<td align="right"><fmt:formatNumber value="${ item.sharePrice }" type="currency" pattern="#,##0.00" /></td>
			<td align="right"><fmt:formatNumber value="${ item.amount }" type="currency" pattern="#,##0.00" /></td>
			<td align="left">${ item.status }</td>
			<td align="left"><fmt:formatDate value="${ item.executeDate }" type="date" /></td>
		</tr>
	</c:forEach>
</table>

<jsp:include page="template-bottom.jsp" />
