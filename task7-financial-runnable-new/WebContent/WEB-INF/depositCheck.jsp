<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<h2>Deposit a Check</h2>
<jsp:include page="error-list.jsp" />

<input type="hidden" name="redirect" value="${redirect}" />
<table class="table table-striped table-condensed">
	<thead>
		<tr>
			<th>Customer Username</th>
			<th>Check Amount</th>
			<th>Operation</th>
		</tr>
	</thead>

	<c:forEach var="item" items="${customerList}">
		<form action="confirmDepositCheck.do" method="POST">
			<tr>
				<td>${ item.username }</td>
				<td><input type="text" name="amount" value=""></td>
				<td><input type="submit" class="btn btn-success"
					value="Deposit" /></td>
			</tr>

			<input type="hidden" name="customerId" value="${ item.customerId }" />
		</form>
	</c:forEach>
</table>
 
<h2>Pending Check Deposit</h2>
<table class="table table-striped">
	<thead>
		<tr>
			<th align="left">#</th>
			<th align="left">Transaction</th>
			<th align="right">Amount</th>
		</tr>
	</thead>
	<%
		int index = 1;
	%>
	<c:forEach var="item" items="${mFundList}">

		<tr>
			<td align="left"><%=index%><%index++;%></td>
			<td align="left">${ item.customerName }</td>
			<td align="right">${ item.amount }</td>
		</tr>
	</c:forEach>
</table>

<jsp:include page="template-bottom.jsp" />