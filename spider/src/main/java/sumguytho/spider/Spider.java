package sumguytho.spider;

import sumguytho.asm.mod.Opcodes;
import sumguytho.asm.mod.ClassReader;
import sumguytho.asm.mod.ClassWriter;
import sumguytho.asm.mod.tree.ClassNode;

import java.util.jar.JarFile;
import java.util.jar.JarEntry;
import java.util.Enumeration;
import java.util.jar.JarOutputStream;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Spider {

	public int getInt() { return 0; }
	public int getAsmInt() { return Opcodes.ASM9; }
	
	public byte[] transform(byte[] classBytes) {
		ClassReader cr = new ClassReader(classBytes);
		ClassNode classNode = new ClassNode();
		cr.accept(classNode, 0);
		ClassWriter cw = new ClassWriter(0);
		classNode.accept(cw);
		return cw.toByteArray();
	}
	
	public boolean isClassFilename(final String filename) {
		return filename.endsWith(".class");
	}
	
	public void transform(final String jarName) {
		final String jarOutName = jarName + ".out";
		try(
				JarFile jarFile = new JarFile(jarName);
				JarOutputStream destJarFile = new JarOutputStream(new FileOutputStream(jarOutName))
			) 
		{
			Enumeration entries = jarFile.entries();
			while (entries.hasMoreElements()) {
			    JarEntry jarEntryIn = (JarEntry) entries.nextElement();
			    String filepath = jarEntryIn.getName();
			    // ZipOutputStream created empty directories for paths
			    // ending with a slash
			    if (jarEntryIn.isDirectory() && !filepath.endsWith(File.separator)) {
			    	filepath += File.separator;
			    }
			    JarEntry jarEntryOut = new JarEntry(filepath);
			    
			    destJarFile.putNextEntry(jarEntryOut);
		        try (InputStream entryStreamIn = jarFile.getInputStream(jarEntryIn)) {
				    if (!jarEntryIn.isDirectory()) {
			        	byte[] classBytes = entryStreamIn.readAllBytes();
					    if (isClassFilename(filepath)) {
					    	System.out.println("Deobfuscating " + filepath);
					    	classBytes = transform(classBytes);
					    }
					    else {
						    System.out.println("Skipping file " + filepath);
					    }
			        	destJarFile.write(classBytes);
				    }
				    else {
				    	System.out.println("Skipping directory " + filepath);
				    }
		        }
		        finally {
		        	destJarFile.closeEntry();
		        }
			}
			System.out.println("Finished writing " + jarOutName);
			
		}
		catch(IOException ex) { System.out.println(ex.toString()); }
	}
}
