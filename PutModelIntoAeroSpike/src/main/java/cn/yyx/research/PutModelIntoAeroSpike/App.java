package cn.yyx.research.PutModelIntoAeroSpike;

import java.io.File;

import cn.yyx.research.AeroSpikeHandle.AeroHelper;
import cn.yyx.research.AeroSpikeHandle.Parameters;
import cn.yyx.research.ModelHandle.ModelIterator;

/**
 * Hello world!
 *
 */
public class App {

	public void StartPutIntoAeroSpike(String trainfilepath) {
		File f = new File(trainfilepath);
		if (!f.exists()) {
			System.err.println("There is no trainfile in path:" + trainfilepath);
			System.exit(1);
		}
		ModelIterator mi = new ModelIterator(trainfilepath);
		mi.IterateFile();
	}

	public static void main(String[] args) {
		Parameters param2 = new Parameters("127.0.0.1", 3000, null, null, "test", "codengram");
		AeroHelper.ANewClient(1, param2);
		Parameters param = new Parameters("127.0.0.1", 3000, null, null, "test", "code1sim");
		AeroHelper.ANewClient(2, param);
		App app = new App();
		
		// real path:/home/yyx/HomeSpace/UnzipAllFiles/TransformedData/BigClassDetail/ClassWorkSpace/sorted-trainfile.lm
		// test path:smaltest/test-trainfile.lm
		
		app.StartPutIntoAeroSpike(
				"/home/yyx/HomeSpace/UnzipAllFiles/TransformedData/BigClassDetail/ClassWorkSpace/sorted-trainfile.lm"
				);
		AeroHelper.CloseClient(1);
	}
}