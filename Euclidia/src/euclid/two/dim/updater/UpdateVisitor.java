package euclid.two.dim.updater;

import euclid.two.dim.model.Boid;
import euclid.two.dim.model.Explosion;
import euclid.two.dim.model.Fish;
import euclid.two.dim.model.Obstacle;
import euclid.two.dim.model.Projectile;
import euclid.two.dim.model.Unit;

public interface UpdateVisitor
{
	public void visit(Unit gso);
	
	public void visit(Fish fish);
	
	public void visit(Boid boid);
	
	public void visit(Obstacle obstacle);
	
	public void visit(Explosion explosion);
	
	public void visit(Projectile projectile);
}
