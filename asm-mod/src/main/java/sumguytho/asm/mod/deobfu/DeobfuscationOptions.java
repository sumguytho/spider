package sumguytho.asm.mod.deobfu;

import java.io.File;

public class DeobfuscationOptions {
	public boolean classesOnly;
	public boolean verbose;
	public File input;
	public File output;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("DeobfuscationOptions(");
		sb.append(String.format("classesOnly=%b, ", classesOnly));
		sb.append(String.format("verbose=%b, ", verbose));
		sb.append(String.format("input=%s, ", input.getPath()));
		sb.append(String.format("output=%s", output.getPath()));
		sb.append(")");
		return sb.toString();
	}
}
