<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<h2 class="page-header">Create a new Employee</h2>
<jsp:include page="error-list.jsp" />

<!-- Choose a certain customer -->	
    <h2> Choose a Customer </h2>
    <p>Please specify a customer to view.</p>
            <table class="table"">
				<thead>
				    <tr>
				        <td> Username </td>
				        <td> Full Name</td>
				    </tr>
				</thead>
				    <tbody>
				    <c:forEach var="cus" items="${customerList}">
				        
				        <tr>
				            <td>
						        <a href="resetPwd.do?username=${cus.username }" >${cus.username }</a>
				            </td>
				            <td>  ${cus.firstname} ${cus.lastname } </td>
				        </tr>
				    </c:forEach>
				</tbody>				
			   </table>
<!-- End of choosing -->

<form method="post" class="form-horizontal" role="form" action="resetPwd.do">

<div class="form-group">
		<label for="inputPassword3" class="col-sm-2 control-label">New Password</label>
			<div class="col-sm-10">
				<input type="password" class="form-control" id="inputPassword3" name="newPassword" value="${form.newPassword }" style="width:12em">
			</div>
	</div>
	<div class="form-group">
		 <label for="inputPassword3" class="col-sm-2 control-label">Confirm Password</label>
			<div class="col-sm-10">
				<input type="password" class="form-control" id="inputPassword3" name="confirmPassword" value="${form.confirmPassword }" style="width:12em">
			</div>
	</div>
	
	<div class="col-sm-offset-2 col-sm-10">
		 <button type="button" class="btn btn-default" >Cancel</button> 
		 <input type="submit" class="btn btn-success" name="button" value="Reset"> 
	</div>
				
</form>		

<jsp:include page="template-bottom.jsp" /> 