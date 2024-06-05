package telran.java51.user.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "admins2")
@NoArgsConstructor
public class AdminAccount extends UserAccount {

	private static final long serialVersionUID = -5760645207196036647L;

	@ElementCollection(targetClass = AdminRole.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "admin_roles", joinColumns = @JoinColumn(name = "login"))
	@Enumerated(EnumType.STRING)
	private Set<AdminRole> roles;

//	@Id
//	@Length(min = 3, message = "The login must be at least 6 symbols")
//	@Length(max = 40, message = "The login must be less than 40 symbols")
//	String login;
//
//	@Setter
//	String password;

	
	public AdminAccount( String login, String password) {
		super(login, password);
		roles = new HashSet<AdminRole>();
		roles.add(AdminRole.DATA_ADMIN);
	}

	public boolean addRole(AdminRole role) {
		return roles.add(role);
	}

	public boolean removeRole(String role) {
		return roles.remove(AdminRole.valueOf(role.toUpperCase()));
	}

	public String toStringForTable() {
		return getLogin() + "," + roles.stream().map(r -> r.name()).collect(Collectors.joining(";"));
	}

	@Override
	public String toString() {
		String roles = this.roles.stream().map(r -> r.name()).collect(Collectors.joining(","));
		return "[login=" + getLogin() + ";" + " roles=" + roles + "]";
	}

}
