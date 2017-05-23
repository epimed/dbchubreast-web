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

		<h1>Connexion</h1>

		<c:url value="/login" var="loginUrl" />

		<form:form class="form-horizontall" method="post" action="${loginUrl}">

			<c:if test="${param.error != null}">
				<div class="alert alert-danger">
					<p>Username ou mot de passe incorrect !</p>
				</div>
			</c:if>
			<c:if test="${param.logout != null}">
				<div class="alert alert-success">
					<p>Vous êtes connectés !</p>
				</div>
			</c:if>

			<p>
				<label for="username">Username</label> <input type="text"
					id="username" name="username" required />
			</p>
			<p>
				<label for="password">Password</label> <input type="password"
					id="password" name="password" required />
			</p>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />

			<button type="submit" class="btn btn-primary">Se connecter</button>
		</form:form>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>