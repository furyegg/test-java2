package test.java.pentaho.utils;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

/**
 * EL Utilities.
 */
public final class ELUtils {

	/**
	 * Create ValueExpression.
	 */
	public static ValueExpression createValueExpression(String expression, Class<?> clazz) {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().getExpressionFactory()
				.createValueExpression(context.getELContext(), expression, clazz);
	}

}