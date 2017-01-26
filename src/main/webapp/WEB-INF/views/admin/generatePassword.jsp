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

		<h1>Générer un mot de passe crypté</h1>

		<c:url value="/login" var="loginUrl" />

		<form:form class="form-horizontall" method="post"
			modelAttribute="formPasswordEncoder"
			action="${pageContext.request.contextPath}/admin/password">

			<!-- Password -->
			<spring:bind path="clearpass">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Password</label>
					<div class="col-sm-5">
						<form:input class="form-control" path="clearpass" type="text" />
						<form:errors class="control-label" path="clearpass" />

					</div>
				</div>
			</spring:bind>

			<button type="submit" class="btn btn-primary">Crypter</button>
		</form:form>


		<div>
			<c:if test="${not empty formPasswordEncoder.hashedpass}">
				<h2>Cryptage</h2>
				<c:forEach var="pass" items="${formPasswordEncoder.hashedpass}"
					varStatus="loop">
					<div class="row">
						<label class="col-sm-2">Variante ${loop.count} : </label>
						<div class="col-sm-10">${pass}</div>
					</div>
				</c:forEach>
			</c:if>
		</div>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>