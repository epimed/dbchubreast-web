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

			<c:set var="listPrelevements" value="${phase.chuPrelevements}" />

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
					value="/patient/${patient.idPatient}/prelevement/add?idTumeur=${tumeur.idTumeur}&idPhase=${phase.idPhase}"
					var="urlAdd" />
				<button class="btn-sm btn-success"
					onclick="location.href='${urlAdd}'">
					Ajouter un prélèvement à la tumeur ${status.count} en phase
					${phase.chuTypePhase.nom}
					<c:if test="${phaseStatus.index!=0}">${phaseStatus.index}</c:if>
				</button>
			</div>


			<p></p>

			<c:if test="${not empty listPrelevements}">

				<table class="table table-bordered">
					<thead>
						<tr class="info">
							<th>TK</th>
							<th>Date</th>
							<th>Type</th>
							<th>Morpho</th>
							<th>Actions</th>
						</tr>
					</thead>


					<c:forEach var="prel" items="${listPrelevements}">
						<tr>
							<td>${prel.tk}</td>
							<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${prel.datePrelevement}" /></td>
							<td>${prel.chuTypePrelevement.nom}</td>
							<td><abbr
								title="${prel.chuMorphologie.nomFr} / ${prel.chuMorphologie.nomEn}">${prel.chuMorphologie.idMorphologie}</abbr>
							<c:if test="${not empty prel.sitePrelevement}">
							<span class="text-info"><small></br> site : ${prel.sitePrelevement}
							</small></span>
							</c:if>
							
							
							</td>
							<td><spring:url value="/prelevement/${prel.idPrelevement}"
									var="selectUrl" />
								<button class="btn-sm btn-info"
									onclick="location.href='${selectUrl}'">Consulter</button> <spring:url
									value="/prelevement/${prel.idPrelevement}/update"
									var="updateUrl" />
								<button class="btn-sm btn-primary"
									onclick="location.href='${updateUrl}'">Modifier</button>
									
									<spring:url
						value="/prelevement/${prel.idPrelevement}/delete"
						var="deleteUrl" />
					<button class="btn-sm btn-danger"
						onclick="location.href='${deleteUrl}'">Supprimer</button>
									
									</td>
						</tr>
						<tr>
							<td colspan="6" bgcolor="#F2F2F2"><c:forEach var="prelBio"
									items="${prel.chuPrelevementBiomarqueurs}">
									<c:if
										test="${prelBio.chuPrelevement.idPrelevement == prel.idPrelevement }">
								${prelBio.chuBiomarqueur.nom}=<c:out value="${prelBio.valeur}" />
										<c:if test="${ not empty prelBio.statut}">(${prelBio.statut})</c:if> &nbsp;
						</c:if>
								</c:forEach></td>
						</tr>
					</c:forEach>


				</table>


			</c:if>

		</c:forEach>


		<!--  -->

	</c:forEach>

</c:if>
