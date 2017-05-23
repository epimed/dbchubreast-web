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


		<c:choose>
			<c:when test="${formComposant['new']}">
				<h1>Ajouter un composant</h1>
			</c:when>
			<c:otherwise>
				<h1>Modifier un composant</h1>
			</c:otherwise>
		</c:choose>

		<form:form class="form-horizontal" method="post"
			modelAttribute="formComposant"
			action="${pageContext.request.contextPath}/thesaurus/composant/update">

			<!-- Hidden attributes -->
			<form:hidden path="idComposant" />
			<form:hidden path="idMethode" />
			<form:hidden path="nomMethode" />

			<!-- Methode -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Methode</label>
				<div class="col-sm-1 control-label">${formComposant.nomMethode}</div>
			</div>

			<!-- Nom international -->
			<spring:bind path="nomInternational">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Libellé *</label>
					<div class="col-sm-10">
						<form:input class="form-control" path="nomInternational" type="text" />
						<form:errors path="nomInternational" class="control-label" />
					</div>

				</div>
			</spring:bind>
			
			<!-- Nom commercial -->
			<spring:bind path="nomCommercial">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Nom commercial</label>
					<div class="col-sm-10">
						<form:input class="form-control" path="nomCommercial" type="text" />
						<form:errors path="nomCommercial" class="control-label" />
					</div>

				</div>
			</spring:bind>
			
			<!-- Classe -->
			<spring:bind path="classe">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Classe</label>
					<div class="col-sm-10">
						<form:input class="form-control" path="classe" type="text" />
						<form:errors path="classe" class="control-label" />
					</div>
				</div>
			</spring:bind>
			
			<!-- Action -->
			<spring:bind path="action">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Mode d'action</label>
					<div class="col-sm-10">
						<form:input class="form-control" path="action" type="text" />
						<form:errors path="action" class="control-label" />
					</div>
				</div>
			</spring:bind>

			
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