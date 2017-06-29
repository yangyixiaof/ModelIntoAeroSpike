package cn.yyx.research.CountModelHandle;

import java.util.ArrayList;
import java.util.Queue;

import com.aerospike.client.Bin;

import cn.yyx.research.AeroSpikeHandle.AeroHelper;
import cn.yyx.research.AeroSpikeHandle.AeroMetaData;
import cn.yyx.research.LMModelHandle.SimpleMethodParser;

public class Order1ModelIterator extends OrderModelIterator{

	public Order1ModelIterator() {
		super(1);
	}
	
	@Override
	protected String DoGramWork(String key, ArrayList<String> predict, ArrayList<Integer> prob,
			Queue<CountModelQueueMember> queue, String[] ss) {
		if (ss == null)
		{
			if (key != null)
			{
				PutToAero(key, queue);
			}
		}
		else
		{
			
			// do Complex Parse Check.
			// ModelChecker.CheckOneSentenceWithNoExit(ss[0]);
			
			String afterkey = null;
			if (SimpleMethodParser.IsMethodInvocation(ss[0]))
			{
				afterkey = SimpleMethodParser.GetMethodInvocationName(ss[0]);
			}
			if (SimpleMethodParser.IsMethodDeclaration(ss[0]))
			{
				afterkey = SimpleMethodParser.GetMethodSignatureName(ss[0]);
			}
			// to this location, afterkey could be null.
			if (key == null)
			{
				if (afterkey != null)
				{
					if (queue == null)
					{
						System.err.println("queue null.");
					}
					queue.add(new CountModelQueueMember(Integer.parseInt(ss[1]), ss[0]));
				}
				return afterkey;
			}
			else
			{
				if (afterkey != null && key.equals(afterkey))
				{
					queue.add(new CountModelQueueMember(Integer.parseInt(ss[1]), ss[0]));
					return key;
				}
				else
				{
					PutToAero(key, queue);
					if (afterkey != null)
					{
						queue.add(new CountModelQueueMember(Integer.parseInt(ss[1]), ss[0]));
					}
				}
				return afterkey;
			}
		}
		// postfix : key must be null.
		return null;
	}
	

	private void PutToAero(String key, Queue<CountModelQueueMember> queue)
	{
		int num = 0;
		ArrayList<String> similar = new ArrayList<String>();
		while (!queue.isEmpty())
		{
			num++;
			if (num > AeroMetaData.MaxMethodSimilarNum)
			{
				break;
			}
			CountModelQueueMember member = queue.poll();
			String mname = member.getVal();
			similar.add(mname);
		}
		AeroHelper.PutIntoAero(AeroMetaData.code1sim, key, new Bin(AeroMetaData.BinSimilarName, similar), similar.size());
		queue.clear();
	}
	
}