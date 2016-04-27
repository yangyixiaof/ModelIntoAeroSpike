package cn.yyx.research.CountModelHandle;

public class CountModelQueueMember implements Comparable<CountModelQueueMember> {
	
	private Integer happens = -1;
	private String val = null;
	
	public CountModelQueueMember(int happens, String val)
	{
		this.setHappens(happens);
		this.setVal(val);
	}
	
	@Override
	public int compareTo(CountModelQueueMember o) {
		return ((Integer)(-happens)).compareTo((Integer)(-o.happens));
	}
	
	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public int getHappens() {
		return happens;
	}

	public void setHappens(int happens) {
		this.happens = happens;
	}
	
}