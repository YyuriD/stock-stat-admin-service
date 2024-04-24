package telran.java51.console;

import java.util.Set;

import org.jline.utils.AttributedString;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

import telran.java51.model.Admin;

@Component
public class CliPromptProviderImpl implements PromptProvider {
	final String NOT_AUTHORIZED = "Not authorized";
	final String AUTHORIZED = "Admin-service";
	final String POSTFIX = ":>";
	final String ADMIN_SIGN = "#";
	final String USER_SIGN = "@";

	@Override
	public AttributedString getPrompt() {
		String prompt = promptBuilder("");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			Set<String> set = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
			if (set.contains("ROLE_" + Admin.MAX_ACCESS_LEVEL)) {
				prompt = promptBuilder(ADMIN_SIGN);
			} else {
				prompt = promptBuilder(USER_SIGN);
			}
		}
		return new AttributedString(prompt);
	}

	private String promptBuilder(String sign) {
		if("".equals(sign)) {
			return NOT_AUTHORIZED + POSTFIX;
		}
		return AUTHORIZED + sign + POSTFIX;
	}

}
