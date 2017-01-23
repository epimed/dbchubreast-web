<!-- Static navbar -->
<nav class="navbar navbar-inverse navbar-static-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${pageContext.request.contextPath}/">BD
				"Cancer du sein" <span class="glyphicon glyphicon-home"
				aria-hidden="true" title="Epimed"></span>
			</a>
		</div>

		<div id="navbar" class="navbar-collapse collapse">

			<ul class="nav navbar-nav">

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Patients<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="${pageContext.request.contextPath}/patient">Rechercher un patient</a></li>
						<li><a href="${pageContext.request.contextPath}/patient/add">Ajouter un patient</a></li>
						<li><a href="${pageContext.request.contextPath}/patients">Liste des patients</a></li>
					</ul></li>
					
					<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Tumeurs<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="${pageContext.request.contextPath}/tumeur">Rechercher une tumeur</a></li>
					</ul></li>
					
					<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Prélèvements<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="${pageContext.request.contextPath}/prelevement">Rechercher un prélèvement</a></li>
					</ul></li>
					
					<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Exports<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="${pageContext.request.contextPath}/download/patients">Exporter les patients</a></li>
						<li><a href="${pageContext.request.contextPath}/download/prelevements">Exporter les prélèvements</a></li>
						<li><a href="${pageContext.request.contextPath}/download/biomarqueurs">Exporter les biomarqueurs</a></li>
					</ul></li>

			</ul>

		</div>
		<!--/.nav-collapse -->
	</div>
</nav>
