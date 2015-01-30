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
<jsp:include page="template-bottom.jsp" />