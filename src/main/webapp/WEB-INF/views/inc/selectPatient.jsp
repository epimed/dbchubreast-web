<!-- Liste des patients -->
<div class="form-group">
	<label>Patient</label> <select class="form-control" id="idPatient"
		name="idPatient" onchange="this.form.submit()">
		<option value="">--- Sélectionner ---</option>
		<c:forEach var="patient" items="${listPatients}">
			<c:choose>
				<c:when test="${not empty patient and patient.idPatient==idPatient}">
					<option value="${patient.idPatient}" selected="selected">${patient.idPatient}
						- ${patient.nom} ${patient.prenom} <fmt:formatDate pattern = "dd/MM/yyyy" value = "${patient.dateNaissance}"/></option>
				</c:when>
				<c:otherwise>
					<option value="${patient.idPatient}">${patient.idPatient}
						- ${patient.nom} ${patient.prenom} <fmt:formatDate pattern = "dd/MM/yyyy" value = "${patient.dateNaissance}"/></option>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		</select>
</div>