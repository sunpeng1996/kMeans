package domain;
import java.util.ArrayList;
import java.util.Collection;
public class DataObject extends ArrayList<Double>{
	private Integer id;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private int signal;
	public int getSignal() {
		return signal;
	}

	public void setSignal(int signal) {
		this.signal = signal;
	}

	private static final long serialVersionUID = 1L;
	public DataObject() {
		super();
	}

	public DataObject(Collection<? extends Double> c) {
		super(c);
	}

	public DataObject(int initialCapacity) {
		super(initialCapacity);
	}

	@Override
	public String toString() {
		StringBuilder s =  new StringBuilder("DataObject [类标号:"+signal+" 数据部分:");
		for(Double d:this){
			s.append(d+" ");
		}
		s.append("]");
		return s.toString();
	}
	
}
