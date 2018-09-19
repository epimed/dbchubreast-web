package dbchubreast_web.service.exporter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class NullExporter implements IExporter {

	private List<String> header = new ArrayList<String>();
	private List<Object> data = new ArrayList<Object>();


	public void generate() {
		this.generateHeader();
		this.generateData();
	}

	private void generateHeader() {
		header.clear();
		header.add("no data");
	}
	
	private void generateData() {
		data.clear();
	}


	@Override
	public List<String> getHeader() {
		return this.header;
	}

	@Override
	public List<Object> getData() {
		return this.data;
	}

}
