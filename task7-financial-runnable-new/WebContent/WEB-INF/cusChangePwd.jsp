<jsp:include page="template-top.jsp" />

<jsp:include page="error-list.jsp" />

			
<div class="row">
        <div class="col-lg-12">
          <h2 class="page-header"> Change Password </h2>
        </div>
      </div>
      <form method="POST" action="cusChangePwd.do">
      <div class="form-group">
        <label>New Password:</label>
        <input required class="form-control" type="password" name="newPassword" value="" />
      </div>
      <div class="form-group">
        <label>Confirm Password</label>
        <input required class="form-control" type="password" name="confirmPassword" value="" />
      </div>
      <!-- <button type="submit" class="btn btn-default">Change Password</button> -->
      <input type="submit" class="btn btn-default" name="button" value="Change Password" />
      </form>
      <p><br>
      </p>

<jsp:include page="template-bottom.jsp" />
