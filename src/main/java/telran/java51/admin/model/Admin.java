package telran.java51.admin.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(of = "login")
@Entity
@Table(name = "admins2")
public class Admin implements Serializable {

	private static final long serialVersionUID = -5760645207196036647L;

	@ElementCollection(targetClass = AdminRole.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "admin_roles", joinColumns = @JoinColumn(name = "login"))
	@Enumerated(EnumType.STRING)
	private Set<AdminRole> roles;

	@Id
	@Length(min = 3, message = "The login must be at least 6 symbols")
	@Length(max = 40, message = "The login must be less than 40 symbols")
	String login;

	@Setter
	String password;

	public Admin() {
		roles = new HashSet<AdminRole>();
		roles.add(AdminRole.DATA_ADMIN);
	}

	public Admin(String login, String password) {
		this();
		this.login = login;
		this.password = password;
	}

	public boolean addRole(AdminRole role) {
		return roles.add(role);
	}

	public boolean removeRole(String role) {
		return roles.remove(AdminRole.valueOf(role.toUpperCase()));
	}

	public String toStringForTable() {
		return login + "," + roles.stream().map(r -> r.name()).collect(Collectors.joining(";"));
	}

	@Override
	public String toString() {
		String roles = this.roles.stream().map(r -> r.name()).collect(Collectors.joining(","));
		return "[login=" + login + ";" + " roles=" + roles + "]";
	}

}
