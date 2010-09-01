package motion.database.model;


@SuppressWarnings("serial")
public class MeasurementConfiguration extends GenericDescription<MeasurementConfigurationStaticAttributes>{

	public MeasurementConfiguration() {
		super(MeasurementConfigurationStaticAttributes.measurementConfigurationID.name(), EntityKind.measurementConfiguration);
	}
	
	public String toString() {
		
		return "MeasurementResult " + super.get(MeasurementConfigurationStaticAttributes.measurementConfigurationID.toString()).value.toString();
	}
}