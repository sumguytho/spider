# Spider - Spiral Deobfuscator

There is this old game called Spiral Knights, its main component, projectx-pcode.jar, is obfucated in a way that prevents ow2/asm from parsing it. The purpose of this project is to make a tool that would produce a deobfuscated jar file that can be used with ow2/asm and some fork of Enigma.

This deobfucator is "good enough". I don't know which obfuscator was used for projectx-pcode.jar and this deobfuscator may certainly fail on other jars fed to it. It just happens to work on the jar I needed it to work on and there is nothing more to it.

This project is based on projects asm and asm-tree which are a part of java bytecode instrumentation framework called asm by objectweb. Subproject asm-mod is a full copy of these projects with a single modified file, ClassReader.java. Actually, all of files were modified because I've changed their base subpackage from org.objectweb.asm to sumguytho.asm.mod but ClassReader is the only one whose functionality has been changed as well. ClassReader was modified in such a way that it deobfuscates java classes as it parses them.

# Quick start
## TODO

# TODO

 - make spider-cli build into a single jar with dependencies; actually, build creates a zip distribution that can be used to launch the whole thing
 - bsd-3
 - if the unreachable stack map frames are confirmed also add description to DeobfuscationKind about unreported unreachable frames because there is some other issue with them the same way as with ???
 - name constants in enums in sumguytho.asm.mod
 - remove fake_stack_map_table, it's nonsensical
 - redistribute with asm license
 - https://commons.apache.org/proper/commons-cli/usage.html
 - remove testing stuff
 - test with latest projectx-pcode.jar
 - rename fake frames to duplicate, overextended frames are fake as well, there is no consistency
 - get rid of my author tags
 - https://docs.gradle.org/current/userguide/distribution_plugin.html
 - include asm license into asm-mod meta-inf https://stackoverflow.com/questions/13254620/meta-inf-services-in-jar-with-gradle

# Resources

 - [Java SE 20 JVMS-4, Class File format](https://docs.oracle.com/javase/specs/jvms/se20/html/jvms-4.html)
