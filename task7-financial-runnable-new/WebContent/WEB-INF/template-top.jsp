<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="pragma" content="no-cache">
<title>Mutual Fund from CFS</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/style.css" rel="stylesheet">

</head>

<body>
	<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div class="page-header">
				<h1 class="text-danger">
					Carnegie Financial Services
					 <small><!-- Ready to get started? --> <!--  <button type="button" class="btn btn-sm btn-danger">Sign Up an Account</button>  --> 
					 <div class="navbar-form navbar-right">
					 <c:choose>
				<c:when test="${not empty customer}">
				Welcome, ${customer.firstname}   ${customer.lastname}
				</c:when>
				<c:when test="${not empty employee}">
				Welcome, ${employee.firstname}   ${employee.lastname}
				
				</c:when>
				<c:otherwise>
				 <!-- <button type="submit" data-toggle="modal" data-target="#modal-container-353912"
						class="btn btn-success">Sign in</button> -->
				</c:otherwise>
				
				</c:choose>
					 
					
						</div>
			<!-- <div class="modal fade" id="modal-container-353912" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
				<div class="modal-dialog">
					<div class="modal-content">
						 <div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button><h4 class="modal-title" id="myModalLabel">
								Sign In
							</h4>
						</div> 
						<div class="modal-body">
							<div class="tabbable" id="tabs-373235">
				<ul class="nav nav-tabs">
					<li class="active">
						<a href="#panel-998277" data-toggle="tab"><font size="4">Customer</font></a>
					</li>
					<li class="">
						<a href="#panel-797162" data-toggle="tab"><font size="4">Employee</font></a>
					</li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="panel-998277">
						<p>
							</p>
							<form class="form-horizontal"  method="post" action="cuslogin.do">
				<div class="form-group">
					 <label for="inputEmail3" class="col-sm-2 control-label"><font size="3" face="Verdana">Username</font></label>
					<div class="col-sm-10">
						<input type="text" name="userName" class="form-control" id="inputEmail3">
					</div>
				</div>
				<div class="form-group">
					 <label for="inputPassword3" class="col-sm-2 control-label"><font size="3" face="Verdana">Password</font></label>
					<div class="col-sm-10">
						<input type="password" name="password" class="form-control" id="inputPassword3">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<div class="checkbox">
							 <label><input type="checkbox"> <font size="3" face="Verdana">Remember me</font></label>
						</div>
					</div>
				</div>
				<div class="modal-footer">
							 <button type="button" class="btn btn-default" data-dismiss="modal">Close</button> <button type="button" class="btn btn-primary">Save changes</button>
							 <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button> <input type="submit" class="btn btn-success" name="button" value="Login"> 
						</div>
			</form>
						<p></p>
					</div>
					<div class="tab-pane" id="panel-797162">
						<p>
							</p><form class="form-horizontal"  method="post" action="emplogin.do">
						<div class="form-group">
					 <label for="inputEmail3" class="col-sm-2 control-label"><font size="3" face="Verdana">Username</font></label>
					<div class="col-sm-10">
						<input type="text"  name="userName" class="form-control" id="inputEmail3">
					</div>
				</div>
				<div class="form-group">
					 <label for="inputPassword3" class="col-sm-2 control-label"><font size="3" face="Verdana">Password</font></label>
					<div class="col-sm-10">
						<input type="password" name="password" class="form-control" id="inputPassword3">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<div class="checkbox">
							 <label><input type="checkbox"> <font size="3" face="Verdana">Remember me</font></label>
						</div>
					</div>
				</div>
				<div class="modal-footer">
							 <button type="button" class="btn btn-default" data-dismiss="modal">Close</button> <button type="button" class="btn btn-primary">Save changes</button>
							 <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button> <input type="submit" class="btn btn-success" name="button" value="Login"> 
						</div>
			</form>
						<p></p>
					</div>
				</div>
			</div>
						</div>
						
					</div>
				</div>				
			</div> -->
			
					</small>
				</h1>
				<small>CFS serves the best service for you</small>
				Or give us a call at (412)888-8888.	
			</div>
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-3 column">
			<div class="panel-group" id="panel-872653">
				<c:choose>
				<c:when test="${not empty customer}">
				<div class="panel panel-default">
					<div class="panel-heading">
						 <a class="panel-title" data-toggle="collapse" data-parent="#panel-872653" href="#panel-element-236921">My Account</a>
					</div>
					<div id="panel-element-236921" class="panel-collapse">
						<div class="panel-body">
							<a  href="viewCustomer.do">View Account</a>						
						</div>
						<div class="panel-body">
							<a  href="cusChangePwd.do">Change Password</a>						
						</div>				
						<div class="panel-body">
							<a  href="logout.do">Logout</a>						
						</div>
					</div>
					<div class="panel-heading">
						 <a class="panel-title" data-toggle="collapse" data-parent="#panel-872653" href="#panel-element-236921">Financial Operation</a>
					</div>
					<div id="panel-element-236921" class="panel-collapse">
						<div class="panel-body">
							<a  href="buyfund.do">Buy Fund</a>						
						</div>
						<div class="panel-body">
							<a  href="sellfund.do">Sell Fund</a>						
						</div>
						<div class="panel-body">
							<a  href="#">Request Check</a>						
						</div>
						<div class="panel-body">
							<a  href="#">Transaction History</a>						
						</div>
						<div class="panel-body">
							<a  href="research.do">Research Fund</a>						
						</div>
					</div>
				</div>
				
				</c:when>
				<c:when test="${not empty employee}">
				<div class="panel panel-default">
					<div class="panel-heading">
						 <a class="panel-title" data-toggle="collapse" data-parent="#panel-872653" href="#panel-element-236921">Financial Operation</a>
					</div>
					<div id="panel-element-236921" class="panel-collapse">
						<div class="panel-body">
							<a  href="#">Deposit Check  </a>						
						</div>
						<div class="panel-body">
							<a  href="createFund.do">Create Fund </a>						
						</div>
						<div class="panel-body">
							<a  href="transition.do">Transition Day </a>						
						</div>
					</div>
					<div class="panel-heading">
						 <a class="panel-title" data-toggle="collapse" data-parent="#panel-872653" href="#panel-element-236922">Account Operation</a>
					</div>
					<div id="panel-element-236922" class="panel-collapse">
						<div class="panel-body">
							<a  href="empRegister.do">Create Employee Account </a>						
						</div>
						<div class="panel-body">
							<a  href="cusRegister.do">Create Customer Account </a>						
						</div>
						<div class="panel-body">
							<a  href="#">Reset Customer Password </a>						
						</div>
						<div class="panel-body">
							<a  href="viewByEmployee.do">View Customer Account </a>						
						</div>
					</div>
					<div class="panel-heading">
						 <a class="panel-title" data-toggle="collapse" data-parent="#panel-872653" href="#panel-element-236923">My Account</a>
					</div>
					<div id="panel-element-236923" class="panel-collapse">
						<div class="panel-body">
							<a  href="empChangePwd.do">Change Password</a>						
						</div>
						<div class="panel-body">
							<a  href="logout.do">Logout</a>						
						</div>
					</div>
					
				</div>
				</c:when>
				<c:otherwise>
				<div class="panel panel-default">
					<div class="panel-heading">
						<a id="modal-353912" href="#modal-container-353912" role="button" class="btn" data-toggle="modal">Sign In</a>
				</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<a id="modal-353912" href="#modal-container-353912" role="button" class="btn" data-toggle="modal">Research Fund</a>
				
				</div>
				</div>
				</c:otherwise>
				</c:choose>
			</div>
		</div>
			<div class="col-md-9 column">
			
	
	
	
	
