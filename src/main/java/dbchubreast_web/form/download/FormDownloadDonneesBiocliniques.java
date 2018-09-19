package dbchubreast_web.form.download;

public class FormDownloadDonneesBiocliniques {

	private String typeDonnees = "prelevements";

	public String getTypeDonnees() {
		return typeDonnees;
	}

	public void setTypeDonnees(String typeDonnees) {
		this.typeDonnees = typeDonnees;
	}

	@Override
	public String toString() {
		return "FormDownloadDonneesBiocliniques [typeDonnees=" + typeDonnees + "]";
	}
	
	
}
