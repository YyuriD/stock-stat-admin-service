package telran.java51.configuration;

import org.jline.utils.AttributedString;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class CliPromptProviderImpl implements PromptProvider {

	@Override
	public AttributedString getPrompt() {
		return new AttributedString("Admin-service:>");
	}

}
