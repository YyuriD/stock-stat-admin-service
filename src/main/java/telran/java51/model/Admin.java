package telran.java51.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

	@Id
	String login;
	@Setter
	String password;

	@Setter
	Integer accessLevel;
	// TODO accessLevel as enum?
}
