package euclid.two.dim.render;

import java.util.ArrayList;

import euclid.two.dim.etherial.Etherial;
import euclid.two.dim.etherial.Explosion;
import euclid.two.dim.etherial.Projectile;
import euclid.two.dim.etherial.Slash;
import euclid.two.dim.etherial.ZergDeath;
import euclid.two.dim.model.Boid;
import euclid.two.dim.model.Fish;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Obstacle;
import euclid.two.dim.model.Unit;
import euclid.two.dim.updater.UpdateVisitor;
import euclid.two.dim.visitor.EtherialVisitor;

public class RenderCreator implements UpdateVisitor, EtherialVisitor
{
	private ArrayList<Renderable> renderables;
	
	public void add(GameSpaceObject gso)
	{
		gso.acceptUpdateVisitor(this);
	}
	
	public void add(Etherial etherial)
	{
		etherial.accept(this);
	}
	
	public RenderCreator()
	{
		this.renderables = new ArrayList<Renderable>();
	}
	
	@Override
	public void visit(Projectile projectile)
	{
		
		this.renderables.add(new ProjectileRender(projectile));
	}
	
	@Override
	public void visit(Explosion explosion)
	{
		this.renderables.add(new ExplosionRender(explosion));
	}
	
	@Override
	public void visit(Unit unit)
	{
		this.renderables.add(new UnitRender(unit));
		
	}
	
	@Override
	public void visit(Fish fish)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void visit(Boid boid)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void visit(Obstacle obstacle)
	{
		// TODO Auto-generated method stub
		
	}
	
	public ArrayList<Renderable> getRenderables()
	{
		return renderables;
	}
	
	@Override
	public void visit(Slash slash)
	{
		this.renderables.add(new SlashRender(slash));
	}
	
	@Override
	public void visit(ZergDeath zergDeath)
	{
		this.renderables.add(new ZergDeathRender(zergDeath));
		
	}
}
