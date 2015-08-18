package euclid.two.dim.ability.request;

public class BuildUnitAbilityRequest extends AbilityRequest {

	@Override
	public void accept(AbilityRequestVisitor visitor) {
		visitor.visit(this);
	}
}
