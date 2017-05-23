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
			<h1>Utilisateurs</h1>
		</div>

		<!-- Results -->
		<%@ include file="../inc/tableUtilisateurs.jsp"%>

		<div>
			<p></p>

			<spring:url value="/admin/user/add" var="url" />
			<button class="btn btn-info" onclick="location.href='${url}'">Ajouter un utilisateur</button>
		</div>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>