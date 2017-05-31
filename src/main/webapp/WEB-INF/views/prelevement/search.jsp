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

		<div class="starter-template">
			<h1>Rechercher un prélèvement</h1>
		</div>

		<form:form class="form-inline" method="POST"
			action="${pageContext.request.contextPath}/prelevement">
			<%@ include file="../inc/selectPatient.jsp"%>
		</form:form>

		<p></p>

		<c:if test="${not empty patient}">

			<div>
				<!-- H1 Patient -->
				<%@ include file="../inc/h1Patient.jsp"%>

				<h2>Prélèvements :</h2>

				<!-- Results -->
				<%@ include file="tablePrelevements.jsp"%>

				<!-- Bouton ajouter tumeur -->
				<%@ include file="boutonAjouterPrelevement.jsp"%>
			</div>
		</c:if>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>