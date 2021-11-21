package hexlet.code;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AppTest {
    @Test void appHasAGreeting() {
        assertNotNull(App.getGreeting(), "app should have a greeting");
    }
}
