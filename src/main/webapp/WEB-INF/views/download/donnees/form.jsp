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

		<h1>Télécharger les données bio-cliniques</h1>
		<p class="lead">Vous pouvez télécharger les données bio-cliniques
			de patients en format CSV</p>
		<p>La base contient ${nbPatients} patients.</p>
		<p>Choisissez le type de données que vous souhaitez télécharger :</p>

		<form:form class="form-horizontal" method="post" modelAttribute="form"
			action="${pageContext.request.contextPath}/download/donnees">

			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Type de données</label>
				<div class="col-sm-10">
					<label class="radio-inline"> <form:radiobutton
							path="typeDonnees" value="prelevements" />prélèvements et
						biomarqueurs
					</label> <label class="radio-inline"> <form:radiobutton
							path="typeDonnees" value="traitements" />traitements
					</label> <br />
					<form:errors path="typeDonnees" class="control-label" />
				</div>
			</div>

			<button type="submit" class="btn btn-primary" name="button"
				value="save">Télécharger CSV</button>

			<p></p>
			<p class="text-info">Attention, la génération du fichier CSV peut
				être assez longue. Merci de cliquer sur le bouton "Télécharger" et
				puis attendre la fin de l'export. Cela peut prendre plusieurs
				minutes.</p>


		</form:form>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>