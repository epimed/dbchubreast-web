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

		<!-- Fil d'Ariane -->
		<%@ include file="../inc/filAriane.jsp"%>


		<!-- Dismissible alert -->
		<%@ include file="../inc/dismissibleAlert.jsp"%>


		<div>

			<!-- H1 Patient -->
			<%@ include file="../inc/h1Patient.jsp"%>

			<h2>Détail de la tumeur</h2>

			<br />

			<div class="row">
				<label class="col-sm-3">ID tumeur</label>
				<div class="col-sm-9">${tumeur.idTumeur}</div>
			</div>

			<div class="row">
				<label class="col-sm-3">Date du diagnostic</label>
				<div class="col-sm-9">
					<fmt:formatDate pattern="dd/MM/yyyy"
						value="${tumeur.dateDiagnostic}" />
				</div>
			</div>

			<div class="row">
				<label class="col-sm-3">Age au diagnostic</label>
				<div class="col-sm-9">${tumeur.ageDiagnostic}</div>
			</div>

			<div class="row">
				<label class="col-sm-3">IMC au diagnostic</label>
				<div class="col-sm-9">${tumeur.imcDiagnostic}</div>
			</div>

			<div class="row">
				<label class="col-sm-3">Côté</label>
				<div class="col-sm-9">${tumeur.cote}</div>
			</div>

			<div class="row">
				<label class="col-sm-3">Dernière nouvelle</label>
				<div class="col-sm-9">
					<fmt:formatDate pattern="dd/MM/yyyy"
						value="${patient.dateEvolution}" />
				</div>
			</div>

			<div class="row">
				<label class="col-sm-3">Statut à la dernière nouvelle</label>
				<div class="col-sm-9">${tumeur.chuEvolution.nom}</div>
			</div>

			<div class="row">
				<label class="col-sm-3">Profondeur</label>
				<div class="col-sm-9">
					<c:if
						test="${not empty listPhasesInitiales and not empty listPhasesInitiales[0].profondeur}">
				${listPhasesInitiales[0].profondeur}
				</c:if>
				</div>
			</div>

			<div class="row">
				<label class="col-sm-3">Stade TNM et taille (mm)</label>
				<div class="col-sm-9">
					<c:if
						test="${not empty listPhasesInitiales and not empty listPhasesInitiales[0].chuTnms}">
						<c:forEach var="tnm" items="${listPhasesInitiales[0].chuTnms}">
						${tnm.t} &nbsp; ${tnm.n} &nbsp; ${tnm.m} &nbsp; ${tnm.type} Taille=${tnm.taille}&nbsp;
					</c:forEach>

					</c:if>
				</div>
			</div>

			<div class="row">
				<label class="col-sm-3">Consentement</label>
				<div class="col-sm-9">
					<c:if
						test="${not empty tumeur.consentement and tumeur.consentement}">oui</c:if>
					<c:if
						test="${not empty tumeur.consentement and not tumeur.consentement}">non</c:if>
				</div>
			</div>

			<div class="row">
				<label class="col-sm-3">Catégorie</label>
				<div class="col-sm-9">${tumeur.categorie}</div>
			</div>

			<div class="row">
				<label class="col-sm-3">Triple negative</label>
				<div class="col-sm-9">
					<c:if
						test="${not empty tumeur.tripleNegative and tumeur.tripleNegative}">oui</c:if>
					<c:if
						test="${not empty tumeur.tripleNegative and not tumeur.tripleNegative}">non</c:if>
				</div>
			</div>

			<div class="row">
				<label class="col-sm-3">Survie (mois)</label>
				<div class="col-sm-9">
					<c:if test="${not empty tumeur.osMonths}">globale : ${tumeur.osMonths}</c:if>
					<c:if
						test="${not empty tumeur.osMonths and not empty tumeur.dfsMonths}">, &nbsp;</c:if>
					<c:if test="${not empty tumeur.dfsMonths}">sans rechute : ${tumeur.dfsMonths}</c:if>
				</div>
			</div>


		</div>

		<!-- Phases de la tumeur -->
		<div>
			<%@ include file="tablePhasesInitiales.jsp"%>
		</div>
		<div>
			<%@ include file="tablePhasesRechutes.jsp"%>
			<p></p>

			<spring:url value="/tumeur/${tumeur.idTumeur}/rechute/add" var="url" />
			<button class="btn-sm btn-warning" onclick="location.href='${url}'">Ajouter
				une rechute</button>

		</div>

		<!-- Boutons consulter -->
		<%@ include file="../inc/boutonsConsulter.jsp"%>


	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>