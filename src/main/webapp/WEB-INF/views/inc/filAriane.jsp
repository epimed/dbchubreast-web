<!-- Patient ID -->
<div>
	<c:if test="${not empty patient and not empty patient.idPatient}">
		<a
			href="${pageContext.request.contextPath}/patient/${patient.idPatient}">Patient&nbsp;${patient.idPatient}</a>&nbsp;>
</c:if>

<!-- Tumeurs -->
<c:if test="${not empty listTumeurs or not empty patient or not empty tumeur}">
		<a
			href="${pageContext.request.contextPath}/patient/${patient.idPatient}/tumeurs">Tumeurs</a>&nbsp;>
</c:if>

<!-- Tumeur ID -->
<c:if test="${not empty tumeur and not empty tumeur.idTumeur}">
		<a
			href="${pageContext.request.contextPath}/tumeur/${tumeur.idTumeur}">Tumeur&nbsp;${tumeur.idTumeur}</a>&nbsp;>
</c:if>


<!-- Prelevements -->
<c:if test="${not empty listPrelevements or not empty prelevement}">
		<a
			href="${pageContext.request.contextPath}/patient/${patient.idPatient}/prelevements">Prélèvements</a>&nbsp;>
</c:if>

<!-- Prelevement ID -->
<c:if test="${not empty prelevement and not empty prelevement.idPrelevement}">
		<a
			href="${pageContext.request.contextPath}/prelevement/${prelevement.idPrelevement}">Prélèvement&nbsp;${prelevement.idPrelevement}</a>&nbsp;>
</c:if>

<!-- Traitements -->
<c:if test="${not empty listTraitements or not empty traitement}">
		<a
			href="${pageContext.request.contextPath}/patient/${patient.idPatient}/traitements">Traitements</a>&nbsp;>
</c:if>

<!-- Traitement ID -->
<c:if test="${not empty traitement and not empty traitement.idTraitement}">
		<a
			href="${pageContext.request.contextPath}/traitement/${traitement.idTraitement}">Traitement&nbsp;${traitement.idTraitement}</a>&nbsp;>
</c:if>

</div>