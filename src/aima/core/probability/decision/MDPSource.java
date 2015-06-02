package aima.core.probability.decision;

/**
 *    Copyright 2015 Felix & 3T
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 **/

import java.util.List;

import aima.core.probability.Randomizer;

/**
 * @author Ravi Mohan
 * 
 */
public interface MDPSource<STATE_TYPE, ACTION_TYPE> {
	MDP<STATE_TYPE, ACTION_TYPE> asMdp();

	STATE_TYPE getInitialState();

	MDPTransitionModel<STATE_TYPE, ACTION_TYPE> getTransitionModel();

	MDPRewardFunction<STATE_TYPE> getRewardFunction();

	List<STATE_TYPE> getNonFinalStates();

	List<STATE_TYPE> getFinalStates();

	MDPPerception<STATE_TYPE> execute(STATE_TYPE state, ACTION_TYPE action,
									  Randomizer r);

	List<ACTION_TYPE> getAllActions();
}
