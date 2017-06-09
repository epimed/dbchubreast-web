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

		<p></p>

		<div>
			<spring:url value="/admin/user/${utilisateur.idUser}/update" var="urlUpdate" />
			<button class="btn-sm btn-primary" onclick="location.href='${urlUpdate}'">Modifier</button>
			
			<spring:url value="/admin/user/${utilisateur.idUser}/delete" var="urlDelete" />
			<button class="btn-sm btn-danger" onclick="location.href='${urlDelete}'">Supprimer</button>
		</div>
		
		<p></p>
		
		<div>
			<spring:url value="/admin/users" var="urlList" />
			<button class="btn-sm btn-info" onclick="location.href='${urlList}'">Consulter la liste d'utilisateurs</button>
		</div>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>