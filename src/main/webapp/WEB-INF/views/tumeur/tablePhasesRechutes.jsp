<c:if test="${not empty listPhasesRechutes}">

	<h3>Rechutes:</h3>

	<table class="table table-bordered">
		<thead>
			<tr class="danger">
				<th>ID phase rechute</th>
				<th>Date</th>
				<th>Topographie ICD-O</th>
				<th>Locale</th>
				<th>Métastases</th>
				<th>Actions</th>

			</tr>
		</thead>

		<c:forEach var="phase" items="${listPhasesRechutes}">
			<tr>
				<td>${phase.idPhase}</td>
				<td><fmt:formatDate pattern="dd/MM/yyyy" value="${phase.dateDiagnostic}" /></td>
				
				<td>${phase.chuTopographie.idTopographie} 
					<span class="text-info"><small></br>${phase.chuTopographie.nomFr}</small></span>
				</td>
				
				<td>
					<c:if test="${phase.locale}">oui</c:if>
					<c:if test="${not phase.locale}">non</c:if>
				</td>
				
				
				<td><c:forEach var="metastase" items="${phase.chuMetastases}"
						varStatus="loop">${metastase.nom}<c:if
							test="${not loop.last}">, </c:if>
					</c:forEach></td>
				
				<td>
				
				<spring:url value="/rechute/${phase.idPhase}/update"
						var="updateUrl" />
					<button class="btn-sm btn-primary"
						onclick="location.href='${updateUrl}'">Modifier</button>
						
				
				<spring:url value="/patient/${patient.idPatient}/prelevement/add?idTumeur=${tumeur.idTumeur}&idPhase=${phase.idPhase}"
						var="addPrelUrl" />
					<button class="btn-sm btn-success"
						onclick="location.href='${addPrelUrl}'">Ajouter un prélèvement</button>
						
				<spring:url value="/patient/${patient.idPatient}/traitement/add?idTumeur=${tumeur.idTumeur}&idPhase=${phase.idPhase}"
						var="addTraitUrl" />
					<button class="btn-sm btn-success"
						onclick="location.href='${addTraitUrl}'">Ajouter un traitement</button>
						
						
				</td>
			</tr>
			<c:if test="${not empty phase.remarque}">
				<tr>
					<td colspan="6">${phase.remarque}</td>
				</tr>
			</c:if>
		</c:forEach>
	</table>

</c:if>
