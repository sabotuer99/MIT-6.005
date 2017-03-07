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

			@Override
			public String evaluate() {
				String result = getSymbolicValue();
				for(Double value : getNumericValue().getValue()){
					result = value.toString();
				}
				
				return result;
			}
		};
	}

}
