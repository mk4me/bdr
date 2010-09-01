package motion.database.model;


@SuppressWarnings("serial")
public class Measurement extends GenericDescription<MeasurementStaticAttributes>{

	public Measurement() {
		super(MeasurementStaticAttributes.measurementID.name(), EntityKind.measurement);
	}
	
	public String toString() {
		
		return "Measurement " + super.get(MeasurementStaticAttributes.measurementID.toString()).value.toString();
	}
}