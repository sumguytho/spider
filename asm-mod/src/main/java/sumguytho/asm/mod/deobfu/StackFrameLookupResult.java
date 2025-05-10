package sumguytho.asm.mod;

/**
 * Contains info about stack frame at a certain offset into StackMapTable.
 * 
 * @author sumguytho <sumguytho@gmail.com>
 */
public class StackFrameLookupResult {
	/** 
	 * Whether further lookups into StackMapTable will bear results.
	 * Fields below only make sense when this is set to true.
	 */
	boolean reachableFramesLeft;
	/** 
	 * Whether there is a parsable stack map frame at provided position.
	 * Fields below only make sense when this is set to true.
	 */
	boolean isValid;
	int nextFrameOffset;
	int frameType;
	/* offsetDelta is returned as raw value as it appears in a frame. */
	int offsetDelta;
	int localCount;
	int localCountDelta;
	int stackCount;
}
