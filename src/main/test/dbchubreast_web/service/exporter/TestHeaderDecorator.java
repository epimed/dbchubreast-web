package dbchubreast_web.service.exporter;
import dbchubreast_web.service.exporter.header.IHeader;
import dbchubreast_web.service.exporter.header.PhaseHeaderDecorator;
import dbchubreast_web.service.exporter.header.PrelevementHeaderDecorator;
import dbchubreast_web.service.exporter.header.TraitementHeaderDecorator;
import dbchubreast_web.service.exporter.header.TumorHeader;

public class TestHeaderDecorator {

	public static void main(String[] args) {
		
		IHeader tumorHeader = new TumorHeader();
		IHeader phaseHeader = new PhaseHeaderDecorator(tumorHeader);
		IHeader prelevementHeader = new PrelevementHeaderDecorator(phaseHeader);
		IHeader traitementHeader = new TraitementHeaderDecorator(phaseHeader);
		
		System.out.println("TumorHeader \t" + tumorHeader.getListOfItems());
		System.out.println("PhaseHeader \t" + phaseHeader.getListOfItems());
		System.out.println("PrelevementHeader \t" + prelevementHeader.getListOfItems());
		System.out.println("TraitementHeader \t" + traitementHeader.getListOfItems());
	}

}
