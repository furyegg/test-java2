package test.web;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;

import com.google.common.collect.Lists;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.slf4j.Logger;
import test.ejb.CalculatorLocal;
import test.interceptor.ConfigPackageSensitive;
import test.util.ConfigPackageThreadLocal;

import java.io.Serializable;
import java.util.List;

@Named("indexBean")
@ViewAccessScoped
@ConfigPackageSensitive
public class IndexBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private ConfigPackageBean configPackageBean;

	@Inject
	private CalculatorLocal calculatorBean;

	private String msg;
	private List<String> configPackages = Lists.newArrayList();
	private String selectedConfigPakcage;
	private String storedConfigPakcage;

	@PostConstruct
	private void init() throws NamingException {
		configPackages.add("ECR");
		configPackages.add("FED");
		selectedConfigPakcage = configPackages.get(0);
		change();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void show() {
		storedConfigPakcage = ConfigPackageThreadLocal.getInstance().get();
	}

	public void change() {
		configPackageBean.setConfigPackage(selectedConfigPakcage);
		log.info("---------- changed to {} -----------------------", selectedConfigPakcage);
		calculatorBean.add(1, 1);
	}

	public List<String> getConfigPackages() {
		return configPackages;
	}

	public void setConfigPackages(List<String> configPackages) {
		this.configPackages = configPackages;
	}

	public String getSelectedConfigPakcage() {
		return selectedConfigPakcage;
	}

	public void setSelectedConfigPakcage(String selectedConfigPakcage) {
		this.selectedConfigPakcage = selectedConfigPakcage;
	}

	public String getStoredConfigPakcage() {
		return storedConfigPakcage;
	}

	public void setStoredConfigPakcage(String storedConfigPakcage) {
		this.storedConfigPakcage = storedConfigPakcage;
	}
}