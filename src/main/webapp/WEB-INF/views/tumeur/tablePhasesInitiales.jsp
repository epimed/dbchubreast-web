<c:if test="${not empty listPhasesInitiales }">

	<h3>Phase initiale:</h3>

	<table class="table table-bordered">
		<thead>
			<tr class="info">
				<th>ID phase initiale</th>
				<th>Date</th>
				<th>Actions</th>
			</tr>
		</thead>

		<c:forEach var="phase" items="${listPhasesInitiales}">
			<tr>
				<td>${phase.idPhase}</td>
				<td><fmt:formatDate pattern="dd/MM/yyyy" value="${phase.dateDiagnostic}" /></td>
				
				<td>
				
				<spring:url value="/tumeur/${tumeur.idTumeur}/update"
						var="updateUrl" />
					<button class="btn-sm btn-primary"
						onclick="location.href='${updateUrl}'">Modifier</button>
						
						
				<spring:url value="/patient/${patient.idPatient}/prelevement/add?idTumeur=${tumeur.idTumeur}&idPhase=${phase.idPhase}"
						var="addPrelUrl" />
					<button class="btn-sm btn-success"
						onclick="location.href='${addPrelUrl}'">Ajouter un pr�l�vement</button>
						
				<spring:url value="/patient/${patient.idPatient}/traitement/add?idTumeur=${tumeur.idTumeur}&idPhase=${phase.idPhase}"
						var="addTraitUrl" />
					<button class="btn-sm btn-success"
						onclick="location.href='${addTraitUrl}'">Ajouter un traitement</button>
								
						
				</td>
			</tr>
			<c:if test="${not empty phase.remarque}">
				<tr>
					<td colspan="3">${phase.remarque}</td>
				</tr>
			</c:if>
		</c:forEach>
	</table>

</c:if>
