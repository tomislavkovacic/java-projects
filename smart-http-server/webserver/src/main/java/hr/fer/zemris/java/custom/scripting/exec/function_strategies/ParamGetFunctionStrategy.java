package hr.fer.zemris.java.custom.scripting.exec.function_strategies;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Deque;

/**
 * Class implements IFunctionStrategy and tries to get parameter from requestContext
 * parameters. If such parameter does not exist a default value is pushed on stack.
 * @author Tomislav
 *
 */

public class ParamGetFunctionStrategy implements IFunctionStrategy {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Deque<Object> stack, RequestContext requestContext) {
		Object dv = stack.pop();
		String name = (String)stack.pop();
		String value = requestContext.getParameter(name);
		stack.push(value == null ? dv : value);
	}
}
