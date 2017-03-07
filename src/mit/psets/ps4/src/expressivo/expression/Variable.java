package expressivo.expression;

import java.util.Map;

import expressivo.Expression;
import expressivo.ExpressionVisitor;

public class Variable implements Expression {

	private String name;
	
	public Variable(String name){
		this.name = name;
	}
	
	@Override
	public void visit(ExpressionVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Variable){
			Variable other = (Variable) o;
			return this.toString().equals(other.toString());
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode(){
		return name.hashCode();
	}

	@Override
	public ExpressionEvaluator getEvaluator(final Map<String, Double> environment) {
		return new ExpressionEvaluator(){

			@Override
			public Maybe<Double> getNumericValue() {
				if(environment == null){
					return new Maybe<Double>(null);
				}
				return new Maybe<Double>(environment.get(name));
			}

			@Override
			public String getSymbolicValue() {
				return name;
			}

		};
	}

}
