<jsp:include page="template-top.jsp" />

<jsp:include page="error-list.jsp" />

<form action="login.do" method="POST">
<div class="form-group">
<label>Username:</label>
<input class="form-control" name="userName">
</div>
<div class="form-group">
<label>Password:</label>
<input class="form-control" name="password" type="password">
</div> <br />
<button type="submit" name="action" value="customer" class="btn btn-default">Customer Login</button>
<button type="submit" name="action" value="employee" class="btn btn-default">Employee Login</button>
</form>

<div class="carousel slide" id="carousel-237806">
				<ol class="carousel-indicators">
					<li class="" data-slide-to="0" data-target="#carousel-237806">
					</li>
					<li data-slide-to="1" data-target="#carousel-237806" class="active">
					</li>
					<li data-slide-to="2" data-target="#carousel-237806" class="">
					</li>
				</ol>
				<div class="carousel-inner">
					<div class="item">
						<img alt="" src="http://lorempixel.com/1600/500/sports/1">
						<div class="carousel-caption">
							<h4>
								First Thumbnail label
							</h4>
							<p>
								Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.
							</p>
						</div>
					</div>
					<div class="item active">
						<img alt="" src="http://lorempixel.com/1600/500/sports/2">
						<div class="carousel-caption">
							<h4>
								Second Thumbnail label
							</h4>
							<p>
								Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.
							</p>
						</div>
					</div>
					<div class="item">
						<img alt="" src="http://lorempixel.com/1600/500/sports/3">
						<div class="carousel-caption">
							<h4>
								Third Thumbnail label
							</h4>
							<p>
								Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.
							</p>
						</div>
					</div>
				</div> <a class="left carousel-control" href="#carousel-237806" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a> <a class="right carousel-control" href="#carousel-237806" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
			</div>

<jsp:include page="template-bottom.jsp" />
