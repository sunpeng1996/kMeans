package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class ViewObject {
	DataVector init;              //初始值质心位置
	DataVector result;		      //结束质心位置
	ArrayList<Error> iter;		  //每次迭代中的误差
	int[] classnum;               //最终分类每个类的大小
	
	@Override
	public String toString() {
		return "ViewObject [init=" + init + ", result=" + result + ", iter="
				+ iter + ", classnum=" + Arrays.toString(classnum)
				+ ", iterationArrayList=" + iterationArrayList + "]";
	}
	ArrayList<Map<Integer, ArrayList<Integer>>> iterationArrayList;
	
	
	public ArrayList<Map<Integer, ArrayList<Integer>>> getIterationArrayList() {
		return iterationArrayList;
	}
	public void setIterationArrayList(
			ArrayList<Map<Integer, ArrayList<Integer>>> iterationArrayList) {
		this.iterationArrayList = iterationArrayList;
	}
	public DataVector getInit() {
		return init;
	}
	public void setInit(DataVector init) {
		this.init = init;
	}
	public DataVector getResult() {
		return result;
	}
	public void setResult(DataVector result) {
		this.result = result;
	}
	public ArrayList<Error> getIter() {
		return iter;
	}
	public void setIter(ArrayList<Error> iter) {
		this.iter = iter;
	}
	public int[] getClassnum() {
		return classnum;
	}
	public void setClassnum(int[] classnum,int size) {
		int index = 0;
		this.classnum = new int[size];
		for(int i :classnum ){
			this.classnum[index] = i;
			index++;
		}
	}

}
