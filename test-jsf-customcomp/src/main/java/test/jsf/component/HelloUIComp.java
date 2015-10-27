package test.jsf.component;

import java.io.IOException;
import java.util.Date;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

@FacesComponent("helloComp")
public class HelloUIComp extends UIComponentBase {

	public void encodeBegin(FacesContext context) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		String hellomsg = (String) getAttributes().get("hellomsg");

		writer.startElement("h3", this);
		if (hellomsg != null)
			writer.writeText(hellomsg, "hellomsg");
		else
			writer.writeText("Hello from a custom JSF UI Component!", null);
		writer.endElement("h3");
		writer.startElement("p", this);
		writer.writeText(" Today is: " + new Date(), null);
		writer.endElement("p");
	}

	public String getFamily() {
		return "HelloFamily";
	}

}
