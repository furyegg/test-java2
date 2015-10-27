package test.jsf.component;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.lang.StringUtils;

@FacesComponent("selectBooleanIntCheckbox")
public class SelecBooleanIntCheckBox extends UIComponentBase {

//	@Override
//	public void encodeEnd(FacesContext context) throws IOException {
//		ResponseWriter writer = context.getResponseWriter();
//		String value = String.valueOf(getAttributes().get("value"));
//		String label = (String) getAttributes().get("label");
//
//		String cid = getClientId();
//
//		writer.startElement("input", this);
//
//		writer.writeAttribute("id", cid, "id");
//		writer.writeAttribute("type", "checkbox", "type");
//
//		if (StringUtils.isNotBlank(value) && !Integer.valueOf(value).equals("0")) {
//			writer.writeAttribute("checked", Boolean.TRUE, "value");
//		}
//		writer.endElement("input");
//
//		writer.startElement("label", this);
//		writer.writeAttribute("for", cid, "label");
//		writer.write(label);
//		writer.endElement("label");
//	}

//	@Override
//	public void decode(FacesContext context) {
//		Map<String, String> requestParameterMap = context.getExternalContext().getRequestParameterMap();
//		String clientId = getClientId(context);
//
//		try {
//
//			String string_submit_val = ((String) requestParameterMap.get(clientId));
//			setSubmittedValue(string_submit_val);
//			setValid(true);
//		} catch (NumberFormatException ex) {
//			// let the converter take care of bad input, but we still have
//			// to set the submitted value, or the converter won't have
//			// any input to deal with
//			setSubmittedValue((String) requestParameterMap.get(clientId));
//		}
//	}

	@Override
	public String getFamily() {
		return "test.SelectBooleanIntCheckbox";
	}

}
