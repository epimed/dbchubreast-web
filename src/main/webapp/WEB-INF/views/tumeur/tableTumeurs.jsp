
<c:if test="${not empty listTumeurs }">

	<table class="table table-bordered">
		<thead>
			<tr class="info">
				<th>Date du diagnostic</th>
				<th>Age</th>
				<th>Côté</th>
				<th>Dernière nouvelle</th>
				<th>Actions</th>

			</tr>
		</thead>

		<c:forEach var="tumeur" items="${listTumeurs}">
			<tr>
				<td><fmt:formatDate pattern="dd/MM/yyyy"
						value="${tumeur.dateDiagnostic}" /> <c:if
						test="${not empty tumeur.tripleNegative and tumeur.tripleNegative}">

						<span class="text-danger"><small></br> triple negative</small></span>

					</c:if> <c:if test="${not empty tumeur.chuPhaseTumeurs}">
						<c:forEach var="phase" items="${tumeur.chuPhaseTumeurs}">
							<c:if
								test="${not empty phase.chuTypePhase and phase.chuTypePhase.idTypePhase==2}">
								<span class="text-info"><small></br>
										${phase.chuTypePhase.nom} le <fmt:formatDate
											pattern="dd/MM/yyyy" value="${phase.dateDiagnostic}" /> </small></span>
							</c:if>
						</c:forEach>
					</c:if></td>
				<td>${tumeur.ageDiagnostic}</td>
				<td>${tumeur.cote}</td>
				<td><fmt:formatDate pattern="dd/MM/yyyy"
						value="${patient.dateEvolution}" /> <c:if
						test="${not empty tumeur.chuEvolution}">
						<span class="text-info"><small><br/>${tumeur.chuEvolution.nom}
						</small></span>
					</c:if></td>

				<td><spring:url value="/tumeur/${tumeur.idTumeur}"
						var="showUrl" />
					<button class="btn-sm btn-info"
						onclick="location.href='${showUrl}'">Consulter</button> <spring:url
						value="/tumeur/${tumeur.idTumeur}/update" var="updateUrl" />
					<button class="btn-sm btn-primary"
						onclick="location.href='${updateUrl}'">Modifier</button> <spring:url
						value="/tumeur/${tumeur.idTumeur}/rechute/add" var="url" />
					<button class="btn-sm btn-warning" onclick="location.href='${url}'">Ajouter
						une rechute</button></td>

			</tr>
		</c:forEach>
	</table>

</c:if>
