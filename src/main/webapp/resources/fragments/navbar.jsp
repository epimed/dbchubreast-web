<!-- User context -->
<sec:authentication var="user" property="principal" />



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

		<!-- Menu -->

		<div id="navbar" class="navbar-collapse collapse">

			<sec:authorize access="isAuthenticated()">

				<c:forEach var="menu" items="${user.listMenus}">

					<ul class="nav navbar-nav">

						<li class="dropdown"><a href="${menu.path}"
							class="dropdown-toggle" data-toggle="dropdown" role="button"
							aria-haspopup="true" aria-expanded="false">${menu.title}<span
								class="caret"></span></a>

							<ul class="dropdown-menu">
								<c:forEach var="submenu" items="${menu.subMenus}">
									<li><a
										href="${pageContext.request.contextPath}${submenu.path}">${submenu.title}</a></li>
								</c:forEach>
							</ul></li>
					</ul>
				</c:forEach>

				<ul class="nav navbar-nav navbar-right">
					<li><a href="${pageContext.request.contextPath}/logout"
						class="navbar-link">Déconnexion</a></li>
				</ul>
				<p class="navbar-text navbar-right">${user.firstName}
					${user.lastName}</p>
			</sec:authorize>

		</div>

		<!--/.nav-collapse -->
	</div>
</nav>


