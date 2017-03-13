package expressivo.expression;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import expressivo.Expression;
import expressivo.ExpressionVisitor;

public class Multiplication implements Expression {

	private List<Expression> terms = new ArrayList<>();
	
	public Multiplication(List<Expression> terms){
		assert terms.size() >= 2;
		this.terms = new ArrayList<>(terms);
	}
	
	private String stringified;
	@Override
	public String toString(){
		if(stringified == null){
			String s = "(";
			for(Expression term : terms){
				s += term.toString() + "*";
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
		if(o instanceof Multiplication){
			Multiplication other = (Multiplication) o;
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
			//if ANY term is zero, the whole expression is zero, byebye, seeya later
			{
				boolean hasZero = false;
				for(Expression term : terms){
					Maybe<Double> val = term.getEvaluator(environment).getNumericValue();
					
					if(val.getValue().size() == 1){
						double num = val.getValue().get(0);
						values.add(num);
						hasZero = hasZero || num == 0;
					} else {
						symbols.add(term.getEvaluator(environment).getSymbolicValue());
					}
				}
				
				if(hasZero){
					symbols = new ArrayList<>(); //right.getEvaluator(environment).getSymbolicValue();
					values = new ArrayList<>();
					values.add(0.0);
				}
			}

			@Override
			public Maybe<Double> getNumericValue() {
				if(symbols.size() == 0){
					Double prod = 1.0;
					for(Double val : values){
						prod *= val;
					}
					return new Maybe<Double>(prod); 
				} else {
					return new Maybe<Double>(null); 
				}
				
			}

			@Override
			public String getSymbolicValue() {
				Double constants = 1.0;
				for(Double val : values){
					constants *= val;
				}
				int pieces = 0;
				String symb = "";
				if(constants != 1){
					symb += String.format( "%.4f", constants ) + "*";
					pieces++;
				} 
				
				for(String sub : symbols){
					symb += sub + "*";
					pieces++;
				}
				
				if(symb.length() > 0){
					symb = symb.substring(0, symb.length() - 1);
				}
				
				if(pieces > 1){
					symb = "(" + symb + ")";
				}

				return symb;
			}
			
		};
	}

	@Override
	public Expression derive(String wrt) {
		
		List<Expression> newTerms = new ArrayList<Expression>();
		Expression u = terms.get(0);
		Expression du = u.derive(wrt);
		Expression v = null;
		Expression dv = null;
		
		if(terms.size() == 2){			
			v = terms.get(1);
			dv = v.derive(wrt);
		} else {
			List<Expression> theRest = new ArrayList<Expression>();
			for(int i = 1; i < terms.size(); i++){
				theRest.add(terms.get(i));
			}
			v = new Multiplication(theRest);
			dv = v.derive(wrt);
		}
		
		Expression first = new Multiplication(Arrays.asList(u, dv));
		Expression second = new Multiplication(Arrays.asList(du, v));
		
		return new Addition(Arrays.asList(first,second));		
	}

}
