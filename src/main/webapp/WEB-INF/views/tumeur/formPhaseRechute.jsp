<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>


<script>
	window.onload = function() {

		<c:choose>
		<c:when test="${formPhaseRechute.locale}">
			setVisibilityForRechuteLocale(true)
		</c:when>
		<c:otherwise>
			setVisibilityForRechuteLocale(false)
		</c:otherwise>
		</c:choose>

		<c:choose>
		<c:when test="${formPhaseRechute.metastases}">
			setVisibilityForRechuteMetastatique(true)
		</c:when>
		<c:otherwise>
			setVisibilityForRechuteMetastatique(false)
		</c:otherwise>
		</c:choose>

	}
	
	function setVisibilityForRechuteLocale(isVisible) {
		setVisibility('topographie', isVisible);
		setVisibility('tnm', isVisible)
	}
	
	function setVisibilityForRechuteMetastatique(isVisible) {
		setVisibility('metastases', isVisible);
	}
	
	function showOrHideElementsForRechuteLocale() {
		showOrHideElement('topographie');
		showOrHideElement('tnm');
	}

	function showOrHideElementsForRechuteMetastatique() {
		showOrHideElement('metastases');
	}
	
	function showOrHideElement(idElement) {
		var x = document.getElementById(idElement);
		if (x.style.display === 'none') {
			x.style.display = 'block';
		} else {
			x.style.display = 'none';
		}
	}

	function setVisibility(idElement, isVisible) {
		if (isVisible) {
			document.getElementById(idElement).style.display = "block";
		} else {
			document.getElementById(idElement).style.display = "none";
		}
	}
</script>

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
			<c:when test="${formPhaseRechute['new']}">
				<h2>
					Ajouter une rechute <small>à la tumeur ${tumeur.idTumeur}
						diagnostiqué le <fmt:formatDate pattern="dd/MM/yyyy"
							value="${tumeur.dateDiagnostic}" />
					</small>
				</h2>
			</c:when>
			<c:otherwise>
				<h2>
					Modifier une rechute <small>de la tumeur ${tumeur.idTumeur}
						diagnostiqué le <fmt:formatDate pattern="dd/MM/yyyy"
							value="${tumeur.dateDiagnostic}" />
					</small>
				</h2>
			</c:otherwise>
		</c:choose>

		<form:form class="form-horizontal" method="post"
			modelAttribute="formPhaseRechute"
			action="${pageContext.request.contextPath}/rechute/update">

			<form:hidden path="idTumeur" />
			<form:hidden path="idPatient" />
			<form:hidden path="idPhase" />
			<form:hidden path="idTopographieTumeurInitiale" />
			<form:hidden path="nomTopographieTumeurInitiale" />


			<!-- Type de rechute -->
			<spring:bind path="locale">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Type de rechute</label>
					<div class="col-sm-10">
						<label class="checkbox-inline"> <form:checkbox
								path="locale" onclick="showOrHideElementsForRechuteLocale()" />
							locale
						</label> <label class="checkbox-inline"> <form:checkbox
								path="metastases" onclick="showOrHideElementsForRechuteMetastatique()" />
							metastatique
						</label>
						<form:errors path="metastases" class="control-label" />

					</div>
				</div>
			</spring:bind>

			<!-- Date de diagnostic -->
			<spring:bind path="dateDiagnostic">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Date du diagnostic de
						la rechute *</label>
					<div class="col-sm-10">
						<form:input class="form-control" path="dateDiagnostic" type="date" />
						<form:errors path="dateDiagnostic" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<!-- Topographie de la phase initiale -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Topographie de la
					phase initiale</label>
				<div class="col-sm-5">
					<span>${formPhaseRechute.idTopographieTumeurInitiale} -
						${formPhaseRechute.nomTopographieTumeurInitiale}</span>
				</div>
			</div>

			<!-- Topographie de la phase de rechute -->
			<div id="topographie">
				<spring:bind path="idTopographie">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-2 control-label">Topographie de la
							rechute *</label>
						<div class="col-sm-10">
							<form:select class="form-control" path="idTopographie">
								<form:option value="" label="--- Sélectionner ---" />
								<c:forEach var="topographieRechute"
									items="${listTopographiesRechute}">
									<form:option value="${topographieRechute.idTopographie}"
										label="${topographieRechute.idTopographie} - ${topographieRechute.nomFr}" />
								</c:forEach>
							</form:select>
							<form:errors path="idTopographie" class="control-label" />
						</div>
					</div>
				</spring:bind>
			</div>

			<div id="tnm">
				<!-- cTNM -->
				<div class="form-group">
					<label class="col-sm-2 control-label">cTNM de la rechute</label>

					<div class="col-sm-1">
						cT
						<form:input class="form-control" path="cT" type="text" />
						<form:errors path="cT" class="text-danger" />
					</div>

					<div class="col-sm-1">
						cN
						<form:input class="form-control" path="cN" type="text" />
						<form:errors path="cN" class="text-danger" />
					</div>
					<div class="col-sm-1">
						cM
						<form:input class="form-control" path="cM" type="text" />
						<form:errors path="cM" class="text-danger" />
					</div>
					<div class="col-sm-2">
						c Taille (mm)
						<form:input class="form-control" path="cTaille" type="text" />
						<form:errors path="cTaille" class="text-danger" />
					</div>
					<div class="col-sm-5">
						<span id="helpBlock" class="help-block">En cas de nodules,
							noter plusieurs tailles séparées par le caractère <code>/</code>,
							par exemple <code>10/5</code>.
						</span>
					</div>
				</div>

				<!-- pTNM -->
				<div class="form-group">
					<label class="col-sm-2 control-label">pTNM de la rechute</label>

					<div class="col-sm-1">
						pT
						<form:input class="form-control" path="pT" type="text" />
						<form:errors path="pT" class="text-danger" />
					</div>

					<div class="col-sm-1">
						pN
						<form:input class="form-control" path="pN" type="text" />
						<form:errors path="pN" class="text-danger" />
					</div>
					<div class="col-sm-1">
						pM
						<form:input class="form-control" path="pM" type="text" />
						<form:errors path="pM" class="text-danger" />
					</div>
					<div class="col-sm-2">
						p Taille (mm)
						<form:input class="form-control" path="pTaille" type="text" />
						<form:errors path="pTaille" class="text-danger" />
					</div>
					<div class="col-sm-5">
						<span id="helpBlock" class="help-block">En cas de nodules,
							noter plusieurs tailles séparées par le caractère <code>/</code>,
							par exemple <code>10/5</code>.
						</span>
					</div>
				</div>
			</div>

			<!-- Metastases -->
			<div id="metastases">
				<spring:bind path="listIdMetastases">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-2 control-label">Métastases *</label>
						<div class="col-sm-10">
							<c:forEach var="metastase" items="${listMetastases}">
								<label class="checkbox-inline"> <form:checkbox
										path="listIdMetastases" value="${metastase.idMetastase}" />
									${metastase.nom}
								</label>
							</c:forEach>
							<form:errors path="listIdMetastases" class="control-label" />
						</div>
					</div>
				</spring:bind>
			</div>

			<!-- Performance Status -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Performance status</label>
				<div class="col-sm-10">
					<form:select class="form-control" path="idPs">
						<form:option value="" label="--- Sélectionner ---" />
						<c:forEach var="performanceStatus"
							items="${listPerformanceStatus}">
							<form:option value="${performanceStatus.idPs}"
								label="${performanceStatus.idPs} - ${performanceStatus.description}" />
						</c:forEach>
					</form:select>
					<form:errors path="idPs" class="control-label" />
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


			<!-- Buttons -->
			<%@ include file="../inc/boutonsFormulaire.jsp"%>


		</form:form>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>