# Spider - Spiral Deobfuscator

There is this old game called Spiral Knights, its main component, projectx-pcode.jar, is obfucated in a way that prevents ow2/asm from parsing it. The purpose of this project is to make a tool that would produce a deobfuscated jar file that can be used with ow2/asm.

This project is just a couple of modified components of ow2/asm/asm and ow2/asm/asm-tree that deobfuscate java classes as they parse them.

# TODO

 - make spider-cli build into a single jar with dependencies; actually, build creates a zip distribution that can be used to launch the whole thing
 - bsd-3
 - now that suggested class version and offsetDelta overflow are resolved ClassWriter and MethodWrites should be good to be replaced with their original counterparts
