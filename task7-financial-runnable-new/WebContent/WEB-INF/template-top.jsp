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
						 <a class="panel-title collapsed" data-toggle="collapse" data-parent="#panel-872653" href="#panel-element-236921">Financial Operation</a>
					</div>
					<div id="panel-element-236921" class="panel-collapse collapse">
						<div class="panel-body">
							<a  href="buyfund.do">Buy Fund</a>						
						</div>
						<div class="panel-body">
							<a  href="sellfund.do">Sell Fund</a>						
						</div>
						<div class="panel-body">
							<a  href="requestCheck.do">Request Check</a>						
						</div>
						<div class="panel-body">
							<a  href="transactionHistory.do">Transaction History</a>						
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
						 <a class="panel-title " data-toggle="collapse" data-parent="#panel-872653" href="#panel-element-236923">My Account</a>
					</div>
					<div id="panel-element-236923" class="panel-collapse">
						<div class="panel-body">
							<a  href="empChangePwd.do">Change Password</a>						
						</div>
						<div class="panel-body">
							<a  href="logout.do">Logout</a>						
						</div>
					</div>
					
					<div class="panel-heading">
						 <a class="panel-title collapsed" data-toggle="collapse" data-parent="#panel-872653" href="#panel-element-236921">Financial Operation</a>
					</div>
					<div id="panel-element-236921" class="panel-collapse collapse">
						<div class="panel-body">
							<a  href="depositCheck.do">Deposit Check  </a>						
						</div>
						<div class="panel-body">
							<a  href="createFund.do">Create Fund </a>						
						</div>
						<div class="panel-body">
							<a  href="transition.do">Transition Day </a>						
						</div>
						<div class="panel-body">
							<a  href="viewHisByEmployee.do">Transaction History</a>						
						</div>
					</div>
					<div class="panel-heading">
						 <a class="panel-title collapsed" data-toggle="collapse" data-parent="#panel-872653" href="#panel-element-236922">Account Operation</a>
					</div>
					<div id="panel-element-236922" class="panel-collapse collapse">
						<div class="panel-body">
							<a  href="empRegister.do">Create Employee Account </a>						
						</div>
						<div class="panel-body">
							<a  href="cusRegister.do">Create Customer Account </a>						
						</div>
						<div class="panel-body">
							<a  href="resetPwd.do">Reset Customer Password </a>						
						</div>
						<div class="panel-body">
							<a  href="viewByEmployee.do">View Customer Account </a>						
						</div>
					</div>
					
					
				</div>
				</c:when>
				<c:otherwise>
				<div class="panel panel-default">
					<div class="panel-heading">
						<a  class="panel-title" href="login.do">Sign In</a>	
				</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<a  class="panel-title" href="research.do">Research Fund</a>	
				
				</div>
				</div>
				</c:otherwise>
				</c:choose>
			</div>
		</div>
			<div class="col-md-9 column">
			
	
	
	
	
