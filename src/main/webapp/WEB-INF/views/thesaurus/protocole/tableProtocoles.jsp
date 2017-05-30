<c:if test="${not empty listMethodes }">

	<c:forEach var="methode" items="${listMethodes}">

		<h2>${methode.nom}</h2>


		<!-- Bouton ajouter -->
		<div>
			<spring:url
				value="/thesaurus/methode/${methode.idMethode}/protocoles/add"
				var="url" />
			<button class="btn btn-success" onclick="location.href='${url}'">Ajouter
				un protocole ${methode.nom}</button>
		</div>

		<p></p>

		<table class="table table-bordered">
			<thead>
				<tr class="info">
					<th>#ID</th>
					<th>Code</th>
					<th>Nom</th>
					<th>Actions</th>
				</tr>
			</thead>


			<c:forEach var="protocole" items="${methode.chuProtocoleTraitements}">
				<tr>
					<td>${protocole.idProtocole}</td>
					<td>${protocole.code}</td>
					<td>${protocole.nom}<c:if
							test="${not empty protocole.chuComposantTraitements}">
							<br />
							<c:forEach var="composant"
								items="${protocole.chuComposantTraitements}" varStatus="loop">
								<span class="text-info"><small>${composant.nomInternational} ${composant.nomCommercial}<c:if
										test="${!loop.last}">, </c:if></small></span>
							</c:forEach>
						</c:if>
					</td>

					<td><spring:url
							value="/thesaurus/protocole/${protocole.idProtocole}/update"
							var="updateUrl" />
						<button class="btn-sm btn-primary"
							onclick="location.href='${updateUrl}'">Modifier</button>
							<spring:url
							value="/thesaurus/protocole/${protocole.idProtocole}/delete"
							var="deleteUrl" />
						<button class="btn-sm btn-danger"
							onclick="location.href='${deleteUrl}'">Supprimer</button>
							</td>
				</tr>
			</c:forEach>

		</table>

		<p></p>

	</c:forEach>

</c:if>
