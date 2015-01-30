<jsp:include page="template-top.jsp" />

<jsp:include page="error-list.jsp" />

<form action="login.do" method="POST">
<div class="form-group">
<label>Username:</label>
<input class="form-control" name="userName">
</div>
<div class="form-group">
<label>Password:</label>
<input class="form-control" name="password" type="password">
</div> <br />
<button type="submit" name="action" value="customer" class="btn btn-default">Customer Login</button>
<button type="submit" name="action" value="employee" class="btn btn-default">Employee Login</button>
</form>


			
<jsp:include page="template-bottom.jsp" />
