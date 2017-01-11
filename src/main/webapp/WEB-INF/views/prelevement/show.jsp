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
			<h1>Patient <a href="${pageContext.request.contextPath}/patient/${patient.idPatient}">${patient.idPatient}</a></h1>
			<p class="lead">
			${patient.nom}&nbsp;${patient.prenom}&nbsp;${patient.dateNaissance}</p>
		</div>


		<h2>Tumeur :</h2>
		<table class="table table-bordered">
			<thead>
				<tr class="success">
					<th>#ID tumeur</th>
					<th>Date</th>
					<th>Age</th>
					<th>Topo</th>
					<th>Côté</th>
					<th>Dernière nouvelle</th>
					<th>Statut</th>
					<th>TN</th>
					<th>Survie OS/DFS</th>

				</tr>
			</thead>
				<tr>
					<td>${tumeur.idTumeur}</td>
					<td>${tumeur.dateDiagnostic}</td>
					<td>${tumeur.ageDiagnostic}</td>
					<td><abbr title="${tumeur.chuTopographie.nomFr}">${tumeur.chuTopographie.idTopographie}</abbr></td>
					<td>${tumeur.cote}</td>
					<td>${tumeur.dateEvolution}</td>
					<td><abbr title="${tumeur.chuEvolution.nom}">${tumeur.chuEvolution.code}</abbr></td>
					<td class="warning">${tumeur.tripleNegative}</td>
					<td class="warning">${tumeur.osMonths}/${tumeur.dfsMonths}</td>
				</tr>
		</table>

		<h2>Prélèvements :</h2>



		<table class="table table-bordered">
			<thead>
				<tr class="info">
					<th>#ID prélèvement</th>
					<th>Date</th>
					<th>Phase</th>
					<th>Type</th>
					<th>Site</th>
					<th>Morpho</th>
					<th>Histologie</th>
					<th>CIS</th>
					<th>Métastases</th>
					<th>Nodules</th>
				</tr>
			</thead>

			<c:forEach var="prel" items="${listPrelevements}">
				<tr>
					<td>${prel.idPrelevement}</td>
					<td>${prel.datePrelevement}</td>
					<td>${prel.chuPhaseTumeur.chuTypePhase.nom}</td>
					<td>${prel.chuTypePrelevement.nom}</td>
					<td>${prel.sitePrelevement}</td>
					<td><abbr title="${prel.chuMorphologie.nomEn}">${prel.chuMorphologie.idMorphologie}</abbr></td>
					<td>${prel.typeHistologique}</td>
					<td>${prel.associationCis}</td>
					<td>${prel.chuPhaseTumeur.metastases}</td>
					<td>${prel.chuPhaseTumeur.nodules}</td>
				</tr>
			</c:forEach>
		</table>




	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>