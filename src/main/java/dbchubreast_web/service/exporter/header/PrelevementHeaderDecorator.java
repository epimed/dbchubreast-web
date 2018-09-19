package dbchubreast_web.service.exporter.header;

import java.util.List;

public class PrelevementHeaderDecorator implements IHeaderDecorator {
	
	private IHeader header;

	public PrelevementHeaderDecorator(IHeader header) {
		this.header = header;
	}
	
	@Override
	public List<String> getListOfItems() {
		
		List<String> list = header.getListOfItems();
		
		list.add("id_prelevement");
		list.add("tk");
		list.add("type_prelevement");
		list.add("site_prelevement");
		list.add("date_prelevement");
		list.add("code_morphologie");
		list.add("morphologie");
		list.add("histologie");
		list.add("association_cis");

		list.add("ganglions_envahis");
		list.add("rupture_capsulaire");
		list.add("recoupe");
		list.add("necrose");
		list.add("emboles");
		list.add("sbr");
		list.add("re");
		list.add("re_statut");
		list.add("rp");
		list.add("rp_statut");
		list.add("her2");
		list.add("her2_statut");
		list.add("ki67");
		list.add("ki67_statut");
		list.add("upa");
		list.add("upa_statut");
		list.add("pai1");
		list.add("pai1_statut");
		
		return list;
	}

}
