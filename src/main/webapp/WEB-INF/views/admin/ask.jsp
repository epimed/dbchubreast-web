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


		<c:if test="${not empty msg}">
			<div class="alert alert-${css} alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>${msg}</strong>
			</div>
		</c:if>

		<h1>Utilisateur</h1>
		<br />

		<div class="row">
			<label class="col-sm-2">ID</label>
			<div class="col-sm-10">${utilisateur.idUser}</div>
		</div>

		<div class="row">
			<label class="col-sm-2">Nom</label>
			<div class="col-sm-10">${utilisateur.lastName}</div>
		</div>

		<div class="row">
			<label class="col-sm-2">Prénom</label>
			<div class="col-sm-10">${utilisateur.firstName}</div>
		</div>

		<div class="row">
			<label class="col-sm-2">Email</label>
			<div class="col-sm-10">${utilisateur.email}</div>
		</div>

		<div class="row">
			<label class="col-sm-2">Login</label>
			<div class="col-sm-10">${utilisateur.login}</div>
		</div>

		<div class="row">
			<label class="col-sm-2">Password</label>
			<div class="col-sm-10">${utilisateur.password}</div>
		</div>

		<div class="row">
			<label class="col-sm-2">Enabled</label>
			<div class="col-sm-10">${utilisateur.enabled}</div>
		</div>

		<div class="row">
			<label class="col-sm-2">Roles</label>
			<div class="col-sm-10">
				<c:forEach var="role" items="${utilisateur.appRoles}">
				${role.idRole} - ${role.name}<br />
				</c:forEach>
			</div>
		</div>

		<div>
			<spring:url value="/admin/user/${utilisateur.idUser}/update" var="url" />
			<button class="btn btn-primary" onclick="location.href='${url}'">Modifier</button>
		</div>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>