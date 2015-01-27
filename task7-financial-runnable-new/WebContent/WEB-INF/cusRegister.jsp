<jsp:include page="template-top.jsp" />
<h2>Create a new Customer</h2>
<jsp:include page="error-list.jsp" />

<form method="post" class="form-horizontal" role="form" action="cusRegister.do">
	<div class="form-group">
		<label for="inputEmail3" class="col-sm-2 control-label">Username</label>
		<div class="col-sm-10">
			<input type="username" class="form-control" id="inputEmail3" name="username" value="${form.username }" style="width:12em">
		</div>
    </div>
    
    <div class="form-group">
		<label for="inputPassword3" class="col-sm-2 control-label">Password</label>
			<div class="col-sm-10">
				<input type="password" class="form-control" id="inputPassword3" name="password" value="${form.password }" style="width:12em">
			</div>
	</div>
	<div class="form-group">
		 <label for="inputPassword3" class="col-sm-2 control-label">Confirm Password</label>
			<div class="col-sm-10">
				<input type="password" class="form-control" id="inputPassword3" name="confirm" value="${form.confirm }" style="width:12em">
			</div>
	</div>
    
    <div class="form-group">
     	<label for="inputEmail3" class="col-sm-2 control-label">First Name</label>
		<div class="col-sm-10">
			<input type="firstName" class="form-control" id="inputEmail3" name="firstName" value="${form.firstName }" style="width:12em">
		</div>
    </div>
    
    <div class="form-group">
		<label for="inputEmail3" class="col-sm-2 control-label">Last Name</label>
		<div class="col-sm-10">
			<input type="lastName" class="form-control" id="inputEmail3" name="lastName" value="${form.lastName }" style="width:12em">
		</div>
    </div>
    
    <div class="form-group">
		<label for="inputEmail3" class="col-sm-2 control-label">Address Line 1</label>
		<div class="col-sm-10">
			<input type="address" class="form-control" id="inputEmail3" name="addrL1" value="${form.addrL1 }">
		</div>
    </div>
    
    <div class="form-group">
		<label for="inputEmail3" class="col-sm-2 control-label">Address Line 2</label>
		<div class="col-sm-10">
			<input type="address" class="form-control" id="inputEmail3" name="addrL2" value="${form.addrL2 }">
		</div>
    </div>
    
    <div class="form-group">
		<label for="inputEmail3" class="col-sm-2 control-label">City</label>
			<div class="col-sm-10">
			    <input type="text"  class="form-control" id="inputEmail3" name="city" value="${form.city }" style="width:12em">
			</div>
	</div>
	
	<div class="form-group">
		<label for="inputEmail3" class="col-sm-2control-label">State</label>
		    <div >
			    <select name="state" id="state" class="form-control" style="width:12em">   
    					    <option value="">Select a State
							<option value="AA">AA - Armed Forces Americas
                            <option value="AE">AE - Armed Forces Canada
                            <option value="AE">AE - Armed Forces Europe
                            <option value="AE">AE - Armed Forces MiddleEast
                            <option value="AK">AK - Alaska
                            <option value="AL">AL - Alabama
                            <option value="AP">AP - Armed Forces Pacific
                            <option value="AR">AR - Arkansas
                            <option value="AS">AS - American Samoa
                            <option value="AZ">AZ - Arizona
                            <option value="CA">CA - California
                            <option value="CO">CO - Colorado
                            <option value="CT">CT - Connecticut
                            <option value="DC">DC - District of Columbia
                            <option value="DE">DE - Delaware
                            <option value="FL">FL - Florida
                            <option value="GA">GA - Georgia
                            <option value="GU">GU - Guam
                            <option value="HI">HI - Hawaii
                            <option value="IA">IA - Iowa
                            <option value="ID">ID - Idaho
                            <option value="IL">IL - Illinois
                            <option value="IN">IN - Indiana
                            <option value="KS">KS - Kansas
                            <option value="KY">KY - Kentucky
                            <option value="LA">LA - Louisiana
                            <option value="MA">MA - Massachusetts
                            <option value="MD">MD - Maryland
                            <option value="ME">ME - Maine
                            <option value="MI">MI - Michigan
                            <option value="MN">MN - Minnesota
                            <option value="MO">MO - Missouri
                            <option value="MP">MP - Northern Mariana Islands
                            <option value="MS">MS - Mississippi
                            <option value="MT">MT - Montana
                            <option value="NC">NC - North Carolina
                            <option value="ND">ND - North Dakota
                            <option value="NE">NE - Nebraska
                            <option value="NH">NH - New Hampshire
                            <option value="NJ">NJ - New Jersey
                            <option value="NM">NM - New Mexico
                            <option value="NV">NV - Nevada
                            <option value="NY">NY - New York
                            <option value="OH">OH - Ohio
                            <option value="OK">OK - Oklahoma
                            <option value="OR">OR - Oregon
                            <option value="PA">PA - Pennsylvania
                            <option value="PR">PR - Puerto Rico
                            <option value="RI">RI - Rhode Island
                            <option value="SC">SC - South Carolina
                            <option value="SD">SD - South Dakota
                            <option value="TN">TN - Tennessee
                            <option value="TX">TX - Texas
                            <option value="UT">UT - Utah
                            <option value="VA">VA - Virginia
                            <option value="VI">VI - Virgin Islands
                            <option value="VT">VT - Vermont
                            <option value="WA">WA - Washington
                            <option value="WI">WI - Wisconsin
                            <option value="WV">WV - West Virginia
                            <option value="WY">WY - Wyoming
  					    </select>
  					</div>
  			</div>
  			
  	 <div class="form-group">	
		<label for="inputEmail3" class="col-sm-2 control-label">Zip</label>
		    <div class="col-sm-10">
			    <input type="text" class="form-control" id="inputEmail3" name="zip" value="${form.zip }" style="width:12em">
			</div>
    </div>
    
	<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<div class="checkbox">
					<label><input type="checkbox"> Remember me</label>
				</div>
			</div>
	</div>
	<div class="col-sm-offset-2 col-sm-10">
		 <button type="button" class="btn btn-default" >Cancel</button> 
		 <input type="submit" class="btn btn-success" name="button" value="Create"> 
	</div>
				
</form>		

<jsp:include page="template-bottom.jsp" /> 
