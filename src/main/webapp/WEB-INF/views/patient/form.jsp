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

		<c:choose>
			<c:when test="${formPatient['new']}">
				<h1>Ajouter un patient</h1>
			</c:when>
			<c:otherwise>
				<h1>Modifier un patient</h1>
			</c:otherwise>
		</c:choose>

		<form:form class="form-horizontall" method="post"
			modelAttribute="formPatient"
			action="${pageContext.request.contextPath}/patient/update">

			<form:hidden path="idPatient" />

			<spring:bind path="rcp">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-2 control-label">Numéro RCP</label>
					<div class="col-10">
						<form:input path="rcp" type="text" class="form-control " id="rcp"
							placeholder="RCP" />
						<form:errors path="rcp" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<spring:bind path="prenom">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-2 control-label required-field">Prénom *</label>
					<div class="col-10">
						<form:input path="prenom" type="text" class="form-control "
							id="prenom" placeholder="Prénom" />
						<form:errors path="prenom" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<spring:bind path="nom">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-2 control-label required-field">Nom *</label>
					<div class="col-10">
						<form:input path="nom" type="text" class="form-control " id="nom"
							placeholder="Nom" />
						<form:errors path="nom" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<spring:bind path="sexe">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-2 control-label required-field">Sexe *</label>
					<div class="col-10">
						<label class="radio-inline"> <form:radiobutton path="sexe"
								value="F" />Femme
						</label> <label class="radio-inline"> <form:radiobutton
								path="sexe" value="M" />Homme
						</label> <br />
						<form:errors path="sexe" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<spring:bind path="dateNaissance">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-2 control-label required-field">Date de naissance *</label>
					<div class="col-10">
						<form:input path="dateNaissance" type="date" class="form-control "
							id="dateNaissance" placeholder="Date de naissance" />
						<form:errors path="dateNaissance" class="control-label" />
					</div>
				</div>
			</spring:bind>


			<spring:bind path="dateDeces">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-2 control-label">Date de décès</label>
					<div class="col-10">
						<form:input path="dateDeces" type="date" class="form-control "
							id="dateDeces" placeholder="Date de décès" />
						<form:errors path="dateDeces" class="control-label" />
					</div>
				</div>
			</spring:bind>


			<spring:bind path="causeDeces">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-2 control-label">Cause de décès</label>
					<div class="col-10">
						<form:input path="causeDeces" type="text" class="form-control "
							id="causeDeces" list="listCauses" />
						<datalist id="listCauses">
							<option value="Suites néoplasiques" />
							<option value="Autre cancer" />
							<option value="Autre cancer du sein" />
							<option value="Multiples cancers dont le sein" />
							<option value="Maladie intercurrente (ou suicide)" />
							<option value="Complications liées au traitement" />
							<option value="Cause inconnue" />
						</datalist>
						<form:errors path="causeDeces" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<spring:bind path="statutBrca">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-2 control-label">Statut BRCA</label>
					<div class="col-10">
						<form:input path="statutBrca" type="text" class="form-control "
							id="statutBrca" list="listBrcaMutations" />
						<datalist id="listBrcaMutations">
							<option value="Non muté" />
							<option value="BRCA1" />
							<option value="BRCA2" />
						</datalist>
						<form:errors path="statutBrca" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<spring:bind path="consentement">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-2 control-label">Consentement</label>
					<div class="col-10">
						<label class="radio-inline"> <form:radiobutton
								path="consentement" value="true" />oui
						</label> <label class="radio-inline"> <form:radiobutton
								path="consentement" value="false" />non
						</label> <br />
						<form:errors path="consentement" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<div class="form-group">
				<div class="col-offset-2 col-10">
					<p></p>
					<button type="submit" class="btn-lg btn-primary pull-right">Enregistrer</button>
				</div>
			</div>


		</form:form>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>