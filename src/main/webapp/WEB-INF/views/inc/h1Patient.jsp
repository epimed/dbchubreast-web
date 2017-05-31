<div>
	<c:if test="${not empty patient}">
		<h1>${patient.nom}&nbsp;${patient.prenom}&nbsp;<fmt:formatDate
				pattern="dd/MM/yyyy" value="${patient.dateNaissance}" />

			<c:if test="${not empty patient.dateDeces}">
				-&nbsp;<fmt:formatDate pattern="dd/MM/yyyy"
					value="${patient.dateDeces}" />
			</c:if>

		</h1>
	</c:if>
</div>