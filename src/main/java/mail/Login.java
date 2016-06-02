package mail;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class Login {
	private String username;
	private String password;

	public void send() {

		try {
			if(this.username.equals("admin")&&this.password.equals("12345")){
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("http://localhost:8080/jboss-javaee-webapp/faces/mail.xhtml");
			}else{
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("http://localhost:8080/jboss-javaee-webapp/faces/failedResponse.xhtml");
			}
				
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}