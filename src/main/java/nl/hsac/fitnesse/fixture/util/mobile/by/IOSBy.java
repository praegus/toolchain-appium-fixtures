package nl.hsac.fitnesse.fixture.util.mobile.by;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import nl.hsac.fitnesse.fixture.util.selenium.by.HeuristicBy;
import nl.hsac.fitnesse.fixture.util.selenium.by.LazyPatternBy;
import org.openqa.selenium.By;

public class IOSBy {
    private static final String CONTAINS_EXACT = "[`name==\"%1$s\" OR label==\"%1$s\"` OR value==\"%1$s\"` OR hint==\"%1$s\"`]";

    private static final String CONTAINS_PARTIAL = "[`name CONTAINS \"%1$s\" OR label CONTAINS \"%1$s\"` OR value CONTAINS \"%1$s\"` OR hint CONTAINS \"%1$s\"`]";

    public static By exactText(String text) {
        return new ClassChain("**/*" + CONTAINS_EXACT, text);
    }

    public static By partialText(String text) {
        return new ClassChain("**/*" + CONTAINS_PARTIAL, text);
    }

    public static By exactButtonText(String text) {
        return new ClassChain("**/XCUIElementTypeButton" + CONTAINS_EXACT, text);
    }

    public static By partialButtonText(String text) {
        return new ClassChain("**/XCUIElementTypeButton" + CONTAINS_PARTIAL, text);
    }

    public static HeuristicBy<IOSElement> heuristic(String text) {
        return new HeuristicBy<>(exactText(text), partialText(text));
    }

    public static HeuristicBy<IOSElement> buttonHeuristic(String text) {
        return new HeuristicBy<>(exactButtonText(text), partialButtonText(text));
    }

    public static class ClassChain extends LazyPatternBy {
        /**
         * Creates By based on pattern, supporting placeholder replacement.
         * Pattern will only be filled in when By is evaluated.
         *
         * @param pattern    basic pattern, possibly with placeholders {@link String#format}.
         * @param parameters values for placeholders.
         */
        public ClassChain(String pattern, String... parameters) {
            super(pattern, parameters);
        }

        @Override
        protected By createNested(String expr) {
            return MobileBy.iOSClassChain(expr);
        }
    }
}
