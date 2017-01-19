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
			<%@ include file="../inc/selectPatient.jsp"%>
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