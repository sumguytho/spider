# Spider - Spiral Deobfuscator

There is this old game called Spiral Knights, its main component, projectx-pcode.jar, is obfucated in a way that prevents ow2/asm from parsing it. The purpose of this project is to make a tool that would produce a deobfuscated jar file that can be used with ow2/asm.

This project is based on projects asm and asm-tree which a part of java bytecode instrumentation framework called asm by objectweb. Subproject asm-mod is a full copy of these projects with a single modified file, ClassReader.java. ClassReader was modified in such a way that it deobfuscates java classes as it parses them.

# TODO

 - make spider-cli build into a single jar with dependencies; actually, build creates a zip distribution that can be used to launch the whole thing
 - bsd-3
 - if the unreachable stack map frames are confirmed also add description to DeobfuscationKind about unreported unreachable frames because there is some other issue with them the same way as with ???
 - name constants in enums in sumguytho.asm.mod
 - remove fake_stack_map_table, it's nonsensical

# possible overextended stack map frame causes

frame breaches stack map table, offsetdelta is gibberish, those are padding bytes

frame is within stack map table but frame extends beyond it, the read overextended frame, all frames afterwards aren't valid

Next frame is placed at unreachable bytecode position. In this case it's never reached by "real" code but actually parsed by "dry" code which is why I get different results. The function that looks up stack frames should be able to report end of parsing directly. It should also be noted that in case same frame extends beyond bytecode it can just be removed but it it's some other frame type it should be registered and pointed at bytecode end.

When the bytecode is parsed first stack map table entry is read with readstackmapframe which stores frame info in Context, sets currentFrameOffset to offset at which a new frame is encountered and returns offset to new entry of StackMapTable. Once bytecode parser reaches the point at which new stack frame is applied, frame from Context is visited and a new frame is read from the table. Therefore, if a frame ends beyond the table, it's never visited.
Actually, it seems bytecode is traversed multiple times with each traversal doing separate things each time. Although the time the stack map table is parsed bytecode is also parsed

if a frame is fake it's removed, if it's overextended it's never visited, hence it doesn't matter whether such frame is same or not, it gets removed anyway

It seems that removing every single frame from stack map table doesn't prevent projectx-pcode.jar from working normally. It would seem that there is no way to mess it up even though I did it a few times. spiral knights runs even if you remove it altogether

Omitting stack map table entirely may go against java spec, needs to be checked.

so, the questions are: do I keep parsing frames after a padding? do I just remove overextended frames? can I nuke StackMapTable?

com.threerings.bureau.data.BureauMarshaller uses java 6 signatures but specifies 49.0, look into it

there is more to omitting stackmaptable than preventing asm from parsing it

java se 20 jvms says the offsetDelta of the first frame is the offset at which stack starts, not offsetDelta+1, it asm accounts for that by setting currentFrameOffset to -1

# Resources

 - [Java SE 20 JVMS-4, Class File format](https://docs.oracle.com/javase/specs/jvms/se20/html/jvms-4.html)
