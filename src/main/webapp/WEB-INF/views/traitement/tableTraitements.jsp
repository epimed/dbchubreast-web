<c:if test="${not empty listTumeurs}">

	<c:forEach var="tumeur" items="${listTumeurs}" varStatus="status">

		<c:set var="listPhases" value="${tumeur.chuPhaseTumeurs}" />

		<h3>
			Tumeur ${status.count} <small>diagnostic <c:if
					test="${not empty tumeur.dateDiagnostic}">du <fmt:formatDate
						pattern="dd/MM/yyyy" value="${tumeur.dateDiagnostic}" />
				</c:if> <c:if test="${not empty tumeur.ageDiagnostic}"> à l'age de ${tumeur.ageDiagnostic} ans</c:if>
				<c:if test="${not empty tumeur.cote}">, côté ${tumeur.cote}</c:if>
			</small>
		</h3>

		<c:if
			test="${not empty tumeur.tripleNegative and tumeur.tripleNegative}">
			<p class="text-danger">
				<em>Triple negative</em>
			</p>
		</c:if>

		<c:forEach var="phase" items="${listPhases}" varStatus="phaseStatus">

			<c:set var="listTraitements" value="${phase.chuTraitements}" />


			<h4>
				Phase ${phase.chuTypePhase.nom}
				<c:if test="${phaseStatus.index!=0}">${phaseStatus.index}
				<small><c:if test="${not empty phase.dateDiagnostic}">diagnostic du <fmt:formatDate
								pattern="dd/MM/yyyy" value="${phase.dateDiagnostic}" />
						</c:if></small>
				</c:if>
			</h4>

			<div>
				<spring:url
					value="/patient/${patient.idPatient}/traitement/add?idTumeur=${tumeur.idTumeur}&idPhase=${phase.idPhase}"
					var="urlAdd" />
				<button class="btn-sm btn-success"
					onclick="location.href='${urlAdd}'">
					Ajouter un traitement à la tumeur ${status.count} en phase
					${phase.chuTypePhase.nom}
					<c:if test="${phaseStatus.index!=0}">${phaseStatus.index}</c:if>
				</button>
			</div>

			<p></p>

			<c:if test="${not empty listTraitements}">

				<table class="table table-bordered">
					<thead>
						<tr class="info">
							<th>Date</th>
							<th>Méthode</th>
							<th>Protocole</th>
							<th>Actions</th>
						</tr>
					</thead>

					<c:forEach var="traitement" items="${listTraitements}">

						<tr>
							<td>
								<fmt:formatDate pattern="dd/MM/yyyy" value="${traitement.dateDebut}" />
								<c:if test="${not empty traitement.dateFin}">
								<span class="text-info"><small><br/>terminé le <fmt:formatDate pattern="dd/MM/yyyy" value="${traitement.dateFin}" /></small></span>
								</c:if>
							</td>
							<td>${traitement.chuMethodeTraitement.nom}<span
								class="text-info"><small> <c:if
											test="${not empty traitement.chuTypeTraitement}">

											<br/> traitement
											${traitement.chuTypeTraitement.nom} 
								</c:if> <c:if test="${not empty traitement.chuReponse}">
											<br/>
									réponse : ${traitement.chuReponse.nom}
								</c:if>
								</small> </span>


							</td>

							<td>${traitement.chuProtocoleTraitement.nom}<c:if
									test="${not empty traitement.chuProtocoleTraitement.chuComposantTraitements}">
									<br />
									<c:forEach var="composant"
										items="${traitement.chuProtocoleTraitement.chuComposantTraitements}"
										varStatus="loop">
										<span class="text-info"><small>${composant.nomInternational}
												${composant.nomCommercial}<c:if test="${!loop.last}">, </c:if>
										</small></span>
									</c:forEach>
								</c:if>
							</td>

							<td><spring:url
									value="/traitement/${traitement.idTraitement}" var="showUrl" />
								<button class="btn-sm btn-info"
									onclick="location.href='${showUrl}'">Consulter</button> <spring:url
									value="/traitement/${traitement.idTraitement}/update"
									var="updateUrl" />
								<button class="btn-sm btn-primary"
									onclick="location.href='${updateUrl}'">Modifier</button> <spring:url
									value="/traitement/${traitement.idTraitement}/delete"
									var="deleteUrl" />
								<button class="btn-sm btn-danger"
									onclick="location.href='${deleteUrl}'">Supprimer</button></td>

						</tr>

					</c:forEach>

				</table>

			</c:if>

		</c:forEach>

	</c:forEach>
</c:if>
