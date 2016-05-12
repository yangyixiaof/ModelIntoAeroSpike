package cn.yyx.research.PutModelIntoAeroSpike;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Key;
import com.aerospike.client.Record;

import cn.yyx.research.AeroSpikeHandle.AeroHelper;
import cn.yyx.research.AeroSpikeHandle.AeroMetaData;
import cn.yyx.research.AeroSpikeHandle.Parameters;

public class AeroSpikeReadTest {
	
	public static void main(String[] args) {
		String ip = "192.168.1.101";
		Parameters param2 = new Parameters(ip, 3000, null, null, "yyx", "codengram");
		AeroHelper.ANewClient(AeroMetaData.codengram, param2);
		
		AerospikeClient ac = AeroHelper.GetClient(AeroMetaData.codengram);
		Record record = ac.get(param2.policy, new Key(param2.getNamespace(), param2.getSet(), "DH@{"), AeroMetaData.BinPredictName, AeroMetaData.BinProbabilityName);
		System.out.println("record:" + record);
	}
	
}