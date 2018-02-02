<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Draft Board Page</title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/style.css" rel="stylesheet">
	<script src="js/jquery-slim.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </head>
  <body>
	<div class="container-fluid">
	
	<%@include file="nav.jsp"%>
	
	<div class="row">
			<div class="col-md-12">
				<%@include file="draftBoard.jsp"%>
			</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<%@include file="resultBoard.jsp"%>
		</div>
	</div>
			
			
						
	</div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script type="text/javascript" src="js/fliplightbox.min.js"></script>
	<script type="text/javascript">$('#graphics').flipLightBox()</script>
    
</body>
</html>