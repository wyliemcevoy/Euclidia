package euclid.two.dim.datastructure;

import java.util.ArrayList;

import euclid.two.dim.model.EuVector;

public class KDTreeNode {
	EuVector location;

	private enum Split {
		X, Y;

		public Split next() {
			if (this.equals(X)) {
				return Y;
			}
			else {
				return X;
			}
		}
	}

	public KDTreeNode(ArrayList<EuVector> points) {
		for (EuVector point : points) {
			EuVector middleX = getMiddlePoint(points, Split.X);

		}
	}

	private EuVector getMiddlePoint(ArrayList<EuVector> points, Split xsplit) {
		// TODO Auto-generated method stub
		return null;
	}

}
