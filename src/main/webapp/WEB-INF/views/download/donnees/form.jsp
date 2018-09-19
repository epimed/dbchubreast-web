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

		<h1>T�l�charger les donn�es bio-cliniques</h1>
		<p class="lead">Vous pouvez t�l�charger les donn�es bio-cliniques
			de patients en format CSV</p>
		<p>La base contient ${nbPatients} patients.</p>
		<p>Choisissez le type de donn�es que vous souhaitez t�l�charger :</p>

		<form:form class="form-horizontal" method="post" modelAttribute="form"
			action="${pageContext.request.contextPath}/download/donnees">

			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Type de donn�es</label>
				<div class="col-sm-10">
					<label class="radio-inline"> <form:radiobutton
							path="typeDonnees" value="prelevements" />pr�l�vements et
						biomarqueurs
					</label> <label class="radio-inline"> <form:radiobutton
							path="typeDonnees" value="traitements" />traitements
					</label> <br />
					<form:errors path="typeDonnees" class="control-label" />
				</div>
			</div>

			<button type="submit" class="btn btn-primary" name="button"
				value="save">T�l�charger CSV</button>

			<p></p>
			<p class="text-info">Attention, la g�n�ration du fichier CSV peut
				�tre assez longue. Merci de cliquer sur le bouton "T�l�charger" et
				puis attendre la fin de l'export. Cela peut prendre plusieurs
				minutes.</p>


		</form:form>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>