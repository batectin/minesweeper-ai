package aima.core.agent.impl;

import aima.core.agent.Action;
import aima.core.agent.Agent;
import aima.core.agent.EnvironmentState;
import aima.core.agent.EnvironmentView;

/**
 * Simple environment view which uses the standard
 * output stream to inform about relevant events.
 * @author Ruediger Lunde
 */
public class SimpleEnvironmentView implements EnvironmentView {
	public void agentActed(Agent agent, Action action,
			EnvironmentState resultingState) {
		System.out.println("Agent acted: " + action.toString());
	}
	
	public void agentAdded(Agent agent, EnvironmentState resultingState) {
		System.out.println("Agent added.");
	}
	
	public void notify(String msg) {
		System.out.println("Message: " + msg);
	}
}
