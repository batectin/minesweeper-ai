package aima.core.logic.fol.parsing;

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

import aima.core.logic.fol.parsing.ast.ConnectedSentence;
import aima.core.logic.fol.parsing.ast.Constant;
import aima.core.logic.fol.parsing.ast.Function;
import aima.core.logic.fol.parsing.ast.NotSentence;
import aima.core.logic.fol.parsing.ast.Predicate;
import aima.core.logic.fol.parsing.ast.QuantifiedSentence;
import aima.core.logic.fol.parsing.ast.TermEquality;
import aima.core.logic.fol.parsing.ast.Variable;

/**
 * @author Ravi Mohan
 * 
 */
public interface FOLVisitor {
	public Object visitPredicate(Predicate p, Object arg);

	public Object visitTermEquality(TermEquality equality, Object arg);

	public Object visitVariable(Variable variable, Object arg);

	public Object visitConstant(Constant constant, Object arg);

	public Object visitFunction(Function function, Object arg);

	public Object visitNotSentence(NotSentence sentence, Object arg);

	public Object visitConnectedSentence(ConnectedSentence sentence, Object arg);

	public Object visitQuantifiedSentence(QuantifiedSentence sentence,
										  Object arg);
}
