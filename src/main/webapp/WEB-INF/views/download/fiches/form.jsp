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

		<h1>Télécharger les fiches de patients</h1>
		<p class="lead">Entrez une liste d'identifiants de patients
			(numéros EPT) et télécharger les fiches patients en format PDF</p>
		<p>La base contient ${nbPatients} patients.</p>


		<form:form class="form-horizontal" method="post" modelAttribute="form"
			action="${pageContext.request.contextPath}/download/fiches">

			<div class="input-group">
				<span class="input-group-addon" id="basic-addon1">Identifiants
					de patients</span>
				<textarea class="form-control" rows="3" name="text" id="text"
					placeholder="Entrez une liste d'identifiants ici (ex. EPT0001, EPT0002)"></textarea>
			</div>
			<p></p>
			<p>
				<button type="submit" class="btn btn-primary" name="button"
					value="save">Télécharger PDF</button>
					
					<button type="submit" class="btn btn-default pull-right" name="button"
					value="saveall">Télécharger tous les patients PDF</button>

			</p>

		</form:form>
		<p></p>
		<p class="text-danger">${message}</p>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>