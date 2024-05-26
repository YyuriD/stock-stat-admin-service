package telran.java51.admin.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "login")
@Entity
@Table(name = "admins")
public class Admin implements Serializable {

	private static final long serialVersionUID = -5760645207196036647L;
	
	public static final Integer MIN_ACCESS_LEVEL = 1;
	public static final Integer MAX_ACCESS_LEVEL = 10;
	
	
	@Id
	@Length(min = 3, message = "The login must be at least 3 characters")
    @Length(max = 30, message = "The login must be less than 10 characters")
	String login;
	
	@Setter
	String password;

	@Setter
	@Min(value = 1, message = "The min accessLevel must be 1") 
	@Max(value = 10, message = "The max accessLevel must be 10")
	Integer accessLevel;

	
	
	public String toStringForTable() {
		return login + ","+ accessLevel;
	}

	@Override
	public String toString() {
		return "[login=" + login + ", accessLevel=" + accessLevel + "]";
	}
	
	
	
}
