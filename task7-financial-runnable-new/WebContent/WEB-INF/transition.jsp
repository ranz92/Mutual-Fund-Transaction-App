<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h2>Transition Day</h2>
<jsp:include page="error-list.jsp" />

<div class="col-md-2 column">
	<p>Date of Transition</p>
</div>
<form action="transition.do" method="POST">

<div class="col-md-3 column">
	<div class="form-group">
		<div class="input-group date">
			<input class="form-control" name="date" data-date-format="mm/dd/yyyy"> <span
				class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
		</div>
	</div>
</div>
<div class="col-md-3 column">
	<p>Last Transition Day</p></div>
	<div class="col-md-3 column">
	<p>${lastTranDay}</p></div>
	<div class="col-md-1 column"></div>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Id</th>
				<th>Product</th>
				<th>Description</th>
				<th style="text-align:right;">Last Price</th>
				<th style="text-align:right;">Set Price</th>
			</tr>
		</thead>

		<c:forEach var="item" items="${fundList}">
			<tr>
				<td>${ item.id }</td>
				<td>${ item.name }</td>
				<td>${ item.symbol }</td>
				<td align="right">$<fmt:formatNumber value="${ item.lastPrice }" type="currency" pattern="#,##0.00" /></td>
				<td align="right"><input style="text-align:right;" type="text" name="price${item.id}" /></td>
			</tr>
		</c:forEach>
		
	</table>
	<input type="hidden" name="count" value="${count}" />
	<input type="submit" class="btn btn-success" value="Submit" />
</form>

</div>
</div>
<hr>
<div class="row clearfix">
		<div class="col-md-12 column">
			<div class="row">
				<div class="col-md-4">
					<div class="thumbnail">
						<img alt="300x200" src="http://lorempixel.com/600/200/people">
						<div class="caption">
							<h3>
								Thumbnail label
							</h3>
							<p>
								Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.
							</p>
							<p>
								<a class="btn btn-primary" href="#">Action</a> <a class="btn" href="#">Action</a>
							</p>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="thumbnail">
						<img alt="300x200" src="http://lorempixel.com/600/200/city">
						<div class="caption">
							<h3>
								Thumbnail label
							</h3>
							<p>
								Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.
							</p>
							<p>
								<a class="btn btn-primary" href="#">Action</a> <a class="btn" href="#">Action</a>
							</p>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="thumbnail">
						<img alt="300x200" src="http://lorempixel.com/600/200/sports">
						<div class="caption">
							<h3>
								Thumbnail label
							</h3>
							<p>
								Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.
							</p>
							<p>
								<a class="btn btn-primary" href="#">Action</a> <a class="btn" href="#">Action</a>
							</p>
						</div>
					</div>
				</div>
			</div> 
			
		</div>
		
	</div>
	<p class="pull-right">
			<a href="#">Back to top</a>
		</p>
		<p>
		&copy; 2015 Carnegie eBiz. All Rights Reserved. <br/>
	<address> <strong>Carnegie Financial Services, Inc.</strong><br> 500 Forbes Ave<br> Pittsburgh, PA 15213<br> <abbr title="Phone">Phone:</abbr> (412) 268-2000</address>

		</p>
		</div>
		<link href="css/datepicker.css" rel="stylesheet">
<link href="css/datepicker3.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/scripts.js"></script>
<script type="text/javascript" src="js/bootstrap-datepicker.js"></script>

<script>
	window.onload = function() {
		$('.input-group.date').datepicker({
			startDate : "${nextTranDay}",
			autoclose : true
		});
	}
</script>
</body>
</html>
