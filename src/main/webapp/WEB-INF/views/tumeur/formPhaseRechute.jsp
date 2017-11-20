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
			<c:when test="${formPhaseRechute['new']}">
				<h2>
					Ajouter une rechute <small>à la tumeur ${tumeur.idTumeur}</small>
				</h2>
			</c:when>
			<c:otherwise>
				<h2>
					Modifier une rechute <small>de la tumeur ${tumeur.idTumeur}</small>
				</h2>
			</c:otherwise>
		</c:choose>

		<form:form class="form-horizontal" method="post"
			modelAttribute="formPhaseRechute"
			action="${pageContext.request.contextPath}/rechute/update">

			<form:hidden path="idTumeur" />
			<form:hidden path="idPatient" />
			<form:hidden path="idPhase" />

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

			<!-- cTNM -->
			<div class="form-group">
				<label class="col-sm-2 control-label">cTNM</label>

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
				<label class="col-sm-2 control-label">pTNM</label>

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

			<!-- Locale -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Rechute locale</label>
				<div class="col-sm-10">
					<label class="radio-inline"> <form:radiobutton
							path="locale" value="true" /> oui
					</label> <label class="radio-inline"> <form:radiobutton
							path="locale" value="false" /> non
					</label>
					<form:errors path="locale" />
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