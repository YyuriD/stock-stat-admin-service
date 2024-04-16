package telran.java51.access.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "email")
@Entity
public class User implements Serializable {
	
	private static final long serialVersionUID = -5760645207196036647L;
	
	@Id
	String email;
	@Setter
	String password;
	
	//TODO roles
}
