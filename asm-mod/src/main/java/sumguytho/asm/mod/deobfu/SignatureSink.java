package sumguytho.asm.mod.deobfu;

import sumguytho.asm.mod.signature.SignatureVisitor;

import sumguytho.asm.mod.Opcodes;

public class SignatureSink extends SignatureVisitor {

	public SignatureSink() {
		super(Opcodes.ASM9);
	}

}
