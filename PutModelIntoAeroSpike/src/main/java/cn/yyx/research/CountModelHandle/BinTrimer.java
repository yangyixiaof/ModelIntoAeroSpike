package cn.yyx.research.CountModelHandle;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

import cn.yyx.research.AeroSpikeHandle.AeroMetaData;
import cn.yyx.research.LMModelHandle.ModelQueueMember;

public class BinTrimer {
	
	private ArrayList<String> predict = null;
	private ArrayList<Double> prob = null;
	
	public void TrimBin(ArrayList<String> predict, ArrayList<Double> prob)
	{
		Queue<ModelQueueMember> sque = new PriorityQueue<ModelQueueMember>();
		if (predict.size() > AeroMetaData.MaxBinNum)
		{
			int len = predict.size();
			for (int i=0;i<len;i++)
			{
				sque.add(new ModelQueueMember(prob.get(i), predict.get(i)));
			}
			predict = new ArrayList<String>();
			prob = new ArrayList<Double>();
			for (int i=0;i<AeroMetaData.MaxBinNum;i++)
			{
				ModelQueueMember sp = sque.poll();
				predict.add(sp.getVal());
				prob.add(sp.getPriority());
			}
		}
		else
		{
			this.setPredict(predict);
			this.setProb(prob);
		}
	}

	public ArrayList<Double> getProb() {
		return prob;
	}

	public void setProb(ArrayList<Double> prob) {
		this.prob = prob;
	}

	public ArrayList<String> getPredict() {
		return predict;
	}

	public void setPredict(ArrayList<String> predict) {
		this.predict = predict;
	}

	public int Size() {
		return predict.size();
	}
	
}