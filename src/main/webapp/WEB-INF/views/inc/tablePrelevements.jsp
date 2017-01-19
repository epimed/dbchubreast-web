<c:if test="${not empty listPrelevements }">

	<table class="table table-bordered">
		<thead>
			<tr class="info">
				<th>#ID prel.</th>
				<th>#ID tumeur</th>
				<th>Type</th>
				<th>Date</th>
				<th>Site</th>
				<th>Morpho</th>
				<th>Histologie</th>
				<th>CIS</th>
				<th>Actions</th>
			</tr>
		</thead>

		<c:forEach var="prel" items="${listPrelevements}">
			<tr>
				<td><a
					href="${pageContext.request.contextPath}/prelevement/${prel.idPrelevement}">${prel.idPrelevement}</a></td>
				<td><a
					href="${pageContext.request.contextPath}/tumeur/${prel.chuPhaseTumeur.chuTumeur.idTumeur}">
						${prel.chuPhaseTumeur.chuTumeur.idTumeur}</a></td>
				<td>${prel.chuTypePrelevement.nom}</td>
				<td>${prel.datePrelevement}</td>
				<td>${prel.sitePrelevement}</td>
				<td><abbr title="${prel.chuMorphologie.nomEn}">${prel.chuMorphologie.idMorphologie}</abbr></td>
				<td>${prel.typeHistologique}</td>
				<td>${prel.associationCis}</td>
				<td><spring:url
						value="/prelevement/${prel.idPrelevement}/update" var="updateUrl" />
					<button class="btn btn-primary"
						onclick="location.href='${updateUrl}'">Modifier</button></td>
			</tr>
			<tr>
				<td colspan="9"><c:forEach var="prelBio"
						items="${listPrelevementBiomarqueurs}">
						<c:if
							test="${prelBio.chuPrelevement.idPrelevement == prel.idPrelevement }">
								${prelBio.chuBiomarqueur.nom}=${prelBio.valeur} <c:if test="${ not empty prelBio.statut}">(${prelBio.statut})</c:if> &nbsp;
						</c:if>
					</c:forEach></td>
			</tr>
		</c:forEach>
	</table>

</c:if>
