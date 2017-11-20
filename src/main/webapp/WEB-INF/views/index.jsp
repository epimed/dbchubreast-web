<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>

	<!-- Container -->
	<div class="container">

		<div class="starter-template">
			<h1>Base de données ${globalApplicationName}</h1>
			
			<h3>Nombre de patients : ${nbPatients}</h3>
				
			<h3>Nombre de tumeurs : ${nbTumeurs}</h3>
		</div>


	</div>


	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>