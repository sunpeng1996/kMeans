package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import domain.ClassDistance;
import domain.DataObject;
import domain.DataVector;
import domain.PointDistance;
import domain.PointDistanceFunction;
import domain.ViewObject;

public class KMeans_Operator {
	public ViewObject Cluster(DataVector datavector, int classnum,PointDistance PD,ClassDistance CD){
		ViewObject ret = new ViewObject();
		//初始化操作
		int m = datavector.size();
		PointDistanceFunction pdf = choosePointDisFunc(PD);
		Map<Integer,DataVector> classes = new HashMap<Integer,DataVector>();       //用来存储各个簇
		Map<Integer, ArrayList<Integer>> clusterMap = new HashMap<Integer, ArrayList<Integer>>();

		Map<Integer, ArrayList<Integer>> copyMap= new HashMap<Integer, ArrayList<Integer>>();
		ArrayList<Map<Integer, ArrayList<Integer>>> iterationArrayList = new ArrayList<Map<Integer,ArrayList<Integer>>>();
		for(int i=0;i<m;++i){									   //将每个元素都作为一个初始簇放入map里
			DataVector dv = new DataVector();
			dv.add(datavector.get(i));
			classes.put(i, dv);
			ArrayList<Integer> arr = new ArrayList<Integer>();
			arr.add(i);
			clusterMap.put(i, arr);
		}
		for(Entry<Integer, ArrayList<Integer>> e:clusterMap.entrySet()){
			Integer integer = new Integer(e.getKey());
			ArrayList<Integer> arrayList = (ArrayList<Integer>) e.getValue().clone();
			copyMap.put(integer, arrayList);
		}
		iterationArrayList.add(copyMap);
		//初始化距离矩阵
		double[][] distances = new double[m][m];
		System.out.println(classes);
		for(int i=0;i<m;++i){
			for(int j = 0;j<i+1;j++){
				System.out.print("\t");
			}
			for(int j = i+1;j<m;j++){
				distances[i][j] = getClassDistance(classes.get(i), classes.get(j), pdf, CD);
				System.out.printf("%.2f\t",distances[i][j]);
			}
			System.out.println();
		}
		//begin iteration
		int size = m;
		while(size>classnum){
			
			double min = Integer.MAX_VALUE;
			DataVector unionee = null;
			DataVector unioner = null;
			int index_row = 0;
			int index_col = 0;
			for(int i=0;i<m;++i){
				for(int j = i+1;j<m;j++){
					if(min > distances[i][j] && (Math.abs(distances[i][j])>0.000001)) {
						min = distances[i][j];
						index_row = i;
						index_col = j;
					}
				}
			}
			System.out.println(min+" row:"+index_row+" col:"+index_col);
			//union class
			int index_min = index_col>index_row? index_row:index_col;
			int index_max = index_col<index_row? index_row:index_col;
			unionee = classes.get(index_max);
			unioner = classes.get(index_min);
			for(DataObject dobj:unionee){
				unioner.add(dobj);
			}
			for(Integer i:clusterMap.get(index_max)){
				clusterMap.get(index_min).add(i);
			}
			clusterMap.remove(index_max);

			classes.remove(index_max);
			
			//update distances matrix
			for(int i=0;i<m;++i){
				for(int j = i+1;j<m;j++){
					if ( i == index_max || j == index_max) {
						distances[i][j]= 0; 						
					}
					if ((i == index_min || j == index_min)&&(Math.abs(distances[i][j])>0.000001) ) {
						distances[i][j] = getClassDistance(classes.get(i), classes.get(j), pdf, CD);
					}
				}
			}
			displaymatrix(distances, m);
			System.out.println(clusterMap);
			//深拷贝map将其放入
			Map<Integer, ArrayList<Integer>> copy= new HashMap<Integer, ArrayList<Integer>>();
			for(Entry<Integer, ArrayList<Integer>> e:clusterMap.entrySet()){
				Integer integer = new Integer(e.getKey());
				ArrayList<Integer> arrayList = (ArrayList<Integer>) e.getValue().clone();
				copy.put(integer, arrayList);
			}
			iterationArrayList.add(copy);
			size--;
		}
		System.out.println(iterationArrayList);
		ret.setIterationArrayList(iterationArrayList);
		return ret;
	}
	private void displaymatrix(double[][] distances,int m){
		for(int i=0;i<m;++i){
			for(int j = 0;j<i+1;j++){
				System.out.print("\t");
			}
			for(int j = i+1;j<m;j++){
				System.out.printf("%.2f\t",distances[i][j]);
			}
			System.out.println();
		}
	}
	public ViewObject Normal(DataVector datavector,int classnum,PointDistance PD){
		ViewObject ret = new ViewObject();
		
		int n = datavector.getDimension();
		PointDistanceFunction pdf = choosePointDisFunc(PD);
		DataVector points = getRandomData(datavector,classnum);
		DataVector initDataVector = new DataVector();
		for(DataObject do1:points){
			initDataVector.add((DataObject) do1.clone());
		}
		ret.setInit(initDataVector);
		
		double min = 0;
		double distance = 0;
		boolean unchange = true;
		int[] num = new int[classnum];
		DataObject[] sum = new DataObject[classnum];  //总和
		//begin iteration
		while(true){
			System.out.println(points);
			//一轮迭代初始化
			unchange = true;
			
			//遍历数据集合
			for(DataObject dobj:datavector){
				//计算每个数据最近的点，并赋予类标号  
				min = pdf.getDistance(dobj, points.get(dobj.getSignal()));
				for(int i = 0;i<classnum;++i){
					distance = pdf.getDistance(dobj, points.get(i));
					if(min > distance){
						min = distance;
						unchange = false;
						dobj.setSignal(i);
					}
				}
				//重新计算质心
				double temp = 0;
				for(int i = 0;i<n;++i){
					temp =  sum[dobj.getSignal()].get(i)+dobj.get(i);
					sum[dobj.getSignal()].set(i,temp);
					num[dobj.getSignal()]++;
				}
				
			}
			//重新设置质心
			for(int sig = 0;sig<classnum;++sig){
				for(int dim = 0;dim<n;++dim){
					points.get(sig).set(dim, sum[sig].get(dim)/num[sig]);
				}
			}
			System.out.println(datavector);
			if(unchange) break;
			
		}
		for (DataObject dobj:datavector) {
			num[dobj.getSignal()]++;
		}
		ret.setClassnum(num,classnum);
		ret.setResult((DataVector) points.clone());
		return ret;
	}
	private DataVector getRandomData(DataVector datavector,int classnum){
		DataVector points = new DataVector();
		int[] randnum = getRandompoints(classnum,datavector.size());
		for(int i = 0;i<classnum;++i){
			points.add(datavector.get(randnum[i]));
		}
		return points;
	}
	private int[] getRandompoints(int classnum,int size){
		int[] randomnum = new int[classnum];
		Random rand = new Random();
		boolean[] bool = new boolean[size];
		int randint = 0;
		for(int i = 0;i<classnum;++i){
			do{
				randint = rand.nextInt(size);
			}while(bool[randint]);
			bool[randint] = true;
			randomnum[i] = randint;
		}		
		return randomnum;
	}
	public PointDistanceFunction choosePointDisFunc(PointDistance PD){
		PointDistanceFunction pdf = null;
		if(PD == PointDistance.BLOCK){
			pdf = new Block();
		}else if(PD == PointDistance.CHEBYCHEV){
			pdf = new Chebychev();
		}else if(PD == PointDistance.CORRELATION){
			pdf = new Correlation();
		}else if(PD == PointDistance.COSIN){
			pdf = new Cosin();
		}else if(PD == PointDistance.EUCLIDEAN){
			pdf = new Euclidean();
		}
		return pdf;
	}
	public double getClassDistance(DataVector class1,DataVector class2,PointDistanceFunction pdf,ClassDistance CD){
		double d = 0;
		if(CD == ClassDistance.AVR){
			d = avr(class1, class2,pdf); 
		}else if(CD == ClassDistance.MIN){
			d = min(class1, class2,pdf); 
		}else if(CD == ClassDistance.MAX){
			d = max(class1, class2,pdf); 
		}else if(CD == ClassDistance.CORE){
			d = core(class1, class2,pdf); 
		}
		return d;
	}
	public double min(DataVector class1,DataVector class2,PointDistanceFunction pdf){
		double min = 0;
		for(int i = 0;i<class1.size();i++){
			for(int j = 0;j<class2.size();j++){
				double d = pdf.getDistance(class1.get(i), class2.get(j));
				if(min > d){
					min = d;
				}
			}
		}
		return min;
	}
	public double max(DataVector class1,DataVector class2,PointDistanceFunction pdf){
		double max = 0;
		for(int i = 0;i<class1.size();i++){
			for(int j = 0;j<class2.size();j++){
				double d = pdf.getDistance(class1.get(i), class2.get(j));
				
				if(max < d){
					max = d;
				}
			}
		}
		return max;
	}
	public double avr(DataVector class1,DataVector class2,PointDistanceFunction pdf){
		double avr = 0;
		for(int i = 0;i<class1.size();i++){
			for(int j = 0;j<class2.size();j++){
				avr += pdf.getDistance(class1.get(i), class2.get(j));
			}
		}
		return avr/(class1.size()*class2.size());
	}
	public double core(DataVector class1,DataVector class2,PointDistanceFunction pdf){
		int n = class1.getDimension();
		DataObject do1 = new DataObject();
		DataObject do2 = new DataObject();
		for(int i = 0;i<n;i++){
			Double avr1 = new Double(0);
			Double avr2 = new Double(0);
			for(int j = 0;j<class1.size();++j){
				avr1 += class1.get(i).get(j);
			}
			for(int j = 0;j<class2.size();++j){
				avr2 += class2.get(i).get(j);
			}
			avr1 /= class1.size();
			avr2 /= class2.size();
			do1.add(avr1);
			do2.add(avr2);
		}
		return pdf.getDistance(do1, do2);
	}
}
