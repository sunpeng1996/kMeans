package service;

import domain.DataObject;
import domain.PointDistanceFunction;

public class Cosin implements PointDistanceFunction {

	@Override
	public double getDistance(DataObject do1, DataObject do2) {
		int n = do1.size();
		double len1 = 0;
		double len2 = 0;
		for(int i = 0;i<n;i++){
			len1 += Math.pow(do1.get(i),2);
			len2 += Math.pow(do2.get(i),2);
		}
		len1 = Math.sqrt(len1);
		len2 = Math.sqrt(len2);
		double sum = 0;
		for(int i = 0;i<n;i++){
			sum += do1.get(i)*do2.get(i);
		}
		return sum/(len1*len2);
	}

}
