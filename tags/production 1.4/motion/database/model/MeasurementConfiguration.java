package motion.database.model;


@SuppressWarnings("serial")
public class MeasurementConfiguration extends GenericDescription<MeasurementConfigurationStaticAttributes>{

	public MeasurementConfiguration() {
		super(MeasurementConfigurationStaticAttributes.MeasurementConfID.name(), EntityKind.measurement_conf);
	}
	
	public String toString() {
		
		return "MeasurementResult " + super.get(MeasurementConfigurationStaticAttributes.MeasurementConfID.toString()).value.toString();
	}
}