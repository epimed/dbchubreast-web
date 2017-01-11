<c:if test="${not empty listPatients }">

	<table class="table table-striped">
		<thead>
			<tr>
				<th>#ID</th>
				<th>Nom</th>
				<th>Pr�nom</th>
				<th>RCP</th>
				<th>Sexe</th>
				<th>Date de naissance</th>
				<th>Date de d�c�s</th>
				<th>Statut BRCA</th>
				<th>Actions</th>
			</tr>
		</thead>


		<c:forEach var="patient" items="${listPatients}">
			<tr>
				<td><a
					href="${pageContext.request.contextPath}/patient/${patient.idPatient}">${patient.idPatient}</a></td>
				<td>${patient.nom}</td>
				<td>${patient.prenom}</td>
				<td>${patient.rcp}</td>
				<td>${patient.sexe}</td>
				<td>${patient.dateNaissance}</td>
				<td>${patient.dateDeces}</td>
				<td>${patient.statutBrca}</td>

				<td><spring:url value="/patient/${patient.idPatient}/update"
						var="updateUrl" />
					<button class="btn btn-primary"
						onclick="location.href='${updateUrl}'">Modifier</button></td>
			</tr>
		</c:forEach>

	</table>

</c:if>