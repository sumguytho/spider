package sumguytho.asm.mod.deobfu;

public enum ParseResult {
	// Success.
	VALID,
	// Failed due to unknown type.
	INVALID,
	// Failed because the structure being read breached
	// set bounds.
	OUT_OF_BOUNDS;
}
