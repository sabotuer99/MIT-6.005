package expressivo.expression;

import java.util.Map;

import expressivo.Expression;
import expressivo.ExpressionVisitor;

public class Number implements Expression {

	private double value;
	
	public Number(double value){
		this.value = value;
	}
	
	@Override
	public void visit(ExpressionVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString(){
		return String.format( "%.4f", value );
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Number){
			Number other = (Number) o;
			return this.value == other.value;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode(){
		return toString().hashCode();
	}

	@Override
	public ExpressionEvaluator getEvaluator(Map<String, Double> environment) {

		return new ExpressionEvaluator(){

			@Override
			public Maybe<Double> getNumericValue() {
				return new Maybe<Double>(value);
			}

			@Override
			public String getSymbolicValue() {
				return String.format( "%.4f", value );
			}
		};
	}

	@Override
	public Expression derive(String wrt) {
		return new Number(0);
	}
}
