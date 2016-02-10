package cn.yyx.research.AeroSpikeHandle;

import java.util.ArrayList;
import java.util.List;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;

public class AeroHelper {
	
	public static final Console console = new Console();
	private static AeroClientManager acm = new AeroClientManager();
	
	public static void ANewClient(Integer id, Parameters param)
	{
		// new Parameters("127.0.0.1", 3000, null, null, "test", "demoset");
		acm.ANewClient(id, param);
	}
	
	public static void CloseClient(Integer id)
	{
		acm.CloseClient(id);
	}

	public static void testListStrings(Integer id, String key1, String key2, String value) throws Exception {
		console.info("Read/Write ArrayList<String>");
		AerospikeClient client = acm.GetClient(id);
		Parameters params = acm.GetParameters(id);
		Key key = new Key(params.namespace, params.set, "listkey1");
		client.delete(params.writePolicy, key);

		ArrayList<String> list = new ArrayList<String>();
		list.add("string1");
		list.add("string2");
		list.add("string3");

		Bin bin = new Bin(params.getBinName("listbin1"), list);
		client.put(params.writePolicy, key, bin);

		Record record = client.get(params.policy, key, bin.name);
		List<?> receivedList = (List<?>) record.getValue(bin.name);

		ValidateHelper.validateSize(3, receivedList.size());
		ValidateHelper.validate("string1", receivedList.get(0));
		ValidateHelper.validate("string2", receivedList.get(1));
		ValidateHelper.validate("string3", receivedList.get(2));

		console.info("Read/Write ArrayList<String> successful.");
	}

}
