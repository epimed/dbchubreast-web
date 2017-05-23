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

		<!-- Fil d'Ariane -->
		<%@ include file="../inc/filAriane.jsp"%>


		<c:if test="${not empty msg}">
			<p></p>
			<div class="alert alert-${css} alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>${msg}</strong>
			</div>
		</c:if>


		<div>

			<!-- H1 Patient -->
			<%@ include file="../inc/h1Patient.jsp"%>

			<h2>Détail de la tumeur</h2>

			<br />

			<div class="row">
				<label class="col-sm-2">ID tumeur</label>
				<div class="col-sm-10">${tumeur.idTumeur}</div>
			</div>

			<div class="row">
				<label class="col-sm-2">Date du diagnostic</label>
				<div class="col-sm-10">${tumeur.dateDiagnostic}</div>
			</div>

			<div class="row">
				<label class="col-sm-2">Age au diagnostic</label>
				<div class="col-sm-10">${tumeur.ageDiagnostic}</div>
			</div>

			<div class="row">
				<label class="col-sm-2">Topographie ICD-O</label>
				<div class="col-sm-10">${tumeur.chuTopographie.idTopographie}
					- ${tumeur.chuTopographie.nomFr} ${tumeur.chuTopographie.nomEn}</div>
			</div>

			<div class="row">
				<label class="col-sm-2">Côté</label>
				<div class="col-sm-10">${tumeur.cote}</div>
			</div>

			<div class="row">
				<label class="col-sm-2">Dernière nouvelle</label>
				<div class="col-sm-10">${tumeur.dateEvolution}</div>
			</div>

			<div class="row">
				<label class="col-sm-2">Statut</label>
				<div class="col-sm-10">${tumeur.chuEvolution.code} -
					${tumeur.chuEvolution.nom}</div>
			</div>

		</div>

		<!-- Phases de la tumeur -->
		<div>
			<%@ include file="../inc/tablePhasesInitiales.jsp"%>
		</div>
		<div>
			<%@ include file="../inc/tablePhasesRechutes.jsp"%>
			<p></p>

			<spring:url value="/tumeur/${tumeur.idTumeur}/rechute/add" var="url" />
			<button class="btn btn-danger" onclick="location.href='${url}'">Ajouter
				une rechute</button>

		</div>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>