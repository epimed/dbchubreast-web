<c:if test="${not empty listTraitements }">

	<table class="table table-bordered">
		<thead>
			<tr class="info">
				<th>#ID traitement</th>
				<th>#ID tumeur</th>
				<th>Phase</th>
				<th>Date</th>
				<th>Méthode</th>
				<th>Actions</th>
			</tr>
		</thead>

		<c:forEach var="traitement" items="${listTraitements}">
			<tr>
				<td><a
					href="${pageContext.request.contextPath}/traitement/${traitement.idTraitement}">${traitement.idTraitement}</a></td>
				<td><a
					href="${pageContext.request.contextPath}/tumeur/${traitement.chuPhaseTumeur.chuTumeur.idTumeur}">
						${traitement.chuPhaseTumeur.chuTumeur.idTumeur}</a></td>
				<td>${traitement.chuPhaseTumeur.chuTypePhase.nom}</td>		
				<td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${traitement.dateDebut}"/></td>
				<td>${traitement.chuMethodeTraitement.nom}</td>
				<td>
				
				<spring:url
						value="/traitement/${traitement.idTraitement}" var="showUrl" />
					<button class="btn-sm btn-info"
						onclick="location.href='${showUrl}'">Consulter</button>
				
				<spring:url
						value="/traitement/${traitement.idTraitement}/update" var="updateUrl" />
					<button class="btn-sm btn-primary"
						onclick="location.href='${updateUrl}'">Modifier</button>
						
				<spring:url
						value="/traitement/${traitement.idTraitement}/delete" var="deleteUrl" />
					<button class="btn-sm btn-danger"
						onclick="location.href='${deleteUrl}'">Supprimer</button>
								
				</td>
			</tr>
		</c:forEach>
	</table>

</c:if>
