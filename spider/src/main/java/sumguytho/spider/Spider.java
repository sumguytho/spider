package sumguytho.spider;

import sumguytho.asm.mod.Opcodes;
import sumguytho.asm.mod.ClassReader;
import sumguytho.asm.mod.ClassWriter;

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
	
	public byte[] transform(byte[] clazz) {
		return new byte[]{};
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
			    final String className = jarEntryIn.getName();
			    if (!isClassFilename(className)) {
				    System.out.println("Skipping " + className);
			    	continue;
			    }
			    
			    JarEntry jarEntryOut = new JarEntry(className);
			    destJarFile.putNextEntry(jarEntryOut);
		        try (InputStream entryStreamIn = jarFile.getInputStream(jarEntryIn)) {
		        	byte[] buffer = new byte[1024];
		        	while (true) {
		        		int read = entryStreamIn.read(buffer);
		        		if (read == -1)
		                    break;
		        		destJarFile.write(buffer, 0, read);
		        	}
		        }
		        finally {
		        	destJarFile.closeEntry();
		        }
			    System.out.println("Successfully written " + className);
			}
			
		}
		catch(IOException ex) { System.out.println(ex.toString()); }
	}
}
