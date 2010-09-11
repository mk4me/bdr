package motion.database.model;


@SuppressWarnings("serial")
public class Measurement extends GenericDescription<MeasurementStaticAttributes>{

	public Measurement() {
		super(MeasurementStaticAttributes.MeasurementID.name(), EntityKind.measurement);
	}
	
	public String toString() {
		
		return "Measurement " + super.get(MeasurementStaticAttributes.MeasurementID.toString()).value.toString();
	}
}