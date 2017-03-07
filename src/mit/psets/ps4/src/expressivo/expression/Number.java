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
	public ExpressionEvaluator getEvaluator(Map<String, Double> environment) {

		return new ExpressionEvaluator(){

			@Override
			public Maybe<Double> getNumericValue() {
				return new Maybe<Double>(value);
			}

			@Override
			public String getSymbolicValue() {
				return Double.toString(value);
			}

			@Override
			public String evaluate() {
				return Double.toString(value);
			}};
	}
}
