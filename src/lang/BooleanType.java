package lang;

import parser.OperatorNode.OperatorType;

public class BooleanType extends ObjectType {
	protected boolean value;
	
	public BooleanType(String value) {
		this.value = Boolean.parseBoolean(value);
	}
	
	public BooleanType(boolean value) {
		this.value = value;
	}
	
	@Override
	public StringType toStringType() {
		// TODO Auto-generated method stub
		return new StringType(Boolean.toString(value));
	}
	
	//TODO override operators
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public BooleanType toBooleanType() {
		// TODO Auto-generated method stub
		return this;
	}
	
	
	public boolean toBoolean() {
		return this.value;
	}
	
	//operators
	
	@Override
	public ObjectType operator(OperatorType type) {
		//TODO implement Not operator
		throw new RuntimeException("Invalid left-hand side expression in postfix operation");
	}
	
	@Override
	public ObjectType operator(OperatorType type, ObjectType right) {
		BooleanType right_bool = right.toBooleanType();
		if (type == OperatorType.And)
			return new BooleanType(this.value && right_bool.toBoolean());
		else
			return new BooleanType(this.value || right_bool.toBoolean());
	}
}