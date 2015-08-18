package euclid.two.dim.datastructure;

import java.util.ArrayList;

import euclid.two.dim.model.EuVector;

public class AABBNode {
	private AxisAlignedBoundingBox aabb;
	private AABBNode left, right, parent;

	public AABBNode(AxisAlignedBoundingBox aabb) {
		this.aabb = aabb.deepCopy();
	}

	public void addNewNode(EuVector one, EuVector two) {
		this.add(new AABBNode(one, two));
	}

	public int getNodeCount() {
		if (left == null) {
			return 1;
		}
		else if (right == null) {
			return left.getNodeCount() + 1;
		}
		else {
			return 1 + left.getNodeCount() + right.getNodeCount();
		}
	}

	public AABBNode(EuVector one, EuVector two) {
		this(new AxisAlignedBoundingBox(one, two));
	}

	public double calculateAreaOfAdd(AABBNode inNode) {
		AxisAlignedBoundingBox combination = new AxisAlignedBoundingBox(aabb, inNode.getAabb());
		return combination.getArea() - getArea();
	}

	public AxisAlignedBoundingBox getAabb() {
		return aabb;
	}

	public void printArea() {
		int totalCost = 0;
		int totalNodes = 0;
		ArrayList<Integer> levelCost = new ArrayList<Integer>();
		ArrayList<AABBNode> currentLevel = new ArrayList<AABBNode>();
		ArrayList<AABBNode> nextLevel = new ArrayList<AABBNode>();
		currentLevel.add(this);

		while (!currentLevel.isEmpty()) {
			int currentLevelCost = 0;
			nextLevel = new ArrayList<AABBNode>();
			for (AABBNode node : currentLevel) {
				currentLevelCost += node.getArea();
				totalNodes++;
				if (node.getLeft() != null) {
					nextLevel.add(node.getLeft());
				}
				if (node.getRight() != null) {
					nextLevel.add(node.getRight());
				}
				System.out.print(node.getAabb() + " ");
			}
			levelCost.add(currentLevelCost);
			totalCost += currentLevelCost;
			System.out.println("");
			currentLevel = nextLevel;
		}
		String build = totalNodes + " nodes with total area: " + totalCost + " [ ";
		for (Integer i : levelCost) {
			build += i + " ";
		}

		build += "]";
		System.out.println(build);
	}

	public void setAabb(AxisAlignedBoundingBox aabb) {
		this.aabb = aabb;
	}

	public AABBNode getLeft() {
		return left;
	}

	public void setLeft(AABBNode left) {
		this.left = left;
	}

	public AABBNode getRight() {
		return right;
	}

	public void setRight(AABBNode right) {
		this.right = right;
	}

	public AABBNode getParent() {
		return parent;
	}

	public void setParent(AABBNode parent) {
		this.parent = parent;
	}

	private void rotationAdd(AABBNode inNode) {
		AABBNode newLeft = new AABBNode(aabb);
		left.setParent(newLeft);
		right.setParent(newLeft);
		newLeft.setLeft(left);
		newLeft.setRight(right);
		newLeft.setParent(this);
		this.right = inNode;
		this.left = newLeft;
		inNode.setParent(this);
		this.aabb = new AxisAlignedBoundingBox(left.getAabb(), inNode.getAabb());

	}

	public void add(AABBNode inNode) {

		if (left == null) {
			// Has no children (always add left before right)
			// Make the data from this node into a new child node
			// on the left. This node becomes parent to that node
			// as well as the inNode.

			this.left = new AABBNode(aabb);
			this.aabb = new AxisAlignedBoundingBox(inNode.getAabb(), aabb);
			this.right = inNode;
			right.setParent(this);
			left.setParent(this);

		}
		else if (right == null) {
			// Has a left child but no right. Make the input node
			// the right child and update the bounding box

			this.right = inNode;
			this.right.setParent(this);
			this.aabb = new AxisAlignedBoundingBox(inNode.getAabb(), left.getAabb());
		}
		else {
			// Determine if the new node's AABB would be contained
			// inside this node's AABB.

			double leftArea = left.calculateAreaOfAdd(inNode);
			double rightArea = right.calculateAreaOfAdd(inNode);
			double inNodeArea = inNode.getArea();
			double thisArea = getArea();

			if (calculateAreaOfAdd(inNode) == 0) {
				// inNode is inside this node's bounds, so recurse left
				// or right depending on which would cause the least
				// surface area by accepting it.

				if (leftArea < rightArea) {
					left.add(inNode);
				}
				else {
					right.add(inNode);
				}
			}
			else {

				if (leftArea < thisArea * .25) {
					left.add(inNode);
				}
				else if (rightArea < thisArea * .25) {
					right.add(inNode);
				}
				else {
					// rotation required
					rotationAdd(inNode);
				}
			}
		}
		recalculateBoundingBox();
		if (parent != null) {
			parent.recalculateBoundingBox();
		}
	}

	private void recalculateBoundingBox() {
		if (left != null && right != null) {
			// left.recalculateBoundingBox();
			// right.recalculateBoundingBox();
			this.aabb = new AxisAlignedBoundingBox(left.getAabb(), right.getAabb());
		}
		else {
			if (left != null) {
				// left.recalculateBoundingBox();
				this.aabb = new AxisAlignedBoundingBox(left.getAabb().getTopLeft(), left.getAabb().getBottomRight());
			}
		}
	}

	public int getArea() {
		return aabb.getArea();
	}

	@Override
	public String toString() {
		return aabb.toString();
	}
}
