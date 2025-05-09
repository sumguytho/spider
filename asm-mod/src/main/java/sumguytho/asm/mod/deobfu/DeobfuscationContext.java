package sumguytho.asm.mod;

/**
 * A class used to store corrections that must be applied to a class
 * as it's parsed.
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
	 * Counting resolved stack map frames and stack map frames with offsetDelta=0.
	 * If all stack map frames have offsetDelta=0 the attribute can (presumably) be omitted altogether.
	 */
	public int stackMapFrames;
	public int zeroOffsetDeltaStackMapFrames;
	
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
}
