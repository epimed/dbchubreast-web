
<c:if test="${not empty listTumeurs }">

	<table class="table table-bordered">
		<thead>
			<tr class="success">
				<th>#ID tumeur</th>
				<th>#ID patient</th>
				<th>Date</th>
				<th>Age</th>
				<th>Topo</th>
				<th>Côté</th>
				<th>Dernière nouvelle</th>
				<th>Statut</th>
				<th>TN</th>
				<th>Actions</th>

			</tr>
		</thead>

		<c:forEach var="tumeur" items="${listTumeurs}">
			<tr>
				<td><a href="<spring:url value="/tumeur/${tumeur.idTumeur}"/>">
						${tumeur.idTumeur}</a></td>
				<td><a href="<spring:url value="/patient/${tumeur.chuPatient.idPatient}"/>">
						${tumeur.chuPatient.idPatient}</a></td>
				<td>${tumeur.dateDiagnostic}</td>
				<td>${tumeur.ageDiagnostic}</td>
				<td><abbr title="${tumeur.chuTopographie.nomFr}">${tumeur.chuTopographie.idTopographie}</abbr></td>
				<td>${tumeur.cote}</td>
				<td>${tumeur.dateEvolution}</td>
				<td><abbr title="${tumeur.chuEvolution.nom}">${tumeur.chuEvolution.code}</abbr></td>
				<td class="warning">${tumeur.tripleNegative}</td>

				<td><spring:url value="/tumeur/${tumeur.idTumeur}/update"
						var="updateUrl" />
					<button class="btn btn-primary"
						onclick="location.href='${updateUrl}'">Modifier</button></td>

			</tr>
		</c:forEach>
	</table>

</c:if>
