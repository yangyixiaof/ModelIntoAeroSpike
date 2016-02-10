package cn.yyx.research.AeroSpikeHandle;

import java.util.Map;
import java.util.TreeMap;

import com.aerospike.client.async.AsyncClient;
import com.aerospike.client.async.AsyncClientPolicy;

public class AeroClientManager {
	
	Map<Integer, AsyncClient> clientManager = new TreeMap<Integer, AsyncClient>();
	Map<Integer, Parameters> paramManager = new TreeMap<Integer, Parameters>();
	
	public AsyncClient GetClient(Integer id)
	{
		return clientManager.get(id);
	}
	
	public Parameters GetParameters(Integer id)
	{
		return paramManager.get(id);
	}
	
	public AeroClientManager() {
	}
	
	public void ANewClient(Integer id, Parameters params)
	{
		AsyncClientPolicy policy = new AsyncClientPolicy();
		policy.user = params.user;
		policy.password = params.password;
		policy.asyncMaxCommands = 300;
		policy.asyncSelectorThreads = 1;
		policy.asyncSelectorTimeout = 10;
		policy.failIfNotConnected = true;
		
		params.policy = policy.asyncReadPolicyDefault;
		params.writePolicy = policy.asyncWritePolicyDefault;
		
		AsyncClient client = new AsyncClient(policy, params.host, params.port);

		try {
			params.setServerSpecific(client);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		clientManager.put(id, client);
		paramManager.put(id, params);
	}
	
	public void CloseClient(Integer id)
	{
		clientManager.get(id).close();
		clientManager.remove(id);
		paramManager.remove(id);
	}
	
}