package sumguytho.asm.mod.deobfu;

/**
 * Contains info about stack frame at a certain offset into StackMapTable.
 * 
 * @author sumguytho <sumguytho@gmail.com>
 */
public class StackFrameLookupResult {
	public Verdict verdict = Verdict.VALID;
	// Offset of next stack map frame in a StackMapTable.
	public int nextOffset;
	public int frameType;
	/* offsetDelta is returned as raw value as it appears in a frame. */
	public int offsetDelta;
	public int localCount;
	public int localCountDelta;
	public int stackCount;
	
	public void updateResult(VerificationTypeInfoValidationResult res) {
		if (res.result == ParseResult.VALID) {
			nextOffset = res.nextOffset;
			verdict = Verdict.VALID;
		}
		else {
			verdict = Verdict.PADDING;
		}
	}
	
	public enum Verdict {
		VALID,
		FAKE,
		OVEREXTENDED,
		PADDING,
		NO_VALID_FRAMES_LEFT;
	}
}
