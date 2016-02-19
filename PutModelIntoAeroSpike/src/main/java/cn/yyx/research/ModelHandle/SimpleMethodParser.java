package cn.yyx.research.ModelHandle;

public class SimpleMethodParser {
	
	// this is a simple parse for Special Java8, will be replaced by special parse project.
	
	public static boolean IsMethodInvocation(String onesentence)
	{
		if (onesentence.startsWith(GCodeMetaInfo.MethodInvocationHint))
		{
			return true;
		}
		return false;
	}
	
	public static String GetMethodInvocationName(String onesentence)
	{
		int atidx = onesentence.indexOf('@');
		int paidx = onesentence.indexOf('(');
		return onesentence.substring(atidx+1, paidx);
	}
	
}