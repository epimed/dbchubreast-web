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

	<!-- Fil d'Ariane -->
	<%@ include file="../inc/filAriane.jsp"%>


	<!-- Dismissible alert -->
	<%@ include file="../inc/dismissibleAlert.jsp"%>

	<div class="container">
		<h1>Importer un fichier avec des données cliniques
			supplémentaires</h1>
		<h3>Etape 1. Préparez votre fichier en suivant les
			recommandations suivantes :</h3>
		<ul>
			<li>Format de fichier CSV, extension "*.csv"</li>
			<li>Séparateur virgule "," ou point-virgule ";"</li>
			<li>Une seule ligne d'entête</li>
			<li>Une seule ligne de données par patient</li>
			<li>Noms de colonnes uniques, en minuscules, sans accents, en
				utilisant le symbole "_" à la place des espaces ex : "nom_colonne"</li>
			<li>La première colonne doit s'appeler "id_patient" et contenir
				l'identifiant unique du patient</li>
		</ul>
		<h3>Etape 2. Cliquez sur le bouton ci-dessous pour sélectionner
			et importer votre fichier</h3>

		<!--  
			action="data?${_csrf.parameterName}=${_csrf.token}"
			 -->

		<form:form method="POST" 
			modelAttribute="formUpload"
			action="${pageContext.request.contextPath}/upload"
			enctype="multipart/form-data">

			<div>
				<label for="file" class="btn btn-primary">Importer</label> <input
					type="file" name="file" id="file" accept=".csv"
					onchange="this.form.submit()" style="display: none" />
			</div>

			<p></p>
			<div>
				<p>
					<form:errors path="file" cssClass="text-danger" />
				</p>
			</div>


		</form:form>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>
