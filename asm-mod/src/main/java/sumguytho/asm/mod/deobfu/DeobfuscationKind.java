package sumguytho.asm.mod.deobfu;

public interface DeobfuscationKind {
	/* 
	 * Attributes used in a class file suggest a higher class version than the one
	 * specified in a class file.
	 */
	final String CLASS_VERSION_SUGGESTION = "class_version_suggestion";
	/*
	 * After the last compressed stack frame in StackMapTable attribute
	 * before the table ends there are padding bytes 0xff, they can't
	 * be parsed. Well, they can be parsed as full frames but they
	 * aren't actually full frames so you will get a huge offsetDelta
	 * most of the time because you've read contents of some other attribute.
	 */
	final String STACK_MAP_FRAME_PADDING = "stack_map_frame_padding";
	/*
	 * A value of 0xffff in offsetDelta field of compressed frames
	 * overflows to 0 which means a frame starts immediately after
	 * the previous one which probably should not happen. The field
	 * offsetDelta is designed the way it is deliberately so that there
	 * is never more than one stack frame per bytecode instruction.
	 */
	final String OFFSET_DELTA_OVERFLOW = "offset_delta_overflow";
	/*
	 * When all frames in a stack map table have their offsetDelta set
	 * to 0xffff. This doesn't happen anywhere but the code to report
	 * this issue is there just in case. This is different from a single
	 * offset_delta_overflow occurrence because in this case StackMapTable
	 * can be removed as a whole, not just a single frame with deviating
	 * offsetDelta.
	 */
	final String FAKE_STACK_MAP_TABLE = "fake_stack_map_table";
	/*
	 * Signature of a class indicates that a class is a superclass
	 * of itself. In other words, the class inherits from itself.
	 */
	final String CYCLIC_SUPERCLASS_REFERENCE = "cyclic_superclass_reference";
	/*
	 * There is a stack frame that uses more local variables than declared
	 * in max_locals field of a Code attribute of a Method.
	 */
	final String INSUFFICIENT_MAX_LOCALS = "insufficient_max_locals";
	/*
	 * There is a stack frame that uses more stack space than declared
	 * in max_stack field of a Code attribute of a Method.
	 */
	final String INSUFFICIENT_MAX_STACK = "insufficient_max_stack";
	/*
	 * Stack map frame ends beyond the StackMapTable. There is a related issue when
	 * less stack map frames are visited than there are in StackMapTable. I decided
	 * not to report it since frames after an overextended one are always removed so
	 * you will always get less actual stack map frames this way.
	 * 
	 * This is the first issue you will encounter when trying to load projectx-pcode.jar
	 * into enigma. This is because there is some class with padding 0xff bytes so you
	 * will read offsetDelta from bytes of some other attribute and end up with a huge
	 * value that lands beyond bytecode bounds.
	 */
	final String OVEREXTENDED_STACK_MAP_FRAME = "overextended_stack_map_frame";
}
