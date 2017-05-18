<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>EpiMed Database - BD "Cancer du sein"</title>

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

			<h2>Souhaitez-vous supprimer définitivement le traitement
				suivant ?</h2>

			<br />

			<!-- Traitement ID -->
			<div class="row">
				<label class="col-sm-2">ID traitement</label>
				<div class="col-sm-10">${traitement.idTraitement}</div>
			</div>

			<!-- Tumeur ID -->
			<div class="row">
				<label class="col-sm-2">ID tumeur</label>

				<div class="col-sm-10">${traitement.chuPhaseTumeur.chuTumeur.idTumeur}</div>
			</div>

			<!-- Phase tumeur -->
			<div class="row">
				<label class="col-sm-2">Phase</label>
				<div class="col-sm-10">${traitement.chuPhaseTumeur.chuTypePhase.nom}</div>
			</div>

			<!-- Methode -->
			<div class="row">
				<label class="col-sm-2">Methode</label>
				<div class="col-sm-10">${traitement.chuMethodeTraitement.nom}
					<c:if test="${not empty traitement.chuTypeTraitement}">
						(traitement ${traitement.chuTypeTraitement.nom})
					</c:if>
				</div>
			</div>

			<!-- Protocole -->
			<div class="row">
				<label class="col-sm-2">Protocole</label>
				<div class="col-sm-10">${traitement.chuProtocoleTraitement.nom}</div>
			</div>


			<!-- Date de debut -->
			<div class="row">
				<label class="col-sm-2">Date de début</label>
				<div class="col-sm-10">
					<fmt:formatDate pattern="dd/MM/yyyy"
						value="${traitement.dateDebut}" />
				</div>
			</div>

			<!-- Date de fin -->
			<div class="row">
				<label class="col-sm-2">Date de fin</label>
				<div class="col-sm-10">
					<fmt:formatDate pattern="dd/MM/yyyy" value="${traitement.dateFin}" />
				</div>
			</div>

			<!-- Nb cures -->
			<c:if test="${not empty traitement.nbCures}">
				<div class="row">
					<label class="col-sm-2">Nombre de cures</label>
					<div class="col-sm-10">${traitement.nbCures}</div>
				</div>
			</c:if>


			<!-- Dose -->
			<c:if test="${not empty traitement.dose}">
				<div class="row">
					<label class="col-sm-2">Dose</label>
					<div class="col-sm-10">${traitement.dose}</div>
				</div>

			</c:if>


			<!-- GG sentinelle -->
			<c:if test="${not empty traitement.ggSentinelle}">
				<div class="row">
					<label class="col-sm-2">GG sentinelle</label>
					<div class="col-sm-10">${traitement.ggSentinelle}</div>
				</div>

			</c:if>

			<!-- Reponse -->
			<c:if test="${not empty traitement.chuReponse}">
				<div class="row">
					<label class="col-sm-2">Réponse</label>
					<div class="col-sm-10">${traitement.chuReponse.nom}</div>
				</div>

			</c:if>

			<!-- Remarque -->
			<c:if test="${not empty traitement.remarque}">
				<div class="row">
					<label class="col-sm-2">Remarque</label>
					<div class="col-sm-10">${traitement.remarque}</div>
				</div>

			</c:if>

			<p></p>

			<form:form class="form-horizontal" method="post"
				modelAttribute="formTraitement"
				action="${pageContext.request.contextPath}/traitement/${traitement.idTraitement}/delete">

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