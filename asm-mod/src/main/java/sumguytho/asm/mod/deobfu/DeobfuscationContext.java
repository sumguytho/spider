package sumguytho.asm.mod.deobfu;

/**
 * A class used to store corrections that must be applied to a class
 * as it's parsed.
 * 
 * @author sumguytho <sumguytho@gmail.com>
 */
public final class DeobfuscationContext {
	/**
	 * Major version indicated by used attributes.
	 */
	public int suggestedMajorVersion;
	/**
	 * Minor version indicated by used attributes.
	 */
	public int suggestedMinorVersion;
	
	/**
	 * Some frames use more locals / stack than what they declare, actual max counts
	 * should be figured out by traversing stack map frames.
	 */
	public int maxLocals;
	public int maxStack;
	public int maxLabels;
	
	/**
	 * Counting reachable stack map frames.
	 */
	public int stackMapFrames;
	
	/**
	 * Updates suggested minor and major version according to attribute encountered.
	 */
	public void visitAttributeName(final String attributeName) {
		// oh boy, if only there was a better way to collate one value to several other ones
		// TODO:
		if (Constants.CONSTANT_VALUE.equals(attributeName)) {
			setVersionMonotonic(45, 3);
		} else if (Constants.CODE.equals(attributeName)) {
			setVersionMonotonic(45, 3);
		} else if (Constants.EXCEPTIONS.equals(attributeName)) {
			setVersionMonotonic(45, 3);
		} else if (Constants.SOURCE_FILE.equals(attributeName)) {
			setVersionMonotonic(45, 3);
		} else if (Constants.LINE_NUMBER_TABLE.equals(attributeName)) {
			setVersionMonotonic(45, 3);
		} else if (Constants.LOCAL_VARIABLE_TABLE.equals(attributeName)) {
			setVersionMonotonic(45, 3);
	    } else if (Constants.INNER_CLASSES.equals(attributeName)) {
			setVersionMonotonic(45, 3);
		} else if (Constants.SYNTHETIC.equals(attributeName)) {
			setVersionMonotonic(45, 3);
	    } else if (Constants.DEPRECATED.equals(attributeName)) {
			setVersionMonotonic(45, 3);
	    } else if (Constants.ENCLOSING_METHOD.equals(attributeName)) {
			setVersionMonotonic(49, 0);
	    } else if (Constants.SIGNATURE.equals(attributeName)) {
			setVersionMonotonic(49, 0);
	    } else if (Constants.SOURCE_DEBUG_EXTENSION.equals(attributeName)) {
			setVersionMonotonic(49, 0);
	    } else if (Constants.LOCAL_VARIABLE_TYPE_TABLE.equals(attributeName)) {
			setVersionMonotonic(49, 0);
	    } else if (Constants.RUNTIME_VISIBLE_ANNOTATIONS.equals(attributeName)) {
			setVersionMonotonic(49, 0);
	    } else if (Constants.RUNTIME_INVISIBLE_ANNOTATIONS.equals(attributeName)) {
			setVersionMonotonic(49, 0);
	    } else if (Constants.RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS.equals(attributeName)) {
			setVersionMonotonic(49, 0);
	    } else if (Constants.RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS.equals(attributeName)) {
			setVersionMonotonic(49, 0);
	    } else if (Constants.ANNOTATION_DEFAULT.equals(attributeName)) {
			setVersionMonotonic(49, 0);
	    } else if (Constants.STACK_MAP_TABLE.equals(attributeName)) {
			setVersionMonotonic(50, 0);
	    } else if (Constants.BOOTSTRAP_METHODS.equals(attributeName)) {
			setVersionMonotonic(51, 0);
	    } else if (Constants.RUNTIME_VISIBLE_TYPE_ANNOTATIONS.equals(attributeName)) {
			setVersionMonotonic(52, 0);
	    } else if (Constants.RUNTIME_INVISIBLE_TYPE_ANNOTATIONS.equals(attributeName)) {
			setVersionMonotonic(52, 0);
	    } else if (Constants.METHOD_PARAMETERS.equals(attributeName)) {
			setVersionMonotonic(52, 0);
	    } else if (Constants.MODULE.equals(attributeName)) {
			setVersionMonotonic(53, 0);
	    } else if (Constants.MODULE_PACKAGES.equals(attributeName)) {
			setVersionMonotonic(53, 0);
	    } else if (Constants.MODULE_MAIN_CLASS.equals(attributeName)) {
			setVersionMonotonic(53, 0);
	    } else if (Constants.NEST_HOST.equals(attributeName)) {
			setVersionMonotonic(55, 0);
	    } else if (Constants.NEST_MEMBERS.equals(attributeName)) {
			setVersionMonotonic(55, 0);
	    } else if (Constants.RECORD.equals(attributeName)) {
			setVersionMonotonic(60, 0);
	    } else if (Constants.PERMITTED_SUBCLASSES.equals(attributeName)) {
			setVersionMonotonic(61, 0);
	    }
	}
	
	/**
	 * Sets major and minor version but only if they are greater
	 * than the ones stored.
	 */
	private void setVersionMonotonic(final int major, final int minor) {
		if (major > suggestedMajorVersion) {
			suggestedMajorVersion = major;
			suggestedMinorVersion = minor;
		}
		else if (major == suggestedMajorVersion && minor > suggestedMinorVersion) {
			suggestedMinorVersion = minor;
		}
	}
	
	/**
	 * Returns suggested version as int.
	 */
	public int suggestedVersionAsInt() {
		return (suggestedMajorVersion & 0xffff) | (suggestedMinorVersion << 16);
	}
	
	public int getSuggestedVersionMajor() { return suggestedMajorVersion; }
	public int getSuggestedVersionMinor() { return suggestedMinorVersion; }
	
	public boolean suggestedVersionHigherThan(final int classVersionMajor, final int classVersionMinor) {
		return suggestedMajorVersion > classVersionMajor ||
				suggestedMajorVersion == classVersionMajor &&
				suggestedMinorVersion > classVersionMinor;
	}
	
	public void resetMaxLocals() {
		maxLocals = 0;
	}
	
	public void resetMaxStack() {
		maxStack = 0;
	}
	
	public void resetMaxLabels() {
		maxLabels = 0;
	}
	
	public void setMaxLocalsMonotonic(final int maxLocals) {
		this.maxLocals = maxLocals > this.maxLocals ? maxLocals : this.maxLocals; 
	}
	
	public void setMaxStackMonotonic(final int maxStack) {
		this.maxStack = maxStack > this.maxStack ? maxStack : this.maxStack; 
	}
	
	public void setMaxLabelsMonotonic(final int maxLabels) {
		this.maxLabels = maxLabels > this.maxLabels ? maxLabels : this.maxLabels; 
	}
	
	/**
	 * Copy pasted from org.objectweb.asm.Constants so that I don't have to modify the source.
	 * 
	 * Original author: Eric Bruneton
	 */
	public class Constants {
		  public static final String CONSTANT_VALUE = "ConstantValue";
		  public static final String CODE = "Code";
		  public static final String STACK_MAP_TABLE = "StackMapTable";
		  public static final String EXCEPTIONS = "Exceptions";
		  public static final String INNER_CLASSES = "InnerClasses";
		  public static final String ENCLOSING_METHOD = "EnclosingMethod";
		  public static final String SYNTHETIC = "Synthetic";
		  public static final String SIGNATURE = "Signature";
		  public static final String SOURCE_FILE = "SourceFile";
		  public static final String SOURCE_DEBUG_EXTENSION = "SourceDebugExtension";
		  public static final String LINE_NUMBER_TABLE = "LineNumberTable";
		  public static final String LOCAL_VARIABLE_TABLE = "LocalVariableTable";
		  public static final String LOCAL_VARIABLE_TYPE_TABLE = "LocalVariableTypeTable";
		  public static final String DEPRECATED = "Deprecated";
		  public static final String RUNTIME_VISIBLE_ANNOTATIONS = "RuntimeVisibleAnnotations";
		  public static final String RUNTIME_INVISIBLE_ANNOTATIONS = "RuntimeInvisibleAnnotations";
		  public static final String RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS = "RuntimeVisibleParameterAnnotations";
		  public static final String RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS =
		      "RuntimeInvisibleParameterAnnotations";
		  public static final String RUNTIME_VISIBLE_TYPE_ANNOTATIONS = "RuntimeVisibleTypeAnnotations";
		  public static final String RUNTIME_INVISIBLE_TYPE_ANNOTATIONS = "RuntimeInvisibleTypeAnnotations";
		  public static final String ANNOTATION_DEFAULT = "AnnotationDefault";
		  public static final String BOOTSTRAP_METHODS = "BootstrapMethods";
		  public static final String METHOD_PARAMETERS = "MethodParameters";
		  public static final String MODULE = "Module";
		  public static final String MODULE_PACKAGES = "ModulePackages";
		  public static final String MODULE_MAIN_CLASS = "ModuleMainClass";
		  public static final String NEST_HOST = "NestHost";
		  public static final String NEST_MEMBERS = "NestMembers";
		  public static final String PERMITTED_SUBCLASSES = "PermittedSubclasses";
		  public static final String RECORD = "Record";
	}
}
