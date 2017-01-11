<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>EpiMed Database - BD "Cancer du sein"</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>

	<!-- Container -->
	<div class="container">

		<div class="starter-template">
			<h1>Base de données "Cancer du sein"</h1>
			<p class="lead">Nombre de patients dans la base de données:
				${nbPatients}</p>
		</div>

	</div>


	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>