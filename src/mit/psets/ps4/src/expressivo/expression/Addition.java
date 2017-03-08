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
	
	private String stringified;
	public String toString(){
		
		if(stringified == null){
			String s = "(";
			for(Expression term : terms){
				s += term.toString() + "+";
			}
			s = s.substring(0, s.length() - 1);
			s += ")";
			
			stringified = s;
			return s;
		} else {
			return stringified;
		}
		
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
	public int hashCode(){
		return toString().hashCode();
	}
	
	@Override
	public ExpressionEvaluator getEvaluator(final Map<String,Double> environment) {
		
		return new ExpressionEvaluator(){
			//TODO: fix this shit
			List<String> symbols = new ArrayList<>(); //right.getEvaluator(environment).getSymbolicValue();
			List<Double> values = new ArrayList<>(); // right.getEvaluator(environment).getNumericValue();

			//static block
			{
				for(Expression term : terms){
					Maybe<Double> val = term.getEvaluator(environment).getNumericValue();
					if(val.getValue().size() == 1){
						values.add(val.getValue().get(0));
					} else {
						symbols.add(term.getEvaluator(environment).getSymbolicValue());
					}
				}
			}

			@Override
			public Maybe<Double> getNumericValue() {
				if(symbols.size() == 0){
					Double sum = 0.0;
					for(Double val : values){
						sum += val;
					}
					return new Maybe<Double>(sum); 
				} else {
					return new Maybe<Double>(null); 
				}
				
			}

			@Override
			public String getSymbolicValue() {
				Double constants = 0.0;
				for(Double val : values){
					constants += val;
				}
				
				String symb = "";
				if(constants != 0){
					symb += String.format( "%.4f", constants ) + "+";
				} 
				
				for(String sub : symbols){
					symb += sub + "+";
				}
				
				symb = symb.substring(0, symb.length() - 1);
				
				if(symbols.size() > 0){
					symb = "(" + symb + ")";
				}
				
				return symb;
			}
			
		};
	}
	
	

}
