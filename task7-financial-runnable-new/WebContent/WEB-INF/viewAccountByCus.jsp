<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="error-list.jsp" /> 
		
		
		<h2> Account Information </h2>
            <table class="table table-striped ">
				
				<tbody>
					<tr class="active">
						<td >
							Name:
						</td>
						<td>
							${customer.firstname}   ${customer.lastname}
						</td>
						
					</tr>
					<tr>
						<td>
							Address:
						</td>
						<td>
							${customer.addrL1}  
						</td>
						
					</tr>
					<tr class="active">
						<td >
							Address (line 2):
						</td>
						<td>
							${customer.addrL2}  
						</td>
						
					</tr>
					<tr>
						<td>
							City
						</td>
						<td>
							${customer.city}  
						</td>
						
					</tr>
					<tr class="active">
						<td >
							State
						</td>
						<td>
							${customer.state}  
						</td>
						
					</tr>
					<tr>
						<td>
							Zipcode
						</td>
						<td>
							${customer.zip}  
						</td>
						
					</tr>
					
					<tr class="active">
						<td >
							Cash Balance:
						</td>
						<td>
							$<fmt:formatNumber value="${customer.cash/100 }" type="currency" pattern="#,##0.00" />
						</td>
						
					</tr>
					
				<tr >
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

          <div class="tab-pane" id="panel-305422">
			<table class="table">
				<thead>
					<!-- <tr align="right"> -->
					<tr style="text-align:right;">
						<th style="text-align:right;">
							#
						</th>
						<th style="text-align:right;">
							Fund ID
						</th>
						<th style="text-align:right;">
							Number of shares
						</th>
						<th style="text-align:right;">
							Latest Price  ($)
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
				<c:set var="fundPrice" value="${ fundPriceList }" />
				
				<c:set var="count" value="${count+1 }" />
					<!-- <tr align="right"> -->
					<tr style="text-align:right;">
						<td>
							${count}
						</td>
						<td>
							${position.fund_id }
						</td>
						<td >
							<fmt:formatNumber value="${position.shares/1000 }" type="currency" pattern="#,##0.000" />			
						</td >
						<td >
							<fmt:formatNumber value="${fundPrice[count-1] }" type="currency" pattern="#,##0.000" />			
						</td >
						<td>
						    <fmt:formatNumber value="${price[count-1]/1000}" type="currency" pattern="#,##0.00" />
						</td>
					</tr>
				</c:forEach>			
				</tbody>
			</table>
			</div>
	

<jsp:include page="template-bottom.jsp" />
