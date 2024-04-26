package telran.java51;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.test.ShellAssertions;
import org.springframework.shell.test.ShellTestClient;
import org.springframework.shell.test.ShellTestClient.NonInteractiveShellSession;
import org.springframework.shell.test.autoconfigure.ShellTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import static org.awaitility.Awaitility.await;

//TODO test not working
@ShellTest(properties = {
		"spring.shell.test.terminal-width=120",
		"spring.shell.test.terminal-height=40"
	})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class NonInteractiveTestSample {
	
	@Autowired
	ShellTestClient client;

	@Test
	void test() {
		
		NonInteractiveShellSession session = client
			.nonInterative("help")
			.run();

		await().atMost(2, TimeUnit.SECONDS).untilAsserted(() -> {
			ShellAssertions.assertThat(session.screen())
				.containsText("AVAILABLE COMMANDS");
		});
	}
}
