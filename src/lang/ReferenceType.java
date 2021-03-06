package lang;

import parser.OperatorNode.OperatorType;

public class ReferenceType extends ObjectType {
	protected String name;
	protected ObjectType value;
	protected boolean writable;
	protected ObjectType parent = undefined;

	public ObjectType getParent() {
		return parent;
	}

	public ReferenceType(String name, ObjectType value, boolean writable) {
		this.name = name;
		this.value = value;
		this.writable = writable;
		this.extensible = false;
	}
	
	public ReferenceType(ObjectType value) {
		this("", value, true);
	}
	
	public static ReferenceType createConstant(ObjectType value) {
		return new ReferenceType("", value, false);
	}

	public ReferenceType(String name, ObjectType value) {
		this(name, value, true);
	}
	
	public ReferenceType(String name, ObjectType value,ObjectType parent) {
		this(name, value, true);
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public ObjectType getValue() {
		return value;
	}

	static class PropertyNotWritableException extends RuntimeException {
		/**
		 * 
		 */
		private static final long serialVersionUID = -8633011999670559344L;
		protected ReferenceType property;

		public PropertyNotWritableException(ReferenceType property) {
			this.property = property;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return String.format("Propery %s is not accessible",
					this.property.getName());
		}
	}

	public ObjectType get() {
		return value;
	}

	public void set(ObjectType value) {
		if (writable)
			this.value = value;
		else
			throw new PropertyNotWritableException(this);
	}
	
	
	// internal object Delegation
	
	public ObjectType getProperty(String name) {
		return value.getProperty(name);
	}

	public ObjectType invoke(ObjectType obj, ObjectType...args) {
		return value.invoke(obj, args);
	}
	
	/**
	 * @return
	 * @see lang.ObjectType#toStringType()
	 */
	public StringType toStringType() {
		return value.toStringType();
	}

	/**
	 * @return
	 * @see lang.ObjectType#toBooleanType()
	 */
	public BooleanType toBooleanType() {
		return value.toBooleanType();
	}

	/**
	 * @param type
	 * @param right
	 * @return
	 * @see lang.ObjectType#operator(parser.OperatorNode.OperatorType, lang.ObjectType)
	 */
	public ObjectType operator(OperatorType type, ObjectType right) {
		if (type == OperatorType.Assignment) {
			if (writable) {
				value = right;
				if (parent != null)
					parent.setAttribute(name, this);
				return right;
			}else {
				throw new RuntimeException("ReferenceError: Invalid left-hand side in assignment");
			}
		}
		//TODO remember to protect value Objects 
		return value.operator(type, right);
	}

	/**
	 * @param type
	 * @param prefix
	 * @return
	 * @see lang.ObjectType#operator(parser.OperatorNode.OperatorType, boolean)
	 */
	public ObjectType operator(OperatorType type, boolean prefix) {
		return value.operator(type, prefix);
	}

	/**
	 * @param type
	 * @return
	 * @see lang.ObjectType#operator(parser.OperatorNode.OperatorType)
	 */
	public ObjectType operator(OperatorType type) {
		return value.operator(type);
	}

	/**
	 * @param name
	 * @param value
	 * @see lang.ObjectType#setProperty(java.lang.String, lang.ObjectType)
	 */
	public void setProperty(String name, ObjectType value) {
		value.setProperty(name, value);
	}

	/**
	 * @return
	 * @see lang.ObjectType#clone()
	 */
	public ObjectType clone() {
		return value.clone();
	}

	/**
	 * @param methodName
	 * @param args
	 * @return
	 * @see lang.ObjectType#callMethod(java.lang.String, lang.ObjectType[])
	 */
	public ObjectType callMethod(String methodName, ObjectType... args) {
		return value.callMethod(methodName, args);
	}

	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return value.toString();
	}
	
	@Override
	public boolean isUndefined() {
		// TODO Auto-generated method stub
		return value.isUndefined();
	}
	
	@Override
	public boolean isPrimitive() {
		return value.isPrimitive();
	}
	
	@Override
	public NumberType toNumber() {
		return value.toNumber();
	}
}