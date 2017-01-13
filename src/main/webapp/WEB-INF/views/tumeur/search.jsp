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

		<div class="starter-template">
			<h1>Rechercher une tumeur</h1>
		</div>

		<form:form class="form-inline" method="POST"
			action="${pageContext.request.contextPath}/tumeur">

			<!-- Liste des patient -->
			<div class="form-group">
				<label>Patient</label> <select class="form-control" id="idPatient"
					name="idPatient">
					<option value="">--- Sélectionner ---</option>
					<c:forEach var="patient" items="${listPatients}">
						<c:choose>
							<c:when
								test="${not empty patient and patient.idPatient==idPatient}">
								<option value="${patient.idPatient}" selected="selected">${patient.idPatient}
									- ${patient.nom} ${patient.prenom} ${patient.dateNaissance}</option>
							</c:when>
							<c:otherwise>
								<option value="${patient.idPatient}">${patient.idPatient}
									- ${patient.nom} ${patient.prenom} ${patient.dateNaissance}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> <input class="btn btn-primary" type="submit" value="Rechercher" />
			</div>
		</form:form>

		<p></p>

		<c:if test="${not empty patient}">
			<div>
				<!-- H1 Patient -->
				<%@ include file="../inc/h1Patient.jsp"%>

				<h2>Tumeurs :</h2>

				<!-- Results -->
				<%@ include file="../inc/tableTumeurs.jsp"%>

			</div>

			<!-- Bouton ajouter tumeur -->
			<%@ include file="../inc/boutonAjouterTumeur.jsp"%>

		</c:if>
	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>