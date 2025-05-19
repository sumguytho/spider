# How stack map frames are visited

Stack map frames are applied at offsetDelta+1 except for the first frame which is applied at offsetDelta. Each frame define offsetDelta either explicitly (2 byte field) or implicitly (calculated from frame type).

When the readCode method of ClassReader traverses stack map frame table a **while** loop is used to visit all frames at currentBytecodeOffset. The only reason a loop is used instead of a plain **if** is because it's possible that the first frame will use offsetDelta=0 which would mean the frame starts immediately while the readCode is made so that it visits frames on the next iteration after reading them. Bytecode offset of nearest stack map frame is stored in Context, once it's reached the frame is visited and a new frame is read from the stack map frame table, if any. The fact that first frame uses offsetDelta instead of offsetDelta+1 is mitigated by setting currentFrameOffset in Context to -1.

# Kinds of invalid stack map frames (or lack thereof)

I initially tried to classify stack map frame deformities which resulted in obfuscation kinds which are never reported. The reason for this is that the dry run code was erroneous: it did dry run on frames that are unreachable by bytecode. For example, if a frame is overextended it will never be visited because the readCode will never reach the bytecode offset at which the frame should be applied. This was complemented by the fact that both overextended and duplicate stack map frames can only be encountered if instead of dropping everything after the padding we keep on trying to parse valid frames from padding by incrementing offset into stack map table by 1 on each failed parse ("pulling stack map frames out of thin air"). Another consequence of fixing dry run of readStackMapFrame is that max stack and max locals mismatches no longer occur. This means they are only encountered in frames after padding.

The "padding frames" are usually followed by values less than 64 meaning same frame with low offset delta. As fas as I'm concerned, this is gibberish and can be removed because these frames do nothing. This prompted my assumption that once a padding is reaching everything that follows can be removed seamlessly.

Another thing I've noticed is that removing the stack map table attribute from projectx-pcode.jar altogether has the same effect as removing the padding. I can think of several explanations for this:
 - stack map frames are used a hint and can be removed without any functional consequences
 - the class version of 49.0 is actually correct and all stack map frames are simply fake

## What my initial stack map frame logic looked like
- A frame is fully contained within StackMapTable but offsetDelta points beyond the bytecode:
- - If it's a kind of same_frame it can be omitted entirely, all following frames can be omitted as well. It's OVEREXTENDED_STACK_MAP_FRAME.
- - If it's some other kind of frame its offsetDelta needs to be corrected and the frame itself should be traversed (EDIT: as it later turned out, it was never actually traversed outside of dry run). Following frames will be omitted. It's OVEREXTENDED_STACK_MAP_FRAME.
- A frame breaches StackMapTable:
- - This can't be a valid frame. It's STACK_MAP_FRAME_PADDING.
- A frame is correct but its offsetDelta is 0xffff:
- - The frame is invalid and shouldn't be traversed. There may be valid frames left in the table. It's DUPLICATE_STACK_MAP_FRAME.

It should be noted that the way invalid frames are resolved by the JVM should have been taken into account but I didn't do that. I just did what works and things didn't break. JVM is a huge project and is really hard to navigate as someone unfamiliar with it. That said, it would be interesting to maybe sometime in the distant future have a look at stack map frame parser in JVM.

# Outer class null check in FabricMC Enigma

com/threerings/tudey/config/TagConfig declares com/threerings/tudey/config/a its inner class and doesn't provide outer class for it. According to Java SE 20 JVMS 4.7.6. this can be valid:

```
If C is not a member of a class or an interface - that is, if C is a top-level class or interface (JLS §7.6) or a local class (JLS §14.3) or an anonymous class (JLS §15.9.5) - then the value of the outer_class_info_index item must be zero.
```

It's also possible that this is just an obfuscation because putting 2 top level classes in a single java source is very undesirable.

During some refactoring of Enigma someone added a check that prevents an inner class from not having an outer class set (which results in throwing an IllegalStateException, as funny as it is). This would prevent projectx-pcode.jar from being used with Enigma despite deobfuscation. I've submitted a patch to fabricmc/enigma but it was ignored. Interestingly enough, it was cherry-picked by quiltmc/enigma which is why deobfuscated projectx-pcode.jar can be loaded into Enigma fork of QuiltMC.

# Other

com/threerings/bureau/data/BureauMarshaller uses version 50.0 signatures but specifies version 49.0. This has to be caused by something unrelated to code attribute which I do not test for because it doesn't get in the way of my needs. Maybe look into it later? Probably not.

com/google/common/collect/SortedLists uses StackMapTable despite specifying class version 49.0.

com/google/common/util/concurrent/c has frame 0xff at offset 2143 (offsetDelta lands beyond bytecode).

com/google/common/cache/CacheBuilder has frame 0x3f at offset 6551 (offsetDelta lands beyond bytecode).

com/threerings/editor/swing/TreeEditorPanel declares 6 max locals but some frame uses 7 in some of its methods. The frame in question comes after padding.
