# Spider - Spiral Deobfuscator

There is this old game called Spiral Knights, its main component, projectx-pcode.jar, is obfucated in a way that prevents ow2/asm from parsing it. The purpose of this project is to make a tool that would produce a deobfuscated jar file that can be used with ow2/asm.

This project is based on projects asm and asm-tree which a part of java bytecode instrumentation framework called asm by objectweb. Subproject asm-mod is a full copy of these projects with a single modified file, ClassReader.java. ClassReader was modified in such a way that it deobfuscates java classes as it parses them.

# TODO

 - make spider-cli build into a single jar with dependencies; actually, build creates a zip distribution that can be used to launch the whole thing
 - bsd-3
 - if the unreachable stack map frames are confirmed also add description to DeobfuscationKind about unreported unreachable frames because there is some other issue with them the same way as with ???

# possible overextended stack map frame causes

frame breaches stack map table, offsetdelta is gibberish, those are padding bytes

frame is within stack map table but frame extends beyond it, the read overextended frame, all frames afterwards aren't valid

Next frame is placed at unreachable bytecode position. In this case it's never reached by "real" code but actually parsed by "dry" code which is why I get different results. The function that looks up stack frames should be able to report end of parsing directly. It should also be noted that in case same frame extends beyond bytecode it can just be removed but it it's some other frame type it should be registered and pointed at bytecode end.

# Resources

 - [Java SE 20 JVMS-4, Class File format](https://docs.oracle.com/javase/specs/jvms/se20/html/jvms-4.html)
