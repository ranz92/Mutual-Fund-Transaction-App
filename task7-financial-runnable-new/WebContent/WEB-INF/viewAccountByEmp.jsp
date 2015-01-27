<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

		
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
						        <a href="viewByEmployee.do?username=${cus.username }" >${cus.username }</a>
				            </td>
				            <td>  ${cus.firstname} ${cus.lastname } </td>
				        </tr>
				    </c:forEach>
				</tbody>				
			   </table>
<!-- End of choosing -->
			   
    <c:set var="customer" value="${user}" />
			<c:choose>
                    <c:when test="${customer == null}">
                        <br/>
				    </c:when>
				    <c:otherwise> 
				    <h2>    ${customer.username} </h2><br/>
				    <div class="tabbable" id="tabs-813699">
				<ul class="nav nav-tabs">
					<li class="active">
						<a href="#panel-179879" data-toggle="tab">Account Information</a>
					</li>
					<li>
						<a href="#panel-305422" data-toggle="tab">Fund Information</a>
					</li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="panel-179879">
						<table class="table">
				
				<tbody>
					<tr>
						<td>
							First Name
						</td>
						<td>
							${customer.firstname}
						</td>
					</tr>
					<tr>
						<td>
							Last Name
						</td>
						<td>
							${customer.lastname} 
						</td>
					</tr>
					<tr class="active">
						<td>
							Address
						</td>
						<td>
							${customer.addrL1} ${user.addrL2}  
						</td>
					</tr>
					<tr >
						<td>
							Cash Balance ($)
						</td>
						<td>
							${customer.cash}
						</td>
					</tr>
					
					<tr class="active">
					    <td>
							Last Trading Day
						</td>
						<c:set var="transaction" value="${transaction}" />
						<td>
							${transaction.execute_date }
						</td>
					</tr>
				</tbody>
			</table>
					</div>
					<div class="tab-pane" id="panel-305422">
						<table class="table">
				<thead>
					<tr>
						<th>
							#
						</th>
						<th>
							Fund ID
						</th>
						<th>
							Number of shares
						</th>
						<th>
							Position Value ($)
						</th>
					</tr>
				</thead>
				<tbody>
				<c:set var="count" value="0" />
				<c:forEach var="position" items="${positions}" >
				<c:set var="price" value="${ priceList }" />
				
				<c:set var="count" value="${count+1 }" />
					<tr>
						<td>
							${count}
						</td>
						<td>
							${position.fund_id }
						</td>
						<td align="right">
							${position.shares }
						</td >
						<td align="right">
							${price[count-1]}
						</td>
					</tr>
				</c:forEach>			
				</tbody>
			</table>
					</div>
				</div>
			</div>
				    </c:otherwise>
                </c:choose>
	

<jsp:include page="template-bottom.jsp" />
