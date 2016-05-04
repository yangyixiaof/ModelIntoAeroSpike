package cn.yyx.research.PutModelIntoAeroSpike;

import cn.yyx.research.AeroSpikeHandle.AeroHelper;
import cn.yyx.research.AeroSpikeHandle.Parameters;
import cn.yyx.research.CountModelHandle.Order1ModelIterator;
import cn.yyx.research.CountModelHandle.OrderModelIterator;

/**
 * Hello world!
 *
 */
public class ModelSerialize {
	
	public void StartPutCountModelIntoAeroSpike(int order) throws Exception {
		/*{
			// raw one sentence parse block.
			ComplexParser.GetSentence("A@@C0?0*=this.frequencyX");
			ComplexParser.GetSentence("PeE@+.5");
			ComplexParser.GetSentence("Q@ACTION_SIZE.Action]");
			System.err.println("one sentence parse block over.");
			System.exit(1);
		}*/
		
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
		Parameters param2 = new Parameters("127.0.0.1", 3000, null, null, "yyx", "codengram");
		AeroHelper.ANewClient(1, param2);
		Parameters param = new Parameters("127.0.0.1", 3000, null, null, "yyx", "code1sim");
		AeroHelper.ANewClient(2, param);
		ModelSerialize app = new ModelSerialize();
		
		try {
			app.StartPutCountModelIntoAeroSpike(2);
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}
		AeroHelper.CloseClient(1);
		AeroHelper.CloseClient(2);
	}
}