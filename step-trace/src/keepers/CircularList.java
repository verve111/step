package keepers;

import java.util.ArrayList;

import entities.EdgeCurve;
import entities.Line;

public class CircularList<E> extends ArrayList<E>{

	private static final long serialVersionUID = 1L;
	private boolean bAllLines = true; 

	public E getNext(int index) {
		index++;
		if (index == this.size()) {
			return super.get(0);
		} else if (index > this.size()) {
			throw new RuntimeException("CircularList ex");
		}
		return super.get(index);
	}
	
	@Override
	public boolean add(E e) {
		if (e instanceof EdgeCurve) {
			bAllLines &= ((EdgeCurve)e).getEdgeGeometry() instanceof Line;
		} else {
			bAllLines = false;
			System.out.println("warn :: circular list");
		}
		return super.add(e);
	}

	public boolean isAllLines() {
		return bAllLines;
	}
	
}
