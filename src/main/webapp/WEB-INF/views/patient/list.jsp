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


		<!-- Dismissible alert -->
		<%@ include file="../inc/dismissibleAlert.jsp"%>

		<div class="starter-template">
			<h1>Patients</h1>
		</div>



		<!-- Boutons ajouter et rechercher -->
		<div>
			<%@ include file="boutonAjouterPatient.jsp"%>
			<%@ include file="boutonRechercherPatient.jsp"%>
		</div>

		<h4>Nombre de patients trouvés : ${fn:length(listPatients)}</h4>

		<!-- Results -->
		<%@ include file="tablePatients.jsp"%>


	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>