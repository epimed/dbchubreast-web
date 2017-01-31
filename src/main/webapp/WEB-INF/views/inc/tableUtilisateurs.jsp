<c:if test="${not empty listUtilisateurs}">

	<table class="table table-striped">
		<thead>
			<tr>
				<th>ID</th>
				<th>Nom</th>
				<th>Prénom</th>
				<th>Email</th>
				<th>Login</th>
				<th>Enabled</th>
				<th>Roles</th>
				<th>Actions</th>
			</tr>
		</thead>


		<c:forEach var="utilisateur" items="${listUtilisateurs}">
			<tr>
				<td><a
					href="${pageContext.request.contextPath}/admin/user/${utilisateur.idUser}">${utilisateur.idUser}</a></td>
				<td>${utilisateur.lastName}</td>
				<td>${utilisateur.firstName}</td>
				<td>${utilisateur.email}</td>
				<td>${utilisateur.login}</td>
				<td>${utilisateur.enabled}</td>
				<td><c:forEach var="role" items="${utilisateur.appRoles}">
				${role.idRole} - ${role.name}<br />
					</c:forEach></td>

				<td><spring:url
						value="/admin/user/${utilisateur.idUser}/update" var="updateUrl" />
					<spring:url value="/admin/user/${utilisateur.idUser}/delete" var="deleteUrl" />
					<button class="btn btn-primary"
						onclick="location.href='${updateUrl}'">Modifier</button>
					<button class="btn btn-danger"
						onclick="location.href='${deleteUrl}'">Supprimer</button>
						</td>
			</tr>
		</c:forEach>

	</table>

</c:if>