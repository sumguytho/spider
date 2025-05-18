package sumguytho.asm.mod.deobfu;

public class Utils {
	public static String formatMethod(
			final String className,
			final String methodName,
			final String signature
	) {
		return String.format("%s.%s%s", className, methodName, signature);
	}
	public static String formatStackMapTableRef(
			final int start,
			final int end,
			final int offset,
			final int frameType)
	{
		return String.format("table=[%d,%d), pos=%d, type=%d", start, end, offset, frameType);
	}
	public static String formatStackMapTableRef(
			final int start,
			final int end,
			final int offset,
			final int frameType,
			final Integer nextOffset)
	{
		return formatStackMapTableRef(start, end, offset, frameType) + ", next=" + nextOffset.toString();
	}
}
