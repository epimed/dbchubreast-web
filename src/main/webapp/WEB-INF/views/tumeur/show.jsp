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


		<!-- Dismissible alert -->
		<%@ include file="../inc/dismissibleAlert.jsp"%>


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
				<div class="col-sm-10">
					<fmt:formatDate pattern="dd/MM/yyyy"
						value="${tumeur.dateDiagnostic}" />
				</div>
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
				<div class="col-sm-10">
					<fmt:formatDate pattern="dd/MM/yyyy"
						value="${tumeur.dateEvolution}" />
				</div>
			</div>

			<div class="row">
				<label class="col-sm-2">Statut à la dernière nouvelle</label>
				<div class="col-sm-10">${tumeur.chuEvolution.code} ${tumeur.chuEvolution.nom}</div>
			</div>

			<div class="row">
				<label class="col-sm-2">Triple negative</label>
				<div class="col-sm-10">
					<c:if
						test="${not empty tumeur.tripleNegative and tumeur.tripleNegative}">oui</c:if>
					<c:if
						test="${not empty tumeur.tripleNegative and not tumeur.tripleNegative}">non</c:if>
				</div>
			</div>

			<div class="row">
				<label class="col-sm-2">Survie (mois)</label>
				<div class="col-sm-10">
					<c:if test="${not empty tumeur.osMonths}">globale : ${tumeur.osMonths}</c:if>
					<c:if
						test="${not empty tumeur.osMonths and not empty tumeur.dfsMonths}">, &nbsp;</c:if>
					<c:if test="${not empty tumeur.dfsMonths}">sans rechute : ${tumeur.dfsMonths}</c:if>
				</div>
			</div>


		</div>

		<!-- Phases de la tumeur -->
		<div>
			<%@ include file="tablePhasesInitiales.jsp"%>
		</div>
		<div>
			<%@ include file="tablePhasesRechutes.jsp"%>
			<p></p>

			<spring:url value="/tumeur/${tumeur.idTumeur}/rechute/add" var="url" />
			<button class="btn-sm btn-warning" onclick="location.href='${url}'">Ajouter
				une rechute</button>

		</div>

		<!-- Boutons consulter -->
		<%@ include file="../inc/boutonsConsulter.jsp"%>


	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>