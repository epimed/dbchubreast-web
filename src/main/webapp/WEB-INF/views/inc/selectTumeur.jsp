<!-- Liste des tumeurs -->
<div class="form-group">
	<label>Tumeur</label> <select class="form-control" id="idTumeur"
		name="idTumeur" onchange="this.form.submit()">
		<option value="">--- Sélectionner ---</option>
		<c:forEach var="tumeur" items="${listTumeurs}">
			<c:choose>
				<c:when test="${not empty tumeur and tumeur.idTumeur==idTumeur}">
					<option value="${tumeur.idTumeur}" selected="selected">${tumeur.idTumeur}
						- ${tumeur.dateDiagnostic} - ${tumeur.chuTopographie.idTopographie} - ${tumeur.cote}</option>
				</c:when>
				<c:otherwise>
					<option value="${tumeur.idTumeur}">${tumeur.idTumeur}
						- ${tumeur.dateDiagnostic} - ${tumeur.chuTopographie.idTopographie} - ${tumeur.cote}</option>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		</select>
</div>