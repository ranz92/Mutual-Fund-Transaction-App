<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<h2 class="page-header">Reset Password</h2>
<jsp:include page="error-list.jsp" />

<!-- Choose a certain customer -->	
    <p>Please specify a customer to reset.</p>
            <table class="table">
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
						        <a href="resetPwd.do?username=${cus.username }" onclick="return confirm('Really want to reset the password?');">${cus.username }</a>
				            </td>
				            <td>  ${cus.firstname} ${cus.lastname } </td>
				        </tr>
				    </c:forEach>
				</tbody>				
			   </table>
<!-- End of choosing -->



<jsp:include page="template-bottom.jsp" /> 