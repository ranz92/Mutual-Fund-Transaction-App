<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<h3>Deposit a Check</h3>
			<jsp:include page="error-list.jsp" />
			<form method="post">
				<input type="hidden" name="redirect" value="${redirect}" />
				<table class="table table-striped table-condensed">
					<thead>
						<tr>
							<td><strong>Customer ID: </strong></td>
							<td><input type="text" name="customerId"
								value="${form.customerId}" /></td>
						</tr>
						<tr>
							<td><strong>Customer Name: </strong></td>
							<td><input type="text" name="customerName"
								value="${form.customerName}" /></td>
						</tr>
						<tr>
							<td><strong>Amount: </strong></td>
							<td><input type="text" name="amount" value="${form.amount}" /></td>
						</tr>
						<tr>
							<td><strong>Requested On: </strong></td>
							<td><input type="text" name="amount"
								value="${form.requestDate}" /></td>
						</tr>
						<tr>
							<td><strong>Deposited By: </strong></td>
							<td><input type="text" name="employeeId"
								value="${form.employeeId}" /></td>
						</tr>
						<tr>
							<td colspan="2" align="center"><input type="submit"
								name="button" value="Deposit Check" /></td>
						</tr>
				</table>
			</form>
		</div>
	</div>
</div>

<jsp:include page="template-bottom.jsp" />
