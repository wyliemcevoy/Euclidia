package euclid.two.dim.visitor;

import euclid.two.dim.etherial.CircleGraphic;
import euclid.two.dim.etherial.Explosion;
import euclid.two.dim.etherial.ExplosiveProjectile;
import euclid.two.dim.etherial.Projectile;
import euclid.two.dim.etherial.Slash;
import euclid.two.dim.etherial.ZergDeath;

public interface EtherialVisitor
{
	public void visit(Projectile projectile);
	
	public void visit(Explosion explosion);
	
	public void visit(Slash slash);
	
	public void visit(ZergDeath zergDeath);
	
	public void visit(ExplosiveProjectile explosiveProjectile);
	
	public void visit(CircleGraphic circleGraphic);
}
