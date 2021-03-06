package parser;

import java.util.LinkedList;
import java.util.List;
import lang.FunctionType;
import lang.ObjectType;
import lang.ReferenceType;

public class FunctionCallExpression extends ExpressionNode {
	ExpressionNode function;
	List<ExpressionNode> args;
	
	public FunctionCallExpression(ExpressionNode function, List<ExpressionNode> args) {
		this.function = function;
		this.args = args;
	}
	
	@Override
	public StatementStatus execute(Context context) {
		List<ObjectType> objectArgs = new LinkedList<ObjectType>();
		for(ExpressionNode exp : args)
			objectArgs.add(exp.evaluate(context));
		ReferenceType ref = (ReferenceType) function.evaluate(context);
		ObjectType func = ref.getValue();


		ObjectType[] funcArgs = new ObjectType[objectArgs.size()];
		for(int i=0; i<objectArgs.size(); i++)
			funcArgs[i] = ((ObjectType)objectArgs.get(i));
		
		value = ref.getParent().callMethod(ref.getName(), funcArgs);
		return new StatementStatus(StatementStatus.Type.Normal, null, null);
	}
}
