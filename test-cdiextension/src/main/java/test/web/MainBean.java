package test.web;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.slf4j.Logger;

import test.cdiextension.InjectByFormInstanceType;
import test.ejb.CalculatorLocal;
import test.util.ConfigPackageThreadLocal;

import com.google.common.collect.Lists;

@Named("mainBean")
@ViewAccessScoped
//@ConfigPackageSensitive
public class MainBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@InjectByFormInstanceType
	private CalculatorLocal calculatorBean;

	@Inject
	private ConfigPackageBean configPackageBean;

	private String msg;
	private List<String> configPackages = Lists.newArrayList();
	private String selectedConfigPakcage;
	private String storedConfigPakcage;

	@PostConstruct
	private void init() {
		configPackages.add("ECR");
		configPackages.add("FED");
		selectedConfigPakcage = configPackages.get(0);
		configPackageBean.setConfigPackage(selectedConfigPakcage);
		ConfigPackageThreadLocal.getInstance().set(selectedConfigPakcage);
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
		log.info("Now ---> {}", calculatorBean.getCalcEngine());
	}

	public void change() {
		configPackageBean.setConfigPackage(selectedConfigPakcage);
		log.info("---------- changed to {} -----------------------", selectedConfigPakcage);
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