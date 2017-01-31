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

		<!-- Fil d'Ariane -->
		<%@ include file="../inc/filAriane.jsp"%>

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

			<!-- H1 Patient -->
			<%@ include file="../inc/h1Patient.jsp"%>

			<h2>Détail du prélèvement</h2>

			<br />

			<!-- Prelevement ID -->
			<div class="row">
				<label class="col-sm-2">ID prélèvement</label>
				<div class="col-sm-10">${prelevement.idPrelevement}</div>
			</div>

			<!-- Tumeur ID -->
			<div class="row">
				<label class="col-sm-2">ID tumeur</label>
				
				<div class="col-sm-10">${prelevement.chuPhaseTumeur.chuTumeur.idTumeur}</div>
			</div>

			<!-- Phase tumeur ID -->
			<div class="row">
				<label class="col-sm-2">ID phase de la tumeur</label>
				<div class="col-sm-10">${prelevement.chuPhaseTumeur.idPhase} (phase ${prelevement.chuPhaseTumeur.chuTypePhase.nom})</div>
			</div>

			<!-- Type de prelevement -->
			<div class="row">
				<label class="col-sm-2">Type de prélèvement</label>
				<div class="col-sm-10">${prelevement.chuTypePrelevement.nom}</div>
			</div>

			<!-- Date de prelevement -->
			<div class="row">
				<label class="col-sm-2">Date de prélèvement</label>
				<div class="col-sm-10">${prelevement.datePrelevement}</div>
			</div>

			<!-- Site de prelevement -->
			<div class="row">
				<label class="col-sm-2">Site de prélèvement</label>
				<div class="col-sm-10">${prelevement.sitePrelevement}</div>
			</div>

			<!-- Morphologie -->
			<div class="row">
				<label class="col-sm-2">Morphologie ICD-O</label>
				<div class="col-sm-10">${prelevement.chuMorphologie.idMorphologie}
					- ${prelevement.chuMorphologie.nomFr} / ${prelevement.chuMorphologie.nomEn}</div>
			</div>

			<!-- Histologie -->
			<div class="row">
				<label class="col-sm-2">Histologie</label>
				<div class="col-sm-10">${prelevement.typeHistologique}</div>
			</div>

			<!-- Association CIS -->
			<div class="row">
				<label class="col-sm-2">Association "carcinoma in situ"
					(CIS)</label>
				<div class="col-sm-10">${prelevement.associationCis}</div>
			</div>

			<!-- Biomarqueurs -->
			
			<h3>Biomarqueurs:</h3>
			<c:forEach var="prelBio" items="${listPrelevementBiomarqueurs}">

				<div class="row">
					<label class="col-sm-2">${prelBio.chuBiomarqueur.nom}</label>
					<div class="col-sm-10"><c:out value="${prelBio.valeur}" />
						<c:if test="${ not empty prelBio.statut}">(${prelBio.statut})</c:if>
					</div>
				</div>

			</c:forEach>
		</div>
		
		<p></p>
		<div>
			<spring:url value="/prelevement/${prelevement.idPrelevement}/update" var="url" />
			<button class="btn btn-primary" onclick="location.href='${url}'">Modifier</button>
		</div>
		
		
	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>