package service;

import domain.DataObject;
import domain.PointDistanceFunction;

public class Correlation implements PointDistanceFunction {

	@Override
	public double getDistance(DataObject do1, DataObject do2) {
		int n = do1.size();
		int sum = 0;
		double avr1 = 0;
		double avr2 = 0;
		for(int i = 0;i<n;i++){
			avr1 += do1.get(i);
			avr2 += do2.get(i);
		}
		avr1 /= n;
		avr2 /= n;
		
		double d1 = 0;
		double d2 = 0;
		for(int i = 0;i<n;i++){
			d1 += Math.pow((do1.get(i) - avr1),2);
			d2 += Math.pow((do2.get(i) - avr2),2);
		}
		d1 = Math.sqrt(d1);
		d2 = Math.sqrt(d2);
		
		for(int i = 0;i<n;i++){
			sum += (do1.get(i)-avr1)*(do2.get(i)-avr2);
		}
		
		return sum/(d1*d2);
	}

}
