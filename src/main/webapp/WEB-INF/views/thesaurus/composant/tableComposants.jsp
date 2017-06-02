<c:if test="${not empty listMethodes }">

	<c:forEach var="methode" items="${listMethodes}">

		<h2>${methode.nom}</h2>


		<!-- Bouton ajouter -->
		<div>
			<spring:url
				value="/thesaurus/methode/${methode.idMethode}/composants/add"
				var="url" />
			<button class="btn btn-success" onclick="location.href='${url}'">Ajouter
				un composant ${methode.nom}</button>
		</div>

		<p></p>

		<c:if test="${not empty methode.chuComposantTraitements }">

			<table class="table table-bordered">
				<thead>
					<tr class="info">
						<th>Libellé</th>
						<th>Nom alternatif ou commercial</th>
						<th>Classe</th>
						<th>Actions</th>
					</tr>
				</thead>


				<c:forEach var="composant"
					items="${methode.chuComposantTraitements}">
					<tr>
						<td>${composant.nomInternational}</td>
						<td>${composant.nomCommercial}</td>
						<td>${composant.classe}<c:if
								test="${not empty composant.action}">
								<br />
								<span class="text-info"><small>${composant.action}</small></span>
							</c:if>
						</td>

						<td>
							<div>
								<spring:url
									value="/thesaurus/composant/${composant.idComposant}/update"
									var="updateUrl" />
								<button class="btn-sm btn-primary"
									onclick="location.href='${updateUrl}'">Modifier</button>
								<spring:url
									value="/thesaurus/composant/${composant.idComposant}/delete"
									var="deleteUrl" />
								<button class="btn-sm btn-danger"
									onclick="location.href='${deleteUrl}'">Supprimer</button>
							</div>
						</td>
					</tr>
				</c:forEach>

			</table>
		</c:if>
		<p></p>

	</c:forEach>

</c:if>
