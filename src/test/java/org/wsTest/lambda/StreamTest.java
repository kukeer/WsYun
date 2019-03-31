package org.wsTest.lambda;

import java.util.ArrayList;

public class StreamTest {
	
	
	public static void main(String[] args) {
		ArrayList<Float> arrayList = new ArrayList<Float>();
		arrayList.add(1.2f);
		arrayList.add(1.2f);
		arrayList.add(null);
		arrayList.add(null);
		arrayList.add(1.2f);
		arrayList.add(1.2f);
		arrayList.add(1.2f);
		arrayList.add(1.2f);
		double asDouble = arrayList.stream().filter(x -> x!=null)
				.mapToDouble(Float::floatValue)
				.average()
				.getAsDouble();
		System.out.println(asDouble);
	}
}
