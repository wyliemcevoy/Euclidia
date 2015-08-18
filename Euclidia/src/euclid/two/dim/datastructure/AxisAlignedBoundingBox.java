package euclid.two.dim.datastructure;

import euclid.two.dim.model.EuVector;

public class AxisAlignedBoundingBox {
	private EuVector topLeft, bottomRight;

	public AxisAlignedBoundingBox(EuVector one, EuVector two) {
		// figure out if one or two is the top left
		double minX = two.getX();
		double maxX = one.getX();
		if (one.getX() < two.getX()) {
			minX = one.getX();
			maxX = two.getX();
		}

		double minY = two.getY();
		double maxY = one.getY();

		if (one.getY() < two.getY()) {
			minY = one.getY();
			maxY = two.getY();
		}

		this.topLeft = new EuVector(minX, minY);
		this.bottomRight = new EuVector(maxX, maxY);
	}

	public AxisAlignedBoundingBox(AxisAlignedBoundingBox one, AxisAlignedBoundingBox two) {
		double minX = Math.min(one.getTopLeft().getX(), two.getTopLeft().getX());
		double minY = Math.min(one.getTopLeft().getY(), two.getTopLeft().getY());
		double maxX = Math.max(one.getBottomRight().getX(), two.getBottomRight().getX());
		double maxY = Math.max(one.getBottomRight().getY(), two.getBottomRight().getY());

		this.topLeft = new EuVector(minX, minY);
		this.bottomRight = new EuVector(maxX, maxY);
	}

	public AxisAlignedBoundingBox(AxisAlignedBoundingBox clone) {
		this.topLeft = clone.getTopLeft();
		this.bottomRight = clone.getBottomRight();
	}

	public EuVector getTopLeft() {
		return topLeft.deepCopy();
	}

	public EuVector getBottomRight() {
		return bottomRight.deepCopy();
	}

	public int getX() {
		return (int) topLeft.getX();
	}

	public int getY() {
		return (int) topLeft.getY();
	}

	public int getWidth() {
		return (int) (bottomRight.getX() - topLeft.getX());
	}

	public int getHeight() {
		return (int) (bottomRight.getY() - topLeft.getY());
	}

	public int getArea() {
		return getWidth() * getHeight();
	}

	@Override
	public String toString() {
		return "[" + topLeft + " - " + getWidth() + "x" + getHeight() + "]";
	}

	public AxisAlignedBoundingBox deepCopy() {
		// TODO Auto-generated method stub
		return new AxisAlignedBoundingBox(this);
	}
}
