package application;

import java.util.ArrayList;
import java.util.List;

public class ElementsGroup {//今のところ意味はない
	private Integer groupID=null;
	private Integer nextGroupID=null;
	private int grouptype=0;//0:直列 1:並列
	private double potentialDiffernce=0;
	List<Integer> elements=new ArrayList<Integer>();

	public int getGrouptype() {
		return grouptype;
	}
	public void setGrouptype(int grouptype) {
		this.grouptype = grouptype;
	}

	public double getImpedance() {
		double value=0;
		if(this.grouptype==0) {
			value=elements.stream().map(key->Circuit.elementIDs.get(key)).filter(e->e.getImpedance()!=null).mapToDouble(e->e.getImpedance()).sum();
		}
		else {
			value=(1/elements.stream().map(key->Circuit.elementIDs.get(key)).filter(e->e.getImpedance()!=null).mapToDouble(e->e.getImpedance()).map(v->(1/v)).sum());
		}
		return value;
	}
	public Integer getGroupID() {
		return groupID;
	}
	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}
	public Integer getNextGroupID() {
		return nextGroupID;
	}
	public void setNextGroupID(Integer nextGroupID) {
		this.nextGroupID = nextGroupID;
	}
	public double getPotentialDiffernce() {
		return potentialDiffernce;
	}
	public void setPotentialDiffernce(double potentialDiffernce) {
		this.potentialDiffernce = potentialDiffernce;
	}
}
