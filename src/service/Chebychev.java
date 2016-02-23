package service;

import domain.DataObject;
import domain.PointDistanceFunction;

public class Chebychev implements PointDistanceFunction {

	@Override
	public double getDistance(DataObject do1, DataObject do2) {
		int n = do1.size();
		double min = 0;
		double abs = 0;
		for(int i = 0;i<n;i++){
			abs = Math.abs(do1.get(i) - do2.get(i));
			min = abs > min ? min:abs;
		}
		return min;
	}

}
