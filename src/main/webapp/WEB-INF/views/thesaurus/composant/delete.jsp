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
			<p></p>
			<div class="alert alert-${css} alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>${msg}</strong>
			</div>
		</c:if>


		<div>

			<c:choose>
				<c:when test="${not empty listProtocoles}">

					<!-- Le composant est utilise, il ne peut pas etre supprime -->

					<!--  Panel  -->
					<div class="panel panel-danger">
						<div class="panel-heading">
							<h3 class="panel-title">
								<span class="glyphicon glyphicon-exclamation-sign"
									aria-hidden="true"></span> Le composant ne peut pas être
								supprimé !
							</h3>
						</div>
						<div class="panel-body">
							<p>
								Le composant <span class="lead">${composant.nomInternational} ${composant.nomCommercial}</span> ne peut
								pas être supprimé car il est actuellement utilisé dans les
								protocoles listés ci-dessous.
							</p>

							<p>Pour pouvoir supprimer ce composant, il est d'abord
								nécessaire de supprimer ou modifier tous les protocoles et les traitements qui en
								dépendent.</p>

							<p>
								<spring:url value="/thesaurus/composants" var="urlComposants" />
								<button class="btn-lg btn-link"
									onclick="location.href='${urlComposants}'">Retour à la
									liste des composants</button>
							</p>
							
							<p>
								<spring:url value="/thesaurus/protocoles" var="urlProtocoles" />
								<button class="btn-lg btn-link"
									onclick="location.href='${urlProtocoles}'">Retour à la
									liste des protocoles</button>
							</p>
							
						</div>
					</div>


					<!-- Table de protcoles qui utilisent ce traitement  -->

					<table class="table table-bordered">
						<thead>
							<tr class="default">
								<th>#ID</th>
								<th>Code</th>
								<th>Nom</th>
							</tr>
						</thead>

						<c:forEach var="protocole" items="${listProtocoles}">

							<tr>
								<td>${protocole.idProtocole}</td>
								<td>${protocole.code}</td>
								<td>${protocole.nom}</td>
							</tr>
						</c:forEach>
					</table>

				</c:when>
				<c:otherwise>
					<!-- Le composant n'est pas utilise, il peut etre supprime -->

					<h1>Supprimer un composant</h1>
					<p>Confirmez-vous la suppression du composant suivant ?</p>
					<p>
						<span class="lead">${composant.nomInternational} ${composant.nomCommercial}</span>
					</p>

					<form:form class="form-horizontal" method="post"
						modelAttribute="protocole"
						action="${pageContext.request.contextPath}/thesaurus/composant/${composant.idComposant}/delete">

						<!-- Buttons -->
						<div class="pull-left">
							<button type="submit" class="btn btn-danger" name="button"
								value="delete">Supprimer</button>

							<button type="submit" class="btn btn-default" name="button"
								value="cancel">Annuler</button>
						</div>

					</form:form>

				</c:otherwise>
			</c:choose>


		</div>



	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>