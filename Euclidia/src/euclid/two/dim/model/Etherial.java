package euclid.two.dim.model;

import euclid.two.dim.visitor.AcceptsEtherialVisitor;

public abstract class Etherial implements AcceptsEtherialVisitor
{
	public abstract Etherial deepCopy();
}
