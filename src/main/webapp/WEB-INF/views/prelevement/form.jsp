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
			<c:when test="${formPrelevement['new']}">
				<h2>Ajouter un prélèvement</h2>
			</c:when>
			<c:otherwise>
				<h2>Modifier un prélèvement</h2>
			</c:otherwise>
		</c:choose>

		<form:form class="form-horizontal" method="post"
			modelAttribute="formPrelevement"
			action="${pageContext.request.contextPath}/prelevement/update">

			<!-- Hidden attributes -->

			<form:hidden path="idPrelevement" />
			<form:hidden path="idPatient" />

			<!-- TK -->
			<div class="form-group">
				<label class="col-sm-2 control-label">TK</label>
				<div class="col-sm-3">
					<form:input class="form-control" path="tk" type="text" />
					<form:errors path="tk" />
				</div>
			</div>

			<!-- Tumeur -->

			<spring:bind path="idTumeur">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Tumeur *</label>
					<div class="col-sm-10">
						<form:select class="form-control" path="idTumeur"
							onchange="this.form.submit()">
							<form:option value="" label="--- Sélectionner ---" />
							<c:forEach var="tumeur" items="${listTumeurs}">

								<fmt:formatDate pattern="dd/MM/yyyy"
									value="${tumeur.dateDiagnostic}" var="dateTumeur" />

								<form:option value="${tumeur.idTumeur}"
									label="[ID ${tumeur.idTumeur}] ${dateTumeur} - ${tumeur.cote}" />
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
						<form:select class="form-control" path="idPhase"
							onchange="this.form.submit()">
							<form:option value="" label="--- Sélectionner ---" />
							<c:forEach var="phase" items="${listPhases}">
							
							<fmt:formatDate pattern="dd/MM/yyyy"
									value="${phase.dateDiagnostic}" var="datePhase" />
							
								<form:option value="${phase.idPhase}"
									label="[ID ${phase.idPhase}] ${datePhase} - ${phase.chuTypePhase.nom}" />
							</c:forEach>
						</form:select>
						<form:errors path="idPhase" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<!-- Type de prelevement -->
			<spring:bind path="idTypePrelevement">
				<div class="form-group ${status.error ? 'has-error' : ''}">
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
			</spring:bind>


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
				<div class="col-sm-4">
					<form:input class="form-control" path="sitePrelevement" type="text" list="listSitesPrelevement"/>
					<datalist id="listSitesPrelevement">
							<option value="Adénopathie locorégionale (axillaire, sus ou sous claviculaire)" />
							<option value="Adénopathie autre (à distance) : tumeurs d'emblée métastatiques" />
							<option value="Métastase hépatique" />
							<option value="Métastase autre de l'appareil digestif" />
							<option value="Métastase osseuse" />
							<option value="Métastase cérébrale" />
							<option value="Métastase surrénalienne" />
							<option value="Métastase cutanée ou sous-cutanée ou des parties molles" />
						</datalist>
					<form:errors path="sitePrelevement" />
				</div>
				<div class="col-sm-6">
					<span id="helpBlock" class="help-block">Laisser vide par
						défaut si le site de prélèvement correspond à la tumeur principale</span>
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
								label="${morpho.idMorphologie} - ${morpho.nomFr} / ${morpho.nomEn}" />
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

			<div class="form-group">
				<div class="col-sm-2"></div>
				<div class="col-sm-10">
					<span id="helpBlock" class="help-block">En cas de nodules,
						noter plusieurs valeurs séparées par le caractère <code>/</code>,
						par exemple <code>95/100</code> ou <code>1/<</code> ou <code>I/II</code>.
					</span>
				</div>
			</div>

			<div class="form-group">
				<c:forEach var="formBio"
					items="${formPrelevement.listFormBiomarqueurs}" varStatus="loop">
					<label class="col-sm-2 control-label">${formBio.nom}</label>
					<div class="col-sm-2">

						<form:hidden
							path="listFormBiomarqueurs[${loop.index}].idBiomarqueur" />

						<form:hidden path="listFormBiomarqueurs[${loop.index}].nom" />

						<form:hidden path="listFormBiomarqueurs[${loop.index}].statut" />

						<form:input class="form-control"
							path="listFormBiomarqueurs[${loop.index}].valeur" type="text" />

						<form:errors path="listFormBiomarqueurs[${loop.index}].valeur" />

					</div>

				</c:forEach>
			</div>


			<!-- Buttons -->
			<%@ include file="../inc/boutonsFormulaire.jsp"%>

		</form:form>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>
