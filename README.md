# Spider - Spiral Deobfuscator

There is this old game called Spiral Knights, its main component, projectx-pcode.jar, is obfucated in a way that prevents asm by objectweb from parsing it. As a consequence, it can't be used with another java deobfuscation tool, Enigma. The purpose of this project is to make a tool that would produce a deobfuscated jar file that can be used with asm and some fork of Enigma.

This deobfucator is "good enough". I don't know which obfuscator was used for projectx-pcode.jar and this deobfuscator may certainly fail on other jars obfuscated by it. It just happens to work on the jar I needed it to work on and there is nothing more to it.

This project is based on projects asm and asm-tree which are a part of java bytecode instrumentation framework called asm by objectweb. Subproject asm-mod is a full copy of these 2 projects with a single modified file, ClassReader.java. Actually, all of files were modified because I've changed their base subpackage from org.objectweb.asm to sumguytho.asm.mod but ClassReader is the only one whose functionality has been changed as well. ClassReader was modified in such a way that it deobfuscates java classes as it parses them.

Currently output jar can only be used with Enigma fork by QuiltMC (version 2.3.0 or higher). For more info see section "outer class null check" in [notes.md](notes.md).

# Quick start

## Producing a distribution

```
./gradlew assemble
```

Or

```
gradlew.bat assemble
```

The output distribution is stored in spider-cli/build/distributions.

Alternatively, a pre-compiled distribution can be downloaded from releases section.

## Running deobfuscator

Say, you want to deobfuscate file `/some/path/file.jar` and write output jar to `/some/path/file.out.jar`. The command line will look like this (assuming you've unpacked a distribution and are currently in a directory you've unpackaged it into):

```
./spider-cli/bin/spider-cli -i /some/path/file.jar -o /some/path/file.out.jar
```

Or

```
spider-cli/bin/spider-cli.bat -i /some/path/file.jar -o /some/path/file.out.jar
```

## Additional info

Option -h can be used to see all available options.

Specifying the same file as both input and output is unsupported, you will get an IO error.

Be aware that logs produced when supplying option -v may reach tens of megabytes in size depending on size of input jar. Big logs that can't be opened with usual text editors may be viewed using memory-mapping based log viewers like [Klogg](https://github.com/variar/klogg).

# Deobfuscation descriptions

A brief summary of each obfuscation detected by deobfuscator:

 - Incorrect class version: class contains some attributes that suggest a different class version.
 - Stack map frame padding: invalid frames detected in StackMapTable attribute.
 - Duplicate stack map frame: StackMapTable provides multiple frames for a single bytecode offset.
 - Cyclic superclass reference: a class inherits from itself.
 - Insufficient max locals: some method of a class uses more local variables than it declares.
 - Insufficient max stack: size of operand stack of some method of a class needs to be bigger than what the method declares.
 - Overextended stack map frame: stack map frame starts past the end of bytecode.

Note that some deobfuscation heuristics were added during a phase when deobfuscator parsed invalid stack map frames instead of ignoring them and as a result some deobfuscations listed here may never actually be reported.

# Versions of projectx-pcode.jar the deobfuscator works with

The version shouldn't affect anything but I'll list every version I have tested / will test deobfuscator on just in case.

Timestamp: 2022-11-03 21:51:27.000000000 +0300. Sha-256: e4a76086bfd0682e4bb3d1b2791a3bc15d59fbd82ff27f0ce2c5d632c1c5155f.

Timestamp: 2025-05-18 22:10:35.117731319 +0300. Sha-256: 8b61df2b5656019dbc0053f6e4b216759e6d1fe832d1b9e13bcab1512fa19f92.

# Resources

 - [Java SE 20 JVMS-4, Class File format](https://docs.oracle.com/javase/specs/jvms/se20/html/jvms-4.html)
