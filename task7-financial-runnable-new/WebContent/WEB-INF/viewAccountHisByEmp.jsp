<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!-- Choose a certain customer -->
<h2>Choose a Customer</h2>
<p>Please specify a customer to view.</p>
<table class="table">
	<thead>
		<tr>
			<td>Username</td>
			<td>Full Name</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="cus" items="${customerList}">

			<tr>
				<td><a href="transactionHistory.do?username=${cus.username }">${cus.username }</a>
				</td>
				<td>${cus.firstname}${cus.lastname }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<c:set var="customer" value="${user}" />
<h2>Transaction history of ${customer.username}</h2>
<br />
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
			<td align="left"><%=index%> <%
 	index++;
 %></td>
			<td align="left">${ item.transactionType }</td>
			<td align="left">${ item.fundName }</td>
			<td align="right"><fmt:formatNumber value="${ item.numShares }"
					type="number" pattern="#,##0.000" /></td>
			<td align="right"><fmt:formatNumber value="${ item.sharePrice }"
					type="currency" pattern="#,##0.00" /></td>
			<td align="right"><fmt:formatNumber value="${ item.amount }"
					type="currency" pattern="#,##0.00" /></td>
			<td align="left">${ item.status }</td>
			<td align="left"><fmt:formatDate value="${ item.executeDate }"
					type="date" /></td>
		</tr>
	</c:forEach>
</table>


<jsp:include page="template-bottom.jsp" />