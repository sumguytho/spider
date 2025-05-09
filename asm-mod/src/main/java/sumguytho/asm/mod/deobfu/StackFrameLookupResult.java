package sumguytho.asm.mod;

public class StackFrameLookupResult {
	/* Other fields only make sense when this is set to true. */
	boolean isValid;
	int nextFrameOffset;
	int frameType;
	/* offsetDelta is returned as raw value as it appears in a frame. */
	int offsetDelta;
	int localCount;
	int localCountDelta;
	int stackCount;
}
