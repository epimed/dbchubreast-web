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

		<!-- H1 Patient -->
		<%@ include file="../inc/h1Patient.jsp"%>

		<c:choose>
			<c:when test="${formPrelevement['new']}">
				<h2>Ajouter un prélèvement</h2>
			</c:when>
			<c:otherwise>
				<h2>Modifier un prélèvement</h2>
			</c:otherwise>
		</c:choose>

		<form:form class="form-horizontal" method="post"
			modelAttribute="formPrelevement"
			action="${pageContext.request.contextPath}/patient/${formPrelevement.idPatient}/prelevement/add/detail">

			<form:hidden path="idPrelevement" />
			<form:hidden path="idMorphologie" />
			<form:hidden path="datePrelevement" />
			<form:hidden path="sitePrelevement" />
			<form:hidden path="typeHistologique" />
			<form:hidden path="associationCis" />
			<form:hidden path="listBiomarqueurs" />
			<form:hidden path="listValeurs" />

			<!-- Tumeur -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Tumeur *</label>
				<div class="col-sm-10">
					<form:select class="form-control" path="idTumeur"
						onchange="this.form.submit()">
						<form:option value="" label="--- Sélectionner ---" />
						<c:forEach var="tumeur" items="${listTumeurs}">
							<form:option value="${tumeur.idTumeur}"
								label="${tumeur.idTumeur} - ${tumeur.dateDiagnostic} - ${tumeur.cote}" />
						</c:forEach>
					</form:select>
					<form:errors path="idTumeur" class="control-label" />
				</div>
			</div>

			<!-- Phase -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Phase de la tumeur *</label>
				<div class="col-sm-10">
					<form:select class="form-control" path="idPhase"
						onchange="this.form.submit()">
						<form:option value="" label="--- Sélectionner ---" />
						<c:forEach var="phase" items="${listPhases}">
							<form:option value="${phase.idPhase}"
								label="${phase.idPhase} - ${phase.dateDiagnostic} - ${phase.chuTypePhase.nom}" />
						</c:forEach>
					</form:select>
					<form:errors path="idPhase" class="control-label" />
				</div>
			</div>

			<!-- Type de prelevement -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Type de prélèvement *</label>
				<div class="col-sm-10">
					<form:select class="form-control" path="idTypePrelevement"
						onchange="this.form.submit()">
						<form:option value="" label="--- Sélectionner ---" />
						<c:forEach var="typePrelevement" items="${listTypePrelevements}">
							<form:option value="${typePrelevement.idTypePrelevement}"
								label="${typePrelevement.nom}" />
						</c:forEach>
					</form:select>
					<form:errors path="idTypePrelevement" class="control-label" />
				</div>
			</div>

		</form:form>


		<form:form class="form-horizontal" method="post"
			modelAttribute="formPrelevement"
			action="${pageContext.request.contextPath}/prelevement/update">

			<form:hidden path="idPatient" />
			<form:hidden path="idPrelevement" />
			<form:hidden path="idTumeur" />
			<form:hidden path="idPhase" />
			<form:hidden path="idTypePrelevement" />
			<form:hidden path="listBiomarqueurs" />
			<form:hidden path="listValeurs" />


			<!-- Date de prelevement -->
			<spring:bind path="datePrelevement">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Date de prélèvement *</label>
					<div class="col-sm-10">
						<form:input class="form-control" path="datePrelevement"
							type="date" />
						<form:errors path="datePrelevement" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<!-- Site de prelevement -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Site de prélèvement</label>
				<div class="col-sm-3">
					<form:input class="form-control" path="sitePrelevement" type="text" />
					<form:errors path="sitePrelevement" />
				</div>
				<div class="col-sm-7">
					<span id="helpBlock" class="help-block">Laisser vide par
						défaut si le site de prélèvement correspond à la tumeur elle-même</span>
				</div>
			</div>


			<!-- Morphologie ICD-O -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Morphologie ICD-O</label>
				<div class="col-sm-10">
					<form:select class="form-control" path="idMorphologie">
						<form:option value="" label="--- Sélectionner ---" />
						<c:forEach var="morpho" items="${listMorphologies}">
							<form:option value="${morpho.idMorphologie}"
								label="${morpho.idMorphologie} - ${morpho.nomEn}" />
						</c:forEach>
					</form:select>
					<form:errors path="idMorphologie" class="control-label" />
				</div>
			</div>

			<!-- Type histologique -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Histologie</label>
				<div class="col-sm-10">
					<form:input class="form-control" path="typeHistologique"
						type="text" />
					<form:errors path="typeHistologique" />
				</div>
			</div>

			<!-- Association CIS -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Association "carcinoma
					in situ" (CIS)</label>
				<div class="col-sm-10">
					<label class="radio-inline"> <form:radiobutton
							path="associationCis" value="true" /> oui
					</label> <label class="radio-inline"> <form:radiobutton
							path="associationCis" value="false" /> non
					</label>
					<form:errors path="associationCis" />
				</div>
			</div>

			<h3>Biomarqueurs :</h3>

			<c:forEach var="biomarqueur"
				items="${formPrelevement.listBiomarqueurs}" varStatus="loop">
				<!-- Biomarqueur -->
				<div class="form-group">
					<label class="col-sm-2 control-label">${biomarqueur.nom}</label>
					<div class="col-sm-2">
						<form:input class="form-control" path="listValeurs[${loop.index}]" type="text" />
						<form:errors path="listValeurs[${loop.index}]" />
					</div>
				</div>
			</c:forEach>

			<!-- Button -->

			<p></p>

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
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