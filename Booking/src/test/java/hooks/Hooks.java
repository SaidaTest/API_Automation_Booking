package hooks;

import context.TestContext;
import io.cucumber.java.Before;

public class Hooks {
    private TestContext testContext;

    public Hooks(TestContext context) {
        this.testContext = context;
    }

    @Before
    public void setUp() {
        System.out.println("🔹 Scenario starting...");
    }
}
