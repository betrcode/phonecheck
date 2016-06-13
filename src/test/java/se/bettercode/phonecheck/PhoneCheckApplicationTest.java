package se.bettercode.phonecheck;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Tests the command line application using a black box approach.
 */
public class PhoneCheckApplicationTest {

  private final PhoneCheckApplication application = new PhoneCheckApplication();
  private final ByteArrayOutputStream out = new ByteArrayOutputStream();

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
    runAndAssert(input, "YES\n");
  }

  @Test
  public void singleSetWithOneInvalidNumberShouldFail() {
    String input = "1\n" +
        "3\n" +
        "112112\n" +
        "112\n" +
        "343434\n";
    runAndAssert(input, "NO\n");
  }

  @Test
  public void multipleSetsWithOneInvalidNumberShouldFail() {
    String input = "2\n" +
        "3\n" +
        "112112\n" +
        "112\n" +
        "113113\n" +
        "4\n" +
        "454545\n" +
        "565656\n" +
        "676767\n" +
        "787877\n";
    runAndAssert(input, "NO\nYES\n");
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
    runAndAssert(input, "YES\nYES\n");
  }

  @Test
  public void allSetsShouldFail() {
    String input = "2\n" +
        "2\n" +
        "112112\n" +
        "112\n" +
        "2\n" +
        "111111\n" +
        "1111112\n";
    runAndAssert(input, "NO\nNO\n");
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
    runAndAssert(input, "YES\nYES\nYES\nYES\nYES\n");
  }

  @Test(timeout = 4000)
  public void manyNumbersPerformWell() {
    final int numberOfSets = 40;
    final int numberOfPhoneNumbers = 10000;
    final String input = getInputData(numberOfSets, numberOfPhoneNumbers);
    final String expectedResult = getNAnswers(numberOfSets, "YES");
    runAndAssert(input, expectedResult);
  }

  private String getInputData(int numberOfSets, int numberOfPhoneNumbers) {
    StringBuilder inputBuilder = new StringBuilder();
    inputBuilder.append(numberOfSets + "\n");
    for (int i = 0; i < numberOfSets; i++) {
      inputBuilder.append(numberOfPhoneNumbers + "\n");
      for (String phoneNumber : getShuffledPhoneNumbers(numberOfPhoneNumbers)) {
        inputBuilder.append(phoneNumber + "\n");
      }
    }
    return inputBuilder.toString();
  }

  /**
   * We want the order of phone numbers to be shuffled, so the list needs sorting afterwards.
   */
  private List<String> getShuffledPhoneNumbers(int numberOfPhoneNumbers) {
    final int startingPhoneNumber = 200000;
    List<String> phoneNumbers = new ArrayList<>();
    for (int phoneNumber = startingPhoneNumber; phoneNumber < startingPhoneNumber + numberOfPhoneNumbers; phoneNumber++) {
      phoneNumbers.add(Integer.toString(phoneNumber));
    }
    Collections.shuffle(phoneNumbers, new Random());
    return phoneNumbers;
  }

  private String getNAnswers(int n, String answer) {
    StringBuilder output = new StringBuilder();
    for (int i = 0; i < n; i++) {
      output.append(answer + "\n");
    }
    return output.toString();
  }

  private void runAndAssert(String input, String expectedResult) {
    BufferedReader in = new BufferedReader(new StringReader(input));
    application.main(in, new PrintStream(out));
    assertEquals(expectedResult, out.toString());
  }

}
