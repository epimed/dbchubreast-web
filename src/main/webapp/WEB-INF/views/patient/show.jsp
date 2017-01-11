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
			<h1>Patient ${patient.idPatient}</h1>
			<p class="lead">${patient.nom} &nbsp; ${patient.prenom} &nbsp; ${patient.dateNaissance}</p>
		</div>


		<h2>Tumeurs :</h2>



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

			<c:forEach var="tumeur" items="${listTumeurs}">
				<tr>
					<td><a href="<spring:url value="/tumeur/${tumeur.idTumeur}"/>">
							${tumeur.idTumeur}</a></td>
					<td>${tumeur.dateDiagnostic}</td>
					<td>${tumeur.ageDiagnostic}</td>
					<td><abbr title="${tumeur.chuTopographie.nomFr}">${tumeur.chuTopographie.idTopographie}</abbr></td>
					<td>${tumeur.cote}</td>
					<td>${tumeur.dateEvolution}</td>
					<td><abbr title="${tumeur.chuEvolution.nom}">${tumeur.chuEvolution.code}</abbr></td>
					<td class="warning">${tumeur.tripleNegative}</td>
					<td class="warning">${tumeur.osMonths}/${tumeur.dfsMonths}</td>
				</tr>
			</c:forEach>
		</table>




	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>