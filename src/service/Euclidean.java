package service;

import domain.DataObject;
import domain.PointDistanceFunction;

public class Euclidean implements PointDistanceFunction {

	@Override
	public double getDistance(DataObject do1, DataObject do2) {
		int n = do1.size();
		int sum = 0;
		for(int i = 0;i<n;i++){
			sum += Math.pow(do1.get(i) - do2.get(i),2);
		}
		return Math.sqrt(sum);
	}

}
