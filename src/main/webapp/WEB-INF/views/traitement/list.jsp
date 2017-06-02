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

		<!-- Dismissible alert -->
		<%@ include file="../inc/dismissibleAlert.jsp"%>

		<div>

			<!-- H1 Patient -->
			<%@ include file="../inc/h1Patient.jsp"%>

			<h2>Traitements :</h2>

			<!-- Results -->
			<%@ include file="tableTraitements.jsp"%>

		</div>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>