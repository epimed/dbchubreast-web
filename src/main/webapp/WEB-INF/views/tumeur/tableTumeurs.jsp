
<c:if test="${not empty listTumeurs }">

	<table class="table table-bordered">
		<thead>
			<tr class="info">
				<th>ID tumeur</th>
				<th>ID patient</th>
				<th>Date</th>
				<th>Age</th>
				<th>Topo</th>
				<th>Côté</th>
				<th>Dernière nouvelle</th>
				<th>Actions</th>

			</tr>
		</thead>

		<c:forEach var="tumeur" items="${listTumeurs}">
			<tr>
				<td><a href="<spring:url value="/tumeur/${tumeur.idTumeur}"/>">
						${tumeur.idTumeur}</a></td>
				<td><a href="<spring:url value="/patient/${tumeur.chuPatient.idPatient}"/>">
						${tumeur.chuPatient.idPatient}</a></td>
				<td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${tumeur.dateDiagnostic}"/></td>
				<td>${tumeur.ageDiagnostic}</td>
				<td><abbr title="${tumeur.chuTopographie.nomFr}">${tumeur.chuTopographie.idTopographie}</abbr></td>
				<td>${tumeur.cote}</td>
				<td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${tumeur.dateEvolution}"/></td>

				<td>
				
				<spring:url value="/tumeur/${tumeur.idTumeur}"
						var="showUrl" />
					<button class="btn-sm btn-info"
						onclick="location.href='${showUrl}'">Consulter</button>
				
				<spring:url value="/tumeur/${tumeur.idTumeur}/update"
						var="updateUrl" />
					<button class="btn-sm btn-primary"
						onclick="location.href='${updateUrl}'">Modifier</button></td>

			</tr>
		</c:forEach>
	</table>

</c:if>
