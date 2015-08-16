package euclid.two.dim.datastructure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class HashList<T extends HasUUID> implements Iterable<T> {
	private HashMap<UUID, T> map;
	private ArrayList<T> list;

	public HashList() {
		this.map = new HashMap<UUID, T>();
		this.list = new ArrayList<T>();
	}

	public T get(UUID id) {
		return map.get(id);
	}

	public T get(int i) {
		return list.get(i);
	}

	public void add(T t) {
		map.put(t.getUUID(), t);
		list.add(t);
	}

	public T getMedian(Comparator<T> comparator) {
		return getKthOrderStatistic(comparator, Math.floorDiv(list.size(), 2));
	}

	public T getKthOrderStatistic(Comparator<T> comparator, int k) {
		// If size is small, just sort
		if (list.size() < 10) {
			list.sort(comparator);
			return list.get(k);
		}
		return getKthHelper(comparator, k, 0, list.size() - 1);
	}

	public T getKthOrderStatisticWithCopy(Comparator<T> comparator, int k) {
		return getKthWithCopy(comparator, list, k);
	}

	private T getKthWithCopy(Comparator<T> comparator, ArrayList<T> openList, int k) {

		if (openList.size() <= 5) {
			return findKthForSmall(comparator, openList, k, 0, openList.size() - 1);
		}

		T pivot = findPivot(comparator, openList, 0, openList.size() - 1);

		ArrayList<T> highList = new ArrayList<T>();
		ArrayList<T> lowList = new ArrayList<T>();
		ArrayList<T> equalList = new ArrayList<T>();

		for (T t : openList) {
			int compValue = comparator.compare(pivot, t);

			if (compValue == 0) {
				equalList.add(t);
			}
			else if (compValue < 0) {
				highList.add(t);
			}
			else {
				lowList.add(t);
			}
		}
		int lls = lowList.size();
		int els = equalList.size();

		if (lls > k) {
			// The kth element is to the left of the pivot, so partition and recurse
			return getKthWithCopy(comparator, lowList, k);
		}
		else if (lls + els > k) {
			// The pivot is the kth element
			return pivot;
		}
		else {
			// The kth element is right of the pivot, so partition and recurse
			return getKthWithCopy(comparator, highList, k - (lls + els));
		}
	}

	private T getKthHelper(Comparator<T> comparator, int k, int low, int high) {
		System.out.println("getKthHelper k=" + k + " low =" + low + " high=" + high);

		if (high - low <= 5) {
			return findKthForSmall(comparator, list, k, low, high);
		}

		T pivot = findPivot(comparator, list, low, high);

		System.out.println(" pivot = " + pivot.toString());
		int sum = 0;
		ArrayList<T> highList = new ArrayList<T>();
		ArrayList<T> lowList = new ArrayList<T>();
		ArrayList<T> equalList = new ArrayList<T>();

		for (int i = low; i <= high; i++) {
			T t = list.get(i);
			int compValue = comparator.compare(pivot, t);
			if (compValue == 0) {
				equalList.add(t);
			}
			else if (compValue < 0) {
				highList.add(t);
			}
			else {
				lowList.add(t);

			}
			sum += compValue;
		}
		int lls = lowList.size();
		int hls = highList.size();
		int els = equalList.size();

		lowList.addAll(equalList);
		lowList.addAll(highList);
		ArrayList<T> tempList = lowList;

		System.out.println("Partition at : " + pivot + " lower=" + lowList.size() + " equal=" + equalList.size() + " higher=" + highList.size());

		if (lls > k) {
			System.out.println("Partition left : " + lls + " elements less than " + pivot);
			return getKthHelper(comparator, k, low, high - (hls + els));
		}
		else if (lls + els > k) {
			return pivot;
		}
		else {
			// The kth element is right of the pivot, so partition and recurse
			System.out.println("Partition right : " + (lls + els) + " elements greater than " + pivot);
			return getKthHelper(comparator, k - (lls + els), low + (lls + els), high);
		}

	}

	private T findPivot(Comparator<T> comparator, ArrayList<T> targets, int low, int high) {
		if (high - low < 9) {
			return findKthForSmall(comparator, targets, Math.floorDiv(high - low, 2), low, high);
		}

		ArrayList<T> medians = new ArrayList<T>();

		while (low < high) {
			int nextLow = Math.min(low + 4, high);
			medians.add(findKthForSmall(comparator, targets, Math.floorDiv(nextLow - low, 2), low, nextLow));
			low = nextLow + 1;
		}

		return findPivot(comparator, medians, 0, medians.size() - 1);
	}

	private T findKthForSmall(Comparator<T> comparator, ArrayList<T> targets, int k, int low, int high) {

		ArrayList<T> subList = new ArrayList<T>();

		for (int i = low; i <= high; i++) {
			subList.add(targets.get(i));
		}
		subList.sort(comparator);

		return subList.get(k);
	}

	public void sort(Comparator<T> comparator) {
		list.sort(comparator);
	}

	@Override
	public Iterator<T> iterator() {
		return list.iterator();
	}

	public int size() {
		return list.size();
	}

	@Override
	public String toString() {
		String build = "";

		for (T t : list) {
			build += t + " ";
		}

		return build;
	}
}
