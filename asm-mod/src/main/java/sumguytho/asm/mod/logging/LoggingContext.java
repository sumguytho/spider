package sumguytho.asm.mod.logging;

import sumguytho.asm.mod.deobfu.DeobfuscationKind;
import sumguytho.asm.mod.deobfu.Utils;

/**
 * Contains all the context that is needed to report an obfuscation but is missing at the time
 * of reporting due to the nature of visitor pattern used to implement asm.
 *
 * @author sumguytho <sumguytho@gmail.com>
 */
public class LoggingContext {
	public String className = "(null)";
	public String classSignature = "(null)";
	public String classSignatureNew = "(null)";
	public String methodName = "(null)";
	public String methodSignature = "(null)";

	public int classVersionMajor;
	public int classVersionMinor;
	public int classVersionMajorNew;
	public int classVersionMinorNew;

	// Frame type as specified in StackMapTable.
	public int stackMapFrameType;
	// Stack map frame offset into StackMapTable.
	public int stackMapFrameEntryOffset;
	// With fake and overextended frames the offset of next entry is also known.
	public int stackMapFrameNextEntryOffset;
	// Size of a StackMapTable attribute.
	public int stackMapTableStartOffset;
	public int stackMapTableEndOffset;

	public int localsDeclared;
	public int localsUsed;
	public int stackDeclared;
	public int stackUsed;

	public String formatAll() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (DeobfuscationKind kind : DeobfuscationKind.values()) {
			if (first) { first = !first; }
			else { sb.append('\n'); }
			sb.append(format(kind));
		}
		return sb.toString();
	}

	public String format(DeobfuscationKind kind) {
		switch(kind) {
		case INCORRECT_CLASS_VERSION:
			return String.format("Class version suggestion: %s FROM %d.%d TO %d.%d", className,
					classVersionMajor, classVersionMinor, classVersionMajorNew, classVersionMinorNew);
		case CYCLIC_SUPERCLASS_REFERENCE:
			return String.format("Cyclic superclass reference: %s FROM %s TO %s", className, classSignature, classSignatureNew);
		case DUPLICATE_STACK_MAP_FRAME:
			return String.format(
					"Duplicate stack map frame: %s %s",
					Utils.formatMethod(className, methodName, methodSignature),
					Utils.formatStackMapTableRef(
							stackMapTableStartOffset,
							stackMapTableEndOffset,
							stackMapFrameEntryOffset,
							stackMapFrameType
					)
			);
		case INSUFFICIENT_MAX_LOCALS:
			return String.format(
					"Insufficient max locals: %s FROM %d TO %d",
					Utils.formatMethod(className, methodName, methodSignature),
					localsDeclared,
					localsUsed);
		case INSUFFICIENT_MAX_STACK:
			return String.format(
					"Insufficient max stack: %s FROM %d TO %d",
					Utils.formatMethod(className, methodName, methodSignature),
					stackDeclared,
					stackUsed);
		case OVEREXTENDED_STACK_MAP_FRAME:
			return String.format(
					"Overextended stack map frame: %s %s",
					Utils.formatMethod(className, methodName, methodSignature),
					Utils.formatStackMapTableRef(
							stackMapTableStartOffset,
							stackMapTableEndOffset,
							stackMapFrameEntryOffset,
							stackMapFrameType,
							stackMapFrameNextEntryOffset
					)
			);
		case STACK_MAP_FRAME_PADDING:
			return String.format(
					"Stack map frame padding: %s %s",
					Utils.formatMethod(className, methodName, methodSignature),
					Utils.formatStackMapTableRef(
							stackMapTableStartOffset,
							stackMapTableEndOffset,
							stackMapFrameEntryOffset,
							stackMapFrameType
					)
			);
		}
		return "(unknown)";
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("LoggingContext(");

		sb.append(String.format("className=%s, ", className));
		sb.append(String.format("classSignature=%s, ", classSignature));
		sb.append(String.format("classSignatureNew=%s, ", classSignatureNew));
		sb.append(String.format("methodName=%s, ", methodName));
		sb.append(String.format("methodSignature=%s, ", methodSignature));

		sb.append(String.format("classVersionMajor=%d, ", classVersionMajor));
		sb.append(String.format("classVersionMinor=%d, ", classVersionMinor));
		sb.append(String.format("classVersionMajorNew=%d, ", classVersionMajorNew));
		sb.append(String.format("classVersionMinorNew=%d, ", classVersionMinorNew));

		sb.append(String.format("stackMapFrameType=%d, ", stackMapFrameType));
		sb.append(String.format("stackMapFrameEntryOffset=%d, ", stackMapFrameEntryOffset));
		sb.append(String.format("stackMapFrameNextEntryOffset=%d, ", stackMapFrameNextEntryOffset));
		sb.append(String.format("stackMapTableStartOffset=%d, ", stackMapTableStartOffset));
		sb.append(String.format("stackMapTableEndOffset=%d, ", stackMapTableEndOffset));

		sb.append(String.format("localsDeclared=%d, ", localsDeclared));
		sb.append(String.format("localsUsed=%d, ", localsUsed));
		sb.append(String.format("stackDeclared=%d, ", stackDeclared));
		sb.append(String.format("stackUsed=%d", stackUsed));

		sb.append(")");
		return sb.toString();
	}
}
