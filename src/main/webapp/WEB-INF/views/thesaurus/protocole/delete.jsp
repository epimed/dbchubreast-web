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
				<c:when test="${not empty listTraitements}">

					<!-- Le protocole est utilise, il ne peut pas etre supprime -->

					<!--  Panel  -->
					<div class="panel panel-danger">
						<div class="panel-heading">
							<h3 class="panel-title">
								<span class="glyphicon glyphicon-exclamation-sign"
									aria-hidden="true"></span> Le protocole ne peut pas �tre
								supprim� !
							</h3>
						</div>
						<div class="panel-body">
							<p>
								Le protocole <span class="lead">${protocole.nom}</span> ne peut
								pas �tre supprim� car il est actuellement utilis� dans les
								traitements list�s ci-dessous.
							</p>

							<p>Pour pouvoir supprimer ce protocole, il est d'abord
								n�cessaire de supprimer ou modifier tous les traitements qui en
								d�pendent.</p>

							<p>
								<spring:url value="/thesaurus/protocoles" var="urlProtocoles" />
								<button class="btn-lg btn-link"
									onclick="location.href='${urlProtocoles}'">Retour � la
									liste des protocoles</button>
							</p>
						</div>
					</div>


					<!-- Table de traitements qui utilisent le protocole  -->

					<table class="table table-bordered">
						<thead>
							<tr class="default">
								<th>Date</th>
								<th>Traitement</th>
								<th>Patient</th>
								<th>Actions</th>
							</tr>
						</thead>

						<c:forEach var="traitement" items="${listTraitements}">

							<tr>
								<td><fmt:formatDate pattern="dd/MM/yyyy"
										value="${traitement.dateDebut}" /></td>
								<td>${traitement.chuMethodeTraitement.nom}</td>
								<td>
									${traitement.chuPhaseTumeur.chuTumeur.chuPatient.idPatient} -
									${traitement.chuPhaseTumeur.chuTumeur.chuPatient.prenom}
									${traitement.chuPhaseTumeur.chuTumeur.chuPatient.nom}</td>
								<td><spring:url
										value="/traitement/${traitement.idTraitement}"
										var="traitementUrl" />
									<button class="btn-sm btn-info"
										onclick="location.href='${traitementUrl}'">Consulter
										ce traitement</button></td>
							</tr>
						</c:forEach>

					</table>



				</c:when>
				<c:otherwise>
					<!-- Le protocole n'est pas utilise, il peut etre supprime -->

					<h1>Supprimer un protocole</h1>
					<p>Confirmez-vous la suppression du protocole suivant ?</p>
					<p>
						<span class="lead">${protocole.nom}</span>
					</p>

					<form:form class="form-horizontal" method="post"
						modelAttribute="protocole"
						action="${pageContext.request.contextPath}/thesaurus/protocole/${protocole.idProtocole}/delete">

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