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


		<c:choose>
			<c:when test="${formProtocole['new']}">
				<h1>Ajouter un protocole</h1>
			</c:when>
			<c:otherwise>
				<h1>Modifier un protocole</h1>
			</c:otherwise>
		</c:choose>

		<form:form class="form-horizontal" method="post"
			modelAttribute="formProtocole"
			action="${pageContext.request.contextPath}/thesaurus/protocole/update">

			<!-- Hidden attributes -->
			<form:hidden path="idProtocole" />
			<form:hidden path="idMethode" />
			<form:hidden path="nomMethode" />

			<!-- Methode -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Methode</label>
				<div class="col-sm-1 control-label">${formProtocole.nomMethode}</div>
			</div>

			<!-- Code -->
			<spring:bind path="code">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Code</label>
					<div class="col-sm-2">
						<form:input class="form-control" path="code" type="text" />
					</div>
					<div class="col-sm-8">
						<span id="helpBlock" class="help-block">Optionnel, 10
							charactères maxi</span>
					</div>
					<div class="col-sm-2"></div>
					<div class="col-sm-10">
						<form:errors path="code" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<!-- Nom -->
			<spring:bind path="nom">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Nom *</label>
					<div class="col-sm-10">
						<form:input class="form-control" path="nom" type="text" />
						<form:errors path="nom" class="control-label" />
					</div>

				</div>
			</spring:bind>

			<c:if test="${not empty listComposants}">
				<!-- Composants -->
				<spring:bind path="listIdComposants">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-2 control-label">Composants</label>
						<div class="col-sm-10">
							<div class="checkbox">
								<c:forEach var="composant" items="${listComposants}">
									<label> <form:checkbox path="listIdComposants"
											value="${composant.idComposant}" />
										${composant.nomInternational} <b>${composant.nomCommercial}</b>
										<small> (${composant.classe}, ${composant.action})</small>
									</label>
								</c:forEach>
							</div>
							<form:errors path="listIdComposants" />
						</div>
					</div>
				</spring:bind>
			</c:if>

			<!-- Buttons -->
			<div class="pull-right">
				<button type="submit" class="btn btn-primary" name="button"
					value="save">Enregistrer</button>

				<button type="submit" class="btn btn-default" name="button"
					value="cancel">Annuler</button>
			</div>
		</form:form>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>

</body>
</html>