package dbchubreast_web.service.consistency;

import java.util.List;
import java.util.Map;

import dbchubreast_web.entity.ChuPatient;

public interface ConsistencyService {

	public void checkConsistency(List<ChuPatient> listPatients);
	public Map<String, List<String>> getMapMessages();
	public void clearMessages();
}
