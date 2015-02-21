package euclid.two.dim.visitor;

import euclid.two.dim.model.Explosion;
import euclid.two.dim.model.Projectile;

public interface EtherialVisitor
{
	public void visit(Projectile projectile);
	
	public void visit(Explosion explosion);
}
