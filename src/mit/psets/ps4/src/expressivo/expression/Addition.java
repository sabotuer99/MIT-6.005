package expressivo.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import expressivo.Expression;
import expressivo.ExpressionVisitor;

public class Addition implements Expression {

	private Expression left;
	private Expression right;
	
	public Addition(Expression left, Expression right){
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String toString(){
		return "(" + left.toString() + "+" + right.toString() + ")";
	}

	@Override
	public void visit(ExpressionVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Addition){
			Addition other = (Addition) o;
			return this.toString().equals(other.toString());
		} else {
			return false;
		}
	}

	@Override
	public ExpressionEvaluator getEvaluator(final Map<String,Double> environment) {
		
		return new ExpressionEvaluator(){
			
			String rightSymbol = right.getEvaluator(environment).getSymbolicValue();
			String leftSymbol = left.getEvaluator(environment).getSymbolicValue();
			Maybe<Double> rightValue = right.getEvaluator(environment).getNumericValue();
			Maybe<Double> leftValue = left.getEvaluator(environment).getNumericValue();


			@Override
			public String evaluate() {
				String result = getSymbolicValue();
				for(Double value : getNumericValue().getValue()){
					result = value.toString();
				}
				
				return result;
			}

			@Override
			public Maybe<Double> getNumericValue() {
				Double value = null;
				
				for(Double left : leftValue.getValue()){
					for(Double right : rightValue.getValue()){
						value = left + right;
					}
				}
				
				return new Maybe<Double>(value);
			}

			@Override
			public String getSymbolicValue() {
				String left = leftSymbol;
				String right = rightSymbol;
				for(Double maybeLeft : leftValue.getValue()){
					left = maybeLeft.toString();
				}
				for(Double maybeRight : rightValue.getValue()){
					right = maybeRight.toString();
				}
				
				return left + "+" + right;
			}
			
		};
	}
	
	

}
