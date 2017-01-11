<div>
	<c:if test="${not empty patient}">
		<h1>${patient.nom}&nbsp;${patient.prenom}&nbsp;${patient.dateNaissance}</h1>
	</c:if>
</div>