# Spider - Spiral Deobfuscator

There is this old game called Spiral Knights, its main component, projectx-pcode.jar, is obfucated in a way that prevents ow2/asm from parsing it. The purpose of this project is to make a tool that would produce a deobfuscated jar file that can be used with ow2/asm.

This project is based on projects asm and asm-tree which a part of java bytecode instrumentation framework called asm by objectweb. Subproject asm-mod is a full copy of these projects with a single modified file, ClassReader.java. ClassReader was modified in such a way that it deobfuscates java classes as it parses them.

# TODO

 - make spider-cli build into a single jar with dependencies; actually, build creates a zip distribution that can be used to launch the whole thing
 - bsd-3
 - now that suggested class version and offsetDelta overflow are resolved ClassWriter and MethodWrites should be good to be replaced with their original counterparts
