package telran.java51.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "login")
@Entity
@Table(name = "admins")
@ToString
@Component
public class Admin implements Serializable {

	private static final long serialVersionUID = -5760645207196036647L;
	
	public static final Integer MIN_ACCESS_LEVEL = 1;
	public static final Integer MAX_ACCESS_LEVEL = 10;

	@Id
	String login;
	
	@Setter
	String password;

	@Setter
	@Min(1) //TODO MIN_ACCESS_LEVEL
	@Max(10) //TODO MAX_ACCESS_LEVEL
	Integer accessLevel;
	
	public void reset() {
		this.login=null;
		this.password=null;
		this.accessLevel=0;
	}

}
