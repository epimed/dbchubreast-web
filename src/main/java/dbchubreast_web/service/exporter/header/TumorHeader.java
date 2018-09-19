package dbchubreast_web.service.exporter.header;

import java.util.ArrayList;
import java.util.List;

public class TumorHeader implements IHeader {

	private List<String> list = new ArrayList<String>();
	
	@Override
	public List<String> getListOfItems() {
		list.clear();

		list.add("id_patient");

		list.add("id_tumeur");
		list.add("date_diagnostic_tumeur");
		list.add("age_diagnostic");
		list.add("cote");
		list.add("dfs_months");
		list.add("os_months");
		list.add("relapsed");
		list.add("dead");
		list.add("categorie_tumeur");
		list.add("evolution_tumeur");
		
		return list;
	}

	
}
