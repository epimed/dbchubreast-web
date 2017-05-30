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
			<h1>Base de donn�es ${globalApplicationName}</h1>
			<p class="lead">Nombre de patients dans la base de donn�es:
				${nbPatients}</p>
		</div>

		<div>
			<h2>Guide d'utilisation :</h2>
			<ol>
				<li><a href="${pageContext.request.contextPath}/patient/add">Cr�er
						un nouveau patient</a> ou <a
					href="${pageContext.request.contextPath}/patients">rechercher un
						patient existant</a></li>
				<li><a href="${pageContext.request.contextPath}/tumeur">Saisir
						l'information sur les tumeurs du patient</a>, en phase initiale et
					�ventuellement en rechutes</li>
				<li><a href="${pageContext.request.contextPath}/prelevement">Renseigner les pr�l�vements et les biomarqueurs</a></li>
				<li>Renseigner les traitements</li>
				<li>Exporter les donn�es en format Excel : <a
					href="${pageContext.request.contextPath}/download/patients">patients</a>,
					<a
					href="${pageContext.request.contextPath}/download/prelevements">pr�l�vements</a>, traitements
				</li>
				<li>Exporter les donn�es valid�es pour la recherche en format
					Excel : <a
					href="${pageContext.request.contextPath}/download/biomarqueurs">biomarqueurs</a>
			</ol>
		</div>

	</div>


	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>