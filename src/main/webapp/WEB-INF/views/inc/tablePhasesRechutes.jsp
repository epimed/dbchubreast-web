<c:if test="${not empty listPhasesRechutes}">

	<h3>Rechutes:</h3>

	<table class="table table-bordered">
		<thead>
			<tr class="danger">
				<th>#ID phase rechute</th>
				<th>Date</th>
				<th>Performance status</th>
				<th>Locale</th>
				<th>Métastases</th>
				<th>Nodules</th>
				<th>Actions</th>

			</tr>
		</thead>

		<c:forEach var="phase" items="${listPhasesRechutes}">
			<tr>
				<td>${phase.idPhase}</td>
				<td>${phase.dateDiagnostic}</td>
				<td><abbr title=" ${phase.chuPerformanceStatus.description}">${phase.chuPerformanceStatus.idPs}</abbr></td>
				<td>${phase.locale}</td>
				<td><c:forEach var="metastase" items="${phase.chuMetastases}"
						varStatus="loop">${metastase.nom}<c:if
							test="${not loop.last}">, </c:if>
					</c:forEach></td>
				<td class="warning">${phase.nodules}</td>
				<td>
				<spring:url value="/rechute/${phase.idPhase}/update"
						var="updateUrl" />
					<button class="btn btn-primary"
						onclick="location.href='${updateUrl}'">Modifier</button></td>
			</tr>
			<c:if test="${not empty phase.remarque}">
				<tr>
					<td colspan="7">${phase.remarque}</td>
				</tr>
			</c:if>
		</c:forEach>
	</table>

</c:if>
