package euclid.two.dim.input;

public interface InputHandler
{
	public abstract void process(SelectEvent selectEvent);

	public abstract void process(ClickEvent clickEvent);

	public abstract void process(QuitCommand quitEvent);

	public abstract void process(AttackInputCommand attackCommand);
}
