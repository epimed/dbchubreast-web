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

		<!-- H1 Patient -->
		<%@ include file="../inc/h1Patient.jsp"%>

		<c:choose>
			<c:when test="${empty tumeur or empty tumeur.idTumeur}">
				<h2>Ajouter une tumeur</h2>
			</c:when>
			<c:otherwise>
				<h2>Modifier une tumeur</h2>
			</c:otherwise>
		</c:choose>

		<form:form class="form-horizontal" method="post"
			modelAttribute="formTumeurInitiale"
			action="${pageContext.request.contextPath}/tumeur/update">

			<form:hidden path="idTumeur" />

			<div class="form-group">
				<label class="col-sm-2 control-label">Date du diagnostic</label>
				<div class="col-sm-10">
					<form:input class="form-control" path="dateDiagnostic" type="date" />
					<form:errors path="dateDiagnostic" />
				</div>
			</div>


			<div class="form-group">
				<label class="col-sm-2 control-label">Age au diagnostic</label>
				<div class="col-sm-1">
					<form:input class="form-control" path="ageDiagnostic" type="text" />
					<form:errors path="ageDiagnostic" />
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label">Topographie ICD-O</label>
				<div class="col-sm-10">
					<form:select class="form-control" path="idTopographie">
						<form:option value="NONE" label="--- Sélectionner ---" />
						<c:forEach var="topo" items="${listTopographies}">
							<form:option value="${topo.idTopographie}"
								label="${topo.idTopographie} - ${topo.nomFr} / ${topo.nomEn}" />
						</c:forEach>
					</form:select>
					<form:errors path="idTopographie" class="control-label" />
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label">Côté</label>
				<div class="col-sm-10">
					<label class="checkbox-inline"> <form:checkbox path="cote"
							value="G" /> Gauche (G)
					</label> <label class="checkbox-inline"> <form:checkbox path="cote"
							value="D" /> Droit (D)
					</label>
					<form:errors path="ageDiagnostic" />
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-2 control-label">Dernière nouvelle</label>
				<div class="col-sm-10">
					<form:input class="form-control" path="dateEvolution" type="date" />
					<form:errors path="dateEvolution" />
				</div>
			</div>

			<!-- Button -->

			<p></p>

			<div class="form-group">
				<div class="col-offset-2 col-10">
					<p></p>
					<button type="submit" class="btn-lg btn-primary pull-right">Enregistrer</button>
				</div>
			</div>


		</form:form>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>