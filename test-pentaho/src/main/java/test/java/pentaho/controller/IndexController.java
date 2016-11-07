package test.java.pentaho.controller;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Named("indexBean")
@ViewAccessScoped
public class IndexController implements Serializable {

	private static final long serialVersionUID = 1235456310148092003L;
	private static final Logger log = LoggerFactory.getLogger(IndexController.class);

	private String msg = "abc";

	// @Resource(lookup = "java:jboss/infinispan/container/ocelot")
	// private EmbeddedCacheManager cacheManager;

	// private Cache<Object, Object> cache;
	//
	// @PostConstruct
	// private void init() {
	// cache = cacheManager.getCache("carcache");
	// cache.put("a", 1);
	//
	// System.out.println(cache.size());
	// }

	private String type;
	private Map<String, String> typeList;

	@PostConstruct
	private void init() {
		typeList = new HashMap<String, String>();
		typeList.put("ZIP", "ZIP");
		typeList.put("GZ", "GZ");
		typeList.put("NONE", "NONE");

		type = "ZIP";
	}
	
	public void submit() {
		log.info("submit. type: {}", type);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		log.info("setType: {}", type);
	}

	public Map<String, String> getTypeList() {
		return typeList;
	}

	public void setTypeList(Map<String, String> typeList) {
		this.typeList = typeList;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
