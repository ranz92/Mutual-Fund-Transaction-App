<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="databeans.TransactionBean" %>
<jsp:include page="error-list.jsp" />

<h2>Sell Fund</h2>

<table class="table table-striped">
	
		<thead>
		<tr>
			<th>Product</th>
			<th>Ticker</th>
			<th style="text-align:right;">Total Share</th>
			<th style="text-align:right;">Pending Share</th>
			<th style="text-align:right;">Available Share</th>
			<th style="text-align:right;">Share To Sell</th>
			<th>Operation</th>
		</tr>
	</thead>
	
		   
        <c:forEach var="item" items="${ownList }"> 
		   <form action="confirmsell.do" method="POST">
         
        <tr>
			 <td> ${item.name } </td>  
			 <td> ${item.symbol } </td>  
			<%--  $<fmt:formatNumber value="${customer.cash }" type="currency" pattern="#,##0.00" /> --%>
			<%-- <td> ${item.shares } </td> --%>
			<td style="text-align:right;"> <fmt:formatNumber value="${item.shares }" type="currency" pattern="#,##0.000" /></td>
			 <%--  <td> ${pendingShare } </td>  --%>
			  <td style="text-align:right;"> <fmt:formatNumber value="${(item.amount/1000) }" type="currency" pattern="#,##0.000" /></td> 
			<%-- <td> ${(item.shares-pendingShare) } </td> --%>
			<td style="text-align:right;">  <fmt:formatNumber value="${(item.shares-(item.amount/1000)) }" type="currency" pattern="#,##0.000" /></td>
			<td style="text-align:right;"><input type="text" name="shares" /></td>
			<td style="text-align:right;"><input type="submit" class="btn btn-success" value="Sell" /></td>
		</tr>
		
		<input type="hidden" name="fundId" value="${ item.id }" />
		        			
        </form>
		
	</c:forEach>
	
</table>


<h2> Pending Sell </h2>
<table class="table table-striped">
<thead>
<tr>
<th> Product </th>
			<!-- <th>Pending Share</th>  -->
			 <th style="text-align:right;">Share To Sell</th> 
			</tr>
			</thead>
			
			
			<c:forEach var="item" items="${mSellList }">
			<tr>
			<td>${item.name }</td>
			<%-- <td> ${(item.shares-pendingShare) } </td>  --%>
			<%-- <td>  <fmt:formatNumber value="${item.shares }" type="currency" pattern="#,##0.000" /></td> --%>
			<%--  <td> ${item.shares } </td>  --%>
			 <td style="text-align:right;">  <fmt:formatNumber value="${(item.shares/1000)}" type="currency" pattern="#,##0.000" /></td>
			<td> </td>
			</tr>
			</c:forEach>
	
			

</table>
<jsp:include page="template-bottom.jsp" /> 