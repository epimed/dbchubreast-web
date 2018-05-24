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
		<h1>Importer un fichier avec des donn�es cliniques
			suppl�mentaires</h1>
		<h3>Etape 1. Pr�parez votre fichier en suivant les
			recommandations suivantes :</h3>
		<ul>
			<li>Format de fichier CSV, extension <code>*.csv</code></li>
			<li>S�parateur virgule <code>,</code> ou point-virgule <code>;</code></li>
			<li>Une seule ligne d'ent�te</li>
			<li>Une seule ligne de donn�es par patient</li>
			<li>Noms de colonnes uniques, en minuscules, sans accents, en
				utilisant le symbole <code>_</code> � la place des espaces ex : "nom_colonne"</li>
			<li>La premi�re colonne doit s'appeler <code>id_patient</code> et contenir
				l'identifiant unique du patient</li>
		</ul>
		<h3>Etape 2. Cliquez sur le bouton ci-dessous pour s�lectionner
			et importer votre fichier</h3>

		<p class="text-danger">Attention, l'import peut prendre quelques
			(LONGUES !) minutes</p>
		<ul>
			<li>Cliquez sur le bouton "Importer", s�lectionneez votre fichier.</li>
			<li>Attendez patiemment le temps que les donn�es s'enregistrent dans la base de donn�es. Cela peut prendre plusieurs minutes.</li>
			<li>Quand l'import est termin�, un �cran de r�ussite d'import va s'afficher.</li>
		</ul>

		<form:form method="POST" modelAttribute="formUpload"
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
