package dbchubreast_web.service.exporter.header;

import java.util.List;

public class PhaseHeaderDecorator implements IHeaderDecorator {
	
	private IHeader header;

	public PhaseHeaderDecorator(IHeader header) {
		this.header = header;
	}
	
	@Override
	public List<String> getListOfItems() {
		List<String> list = header.getListOfItems();
		list.add("id_phase");
		list.add("type_phase");
		list.add("date_phase");
		list.add("nature_diagnostic");
		list.add("metastases");
		list.add("code_topographie");
		list.add("topographie");
		return list;
	}

}
