<jsp:include page="template-top.jsp" />
<h2>Create a new Employee</h2>
<jsp:include page="error-list.jsp" />

<form method="post" class="form-horizontal" role="form" action="empRegister.do">
	<div class="form-group">
		<label for="inputEmail3" class="col-sm-2 control-label">Username</label>
		<div class="col-sm-10">
			<input type="username" class="form-control" id="inputEmail3" name="username" value="${form.username }" style="width:12em">
		</div>
    </div>
    
    <div class="form-group">
		<label for="inputPassword3" class="col-sm-2 control-label">Password</label>
			<div class="col-sm-10">
				<input type="password" class="form-control" id="inputPassword3" name="password" value="${form.password }" style="width:12em">
			</div>
	</div>
	<div class="form-group">
		 <label for="inputPassword3" class="col-sm-2 control-label">Confirm Password</label>
			<div class="col-sm-10">
				<input type="password" class="form-control" id="inputPassword3" name="confirm" value="${form.confirm }" style="width:12em">
			</div>
	</div>
    
    <div class="form-group">
     	<label for="inputEmail3" class="col-sm-2 control-label">First Name</label>
		<div class="col-sm-10">
			<input type="username" class="form-control" id="inputEmail3" name="firstName" value="${form.firstName }" style="width:12em">
		</div>
    </div>
    
    <div class="form-group">
		<label for="inputEmail3" class="col-sm-2 control-label">Last Name</label>
		<div class="col-sm-10">
			<input type="username" class="form-control" id="inputEmail3" name="lastName" value="${form.lastName }" style="width:12em">
		</div>
    </div>
    
	<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<div class="checkbox">
					<label><input type="checkbox"> Remember me</label>
				</div>
			</div>
	</div>
	<div class="col-sm-offset-2 col-sm-10">
		 <button type="button" class="btn btn-default" >Cancel</button> 
		 <input type="submit" class="btn btn-success" name="button" value="Create"> 
	</div>
				
</form>		

<jsp:include page="template-bottom.jsp" /> 
