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
		<%@ include file="../../inc/dismissibleAlert.jsp"%>


		<div>

			<!-- H1 Patient -->
			<h1>Thésaurus : Protocoles de traitement</h1>


			<p></p>

			<!-- Results -->
			<%@ include file="tableProtocoles.jsp"%>

		</div>



	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>