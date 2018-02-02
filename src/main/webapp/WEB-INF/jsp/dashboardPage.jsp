<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Position Page</title>

     <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/style.css" rel="stylesheet">
    <script src="js/jquery-slim.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </head>
  <body>
  
  
	<div class="container-fluid">
	<%@include file="nav.jsp"%>
	
	<div class="row">
			<div class="col-md-2">
				<div class="center"><h3><strong>Draft Feed:</strong></h3></div>
				<%@include file="draftFeed.jsp"%>
			</div>
			
			
			<div class="col-md-2">
				
				<div class="center"><h3><strong>${currentDrafter.name}'s Team:</strong></h3></div>
					<%@include file="draftersTeam.jsp"%>
				
			</div>
			
			<div class="col-md-8">
				<div class="center"><h3><strong>Suggested Available Players:</strong></h3></div>
					<%@include file="suggestionTable.jsp"%>

			</div>
	</div>

			<c:if test="<%=fantasy.controller.BaseController.errorMessage != null%>">
				<script>
					alert("<%=fantasy.controller.BaseController.errorMessage%>");
				</script>
			</c:if>

			<%@include file="progressBar.jsp"%>
			
	</div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script type="text/javascript" src="js/fliplightbox.min.js"></script>
	<script type="text/javascript">$('#graphics').flipLightBox()</script>
    
</body>
</html>