package motion.database.model;


@SuppressWarnings("serial")
public class Segment extends GenericDescription<SegmentStaticAttributes>{

	public Segment() {
		super(SegmentStaticAttributes.segmentID.name(), EntityKind.segment);
	}
}