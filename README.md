# Spider (*Spi*ral *De*obfuscato*r*)

There is this old game called Spiral Knights, its main component, projectx-pcode.jar, is obfucated in a way that prevents asm from parsing it. The purpose of this project is to make a tool that would produce a deobfuscated jar file that can be used with ow2/asm.

A couple of modified components of ow2/asm/asm and ow2/asm/asm-tree that deobfuscate java classes as they parse them.

# TODO

 - make spider-cli build into a single jar with dependencies; actually, build creates a zip distribution that can be used to launch the whole thing
