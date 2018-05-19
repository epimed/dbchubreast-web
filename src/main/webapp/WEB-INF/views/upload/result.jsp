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

	<!-- Fil d'Ariane -->
	<%@ include file="../inc/filAriane.jsp"%>


	<!-- Dismissible alert -->
	<%@ include file="../inc/dismissibleAlert.jsp"%>

	<div class="container">
		<h1>Importer un fichier avec des données cliniques
			supplémentaires</h1>

		<c:if test="${success}">
			<p class="text-success">${message}</p>
		</c:if>

		<c:if test="${not success}">
			<p class="text-danger">${message}</p>
		</c:if>


	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>
