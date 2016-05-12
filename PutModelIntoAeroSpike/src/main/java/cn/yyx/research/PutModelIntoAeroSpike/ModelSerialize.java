package cn.yyx.research.PutModelIntoAeroSpike;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import cn.yyx.research.CountModelHandle.CountModelMetaInfo;
import cn.yyx.research.CountModelHandle.Order1ModelIterator;
import cn.yyx.research.CountModelHandle.OrderModelIterator;
import cn.yyx.research.AeroSpikeHandle.AeroHelper;
import cn.yyx.research.AeroSpikeHandle.Parameters;

/**
 * Hello world!
 *
 */
public class ModelSerialize {
	
	public static final int code1sim = 1;

	public static final int codengram = 2;
	
	public void StartPutCountModelIntoAeroSpike(int order) throws Exception {
		/*{
			// raw one sentence parse block.
			ComplexParser.GetSentence("A@@C0?0*=this.frequencyX");
			ComplexParser.GetSentence("PeE@+.5");
			ComplexParser.GetSentence("Q@ACTION_SIZE.Action]");
			System.err.println("one sentence parse block over.");
			System.exit(1);
		}*/
		
		// ModelChecker.CheckOneSentenceWithNoExit("A@überServlet.SPARQLServer=true");
		// System.exit(1);
		
		// redirect error stream
		try {
			System.setErr(new PrintStream(new FileOutputStream("system_err.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		{
			OrderModelIterator omi = new Order1ModelIterator();
			omi.IterateFile();
			// System.err.println("Currently Stop Here, just test order1 to check parse functionality.");
			// System.exit(1);
		}
		
		for (int i=1;i<order;i++)
		{
			OrderModelIterator omi = new OrderModelIterator(i+1);
			omi.IterateFile();
		}
	}

	public static void main(String[] args) {
		// testing
		CountModelMetaInfo.finaldir = "/home/yyx/CodeComletionTestSpace/IR_yyx/BigClassDetail/ClassWorkSpace/results";
		String ip = "127.0.0.1";
		
		// normal
		// String ip = "192.168.1.100";
		Parameters param = new Parameters(ip, 3000, null, null, "yyx", "code1sim");
		AeroHelper.ANewClient(code1sim, param);
		Parameters param2 = new Parameters(ip, 3000, null, null, "yyx", "codengram");
		AeroHelper.ANewClient(codengram, param2);
		ModelSerialize app = new ModelSerialize();
		try {
			app.StartPutCountModelIntoAeroSpike(5);
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}
		AeroHelper.CloseClient(1);
		AeroHelper.CloseClient(2);
	}
}