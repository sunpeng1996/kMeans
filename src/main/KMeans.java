package main;

import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.MidiChannel;

import domain.ClassDistance;
import domain.DataObject;
import domain.DataVector;
import domain.PointDistance;
import domain.ViewObject;
import service.KMeans_Operator;

public class KMeans {

	
	public static void main(String[] args) {
		ViewObject viewObject = null;
		KMeans_Operator ko = new KMeans_Operator();
		DataObject do1 = new DataObject();
		DataObject do2 = new DataObject();
		DataObject do3 = new DataObject();
		DataObject do4 = new DataObject();
		DataObject do5 = new DataObject();
		DataObject do6 = new DataObject();
		DataObject do7 = new DataObject();
		DataObject do8 = new DataObject();
		do1.setId(1);
		do2.setId(2);
		do3.setId(3);
		do4.setId(4);
		do5.setId(5);
		do6.setId(6);
		do7.setId(7);
		do8.setId(8);
		PointDistance p = PointDistance.EUCLIDEAN;
		
		do1.add(0d);
		do1.add(1d);
		do2.add(0d);
		do2.add(2d);
		do3.add(1d);
		do3.add(1d);
		do4.add(1d);
		do4.add(2d);
		do5.add(0d);
		do5.add(-1d);
		do6.add(0d);
		do6.add(-2d);
		do7.add(-1d);
		do7.add(-1d);
		do8.add(-1d);
		do8.add(-2d);
		
		DataVector dv = new DataVector();
		dv.add(do1);
		dv.add(do2);
		dv.add(do3);
		dv.add(do4);
		dv.add(do5);
		dv.add(do6);
		dv.add(do7);
		dv.add(do8);
		viewObject = ko.Normal(dv,3, p);
//		viewObject = ko.Cluster(dv, 2, p,ClassDistance.MAX);
		System.out.println(viewObject);
	}

}
