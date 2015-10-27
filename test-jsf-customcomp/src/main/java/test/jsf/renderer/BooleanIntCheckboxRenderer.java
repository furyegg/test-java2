package test.jsf.renderer;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import org.apache.commons.lang.StringUtils;

import com.sun.faces.renderkit.html_basic.CheckboxRenderer;

@FacesRenderer(componentFamily = "test.SelectBooleanIntCheckbox", rendererType = BooleanIntCheckboxRenderer.RENDERER_TYPE)
public class BooleanIntCheckboxRenderer extends CheckboxRenderer {

	public static final String RENDERER_TYPE = "test.renderer.SelectBooleanIntCheckbox";

	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		Map<String, Object> attributes = component.getAttributes();

		String value = String.valueOf(attributes.get("value"));
		String label = (String) attributes.get("label");

		String cid = component.getClientId();

		writer.startElement("input", component);

		writer.writeAttribute("id", cid, "id");
		writer.writeAttribute("type", "checkbox", "type");
		writer.writeAttribute("name", cid, "name");

		if (StringUtils.isNotBlank(value) && !Integer.valueOf(value).equals("0")) {
			writer.writeAttribute("checked", Boolean.TRUE, "value");
		}
		writer.endElement("input");

		writer.startElement("label", component);
		writer.writeAttribute("for", cid, "label");
		writer.write(label);
		writer.endElement("label");
	}

	@Override
	public void decode(FacesContext context, UIComponent component) {
		rendererParamsNotNull(context, component);

		if (!shouldDecode(component)) {
			return;
		}

		String clientId = decodeBehaviors(context, component);

		if (clientId == null) {
			clientId = component.getClientId(context);
		}
		assert (clientId != null);
		// Convert the new value

		Map<String, String> requestParameterMap = context.getExternalContext().getRequestParameterMap();
		boolean isChecked = isChecked(requestParameterMap.get(clientId));
		setSubmittedValue(component, isChecked);
		if (logger.isLoggable(Level.FINE)) {
			logger.log(Level.FINE, "new value after decoding: {0}", isChecked);
		}
	}

	private static boolean isChecked(String value) {
		return !"0".equalsIgnoreCase(value);
	}

}