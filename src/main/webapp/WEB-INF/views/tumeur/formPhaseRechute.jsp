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