<!-- Patient ID -->
<div>
	<c:if test="${not empty patient and not empty patient.idPatient}">
		<a
			href="${pageContext.request.contextPath}/patient/${patient.idPatient}">Patient&nbsp;${patient.idPatient}</a>&nbsp;>
</c:if>

<!-- Tumeurs -->
<c:if test="${not empty listTumeurs}">
		<a
			href="${pageContext.request.contextPath}/patient/${patient.idPatient}/tumeurs">Tumeurs</a>&nbsp;>
</c:if>

<!-- Tumeur ID -->
<c:if test="${not empty tumeur  and not empty tumeur.idTumeur}">
		<a
			href="${pageContext.request.contextPath}/tumeur/${tumeur.idTumeur}">Tumeur&nbsp;${tumeur.idTumeur}</a>&nbsp;>
</c:if>
</div>