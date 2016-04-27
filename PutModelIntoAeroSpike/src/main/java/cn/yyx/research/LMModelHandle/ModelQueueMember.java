package cn.yyx.research.LMModelHandle;

public class ModelQueueMember implements Comparable<ModelQueueMember> {
	
	private Double priority = (double) -1;
	private String val = null;
	
	public ModelQueueMember(double priority, String val)
	{
		this.setPriority(priority);
		this.setVal(val);
	}
	
	@Override
	public int compareTo(ModelQueueMember o) {
		return -priority.compareTo(o.priority);
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