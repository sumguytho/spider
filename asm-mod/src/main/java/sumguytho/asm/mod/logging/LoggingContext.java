package sumguytho.asm.mod.logging;

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
	// Size of a stack map frame within StackMapTable.
	public int stackMapFrameEntrySize;
	// Size of a StackMapTable attribute.
	public int stackMapTableSize;

	// Offset in bytecode at which the stack map frame begins.
	public int stackMapFrameOffset;
	// Number of bytecode instructions the stack map frame affects.
	public int stackMapFrameSize;
	// Total size of a method's code attribute.
	public int bytecodeSize;
	
	public int localsDeclared;
	public int localsUsed;
	public int stackDeclared;
	public int stackUsed;
	
	private String deferredDeobfuscationReport = null;
	
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
		sb.append(String.format("stackMapFrameEntrySize=%d, ", stackMapFrameEntrySize));
		sb.append(String.format("stackMapTableSize=%d, ", stackMapTableSize));
		
		sb.append(String.format("stackMapFrameOffset=%d, ", stackMapFrameOffset));
		sb.append(String.format("stackMapFrameSize=%d, ", stackMapFrameSize));
		sb.append(String.format("bytecodeSize=%d, ", bytecodeSize));
		
		sb.append(String.format("localsDeclared=%d, ", localsDeclared));
		sb.append(String.format("localsUsed=%d, ", localsUsed));
		sb.append(String.format("stackDeclared=%d, ", stackDeclared));
		sb.append(String.format("stackUsed=%d, ", stackUsed));
		
		sb.append(String.format("deferredDeobfuscationReport=%s",
				(deferredDeobfuscationReport == null ? "(null)" : deferredDeobfuscationReport)));
		
		sb.append(")");
		return sb.toString();
	}
}
