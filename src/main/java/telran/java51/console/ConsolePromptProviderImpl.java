package telran.java51.console;

import static telran.java51.console.Constants.ADMIN_SIGN;
import static telran.java51.console.Constants.AUTHORIZED;
import static telran.java51.console.Constants.NOT_AUTHENTICATED;
import static telran.java51.console.Constants.POSTFIX;
import static telran.java51.console.Constants.MODERATOR_SIGN;

import java.util.Set;

import org.jline.utils.AttributedString;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

import telran.java51.user.model.Role;

@Component
public class ConsolePromptProviderImpl implements PromptProvider {

	@Override
	public AttributedString getPrompt() {
		String prompt = promptBuilder();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			String userName = authentication.getName();
			Set<String> set = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
			if (set.contains("ROLE_" + Role.ADMIN.name())) {
				prompt = promptBuilder(ADMIN_SIGN, userName);
			} else {
				prompt = promptBuilder(MODERATOR_SIGN, userName);
			}
		}
		return new AttributedString(prompt);
	}

	private String promptBuilder() {
		return NOT_AUTHENTICATED + POSTFIX;
	}

	private String promptBuilder(String sign, String userName) {
		return AUTHORIZED + sign + userName + POSTFIX;
	}
}
