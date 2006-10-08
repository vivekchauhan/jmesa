package org.jmesa.limit.state;

import org.jmesa.limit.Limit;

public interface State {
	
	public State getState();
	
	public void saveState(Limit limit);

}
