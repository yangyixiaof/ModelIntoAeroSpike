package cn.yyx.research.CountModelHandle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

import com.aerospike.client.Bin;

import cn.yyx.research.AeroSpikeHandle.AeroHelper;
import cn.yyx.research.AeroSpikeHandle.AeroMetaData;

public class OrderModelIterator {
	
	public static final String finaldir = "/home/yyx/HomeSpace/UnzipAllFiles/TransformedData/BigClassDetail/ClassWorkSpace/results";
	
	private int order = -1;
	
	public OrderModelIterator(int order) {
		this.setOrder(order);
	}
	
	@SuppressWarnings("unused")
	public void IterateFile() {
		try {
			
			// tested
			// String filepath = "smalltest/testo2.count";
			
			String filepath = finaldir + "/" + "order" + order + "final.count";
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			String oneline = null;
			int line = 0;
			// int allline = 0;
			// int minimal = 1;
			Queue<CountModelQueueMember> priorityQueue =  new PriorityQueue<CountModelQueueMember>();
			String key = null;
			ArrayList<String> predict = new ArrayList<String>();
			ArrayList<Integer> prob = new ArrayList<Integer>();
			while ((oneline = br.readLine()) != null)
			{
				line++;
				oneline = oneline.trim();
				String[] ss = oneline.split("\\s+");
				key = DoGramWork(key, predict, prob, priorityQueue, ss);
				
				// debugging
				System.out.println("current Handled aline:"+line);
				
				if (AeroMetaData.MaxPutAllLineNum > 0)
				{
					if (line > AeroMetaData.MaxPutAllLineNum)
					{
						break;
					}
				}
			}
			DoGramWork(key, predict, prob, priorityQueue, null);
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected String DoGramWork(String key, ArrayList<String> predict, ArrayList<Integer> prob, Queue<CountModelQueueMember> queue, String[] ss)
	{
		if (ss == null)
		{
			if (key != null)
			{
				PutToAero(key, predict, prob);
			}
		}
		else
		{
			String afterkey = null;
			afterkey = "";
			for (int i=0;i<(order-1);i++)
			{
				afterkey += (" " + ss[i]);
			}
			// to this location, afterkey could be null.
			if (key == null)
			{
				predict.add(ss[order-1]);
				prob.add(Integer.parseInt(ss[order]));
				return afterkey;
			}
			else
			{
				if (afterkey != null && key.equals(afterkey))
				{
					predict.add(ss[order-1]);
					prob.add(Integer.parseInt(ss[order]));
					return key;
				}
				else
				{
					PutToAero(key, predict, prob);
					predict.add(ss[order-1]);
					prob.add(Integer.parseInt(ss[order]));
					return afterkey;
				}
			}
		}
		// postfix : key must be null.
		return null;
	}
	
	private void PutToAero(String key, ArrayList<String> predict, ArrayList<Integer> count)
	{
		ArrayList<Double> prob = ComputeProbFromCount(count);
		AeroHelper.PutIntoAero(1, key, new Bin(AeroMetaData.BinPredictName, predict), new Bin(AeroMetaData.BinProbabilityName, prob));
		predict.clear();
		count.clear();
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
	private ArrayList<Double> ComputeProbFromCount(ArrayList<Integer> count)
	{
		ArrayList<Double> result = new ArrayList<Double>();
		int total = 0;
		{
			Iterator<Integer> itr = count.iterator();
			while (itr.hasNext())
			{
				int ct = itr.next();
				total += ct;
			}
		}
		{
			Iterator<Integer> itr = count.iterator();
			while (itr.hasNext())
			{
				int ct = itr.next();
				result.add((ct*1.0)/(total*1.0));
			}
		}
		return result;
	}
	
}