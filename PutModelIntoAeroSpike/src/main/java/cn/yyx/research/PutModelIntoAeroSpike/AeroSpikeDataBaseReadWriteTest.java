package cn.yyx.research.PutModelIntoAeroSpike;

import java.io.File;

import cn.yyx.research.AeroSpikeHandle.AeroHelper;
import cn.yyx.research.AeroSpikeHandle.Parameters;
import cn.yyx.research.LMModelHandle.ModelIterator;

public class AeroSpikeDataBaseReadWriteTest {
	public void StartPutIntoAeroSpike(String trainfilepath) throws Exception {
		File f = new File(trainfilepath);
		if (!f.exists()) {
			// System.err.println("There is no trainfile in path:" + trainfilepath);
			throw new Exception("There is no trainfile in path:" + trainfilepath);
		}
		ModelIterator mi = new ModelIterator(trainfilepath);
		mi.IterateFile();
	}
	
	public static void main(String[] args) {
		Parameters param2 = new Parameters("127.0.0.1", 3000, null, null, "yyx", "codengram");
		AeroHelper.ANewClient(1, param2);
		Parameters param = new Parameters("127.0.0.1", 3000, null, null, "yyx", "code1sim");
		AeroHelper.ANewClient(2, param);
		AeroSpikeDataBaseReadWriteTest app = new AeroSpikeDataBaseReadWriteTest();
		
		// real path:/home/yyx/HomeSpace/UnzipAllFiles/TransformedData/BigClassDetail/ClassWorkSpace/sorted-trainfile.lm
		// test path:smaltest/test-trainfile.lm
		
		try {
			app.StartPutIntoAeroSpike(
					// "/home/yyx/HomeSpace/UnzipAllFiles/TransformedData/BigClassDetail/ClassWorkSpace/sorted-trainfile.lm"
					"smaltest/test-trainfile.lm"
					);
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}
		AeroHelper.CloseClient(1);
		AeroHelper.CloseClient(2);
	}
}