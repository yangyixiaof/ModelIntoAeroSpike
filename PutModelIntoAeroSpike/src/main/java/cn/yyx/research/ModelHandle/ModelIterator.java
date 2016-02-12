package cn.yyx.research.ModelHandle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.aerospike.client.Bin;
import com.aerospike.client.Key;

import cn.yyx.research.AeroSpikeHandle.AeroHelper;
import cn.yyx.research.AeroSpikeHandle.Parameters;

public class ModelIterator {
	
	File file = null;
	public static final int putAllLineNum = 10;
	Parameters param = null;
	
	public ModelIterator(String filepath, Parameters param) {
		file = new File(filepath);
		this.param = param;
	}
	
	public void IterateFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String oneline = null;
			int line = 0;
			int allline = 0;
			String key = null;
			ArrayList<String> predict = new ArrayList<String>();
			ArrayList<Double> prob = new ArrayList<Double>();
			while ((oneline = br.readLine()) != null)
			{
				allline++;
				oneline = oneline.trim();
				String[] ss = oneline.split("\\s+");
				if (ss.length == 4)
				{
					line++;
					System.out.println("current aline:"+allline + ";valid-line:"+line);
					key = DoWork(key, predict, prob, ss);
					if (putAllLineNum > 0)
					{
						if (line > putAllLineNum)
						{
							break;
						}
					}
				}
			}
			key = DoWork(key, predict, prob, null);
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String DoWork(String key, ArrayList<String> predict, ArrayList<Double> prob, String[] ss)
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
			String afterkey = ss[1] + " " + ss[2];
			if (key == null)
			{
				predict.add(ss[3]);
				prob.add(Double.parseDouble(ss[0]));
				return afterkey;
			}
			else
			{
				if (key.equals(afterkey))
				{
					predict.add(ss[3]);
					prob.add(Double.parseDouble(ss[0]));
					return key;
				}
				else
				{
					PutToAero(key, predict, prob);
					predict.add(ss[3]);
					prob.add(Double.parseDouble(ss[0]));
					return afterkey;
				}
			}
		}
		// postfix : key must be null.
		return null;
	}
	
	private void PutToAero(String key, ArrayList<String> predict, ArrayList<Double> prob)
	{
		AeroHelper.PutIntoAero(1, new Key(param.getNamespace(), param.getSet(), key), new Bin("predict", predict), new Bin("probability", prob));
		predict.clear();
		prob.clear();
	}
	
}