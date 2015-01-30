<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<h2>Request a Check</h2>
<jsp:include page="error-list.jsp" />
<form action="confirmRequestCheck.do" method="POST">
	<input type="hidden" name="redirect" value="${redirect}" />
	<table class="table table-striped table-condensed">
		<thead>
			<tr>
				<td>User Name:</td>
				<td>${customer.username}</td>
			</tr>
			<tr>
				<td>Full Name:</td>
				<td>${customer.firstname}${customer.lastname}</td>
			</tr>
			<tr>
				<td>Available Balance:</td>
				<td>${availableAmount}</td>
			</tr>
			<tr>
				<td>Amount:</td>
				<td><input type="text" name="amount"></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit"
					name="button" class="btn btn-default" value="Request Check" /></td>
			</tr>
	</table>
</form>

<h2>Pending Buy</h2>
<table class="table table-striped">
	<thead>
		<tr>
			<th>#</th>
<!-- 			<th>Date</th> -->
			<th>Transaction</th>
			<th>Amount</th>
		</tr>
	</thead>
	<%
		int index = 1;
	%>
	<c:forEach var="item" items="${mFundList}">

		<tr>
			<td><%=index%><%index++;%></td>
<%-- 			<td>${ item.date }</td> --%>
			<td>${ item.name }</td>
			<td>${ item.amount }</td>
		</tr>
	</c:forEach>
</table>
<jsp:include page="template-bottom.jsp" />
