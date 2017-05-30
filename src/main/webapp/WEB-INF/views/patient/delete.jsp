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
				<c:when test="${not empty listTumeurs}">

					<!-- Le protocole est utilise, il ne peut pas etre supprime -->

					<!--  Panel  -->
					<div class="panel panel-danger">
						<div class="panel-heading">
							<h3 class="panel-title">
								<span class="glyphicon glyphicon-exclamation-sign"
									aria-hidden="true"></span> Le patient ne peut pas être supprimé
								!
							</h3>
						</div>
						<div class="panel-body">
							<p>
								Le patient <span class="lead">${patient.prenom}
									${patient.nom}</span> ne peut pas être supprimé car il existe des
								tumeurs qui sont attribuées à ce patient.
							</p>

							<p>Pour pouvoir supprimer ce patient, il est d'abord
								nécessaire de supprimer ou modifier toutes les tumeurs
								correspondantes.</p>

							<p>
								<spring:url value="/patients" var="urlPatients" />
								<button class="btn-lg btn-link"
									onclick="location.href='${urlPatients}'">Retour à la
									liste des patients</button>
							</p>
						</div>
					</div>


					<!-- Table de traitements qui utilisent le protocole  -->
					<%@ include file="../tumeur/tableTumeurs.jsp"%>


				</c:when>
				<c:otherwise>
					<!-- Le protocole n'est pas utilise, il peut etre supprime -->

					<h1>Supprimer un patient</h1>
					<p>Confirmez-vous la suppression du patient suivant ?</p>
					<p>
						<span class="lead"> ${patient.idPatient} - ${patient.nom}
							${patient.prenom} <fmt:formatDate pattern="dd/MM/yyyy"
								value="${patient.dateNaissance}" />
						</span>
					</p>


					<p>
						<spring:url
							value="/patient/${patient.idPatient}/delete?view=${view}&button=delete"
							var="deleteUrl" />
						<button class="btn-sm btn-danger"
							onclick="location.href='${deleteUrl}'">Supprimer</button>
							
							
							<spring:url
							value="/patient/${patient.idPatient}/delete?view=${view}&button=cancel"
							var="cancelUrl" />
						<button class="btn-sm btn-default"
							onclick="location.href='${cancelUrl}'">Annuler</button>
							
					</p>


				</c:otherwise>
			</c:choose>


		</div>



	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>