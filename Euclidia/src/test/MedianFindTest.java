package test;

import static org.junit.Assert.assertTrue;

import java.util.Comparator;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import euclid.two.dim.datastructure.HashList;

public class MedianFindTest {

	private static final int TEST_SIZE = 10000;
	private Random rand;
	private HashList<IntegerBox> integers;

	@Before
	public void setup() {
		rand = new Random(System.currentTimeMillis());
		integers = new HashList<IntegerBox>();
		for (int i = 0; i < TEST_SIZE; i++) {
			integers.add(new IntegerBox(rand.nextInt(1000)));
		}
	}

	@Test
	public void sortTest() {

		int[] result = new int[TEST_SIZE];

		Comparator<IntegerBox> comparator = new Comparator<IntegerBox>() {

			@Override
			public int compare(IntegerBox ib1, IntegerBox ib2) {
				return ib1.getValue() - ib2.getValue();
			}

		};

		long start = System.currentTimeMillis();

		for (int i = 0; i < integers.size(); i++) {
			result[i] = integers.getKthOrderStatisticWithCopy(comparator, i).getValue();
		}
		long selectTime = System.currentTimeMillis() - start;

		start = System.currentTimeMillis();

		integers.sort(comparator);
		long sortTime = System.currentTimeMillis() - start;

		for (int i = 0; i < result.length; i++) {
			assertTrue("Incorrect sort ", integers.get(i).getValue() == result[i]);
		}

		System.out.println("select time : " + selectTime);
		System.out.println("sort time   : " + sortTime);

	}

}
