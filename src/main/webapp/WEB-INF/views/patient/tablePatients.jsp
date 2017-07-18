<c:if test="${not empty listPatients }">

	<table class="table table-bordered">
		<thead>
			<tr class="info">
				<th>ID</th>
				<th>TK</th>
				<th>RCP</th>
				<th>Nom</th>
				<th>Prénom</th>
				<th>Date de naissance</th>
				<th>Actions</th>
			</tr>
		</thead>


		<c:forEach var="patient" items="${listPatients}">
			<tr>
				<td><a
					href="${pageContext.request.contextPath}/patient/${patient.idPatient}">${patient.idPatient}</a></td>
				<td>${patient.tk}</td>
				<td>${patient.rcp}</td>
				<td>${patient.nom}<c:if
						test="${ not empty patient.nomNaissance}">
						<br />
						<span class="text-info"><small>${patient.nomNaissance}</small></span>
					</c:if>
				</td>
				<td>${patient.prenom}</td>

				<td><fmt:formatDate pattern="dd/MM/yyyy"
						value="${patient.dateNaissance}" /></td>


				<td><spring:url value="/patient/${patient.idPatient}"
						var="showUrl" />
					<button class="btn-sm btn-info"
						onclick="location.href='${showUrl}'">Consulter</button> <spring:url
						value="/patient/${patient.idPatient}/update" var="updateUrl" />
					<button class="btn-sm btn-primary"
						onclick="location.href='${updateUrl}'">Modifier</button> <spring:url
						value="/patient/${patient.idPatient}/delete?view=${pageContext.request.servletPath}"
						var="deleteUrl" />
					<button class="btn-sm btn-danger"
						onclick="location.href='${deleteUrl}'">Supprimer</button></td>


			</tr>
		</c:forEach>

	</table>

</c:if>