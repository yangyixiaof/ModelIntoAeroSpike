package cn.yyx.research.PutModelIntoAeroSpike;

import cn.yyx.research.AeroSpikeHandle.AeroHelper;
import cn.yyx.research.AeroSpikeHandle.Parameters;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        AeroHelper.ANewClient(1, new Parameters("127.0.0.1", 3000, null, null, "test", "demoset"));
        try {
			AeroHelper.testListStrings(1, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
        AeroHelper.CloseClient(1);
    }
}