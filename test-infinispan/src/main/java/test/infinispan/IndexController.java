package test.infinispan;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.cache.annotation.CacheResult;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.infinispan.api.BasicCache;

@Named("indexBean")
@RequestScoped
public class IndexController implements Serializable {

	private static final long serialVersionUID = 5983206217118223542L;

	private String msg = "abc";

	// @Resource(lookup = "java:jboss/infinispan/cache/ocelot/carcache")
	private BasicCache<Object, Object> cache;

	@PostConstruct
	private void init() {
		// cache = cacheManager.getCache("carcache");
		cache.put("a", 1);
		cache.put("b", 2);

		System.out.println(cache.get("b"));
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}