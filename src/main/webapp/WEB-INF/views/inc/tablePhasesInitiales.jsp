<c:if test="${not empty listPhasesInitiales }">

	<h3>Phase initiale:</h3>

	<table class="table table-bordered">
		<thead>
			<tr class="info">
				<th>#ID phase initiale</th>
				<th>Date</th>
				<th>Nature de diagnostic</th>
				<th>Profondeur</th>
				<th>Métastases</th>
				<th>Nodules</th>
				<th>Actions</th>
			</tr>
		</thead>

		<c:forEach var="phase" items="${listPhasesInitiales}">
			<tr>
				<td>${phase.idPhase}</td>
				<td>${phase.dateDiagnostic}</td>
				<td>${phase.natureDiagnostic}</td>
				<td>${phase.profondeur}</td>
				<td><c:forEach var="metastase" items="${phase.chuMetastases}" varStatus="loop">${metastase.nom}<c:if test="${not loop.last}">, </c:if></c:forEach></td>
				<td class="warning">${phase.nodules}</td>
				<td>
				<spring:url value="/tumeur/${tumeur.idTumeur}/update"
						var="updateUrl" />
					<button class="btn btn-primary"
						onclick="location.href='${updateUrl}'">Modifier</button></td>
			</tr>
			<c:if test="${not empty phase.remarque}">
				<tr>
					<td colspan="7">${phase.remarque}</td>
				</tr>
			</c:if>
		</c:forEach>
	</table>

</c:if>
