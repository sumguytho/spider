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

import sumguytho.spider.util.FakeOutputStream;

public class Spider {
	public byte[] transformClass(byte[] classBytes, PrintStream logStream) {
		ClassReader cr = new ClassReader(classBytes, logStream);
		ClassNode classNode = new ClassNode();
		cr.accept(classNode, 0);
		ClassWriter cw = new ClassWriter(0, logStream);
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
		final PrintStream verboseStream = opts.verbose ? logStream : new PrintStream(new FakeOutputStream());
		// First one is type (regular / directory), second one is file path.
		final String fileStatusFormat = opts.classesOnly ? "Omitting %s %s" : "Copying %s %s as is";
		try {
			while (true) {
			    final JarEntry jarEntryIn = jarInStream.getNextJarEntry();

			    if (jarEntryIn == null) { break; }

			    String filepath = jarEntryIn.getName();
			    // TODO: a better way to do this?
			    boolean createEntry;
			    boolean copyContents;
			    boolean deobfuscate;

			    if (jarEntryIn.isDirectory()) {
				    // ZipOutputStream creats empty directories for paths
				    // ending with a slash.
			    	if (!filepath.endsWith(File.separator)) {
			    		filepath += File.separator;
			    	}
			    	verboseStream.println(String.format(fileStatusFormat, "directory", filepath));
			    	createEntry = !opts.classesOnly;
			    	copyContents = false;
			    	deobfuscate = false;
			    }
			    else if (!isClassFilename(filepath)) {
			    	verboseStream.println(String.format(fileStatusFormat, "regular file", filepath));
			    	createEntry = !opts.classesOnly;
			    	copyContents = true;
			    	deobfuscate = false;
			    }
			    else {
			    	verboseStream.println("Deobfuscating " + filepath);
			    	createEntry = copyContents = deobfuscate = true;
			    }
			    if (!createEntry) { continue; }

			    JarEntry jarEntryOut = new JarEntry(filepath);

			    jarOutStream.putNextEntry(jarEntryOut);
		        try {
		        	if (!copyContents) { continue; }
		        	byte[] fileBytes = jarInStream.readAllBytes();
		        	if (deobfuscate) {
		        		fileBytes = transformClass(fileBytes, verboseStream);
		        	}
		        	jarOutStream.write(fileBytes);
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
