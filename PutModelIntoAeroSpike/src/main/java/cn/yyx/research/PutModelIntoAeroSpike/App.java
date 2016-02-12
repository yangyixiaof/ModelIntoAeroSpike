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

	public void StartPutIntoAeroSpike(String trainfilepath, Parameters param) {
		File f = new File(trainfilepath);
		if (!f.exists()) {
			System.err.println("There is no trainfile in path:" + trainfilepath);
			System.exit(1);
		}
		ModelIterator mi = new ModelIterator(trainfilepath, param);
		mi.IterateFile();
	}

	public static void main(String[] args) {
		Parameters param = new Parameters("127.0.0.1", 3000, null, null, "yyx", "codengram");
		AeroHelper.ANewClient(1, param);
		App app = new App();
		app.StartPutIntoAeroSpike(
				"/home/yyx/HomeSpace/UnzipAllFiles/TransformedData/BigClassDetail/ClassWorkSpace/truncated-sorted-trainfile.lm", param);
		AeroHelper.CloseClient(1);
	}
}