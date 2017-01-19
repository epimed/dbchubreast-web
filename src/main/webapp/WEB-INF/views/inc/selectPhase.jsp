<!-- Liste des phases de tumeur -->
<div class="form-group">
	<label>Phase</label> <select class="form-control" id="idPhase"
		name="idPhase" onchange="this.form.submit()">
		<option value="">--- Sélectionner ---</option>
		<c:forEach var="phase" items="${listPhases}">
			<c:choose>
				<c:when test="${not empty phase and phase.idPhase==idPhase}">
					<option value="${phase.idPhase}" selected="selected">${phase.idPhase}
						- ${phase.chuTypePhase.nom} - ${phase.dateDiagnostic}</option>
				</c:when>
				<c:otherwise>
					<option value="${phase.idPhase}">${phase.idPhase}
						- ${phase.chuTypePhase.nom} - ${phase.dateDiagnostic}</option>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		</select>
</div>