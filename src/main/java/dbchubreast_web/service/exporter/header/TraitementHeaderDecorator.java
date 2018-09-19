package dbchubreast_web.service.exporter.header;

import java.util.List;

public class TraitementHeaderDecorator implements IHeaderDecorator {
	
	private IHeader header;

	public TraitementHeaderDecorator(IHeader header) {
		this.header = header;
	}
	
	@Override
	public List<String> getListOfItems() {
		
		List<String> list = header.getListOfItems();
		
		list.add("id_traitement");
		list.add("type_traitement");
		list.add("methode");
		list.add("code_protocole");
		list.add("protocole");
		list.add("composants");
		list.add("reponse");
		list.add("date_debut");
		list.add("date_fin");
		list.add("dose");
		list.add("nb_cures");
		list.add("ganglion_sentinelle");
		list.add("remarque");

		return list;
	}

}
