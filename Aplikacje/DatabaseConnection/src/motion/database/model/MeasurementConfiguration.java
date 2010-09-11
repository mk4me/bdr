package motion.database.model;


@SuppressWarnings("serial")
public class MeasurementConfiguration extends GenericDescription<MeasurementConfigurationStaticAttributes>{

	public MeasurementConfiguration() {
		super(MeasurementConfigurationStaticAttributes.MeasurementConfigurationID.name(), EntityKind.measurement_conf);
	}
	
	public String toString() {
		
		return "MeasurementResult " + super.get(MeasurementConfigurationStaticAttributes.MeasurementConfigurationID.toString()).value.toString();
	}
}