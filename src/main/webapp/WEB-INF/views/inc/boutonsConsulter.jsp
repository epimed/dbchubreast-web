
<p></p>

<div>
	<spring:url value="/patient/${patient.idPatient}/tumeurs"
		var="urlTumeurs" />
	<button class="btn-sm btn-info" onclick="location.href='${urlTumeurs}'">Consulter
		les tumeurs de ce patient</button>

</div>

<p></p>

<div>

	<spring:url value="/patient/${patient.idPatient}/prelevements"
		var="urlPrel" />
	<button class="btn-sm btn-info" onclick="location.href='${urlPrel}'">Consulter
		les prélèvements de ce patient</button>

</div>

<p></p>

<div>

	<spring:url value="/patient/${patient.idPatient}/traitements"
		var="listTrait" />
	<button class="btn-sm btn-info" onclick="location.href='${listTrait}'">Consulter
		les traitements de ce patient</button>
</div>

<p></p>

<div>

	<spring:url value="/patients"
		var="urlPatients" />
	<button class="btn-sm btn-default" onclick="location.href='${urlPatients}'">Afficher
		la liste des patients</button>
</div>

<p></p>
