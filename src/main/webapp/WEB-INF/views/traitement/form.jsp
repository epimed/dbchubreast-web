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

		<!-- H1 Patient -->
		<%@ include file="../inc/h1Patient.jsp"%>

		<c:choose>
			<c:when test="${formTraitement['new']}">
				<h2>Ajouter un traitement</h2>
			</c:when>
			<c:otherwise>
				<h2>Modifier un traitement</h2>
			</c:otherwise>
		</c:choose>

		<form:form class="form-horizontal" method="post"
			modelAttribute="formTraitement"
			action="${pageContext.request.contextPath}/traitement/update">

			<!-- Hidden attributes -->

			<form:hidden path="idTraitement" />
			<form:hidden path="idPatient" />


			<!-- Tumeur -->
			<spring:bind path="idTumeur">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Tumeur *</label>
					<div class="col-sm-10">
						<form:select class="form-control" path="idTumeur"
							onchange="this.form.submit()">
							<form:option value="" label="--- Sélectionner ---" />
							<c:forEach var="tumeur" items="${listTumeurs}">
								<form:option value="${tumeur.idTumeur}"
									label="[ID ${tumeur.idTumeur}] ${tumeur.dateDiagnostic} - ${tumeur.cote}" />
							</c:forEach>
						</form:select>
						<form:errors path="idTumeur" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<!-- Phase -->
			<spring:bind path="idPhase">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Phase de la tumeur *</label>
					<div class="col-sm-10">
						<form:select class="form-control" path="idPhase">
							<form:option value="" label="--- Sélectionner ---" />
							<c:forEach var="phase" items="${listPhases}">
								<form:option value="${phase.idPhase}"
									label="[ID ${phase.idPhase}] ${phase.dateDiagnostic} - ${phase.chuTypePhase.nom}" />
							</c:forEach>
						</form:select>
						<form:errors path="idPhase" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<!-- Methode -->
			<spring:bind path="idMethode">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Methode *</label>
					<div class="col-sm-10">
						<form:select class="form-control" path="idMethode"
							onchange="this.form.submit()">
							<form:option value="" label="--- Sélectionner ---" />
							<c:forEach var="methode" items="${listMethodes}">
								<form:option value="${methode.idMethode}" label="${methode.nom}" />
							</c:forEach>
						</form:select>
						<form:errors path="idMethode" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<!-- Protocole -->
			<spring:bind path="idProtocole">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Protocole *</label>
					<div class="col-sm-10">
						<form:select class="form-control" path="idProtocole">
							<form:option value="" label="--- Sélectionner ---" />
							<c:forEach var="protocole" items="${listProtocoles}">
								<form:option value="${protocole.idProtocole}"
									label="${protocole.nom} - ${protocole.code}" />
							</c:forEach>
						</form:select>
						<form:errors path="idProtocole" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<!-- Date de debut -->
			<spring:bind path="dateDebut">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Date <c:if
							test="${not empty formTraitement.idMethode and  formTraitement.idMethode!=1}">de début</c:if>
						*
					</label>
					<div class="col-sm-10">
						<form:input class="form-control" path="dateDebut" type="date" />
						<form:errors path="dateDebut" class="control-label" />
					</div>
				</div>
			</spring:bind>


			<!-- methode != chururgie -->
			<c:if
				test="${not empty formTraitement.idMethode and  formTraitement.idMethode!=1}">

				<!-- Date de fin -->
				<spring:bind path="dateFin">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-2 control-label">Date de fin</label>
						<div class="col-sm-10">
							<form:input class="form-control" path="dateFin" type="date" />
							<form:errors path="dateFin" class="control-label" />
						</div>
					</div>
				</spring:bind>

				<!-- Nombre de cures -->
				<div class="form-group">
					<label class="col-sm-2 control-label">Nombre de cures</label>
					<div class="col-sm-10">
						<form:input class="form-control" path="nbCures" type="text" />
						<form:errors path="nbCures" />
					</div>
				</div>

			</c:if>


			<!-- methode = chirurgie  -->

			<c:if
				test="${not empty formTraitement.idMethode and  formTraitement.idMethode==1}">

				<!-- GG sentinelle -->
				<div class="form-group">
					<label class="col-sm-2 control-label">GG sentinelle</label>
					<div class="col-sm-10">
						<label class="radio-inline"> <form:radiobutton
								path="ggSentinelle" value="true" /> oui
						</label> <label class="radio-inline"> <form:radiobutton
								path="ggSentinelle" value="false" /> non
						</label>
						<form:errors path="ggSentinelle" />
					</div>
				</div>

			</c:if>


			<!-- Reponse -->
			<spring:bind path="idReponse">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Réponse au traitement</label>
					<div class="col-sm-10">
						<form:select class="form-control" path="idReponse">
							<form:option value="" label="--- Sélectionner ---" />
							<c:forEach var="reponse" items="${listReponses}">
								<form:option value="${reponse.idReponse}" label="${reponse.nom}" />
							</c:forEach>
						</form:select>
						<form:errors path="idReponse" class="control-label" />
					</div>
				</div>
			</spring:bind>


			<!-- Remarque -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Remarque</label>
				<div class="col-sm-10">
					<form:input class="form-control" path="remarque" type="text" />
					<form:errors path="remarque" />
				</div>
			</div>

			<!-- Buttons -->
			<%@ include file="../inc/boutonsFormulaire.jsp"%>

		</form:form>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>