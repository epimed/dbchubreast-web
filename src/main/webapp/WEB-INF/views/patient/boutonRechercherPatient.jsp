<form:form class="form-inline pull-right" method="POST"
	commandName="form" action="${pageContext.request.contextPath}/patients">

	<div class="form-group">
		<input type="text" class="form-control" name="text" id="text"
			value="${text}" size=50 placeholder="Nom, Prénom, TK, RCP ou ID">
	</div>

	<div class="form-group">
		<button type="submit" class="btn btn-default btn-sm" name="button"
			value="search">
			<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
			Rechercher
		</button>
	</div>
</form:form>