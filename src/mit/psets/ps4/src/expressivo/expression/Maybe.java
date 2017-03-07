package expressivo.expression;

import java.util.ArrayList;
import java.util.List;

public class Maybe<T> {

	List<T> optionalValue = new ArrayList<>();;
	
	public Maybe(T value){
		if(value != null){
			optionalValue.add(value);
		}
	}
	
	public List<T> getValue(){
		return new ArrayList<>(optionalValue);
	}
	
	public boolean hasValue(){
		return optionalValue.size() == 1;
	}
	
	@Override
	public String toString(){
		if(hasValue()){
			return optionalValue.get(0).toString();
		} else {
			return "";
		}
	}
}
