package domain;

import java.util.ArrayList;
import java.util.Collection;

public class DataVector extends ArrayList<DataObject> {

	private static final long serialVersionUID = 3631284735920247405L;
	private int dimension;
	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public DataVector() {
		super();
	}

	public DataVector(Collection<? extends DataObject> c) {
		super(c);
	}

	public DataVector(int initialCapacity) {
		super(initialCapacity);
	}

	@Override
	public String toString() {
		StringBuilder s =  new StringBuilder("DataVector 数据量:"+this.size()+"\n");
		for(DataObject d:this){
			s.append("\t"+d+"\n");
		}
		return s.toString();
	}
	
}
