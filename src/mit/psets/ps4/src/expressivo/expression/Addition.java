package expressivo.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import expressivo.Expression;
import expressivo.ExpressionVisitor;

public class Addition implements Expression {

	private List<Expression> terms = new ArrayList<>();
	
	public Addition(List<Expression> terms){
		this.terms = new ArrayList<>(terms);
	}
	
	@Override
	public String toString(){
		String s = "(";
		for(Expression term : terms){
			s += term.toString() + "+";
		}
		s = s.substring(0, s.length() - 1);
		s += ")";
		return s;
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
			//TODO: fix this shit
			String rightSymbol = null; //right.getEvaluator(environment).getSymbolicValue();
			String leftSymbol = null; // left.getEvaluator(environment).getSymbolicValue();
			Maybe<Double> rightValue = null; // right.getEvaluator(environment).getNumericValue();
			Maybe<Double> leftValue = null; // left.getEvaluator(environment).getNumericValue();


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
