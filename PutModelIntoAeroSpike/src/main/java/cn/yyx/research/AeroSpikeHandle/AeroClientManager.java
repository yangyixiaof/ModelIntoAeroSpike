package cn.yyx.research.AeroSpikeHandle;

import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.ScanCallback;
import com.aerospike.client.policy.ClientPolicy;

public class AeroClientManager {

	Map<Integer, AerospikeClient> clientManager = new TreeMap<Integer, AerospikeClient>();
	Map<Integer, Parameters> paramManager = new TreeMap<Integer, Parameters>();

	public AerospikeClient GetClient(Integer id) {
		return clientManager.get(id);
	}

	public Parameters GetParameters(Integer id) {
		return paramManager.get(id);
	}

	public AeroClientManager() {
	}

	public void ANewClient(Integer id, Parameters params) {
		ClientPolicy policy = new ClientPolicy();
		policy.user = params.user;
		policy.password = params.password;
		policy.failIfNotConnected = true;

		params.policy = policy.readPolicyDefault;
		params.writePolicy = policy.writePolicyDefault;

		AerospikeClient client = new AerospikeClient(policy, params.host, params.port);
		DeleteAllRecordsInSet(client, params.getNamespace(), params.getSet());

		clientManager.put(id, client);
		paramManager.put(id, params);

	}

	public void CloseClient(Integer id) {
		clientManager.get(id).close();
		clientManager.remove(id);
		paramManager.remove(id);
	}

	private void DeleteAllRecordsInSet(AerospikeClient client, String name_space, String set_name) {
		LinkedList<Key> keys = new LinkedList<Key>();
		client.scanAll(client.getScanPolicyDefault(), name_space, set_name, new ScanCallback() {
			@Override
			public void scanCallback(Key k, Record r) throws AerospikeException {
				keys.add(k);
			}
		});
		System.out.println("namespace:" + name_space + "#set:" + set_name + "#before_delete_record_num:" + keys.size());
		for (Key key : keys) {
			client.delete(client.getWritePolicyDefault(), key);
		}
		keys.clear();
		client.scanAll(client.getScanPolicyDefault(), name_space, set_name, new ScanCallback() {
			@Override
			public void scanCallback(Key k, Record r) throws AerospikeException {
				keys.add(k);
			}
		});
		System.out.println("namespace:" + name_space + "#set:" + set_name + "#after_delete_record_num:" + keys.size());
	}

}