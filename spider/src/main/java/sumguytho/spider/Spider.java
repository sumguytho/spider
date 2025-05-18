package sumguytho.spider;

import java.io.File;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.JarInputStream;

import sumguytho.asm.mod.ClassReader;
import sumguytho.asm.mod.ClassWriter;
import sumguytho.asm.mod.Opcodes;
import sumguytho.asm.mod.tree.ClassNode;
import sumguytho.asm.mod.deobfu.DeobfuscationOptions;

public class Spider {
	public byte[] transformClass(byte[] classBytes) {
		ClassReader cr = new ClassReader(classBytes);
		ClassNode classNode = new ClassNode();
		cr.accept(classNode, 0);
		ClassWriter cw = new ClassWriter(0);
		classNode.accept(cw);
		return cw.toByteArray();
	}

	public boolean isClassFilename(final String filename) {
		return filename.toLowerCase().endsWith(".class");
	}

	public void transformJar(final JarInputStream jarInStream,
			final JarOutputStream jarOutStream,
			final DeobfuscationOptions opts,
			final PrintStream logStream
	) throws SpiderException
	{
		try {
			while (true) {
			    final JarEntry jarEntryIn = jarInStream.getNextJarEntry();
			    if (jarEntryIn == null) { break; }

			    String filepath = jarEntryIn.getName();
			    // ZipOutputStream creats empty directories for paths
			    // ending with a slash.
			    if (jarEntryIn.isDirectory() && !filepath.endsWith(File.separator)) {
			    	filepath += File.separator;
			    }
			    JarEntry jarEntryOut = new JarEntry(filepath);

			    jarOutStream.putNextEntry(jarEntryOut);
		        try {
				    if (!jarEntryIn.isDirectory()) {
			        	byte[] classBytes = jarInStream.readAllBytes();
					    if (isClassFilename(filepath)) {
					    	System.out.println("Deobfuscating " + filepath);
					    	classBytes = transformClass(classBytes);
					    }
					    else {
						    System.out.println("Skipping file " + filepath);
					    }
					    jarOutStream.write(classBytes);
				    }
				    else {
				    	System.out.println("Skipping directory " + filepath);
				    }
		        }
		        finally {
		        	jarOutStream.closeEntry();
		        }
			}
		}
		catch(IOException ex) {
			throw new SpiderException("An io error occurred: " + ex.getMessage(), ex);
		}
	}

	public void transformJar(final File input,
			final File output,
			final DeobfuscationOptions opts,
			final PrintStream logStream
	) throws SpiderException
	{
		try(
				JarInputStream jarInStream = new JarInputStream(new FileInputStream(input));
				JarOutputStream jarOutStream = new JarOutputStream(new FileOutputStream(output))
			)
		{
			transformJar(jarInStream, jarOutStream, opts, logStream);
		}
		catch(IOException ex) {
			throw new SpiderException("An io error occurred: " + ex.getMessage(), ex);
		}
	}

	public void transformJar(final String input,
			final String output,
			final DeobfuscationOptions opts,
			final PrintStream logStream
	) throws SpiderException
	{
		transformJar(new File(input), new File(output), opts, logStream);
	}

	public void transformJar(
			final DeobfuscationOptions opts,
			final PrintStream logStream
	) throws SpiderException
	{
		transformJar(opts.input, opts.output, opts, logStream);
	}
}
