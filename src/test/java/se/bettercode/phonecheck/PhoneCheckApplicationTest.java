package se.bettercode.phonecheck;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;

/**
 * Tests the command line application using a black box approach.
 */
public class PhoneCheckApplicationTest {

  PhoneCheckApplication application = new PhoneCheckApplication();
  ByteArrayOutputStream out = new ByteArrayOutputStream();

  @Before
  public void resetOutputStream() {
    out.reset();
  }

  @Test
  public void singleSetWithUniqueNumbersShouldPass() {
    String input = "1\n" +
        "3\n" +
        "121212\n" +
        "232323\n" +
        "343434\n";
    runAndAssert(input, "TRUE");
  }

  @Test
  public void singleSetWithOneInvalidNumberShouldFail() {
    String input = "1\n" +
        "3\n" +
        "112112\n" +
        "112\n" +
        "343434\n";
    runAndAssert(input, "FALSE");
  }

  @Test
  public void multipleSetsWithOneInvalidNumberShouldFail() {
    String input = "2\n" +
        "3\n" +
        "112112\n" +
        "112\n" +
        "343434\n" +
        "4\n" +
        "454545\n" +
        "565656\n" +
        "676767\n" +
        "787877\n";
    runAndAssert(input, "FALSE");
  }

  @Test
  public void multipleSetsWithUniqueNumbersInEachSetButDuplicatesBetweenSetsShouldPass() {
    String input = "2\n" +
        "3\n" +
        "112112\n" +
        "111111\n" +
        "343434\n" +
        "4\n" +
        "111111\n" +
        "565656\n" +
        "676767\n" +
        "787877\n";
    runAndAssert(input, "TRUE");
  }

  @Test
  public void multipleSetsWithSingleNumberShouldPass() {
    String input = "5\n" +
        "1\n" +
        "123\n" +
        "1\n" +
        "123\n" +
        "1\n" +
        "123\n" +
        "1\n" +
        "123\n" +
        "1\n" +
        "123\n";
    runAndAssert(input, "TRUE");
  }

  private void runAndAssert(String input, String expectedResult) {
    BufferedReader in = new BufferedReader(new StringReader(input));
    application.main(in, new PrintStream(out));
    assertEquals(expectedResult, out.toString());
  }

}
