<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
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
							<td>${customer.firstname} ${customer.lastname}</td>
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
								name="button" value="Request Check" /></td>
						</tr>
				</table>
			</form>
		</div>
	</div>
</div>
<h2>Pending Buy</h2>
<table class="table table-striped">
	<thead>
		<tr>
			<th>Fund</th>
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
