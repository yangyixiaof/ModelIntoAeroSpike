package cn.yyx.research.ModelHandle;

import java.util.Comparator;

public class ModelQueueMember implements Comparator<ModelQueueMember>{
	
	private Double priority = (double) -1;
	private String val = null;
	
	public ModelQueueMember(double priority, String val)
	{
		this.setPriority(priority);
		this.setVal(val);
	}
	
	@Override
	public int compare(ModelQueueMember o1, ModelQueueMember o2) {
		return -Double.compare(o1.getPriority(), o2.getPriority());
	}

	public Double getPriority() {
		return priority;
	}

	public void setPriority(Double priority) {
		this.priority = priority;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}
	
}