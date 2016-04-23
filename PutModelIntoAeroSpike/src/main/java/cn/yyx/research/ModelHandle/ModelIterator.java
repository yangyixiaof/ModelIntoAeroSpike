package cn.yyx.research.ModelHandle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

import com.aerospike.client.Bin;

import cn.yyx.research.AeroSpikeHandle.AeroHelper;
import cn.yyx.research.AeroSpikeHandle.AeroMetaData;

public class ModelIterator {
	
	File file = null;
	
	public ModelIterator(String filepath) {
		file = new File(filepath);
	}
	
	@SuppressWarnings("unused")
	public void IterateFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String oneline = null;
			int line = 0;
			int allline = 0;
			int minimal = 1;
			Queue<ModelQueueMember> priorityQueue =  new PriorityQueue<ModelQueueMember>();
			String key = null;
			ArrayList<String> predict = new ArrayList<String>();
			ArrayList<Double> prob = new ArrayList<Double>();
			while ((oneline = br.readLine()) != null)
			{
				allline++;
				oneline = oneline.trim();
				
				if (oneline.equals("\\1-grams:"))
				{
					minimal = 2;
				}
				else
				{
					if (oneline.equals("\\2-grams:"))
					{
						DoGramWork(key, null, null, priorityQueue, null, minimal);
						minimal = 3;
					}
					else
					{
						if (oneline.equals("\\3-grams:"))
						{
							key = DoGramWork(key, predict, prob, null, null, minimal);
							minimal = 4;
						}
					}
				}
				
				String[] ss = oneline.split("\\s+");
				if (ss.length >= 2 && minimal >= 2)
				{
					line++;
					System.out.println("current aline:"+allline + ";valid-line:"+line);
					
					if (minimal == 2)
					{
						// parse checking
						// ModelChecker.CheckOneSentence(ss[1]);
						ModelChecker.CheckOneSentenceWeaker(ss[1]);
						/*try {
							ParseRoot.ParseOneSentence(ss[1], null, true);
						} catch (Exception e) {
							System.err.println("Check Parse Error, the system will exit.");
							System.exit(1);
						} catch (Error e) {
							System.err.println("Check Parse Error, the system will exit.");
							System.exit(1);
						}*/
					}
					
					key = DoGramWork(key, predict, prob, priorityQueue, ss, minimal);
					if (AeroMetaData.MaxPutAllLineNum > 0)
					{
						if (line > AeroMetaData.MaxPutAllLineNum)
						{
							break;
						}
					}
				}
			}
			key = DoGramWork(key, predict, prob, priorityQueue, null, minimal);
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String DoGramWork(String key, ArrayList<String> predict, ArrayList<Double> prob, Queue<ModelQueueMember> queue, String[] ss, int minimal)
	{
		if (ss == null)
		{
			if (key != null)
			{
				if (minimal == 2)
				{
					PutToAero(key, queue);
				}
				else
				{
					PutToAero(key, predict, prob);
				}
			}
		}
		else
		{
			String afterkey = null;
			if (minimal == 2)
			{
				if (SimpleMethodParser.IsMethodInvocation(ss[1]))
				{
					afterkey = SimpleMethodParser.GetMethodInvocationName(ss[1]);
				}
				if (SimpleMethodParser.IsMethodDeclaration(ss[1]))
				{
					afterkey = SimpleMethodParser.GetMethodSignatureName(ss[1]);
				}
			}
			else
			{
				afterkey = ss[1];
				for (int i=2;i<minimal-1;i++)
				{
					afterkey += (" " + ss[i]);
				}
			}
			// to this location, afterkey could be null.
			if (key == null)
			{
				if (minimal == 2)
				{
					if (afterkey != null)
					{
						if (queue == null)
						{
							System.err.println("queue null.");
						}
						queue.add(new ModelQueueMember(Double.parseDouble(ss[0]), ss[minimal-1]));
					}
				}
				else
				{
					predict.add(ss[minimal-1]);
					prob.add(Double.parseDouble(ss[0]));
				}
				return afterkey;
			}
			else
			{
				if (afterkey != null && key.equals(afterkey))
				{
					if (minimal == 2)
					{
						queue.add(new ModelQueueMember(Double.parseDouble(ss[0]), ss[minimal-1]));
					}
					else
					{
						predict.add(ss[minimal-1]);
						prob.add(Double.parseDouble(ss[0]));
					}
					return key;
				}
				else
				{
					if (minimal == 2)
					{
						PutToAero(key, queue);
						if (afterkey != null)
						{
							queue.add(new ModelQueueMember(Double.parseDouble(ss[0]), ss[minimal-1]));
						}
					}
					else
					{
						PutToAero(key, predict, prob);
						predict.add(ss[minimal-1]);
						prob.add(Double.parseDouble(ss[0]));
					}
					return afterkey;
				}
			}
		}
		// postfix : key must be null.
		return null;
	}
	
	private void PutToAero(String key, Queue<ModelQueueMember> queue)
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
			ModelQueueMember member = queue.poll();
			String mname = member.getVal();
			similar.add(mname);
		}
		AeroHelper.PutIntoAero(2, key, new Bin(AeroMetaData.BinSimilarName, similar));
		queue.clear();
	}
	
	private void PutToAero(String key, ArrayList<String> predict, ArrayList<Double> prob)
	{
		
		// ModelChecker.CheckWillBePutModel(key, predict);
		
		AeroHelper.PutIntoAero(1, key, new Bin(AeroMetaData.BinPredictName, predict), new Bin(AeroMetaData.BinProbabilityName, prob));
		predict.clear();
		prob.clear();
	}
	
}