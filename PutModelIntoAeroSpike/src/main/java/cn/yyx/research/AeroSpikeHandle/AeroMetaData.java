package cn.yyx.research.AeroSpikeHandle;

public class AeroMetaData {
	
	public static final int code1sim = 1;

	public static final int codengram = 2;
	
	// if -1, infinite.
	public static final int MaxPutAllLineNum = -1;
	// must > 0
	public static final int MaxMethodSimilarNum = 20;
	
	public static final int MaxBinNum = 20;
	
	// code1sim
	public static final String BinSimilarName = "similar";
	// codengram
	public static final String BinPredictName = "predict";
	public static final String BinProbabilityName = "probability";
	
}