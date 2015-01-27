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
							<td>User Name:</td>
							<td>${username}</td>
						</tr>
						<tr>
							<td>Full Name:</td>
							<td>${firstName}${lastName}</td>
						</tr>
						<tr>
							<td>Current Cash Balance:</td>
							<td>${cash}</td>
						</tr>
						<tr>
							<td>Amount:</td>
							<td><input type="text" name="amount"></td>
						</tr>
						<tr>
							<td><input type="checkbox"></td>
							<td>I have read and agreed with the<a
								href="https://www.google.com/"></a> Terms of Service of CFS
							</td>
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
