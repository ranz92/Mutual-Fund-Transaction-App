<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!-- Choose a certain customer -->	
    <h2> Choose a Customer </h2>
    <p>Please specify a customer to view the Transaction History.</p>
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
						<td class="active">
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
					<tr>
						<td class="active">
							City
						</td>
						<td>
							${customer.city}  
						</td>
						
					</tr>
					<tr>
						<td>
							State
						</td>
						<td>
							${customer.state}  
						</td>
						
					</tr>
					<tr>
						<td class="active">
							Zipcode
						</td>
						<td>
							${customer.zip}  
						</td>
					<tr >
						<td>
							Cash Balance ($)
						</td>
						<td>
						<fmt:formatNumber value="${customer.cash /100 }" type="currency" pattern="#,##0.00" />
						</td>
					</tr>
					
					<tr class="active">
					    <td>
							Last Trading Day
						</td>
						<c:set var="transaction" value="${transaction}" />
						<td>
						    <c:choose>
                                <c:when test="${transaction == null}">
                                    No trading in the past
				                </c:when>
				                <c:otherwise> 
							       <fmt:formatDate value="${transaction.execute_date }" type="date"/> 
							    </c:otherwise>
							</c:choose>
						</td>
					</tr>
				</tbody>
			</table>
					</div>
					<div class="tab-pane" id="panel-305422">
						<table class="table">
				<thead>
					<tr >
						<th>
							#
						</th>
						<th>
							Fund ID
						</th>
						<th style="text-align:right;">
							Number of shares
						</th>
						<th style="text-align:right;">
							Position Value ($)
						</th>
					</tr>
				</thead>
				<tbody>
				<c:set var="count" value="0" />
				<c:forEach var="position" items="${positions}" >
				<c:set var="price" value="${ priceList }" />
				
				<c:set var="count" value="${count+1 }" />
					<tr >
						<td>
							${count}
						</td>
						<td>
							${position.fund_id }
						</td>
						<td align="right">
							<fmt:formatNumber value="${position.shares/1000 }" type="currency" pattern="#,##0.000" />			
						</td >
						<td align="right">
						    <fmt:formatNumber value="${price[count-1]}" type="currency" pattern="#,##0.00" />
							
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
