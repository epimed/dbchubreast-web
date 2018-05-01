<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>

	<!-- Container -->
	<div class="container">

		<!-- Fil d'Ariane -->
		<%@ include file="../inc/filAriane.jsp"%>

		<c:if test="${not empty msg}">
			<p></p>
			<div class="alert alert-${css} alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>${msg}</strong>
			</div>
		</c:if>


		<div>

			<!-- H1 Patient -->
			<%@ include file="../inc/h1Patient.jsp"%>

			<h2>Souhaitez-vous supprimer définitivement la rechute suivante
				?</h2>

			<p class="text-danger">Attention, la suppression de cette rechute
				va supprimer également les prélèvenemnts et les traitements
				correspondants s'ils existent dans la base de données.</p>

			<!-- Phase ID -->
			<div class="row">
				<label class="col-sm-2">ID phase rechute</label>
				<div class="col-sm-10">${phase.idPhase}</div>
			</div>

			<!-- Date -->
			<div class="row">
				<label class="col-sm-2">Date de rechute</label>
				<div class="col-sm-10">
					<fmt:formatDate pattern="dd/MM/yyyy"
						value="${phase.dateDiagnostic}" />
				</div>
			</div>


			<!-- Affichage des prelevements -->
			<c:if test="${not empty listPrelevements}">
				<h3>Prélèvements:</h3>
				<table class="table table-bordered">
					<thead>
						<tr class="info">
							<th>Date</th>
							<th>Type</th>
							<th>Morpho</th>
						</tr>
					</thead>
					<c:forEach var="prel" items="${listPrelevements}">
						<tr>
							<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${prel.datePrelevement}" /></td>
							<td>${prel.chuTypePrelevement.nom}</td>
							<td><abbr
								title="${prel.chuMorphologie.nomFr} / ${prel.chuMorphologie.nomEn}">${prel.chuMorphologie.idMorphologie}</abbr>
								<c:if test="${not empty prel.sitePrelevement}">
									<span class="text-info"><small><br /> site :
											${prel.sitePrelevement} </small></span>
								</c:if></td>
						</tr>
					</c:forEach>
				</table>
			</c:if>

			<!-- Affichage des traitements -->
			<c:if test="${not empty listTraitements}">
				<h3>Traitements:</h3>

				<table class="table table-bordered">
					<thead>
						<tr class="info">
							<th>Date</th>
							<th>Méthode</th>
							<th>Protocole</th>

						</tr>
					</thead>

					<c:forEach var="traitement" items="${listTraitements}">

						<tr>
							<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${traitement.dateDebut}" /></td>
							<td>${traitement.chuMethodeTraitement.nom}</td>
							<td>${traitement.chuProtocoleTraitement.nom}</td>

						</tr>

					</c:forEach>
				</table>
			</c:if>


			<form:form class="form-horizontal" method="post"
				modelAttribute="formTumeur"
				action="${pageContext.request.contextPath}/rechute/${phase.idPhase}/delete">

				<!-- Buttons -->

				<button type="submit" class="btn btn-danger" name="button"
					value="delete">Supprimer</button>

				<button type="submit" class="btn btn-default" name="button"
					value="cancel">Annuler</button>


			</form:form>

		</div>
	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>