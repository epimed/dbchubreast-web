package dbchubreast_web.service.exporter;

import java.util.List;

public interface IExporter {
	public void generate();
	public List<String> getHeader();
	public List<Object> getData();
}
