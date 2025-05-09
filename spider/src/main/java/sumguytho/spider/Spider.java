package sumguytho.spider;

import sumguytho.asm.mod.Opcodes;
import sumguytho.asm.mod.ClassReader;
import sumguytho.asm.mod.ClassWriter;

public class Spider {

	public int getInt() { return 0; }
	public int getAsmInt() { return Opcodes.ASM9; }
	public void transform(final String jar) {
		System.out.println(jar);
	}
}
