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
			<h1>Rechercher une tumeur</h1>
		</div>

		<form:form class="form-inline" method="POST" commandName="form"
			action="tumeur">

			<div class="form-group">
				<label for="text">ID tumeur, patient, topo, statut</label> <input
					type="text" class="form-control" name="text" id="text"
					value="${text}" placeholder="1 ou EPT0001 ou C50.2 ou RC">
			</div>

			<p></p>
			<input class="btn btn-primary" type="submit" value="Rechercher" />
			<span class="text-danger">${message}</span>
			<p></p>

		</form:form>

		<div>

			<!-- Results -->
			<%@ include file="../inc/tableTumeurs.jsp"%>

		</div>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>