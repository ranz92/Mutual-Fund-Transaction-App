<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<h2>Deposit a Check</h2>
			<jsp:include page="error-list.jsp" />
			<form action="confirmDepositCheck.do" method="POST">
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
						<tr>
							<td>${ item.username }</td>
							<td><input type="text" name="amount"></td>
							<td><input type="submit" class="btn btn-success"
								value="Deposit" /></td>
						</tr>

						<input type="hidden" name="fundId" value="${ item.customerId }" />

					</c:forEach>
				</table>
			</form>
		</div>
	</div>
</div>

<jsp:include page="template-bottom.jsp" />
