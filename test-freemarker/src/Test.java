import java.io.File;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class Test {

	public static void main(String[] args) throws Exception {
		Configuration cfg = new Configuration();
		// Specify the data source where the template files come from.
		// Here I set a file directory for it:
		// cfg.setDirectoryForTemplateLoading(new File("src/"));
		// Specify how templates will see the data-model. This is an advanced topic...
		// but just use this:
		// cfg.setObjectWrapper(new DefaultObjectWrapper());

		Map root = new HashMap();
		root.put("ip", "172.20.30.173");
		// root.put("db.sys.user", "123");

		// Template temp = cfg.getTemplate("IP Address: ${ip}");
		StringReader reader = new StringReader("IP Address: ${ip}");
		Template temp = new Template("test", reader, cfg);

		StringWriter writer = new StringWriter();
		temp.process(root, writer);
		System.out.println(writer.toString());
//		 Configuration templateConfig = new Configuration();
//		templateConfig.setNumberFormat("#");
//		
//		Template temp = new Template("test123123", new StringReader("[#ftl][@compress single_line=false]SELECT ${amountField} as amount, S_TAVA_Ref as dealRef from ${tableName} where STBITEM = '${stbItemValue}' and <#if stbGroupExisted == true> STBGROUP <#else> STBBRANCH </#if> = '${stbGroupValue}' and stbInstance=${stbInstanceValue}[/@compress]"),templateConfig);
//		
//		Map<String, Object> model = new HashMap<String, Object>();
//		model.put("amountField", "sdfd");
//		model.put("stbItemValue", "sdfd");
//		model.put("stbInstanceValue", 12);
//		model.put("tableName", "sdfd");
//		model.put("ignoreInstanceId", 23);
//		model.put("stbGroupExisted", true);
//		model.put("stbGroupValue", "sdfd");
//		StringWriter result = new StringWriter();
//		temp.process(model, result);
//		System.out.print( result.toString());
	}

}
