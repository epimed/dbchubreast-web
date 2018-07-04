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

		<c:choose>
			<c:when test="${formPatient['new']}">
				<h1>Ajouter un patient</h1>
			</c:when>
			<c:otherwise>
				<h1>Modifier un patient</h1>
			</c:otherwise>
		</c:choose>

		<form:form class="form-horizontal" method="post"
			modelAttribute="formPatient"
			action="${pageContext.request.contextPath}/patient/update">

			<form:hidden path="idPatient" />

			<!-- TK / RCP -->
			<div class="form-group">
			
				<label class="col-sm-2 control-label">Numéro RCP</label>
				<div class="col-sm-3">
					<form:input class="form-control" path="rcp" type="text" />
					<form:errors path="rcp" />
				</div>
				
				<label class="col-sm-2 control-label">Numéro TK</label>
				<div class="col-sm-3">
					<form:input class="form-control" path="tk" type="text" />
					<form:errors path="tk" />
				</div>
				
			</div>

			<!-- Prenom -->
			<spring:bind path="prenom">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Prénom *</label>
					<div class="col-sm-10">
						<form:input class="form-control" path="prenom" type="text" />
						<form:errors path="prenom" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<!-- Nom -->
			<spring:bind path="nom">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Nom *</label>
					<div class="col-sm-10">
						<form:input class="form-control" path="nom" type="text" />
						<form:errors path="nom" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<!-- Nom de naissance -->
			<spring:bind path="nomNaissance">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Nom de naissance</label>
					<div class="col-sm-10">
						<form:input class="form-control" path="nomNaissance" type="text" />
						<form:errors path="nomNaissance" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<!-- Sexe -->
			<spring:bind path="sexe">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Sexe *</label>
					<div class="col-sm-10">
						<label class="radio-inline"> <form:radiobutton path="sexe"
								value="F" />Femme
						</label> <label class="radio-inline"> <form:radiobutton
								path="sexe" value="M" />Homme
						</label> <br />
						<form:errors path="sexe" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<!-- Date de naissance -->
			<spring:bind path="dateNaissance">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Date de naissance *</label>
					<div class="col-sm-10">
						<form:input path="dateNaissance" type="date" class="form-control "
							id="dateNaissance" placeholder="Date de naissance" />
						<form:errors path="dateNaissance" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<!-- Date de deces -->
			<spring:bind path="dateDeces">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Date de décès</label>
					<div class="col-sm-10">
						<form:input path="dateDeces" type="date" class="form-control "
							id="dateDeces" placeholder="Date de décès" />
						<form:errors path="dateDeces" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<!-- Cause de deces -->
			<spring:bind path="idCauseDeces">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Cause de décès</label>
					<div class="col-sm-10">
						<form:select class="form-control" path="idCauseDeces">
							<form:option value="" label="--- Sélectionner ---" />
							<c:forEach var="causeDeces"
								items="${formPatient.listCausesDeces}">
								<form:option value="${causeDeces.idCauseDeces}"
									label="${causeDeces.nom}" />
							</c:forEach>
						</form:select>
						<form:errors path="idCauseDeces" class="control-label" />
					</div>
				</div>
			</spring:bind>


			<!-- Tumeurs - causes de deces -->
			<spring:bind path="listIdTumeursCausesDeces">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-4 control-label">Tumeur(s) de sein qui
						ont causé le décès</label>
					<div class="col-sm-8">
						<c:forEach var="tumeur" items="${formPatient.listTumeurs}"
							varStatus="loop">
							<div class="checkbox">
								<label> <form:checkbox path="listIdTumeursCausesDeces"
										value="${tumeur.idTumeur}" /> Tumeur ${loop.count} du
									<fmt:formatDate pattern="dd/MM/yyyy" value="${tumeur.dateDiagnostic}"/>
									<c:if test="${not empty tumeur.chuEvolution}"> [${tumeur.chuEvolution.nom}]</c:if>
								</label>
							</div>
						</c:forEach>
						<c:if test="${empty formPatient.listTumeurs}">Aucune tumeur de sein n'est enregistrée pour ce patient</c:if>
						<form:errors path="listIdTumeursCausesDeces" class="control-label" />
					</div>
				</div>
			</spring:bind>



			<!-- Statut BRCA -->
			<spring:bind path="statutBrca">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Statut BRCA</label>
					<div class="col-sm-10">
						<form:select path="statutBrca" class="form-control">
							<form:option value="" label="--- Sélectionner ---" />
							<form:option value="Non muté" />
							<form:option value="BRCA1" />
							<form:option value="BRCA2" />
						</form:select>
						<form:errors path="statutBrca" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<!-- Remarque -->
			<spring:bind path="remarque">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Remarque</label>
					<div class="col-sm-10">
						<form:input class="form-control" path="remarque" type="text" />
						<form:errors path="remarque" class="control-label" />
					</div>
				</div>
			</spring:bind>







			<!-- Buttons -->
			<%@ include file="../inc/boutonsFormulaire.jsp"%>


		</form:form>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>