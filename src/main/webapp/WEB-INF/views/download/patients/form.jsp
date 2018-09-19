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

		<h1>Télécharger les données de patients</h1>
		<p class="lead">Vous pouvez télécharger les informations sur les
			patients en format CSV</p>
		<p>La base contient ${nbPatients} patients.</p>

		<form:form class="form-horizontal" method="post"
			modelAttribute="formDownloadPatients"
			action="${pageContext.request.contextPath}/download/patients">

			<button type="submit" class="btn btn-primary" name="button"
				value="save">Télécharger CSV</button>

		</form:form>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>