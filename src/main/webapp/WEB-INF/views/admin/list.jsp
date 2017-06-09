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

		<div>
			<spring:url value="/admin/user/add" var="url" />
			<button class="btn-sm btn-success" onclick="location.href='${url}'">Ajouter
				un utilisateur</button>
		</div>

		<!-- Results -->
		<%@ include file="tableUtilisateurs.jsp"%>



	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>