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
			<c:when test="${formTumeurInitiale['new']}">
				<h2>
					Ajouter une tumeur <small>en phase initiale</small>
				</h2>
			</c:when>
			<c:otherwise>
				<h2>
					Modifier une tumeur <small>en phase initiale</small>
				</h2>
			</c:otherwise>
		</c:choose>

		<form:form class="form-horizontal" method="post"
			modelAttribute="formTumeurInitiale"
			action="${pageContext.request.contextPath}/tumeur/update">

			<form:hidden path="idTumeur" />
			<form:hidden path="idPatient" />
			<form:hidden path="dateDeces" />
			<form:hidden path="idPhase" />
			<form:hidden path="idEvolution" />
			<form:hidden path="dateEvolution" />

			<!-- Date de diagnostic -->
			<spring:bind path="dateDiagnostic">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Date du diagnostic</label>
					<div class="col-sm-10">
						<form:input class="form-control" path="dateDiagnostic" type="date" />
						<form:errors path="dateDiagnostic" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<!-- Age au diagnostic -->
			<spring:bind path="ageDiagnostic">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Age au diagnostic</label>
					<div class="col-sm-1">
						<form:input class="form-control" path="ageDiagnostic" type="text" />
					</div>
					<div class="col-sm-9">
						<form:errors path="ageDiagnostic" class="control-label" />
						<span id="helpBlock" class="help-block">L'age est à saisir
							uniquement si la date du diagnostic et/ou la date de naissance ne
							sont pas connues. Si les deux dates sont renseignées, l'age au
							diagnostic sera calculé automatiquement.</span>
					</div>
				</div>
			</spring:bind>

			<!-- Nature de diagnostic -->
			<spring:bind path="natureDiagnostic">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Nature de diagnostic</label>
					<div class="col-sm-10">
						<form:input class="form-control" path="natureDiagnostic"
							type="text" list="listNatureDiagnostic" />
						<datalist id="listNatureDiagnostic">
							<option value="Biopsie" />
							<option value="Mammo/Echo" />
							<option value="Mammographie" />
							<option value="TEP" />
							<option value="IRM" />
						</datalist>
						<form:errors path="natureDiagnostic" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<!-- Topographie ICD-O -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Topographie ICD-O</label>
				<div class="col-sm-10">
					<form:select class="form-control" path="idTopographie">
						<form:option value="" label="--- Sélectionner ---" />
						<c:forEach var="topo" items="${listTopographies}">
							<form:option value="${topo.idTopographie}"
								label="${topo.idTopographie} - ${topo.nomFr} / ${topo.nomEn}" />
						</c:forEach>
					</form:select>
					<form:errors path="idTopographie" class="control-label" />
				</div>
			</div>

			<!-- Côté -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Côté</label>
				<div class="col-sm-10">
					<label class="radio-inline"> <form:radiobutton path="cote"
							value="G" /> Gauche (G)
					</label> <label class="radio-inline"> <form:radiobutton path="cote"
							value="D" /> Droit (D)
					</label>
					<form:errors path="cote" />
				</div>
			</div>

			<!-- Date de la dernière nouvelle -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Dernière nouvelle</label>
				<div class="col-sm-10">

					<c:choose>
						<c:when test="${formTumeurInitiale.idEvolution==5  or not empty formTumeurInitiale.dateDeces}">
							<form:input class="form-control" path="dateEvolution" type="date"
								disabled="true" />
						</c:when>
						<c:otherwise>
							<form:input class="form-control" path="dateEvolution" type="date" />
						</c:otherwise>
					</c:choose>

					<form:errors path="dateEvolution" />
				</div>
			</div>

			<!-- Statut -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Statut</label>
				<div class="col-sm-10">
					<c:choose>
						<c:when test="${formTumeurInitiale.idEvolution==5 or not empty formTumeurInitiale.dateDeces}">
							<form:select class="form-control" path="idEvolution"
								disabled="true">
								<form:option value="" label="--- Sélectionner ---" />
								<c:forEach var="evolution" items="${listEvolutions}">
									<form:option value="${evolution.idEvolution}"
										label="${evolution.idEvolution} - ${evolution.code} - ${evolution.nom}" />
								</c:forEach>
							</form:select>
						</c:when>
						<c:otherwise>
							<form:select class="form-control" path="idEvolution">
								<form:option value="" label="--- Sélectionner ---" />
								<c:forEach var="evolution" items="${listEvolutions}">
									<form:option value="${evolution.idEvolution}"
										label="${evolution.idEvolution} - ${evolution.code} - ${evolution.nom}" />
								</c:forEach>
							</form:select>
						</c:otherwise>
					</c:choose>
					<form:errors path="idEvolution" class="control-label" />
				</div>
			</div>

			<!-- Profondeur -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Profondeur</label>
				<div class="col-sm-3">
					<form:input class="form-control" path="profondeur" type="text" />
					<form:errors path="profondeur" />
				</div>
			</div>

			<!-- cTNM -->
			<div class="form-group">
				<label class="col-sm-2 control-label">cTNM</label>
				<div class="col-sm-1">
					cT
					<form:input class="form-control" path="cT" type="text" />
					<form:errors path="cT" />
				</div>
				<div class="col-sm-1">
					cN
					<form:input class="form-control" path="cN" type="text" />
					<form:errors path="cN" />
				</div>
				<div class="col-sm-1">
					cM
					<form:input class="form-control" path="cM" type="text" />
					<form:errors path="cM" />
				</div>
				<div class="col-sm-2">
					c Taille (mm)
					<form:input class="form-control" path="cTaille" type="text" />
					<form:errors path="cTaille" />
				</div>
			</div>

			<!-- pTNM -->
			<div class="form-group">
				<label class="col-sm-2 control-label">pTNM</label>
				<div class="col-sm-1">
					pT
					<form:input class="form-control" path="pT" type="text" />
					<form:errors path="pT" />
				</div>
				<div class="col-sm-1">
					pN
					<form:input class="form-control" path="pN" type="text" />
					<form:errors path="pN" />
				</div>
				<div class="col-sm-1">
					pM
					<form:input class="form-control" path="pM" type="text" />
					<form:errors path="pM" />
				</div>
				<div class="col-sm-2">
					p Taille (mm)
					<form:input class="form-control" path="pTaille" type="text" />
					<form:errors path="pTaille" />
				</div>
			</div>

			<!-- Metastases -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Métastases</label>
				<div class="col-sm-10">
					<c:forEach var="metastase" items="${listMetastases}">
						<label class="checkbox-inline"> <form:checkbox
								path="listIdMetastases" value="${metastase.idMetastase}" />
							${metastase.nom}
						</label>
					</c:forEach>
					<form:errors path="listIdMetastases" />
				</div>
			</div>

			<!-- Remarque -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Remarque</label>
				<div class="col-sm-10">
					<form:input class="form-control" path="remarque" type="text" />
					<form:errors path="remarque" />
				</div>
			</div>


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