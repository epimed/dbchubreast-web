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
			<h1>Rechercher un patient</h1>
		</div>

		<form:form class="form-inline" method="POST" commandName="form"
			action="patient">

			<div class="form-group">
				<label for="text">Nom, RCP ou ID</label> <input type="text"
					class="form-control" name="text" id="text" value="${text}" placeholder="EPT0001">
			</div>

			<p></p>
			<input class="btn btn-primary" type="submit" value="OK" />
			<span class="text-danger">${message}</span>
			<p></p>

		</form:form>

		<!-- Results -->
		<%@ include file="../inc/tablePatients.jsp"%>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>