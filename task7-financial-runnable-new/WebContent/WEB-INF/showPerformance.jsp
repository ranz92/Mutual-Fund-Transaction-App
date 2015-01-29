<jsp:include page="template-top.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.*"%>
<h2>Research fund</h2>
<jsp:include page="error-list.jsp" />

<table class="table table-striped">
	<thead>
		<tr>
			<th>Price Date</th>
			<th></th>
			<th  style=" text-align:right">Fund Price</th>
			
		</tr>
	</thead>
	
		   
        <c:forEach var="item" items="${priceList}">
		   <form action="showPerformance.do" method="POST">
         
        <tr>
        	<td><fmt:formatDate value="${ item.price_date}" type="date"/> </td><td></td>
			<td align = "right"><fmt:formatNumber value="${ item.price/100}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></td>
			<td></td><td></td><td></td><td></td><td></td>
			
			
		</tr>
		
		<input type="hidden" name="fundId" value="${ item.fund_id }" />
        
		
		        			
        </form>
		
	</c:forEach>
	
</table>

<table style="
    margin-top: 50px;
    margin-bottom: 300px;">
		<tr>
<td id="box" ></td>

 </tr>
</table>		
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
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/scripts.js"></script>

<script language="javascript">
var gov=new Object();
var Class = {
  create: function() {
    return function() {
      this.initialize.apply(this, arguments);
    }
  }
}
Object.extend = function(destination, source) {
  for (var property in source) {
    destination[property] = source[property];
  }
  return destination;
}
 var $ = function(elem) {
    if (arguments.length > 1) {
      for (var i = 0, elems = [], length = arguments.length; i < length; i++)
        elems.push($(arguments[i]));
      return elems;
    }
    if (typeof elem == 'string') {
      return document.getElementById(elem);
    } else {
      return elem;
    }
  };
var period =  Class.create();
period.prototype = {
   initialize:function(value,time){
    this.value = value;      
    this.time = time;
   }
};
gov.Graphic = Class.create();
gov.Graphic.prototype={
 initialize: function(data,elm,options){
  this.setOptions(options);
  this.entity=document.createElement("div");
  this.pointBox=$(elm);
  this.showPointGraphic(data);
 },
 setOptions: function(options) {
  this.options = {
   height:270,                 
   maxHeight:'${maxPrice}',             
   barDistance:70,           
   topDistance:0,             
   bottomDistance:0,       
   leftDistance:20,
   pointWidth:5,               
   pointHeight:5,             
   pointColor:"#ff0000",    
   lineColor:"#ffd43a",      
   valueWidth:20,           
   valueColor:"#000",       
   timeWidth:5,             
   timeColor:"#000",       
   disvalue:true,             
   distime:true              
  }
  Object.extend(this.options, options || {});
   },
 showPointGraphic:function(data,obj)
 {
   var This=this;
   var showPoints=new Array();
   var values=new Array();
   var times=new Array();
   This.points=data;
   This.count=data.value.length;
   for(var i=0;i<This.count;i++)
   {
    var showPoint=document.createElement("div");
                var spanValue=document.createElement("span");
                var spanTime=document.createElement("span");
    showPoint.height=This.points.value[i];
                showPoint.value=This.points.value[i];
                showPoint.time=This.points.time[i];
    
    showPoint.style.backgroundColor=this.options.pointColor;
    showPoint.style.fontSize="0px";
    showPoint.style.position="absolute";
    showPoint.style.zIndex ="999";
    showPoint.style.width=this.options.pointWidth+"px";
    showPoint.style.height=this.options.pointHeight+"px";
    showPoint.style.top=this.options.topDistance+"px";
                
    spanValue.style.position="absolute";
    spanValue.style.width=this.options.valueWidth+"px";
    spanValue.style.textAlign="center";
    spanValue.style.color=this.options.valueColor;
    spanValue.style.zIndex ="999";
                spanTime.style.position="absolute";
    spanTime.style.width=this.options.timeWidth+"px";
    spanTime.style.textAlign="center";
    spanTime.style.color=this.options.timeColor;
    var timeHeight=15;
    var valueHeight=21;
    if(!this.options.disvalue) {
     spanValue.style.display="none";
     valueHeight=this.options.pointHeight;
    }
    if(!this.options.distime) {
     spanTime.style.display="none";
     timeHeight=0;
    }
    var left;
    if(showPoints.length!=0){
     left=parseInt(showPoints[showPoints.length-1].style.left)+parseInt(showPoints[showPoints.length-1].style.width)+this.options.barDistance;
    }
    else{
     left=this.options.leftDistance;
    }
    
    showPoint.style.left=left+"px";
                spanValue.style.left=left+parseInt((this.options.pointWidth-this.options.valueWidth)/2)+"px";
                spanTime.style.left=left+parseInt((this.options.pointWidth-this.options.timeWidth)/2)+"px";
            
    if(showPoint.height>this.options.maxHeight)
    {
     showPoint.height=this.options.maxHeight;
    }
    
          spanValue.innerHTML=showPoint.value;
    spanTime.innerHTML=showPoint.time;
                        
                showPoints.push(showPoint);
                values.push(spanValue);
                times.push(spanTime);
    This.entity.appendChild(showPoint);
    This.entity.appendChild(spanValue);
    This.entity.appendChild(spanTime);
    
    var percentage=showPoints[i].height/this.options.maxHeight||0;
    var pointTop=(this.options.height-this.options.topDistance-this.options.bottomDistance-timeHeight-valueHeight)*percentage;
    showPoints[i].style.top=(this.options.height-this.options.bottomDistance-pointTop-timeHeight-this.options.pointHeight)+"px";
                values[i].style.top=(this.options.height-this.options.bottomDistance-pointTop-timeHeight-valueHeight)+"px";
                times[i].style.top=this.options.height-this.options.bottomDistance-timeHeight+"px";
   }
   var _leng=showPoints.length
   for(var i=0;i<_leng;i++)
   {
    if(i>0)
     {
      This.drawLine(parseInt(showPoints[i-1].style.left),
             parseInt(showPoints[i-1].style.top),
             parseInt(showPoints[i].style.left),
             parseInt(showPoints[i].style.top)
             );
     }
   }
   This.Constructor.call(This);
  },
  drawLine:function(startX,startY,endX,endY)
  {
   var xDirection=(endX-startX)/Math.abs(endX-startX);
   var yDirection=(endY-startY)/Math.abs(endY-startY);
   var xDistance=endX-startX;
   var yDistance=endY-startY;
   var xPercentage=1/Math.abs(endX-startX);
   var yPercentage=1/Math.abs(endY-startY);
   if(Math.abs(startX-endX)>=Math.abs(startY-endY))
   {
    var _xnum=Math.abs(xDistance)
    for(var i=0;i<=_xnum;i++)
    {
     var point=document.createElement("div");
     point.style.position="absolute";
     point.style.backgroundColor=this.options.lineColor;
     point.style.fontSize="0";
     point.style.width="1px";
     point.style.height="1px";       
     
     startX+=xDirection;
     point.style.left=startX+this.options.pointWidth/2+"px";
     startY=startY+yDistance*xPercentage;
     point.style.top=startY+this.options.pointHeight/2+"px";
     this.entity.appendChild(point);
    }
   }
   else
   {
    var _ynum=Math.abs(yDistance)
    for(var i=0;i<=_ynum;i++)
    {
     var point=document.createElement("div");
     point.style.position="absolute";
     point.style.backgroundColor=this.options.lineColor;
     point.style.fontSize="0";
     point.style.width="1px";
     point.style.height="1px";       
     
     startY+=yDirection;
     point.style.top=startY+this.options.pointWidth/2+"px";
     startX=startX+xDistance*yPercentage;
     point.style.left=startX+this.options.pointHeight/2+"px";
     this.entity.appendChild(point);
    }
   }
  },
  Constructor:function()
  {
   this.entity.style.position="absolute";
   this.pointBox.innerHTML="";
   this.pointBox.appendChild(this.entity);
  }
}
window.onload=function(){
	 var d1 = '${costList}';
	  var d2 = '${dateList}';
	 d1 = d1.substring(1,d1.length-1);
	 d2 = d2.substring(1,d2.length-1);
	 var s1 = d1.split(",");
	 var s2 = d2.split(",");
	 
	 	var data=new period(s1,s2);
		 new gov.Graphic(data,"box");
	 }
  
 
</script>

</body>
</html>

