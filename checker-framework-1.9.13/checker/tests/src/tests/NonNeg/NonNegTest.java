package tests;

import org.checkerframework.framework.test.CheckerFrameworkTest;
import org.junit.runners.Parameterized.Parameters;

import java.io.File;

public class NonNegTest extends CheckerFrameworkTest {

    public NonNegTest(File testFile) {
        super(testFile,
                Trivial.NonNegChecker.class,
                "NonNeg",
                "-Anomsgtext");
    }

    @Parameters
    public static String[] getTestDirs() {
        return new String[]{"NonNeg"};
    }
}