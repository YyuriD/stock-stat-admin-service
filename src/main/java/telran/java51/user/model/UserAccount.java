package telran.java51.user.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@EqualsAndHashCode(of = "login")
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class UserAccount implements Serializable{
	
	private static final long serialVersionUID = -6676524803011155408L;

	public UserAccount(String login, String password) {
		this.login = login;
		this.password = password;
	}
	
	@Id
	String login;
	
	@Setter
	String password;
}
