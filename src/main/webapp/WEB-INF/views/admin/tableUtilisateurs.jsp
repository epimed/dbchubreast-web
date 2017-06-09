<c:if test="${not empty listUtilisateurs}">

	<table class="table table-striped">
		<thead>
			<tr>
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
				<td>${utilisateur.lastName}</td>
				<td>${utilisateur.firstName}</td>
				<td>${utilisateur.email}</td>
				<td>${utilisateur.login}</td>
				<td>${utilisateur.enabled}</td>
				<td><c:forEach var="role" items="${utilisateur.appRoles}">
				${role.idRole}<br />
					</c:forEach></td>

				<td><spring:url
						value="/admin/user/${utilisateur.idUser}" var="showUrl" />
					<button class="btn-sm btn-info"
						onclick="location.href='${showUrl}'">Consulter</button> 
						
						
						
					<spring:url
						value="/admin/user/${utilisateur.idUser}/update" var="updateUrl" />
					<button class="btn-sm btn-primary"
						onclick="location.href='${updateUrl}'">Modifier</button> 
						
						
						<spring:url
						value="/admin/user/${utilisateur.idUser}/delete" var="deleteUrl" />
					<button class="btn-sm btn-danger"
						onclick="location.href='${deleteUrl}'">Supprimer</button></td>
			</tr>
		</c:forEach>

	</table>

</c:if>