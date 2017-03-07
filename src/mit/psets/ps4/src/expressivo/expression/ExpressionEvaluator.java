package expressivo.expression;

import java.util.List;
import java.util.Map;

public interface ExpressionEvaluator {
	Maybe<Double> getNumericValue();
	String getSymbolicValue();
	String evaluate();
}
