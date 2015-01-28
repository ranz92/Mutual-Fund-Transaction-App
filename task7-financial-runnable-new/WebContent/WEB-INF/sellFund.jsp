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
			<th>Total Share</th>
			<th>Pending Share</th>
			<th>Available Share</th>
			<th>Share To Sell</th>
			<th>Operation</th>
		</tr>
	</thead>
	
		   
        <c:forEach var="item" items="${ownList }"> 
		   <form action="confirmsell.do" method="POST">
         
        <tr>
			
			 <td> ${item.fund_id } </td>  
			<td> ${item.shares } </td>
			  <td> ${pendingShare } </td>  
			<td> ${(item.shares-pendingShare) } </td>
			<td><input type="text" name="shares" /></td>
			<td><input type="submit" class="btn btn-success" value="Sell" /></td>
		</tr>
		
		<input type="hidden" name="fundId" value="${ item.fund_id }" />
        
		
		        			
        </form>
		
	</c:forEach>
	
</table>


<h2> Pending Sell </h2>
<table class="table table-striped">
<thead>
<tr>
<th> Product </th>
			<th>Available Share</th> 
			 <th>Share To Sell</th> 
			</tr>
			</thead>
			
			
			<c:forEach var="item" items="${mSellList }">
			<tr>
			<td>${item.name }</td>
			<td> ${(item.shares-pendingShare) } </td> 
			 <td> ${item.shares } </td> 
			<td> </td>
			</tr>
			</c:forEach>
	
			

</table>
<jsp:include page="template-bottom.jsp" /> 