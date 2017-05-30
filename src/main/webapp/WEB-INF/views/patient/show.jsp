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

		<h1>Détail du patient</h1>
		<br />

		<div class="row">
			<label class="col-sm-2">ID patient</label>
			<div class="col-sm-10">${patient.idPatient}</div>
		</div>

		<div class="row">
			<label class="col-sm-2">Numéro TK</label>
			<div class="col-sm-10">${patient.tk}</div>
		</div>

		<div class="row">
			<label class="col-sm-2">RCP</label>
			<div class="col-sm-10">${patient.rcp}</div>
		</div>

		<div class="row">
			<label class="col-sm-2">Prénom</label>
			<div class="col-sm-10">${patient.prenom}</div>
		</div>

		<div class="row">
			<label class="col-sm-2">Nom</label>
			<div class="col-sm-10">${patient.nom}</div>
		</div>

		<div class="row">
			<label class="col-sm-2">Sexe</label>
			<div class="col-sm-10">${patient.sexe}</div>
		</div>

		<div class="row">
			<label class="col-sm-2">Date de naissance</label>
			<div class="col-sm-10">
				<fmt:formatDate pattern="dd/MM/yyyy"
					value="${patient.dateNaissance}" />
			</div>

		</div>

		<div class="row">
			<label class="col-sm-2">Date de décès</label>
			<div class="col-sm-10">
				<fmt:formatDate pattern="dd/MM/yyyy" value="${patient.dateDeces}" />
			</div>
		</div>

		<div class="row">
			<label class="col-sm-2">Cause de décès</label>
			<div class="col-sm-10">${patient.causeDeces}</div>
		</div>

		<div class="row">
			<label class="col-sm-2">Statut BRCA</label>
			<div class="col-sm-10">${patient.statutBrca}</div>
		</div>

		<div class="row">
			<label class="col-sm-2">Consentement</label>
			<div class="col-sm-10">
				<c:if
					test="${not empty patient.consentement and patient.consentement}">oui</c:if>
				<c:if
					test="${not empty patient.consentement and not patient.consentement}">non</c:if>
			</div>
		</div>

		<p></p>

		<div>
			<spring:url value="/patient/${patient.idPatient}/update"
				var="urlUpdate" />
			<button class="btn-sm btn-primary"
				onclick="location.href='${urlUpdate}'">Modifier</button>
				
				<spring:url
						value="/patient/${patient.idPatient}/delete?view=${pageContext.request.servletPath}" var="deleteUrl" />
					<button class="btn-sm btn-danger"
						onclick="location.href='${deleteUrl}'">Supprimer</button></td>
				
		</div>

		<!-- Boutons consulter -->
		<%@ include file="../inc/boutonsConsulter.jsp"%>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>