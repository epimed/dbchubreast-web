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

		<c:choose>
			<c:when test="${formUtilisateur['new']}">
				<h2>Ajouter un utilisateur</h2>
			</c:when>
			<c:otherwise>
				<h2>Modifier un utilisateur</h2>
			</c:otherwise>
		</c:choose>

		<form:form class="form-horizontal" method="post"
			modelAttribute="formUtilisateur"
			action="${pageContext.request.contextPath}/admin/user/update">

			<form:hidden path="idUser" />

			<!-- Nom -->
			<spring:bind path="lastName">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Nom</label>
					<div class="col-sm-10">
						<form:input class="form-control" path="lastName" type="text" />
						<form:errors class="control-label" path="lastName" />
					</div>
				</div>
			</spring:bind>

			<!-- Prenom -->
			<spring:bind path="firstName">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Prénom</label>
					<div class="col-sm-10">
						<form:input class="form-control" path="firstName" type="text" />
						<form:errors class="control-label" path="firstName" />
					</div>
				</div>
			</spring:bind>

			<!-- Email -->
			<spring:bind path="email">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Email</label>
					<div class="col-sm-10">
						<form:input class="form-control" path="email" type="text" />
						<form:errors class="control-label" path="email" />
					</div>
				</div>
			</spring:bind>
			
			<!-- Login -->
			<spring:bind path="login">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Login</label>
					<div class="col-sm-10">
						<form:input class="form-control" path="login" type="text" />
						<form:errors class="control-label" path="login" />
					</div>
				</div>
			</spring:bind>
			
			<!-- Password -->
			<spring:bind path="password">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Password</label>
					<div class="col-sm-10">
						<form:input class="form-control" path="password" type="text" />
						<form:errors class="control-label" path="password" />
					</div>
				</div>
			</spring:bind>
			
			<!-- Enabled -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Enabled</label>
				<div class="col-sm-10">
					<label class="radio-inline"> <form:radiobutton path="enabled"
							value="true" /> true
					</label> <label class="radio-inline"> <form:radiobutton path="enabled"
							value="false" /> false
					</label>
					<form:errors path="enabled" />
				</div>
			</div>

			<!-- Roles -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Roles</label>
				<div class="col-sm-10">
					<c:forEach var="role" items="${listRoles}">
						<label class="checkbox-inline"> <form:checkbox
								path="roles" value="${role.idRole}" /> ${role.idRole} -
							${role.name}
						</label>
					</c:forEach>
					<form:errors path="roles" />
				</div>
			</div>



			<!-- Button -->

			<p></p>

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<p></p>
					<button type="submit" class="btn-lg btn-primary pull-right">Enregistrer</button>
				</div>
			</div>


		</form:form>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>