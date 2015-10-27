package test.login;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named("loginBean2")
@SessionScoped
public class LoginController2 implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String SUCCESS_MESSAGE = "Welcome";
	private static final String FAILURE_MESSAGE = "Incorrect username and password combination";

	private User currentUser;
	private boolean renderedLoggedIn = false;
	
	private final static String[] colors;  
	  
    private final static String[] manufacturers;

	@Inject
	private Credentials credentials;

	public String login() {
		if ("demo".equals(credentials.getUsername()) && "demo".equals(credentials.getPassword())) {
			currentUser = new User("demo");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SUCCESS_MESSAGE));
			return "home2.xhtml";
		}

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, FAILURE_MESSAGE, FAILURE_MESSAGE));
		return null;
	}

	public boolean isRenderedLoggedIn() {
		if (currentUser != null) {
			return renderedLoggedIn;
		} else {
			return false;
		}
	}

	public void renderLoggedIn() {
		this.renderedLoggedIn = true;
	}

	@Produces
	@Named("currentUser2")
	public User getCurrentUser() {
		return currentUser;
	}
	
	static {  
        colors = new String[10];  
        colors[0] = "Black";  
        colors[1] = "White";  
        colors[2] = "Green";  
        colors[3] = "Red";  
        colors[4] = "Blue";  
        colors[5] = "Orange";  
        colors[6] = "Silver";  
        colors[7] = "Yellow";  
        colors[8] = "Brown";  
        colors[9] = "Maroon";  
          
        manufacturers = new String[10];  
        manufacturers[0] = "Mercedes";  
        manufacturers[1] = "BMW";  
        manufacturers[2] = "Volvo";  
        manufacturers[3] = "Audi";  
        manufacturers[4] = "Renault";  
        manufacturers[5] = "Opel";  
        manufacturers[6] = "Volkswagen";  
        manufacturers[7] = "Chrysler";  
        manufacturers[8] = "Ferrari";  
        manufacturers[9] = "Ford";  
    }  
  
      
      
    private List<Car> carsSmall;  
      
    public LoginController2() {  
        carsSmall = new ArrayList<Car>();  
          
        populateRandomCars(carsSmall, 9);  
    }  
      
    private void populateRandomCars(List<Car> list, int size) {  
        for(int i = 0 ; i < size ; i++)  
            list.add(new Car(getRandomModel(), getRandomYear(), getRandomManufacturer(), getRandomColor()));  
    }  
      
    public List<Car> getCarsSmall() {  
        return carsSmall;  
    }  
  
    private String getRandomYear() {  
        return String.valueOf((Math.random() * 50 + 1960));  
    }  
      
    private String getRandomColor() {  
        return colors[(int) (Math.random() * 10)];  
    }  
      
    private String getRandomManufacturer() {  
        return manufacturers[(int) (Math.random() * 10)];  
    }  
      
    private String getRandomModel() {  
        return UUID.randomUUID().toString().substring(0, 8);  
    }

}
