package entities;

public abstract class FaceBoundAbstract extends AbstractEntity {

	protected EdgeLoop el;
	
	public FaceBoundAbstract(String lineId) {
		super(lineId);
	}

	public EdgeLoop getEdgeLoop() {
		return el;
	} 
	
}
