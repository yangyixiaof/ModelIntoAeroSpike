package cn.yyx.research.AeroSpikeHandle;

import java.util.ArrayList;
import java.util.List;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;

import cn.yyx.research.AeroSpikeHandle.AeroClientManager;
import cn.yyx.research.AeroSpikeHandle.Console;
import cn.yyx.research.AeroSpikeHandle.Parameters;
import cn.yyx.research.AeroSpikeHandle.ValidateHelper;

public class AeroHelper {
	
	public static final Console console = new Console();
	private static AeroClientManager acm = new AeroClientManager();
	
	public static void ANewClient(Integer id, Parameters param)
	{
		// new Parameters("127.0.0.1", 3000, null, null, "test", "demoset");
		acm.ANewClient(id, param);
	}
	
	public static AerospikeClient GetClient(Integer id)
	{
		return acm.GetClient(id);
	}
	
	public static void CloseClient(Integer id)
	{
		acm.CloseClient(id);
	}
	
	public static void PutIntoAero(Integer id, String key, Bin bin1, int binsize)
	{
		if (binsize > AeroMetaData.MaxMethodSimilarNum)
		{
			System.out.println("What the fuck! size too large, the system will exit.");
			System.err.println("What the fuck! size too large, the system will exit.");
			System.exit(1);
		}
		try {
			AerospikeClient client = acm.GetClient(id);
			Parameters param = acm.GetParameters(id);
			client.put(null, new Key(param.getNamespace(), param.getSet(), key), bin1);
		} catch (Exception e) {
			System.out.println("Error Happened in 1 order." + "Error Key is:" + key + ";" + "prredicts size:" + binsize);
			e.printStackTrace(System.out);
			e.printStackTrace();
			System.exit(1);
		}
		catch (Error e) {
			System.out.println("Error Happened in 1 order." + "Error Key is:" + key + ";" + "prredicts size:" + binsize);
			e.printStackTrace(System.out);
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void PutIntoAero(Integer id, String key, Bin bin1, Bin bin2, int binsize)
	{
		if (binsize > AeroMetaData.MaxBinNum)
		{
			System.out.println("What the fuck! size too large, the system will exit.");
			System.err.println("What the fuck! size too large, the system will exit.");
			System.exit(1);
		}
		try {
			AerospikeClient client = acm.GetClient(id);
			Parameters param = acm.GetParameters(id);
			client.put(null, new Key(param.getNamespace(), param.getSet(), key), bin1, bin2);
		} catch (Exception e) {
			System.out.println("Error Happened in 2 and above 2 order." + "Error Key is:" + key + ";" + "prredicts size:" + binsize);
			e.printStackTrace(System.out);
			e.printStackTrace();
			System.exit(1);
		}
		catch (Error e) {
			System.out.println("Error Happened in 2 and above 2 order." + "Error Key is:" + key + ";" + "prredicts size:" + binsize);
			e.printStackTrace(System.out);
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void testListStrings(Integer id) throws Exception {
		console.info("Read/Write ArrayList<String>");
		AerospikeClient client = acm.GetClient(id);
		Parameters params = acm.GetParameters(id);
		Key key2 = new Key(params.getNamespace(), params.getSet(), "listkey1");
		client.delete(params.writePolicy, key2);

		ArrayList<String> list2 = new ArrayList<String>();
		list2.add("string1");
		list2.add("string2");
		list2.add("string3");

		Bin bin2 = new Bin("listbin1", list2);
		client.put(params.writePolicy, key2, bin2);
		
		Key key = new Key(params.getNamespace(), params.getSet(), "listkey2");
		client.delete(params.writePolicy, key);

		ArrayList<String> list = new ArrayList<String>();
		list.add("string4");
		list.add("string5");
		list.add("string6");

		Bin bin = new Bin("listbin1", list);
		client.put(params.writePolicy, key, bin);

		Record record = client.get(params.policy, key, bin.name);
		List<?> receivedList = (List<?>) record.getValue(bin.name);

		ValidateHelper.validateSize(3, receivedList.size());
		ValidateHelper.validate("string4", receivedList.get(0));
		ValidateHelper.validate("string5", receivedList.get(1));
		ValidateHelper.validate("string6", receivedList.get(2));

		console.info("Read/Write ArrayList<String> successful.");
	}

}