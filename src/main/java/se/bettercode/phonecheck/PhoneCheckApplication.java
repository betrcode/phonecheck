package se.bettercode.phonecheck;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Command line application that reads a set of phonenumbers,
 * given the number of sets and number of phonenumbers in each set
 * and then verifies each set to assert that no number starts with
 * another existing number.
 */
public class PhoneCheckApplication {

  public static void main(String[] args) {
    final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    final PrintStream out = new PrintStream(System.out);
    main(in, out);
  }

  /**
   * Testable method
   */
  public static void main(BufferedReader in, PrintStream out) {
    final List<List<String>> listOfNumbersList = readInput(in);
    areValidNumbers(listOfNumbersList).forEach(out::println);
  }

  /**
   * Read input on this format
   * 2
   * 3
   * 346723
   * 873467
   * 983423
   * 5
   * 872323
   * 232323
   * 343434
   * 454545
   * 454545
   * <p>
   * Where the "2" is number of sets
   * The "3" is number of phonenumbers in the set
   * The 6-digit numbers are phonenumbers (but can be any length)
   * The "5" is number of phonenumbers in the next set
   * The 6-digit numbers are phonenumbers (but can be any length)
   *
   * @param reader
   * @return a list of lists of phonenumbers
   */
  private static List<List<String>> readInput(BufferedReader reader) {
    List<List<String>> listOfLists = new ArrayList<>();
    ArrayList<String> numberList;
    final Scanner in = new Scanner(reader);
    final int cases = Integer.parseInt(in.nextLine());
    for (int i = 0; i < cases; i++) {
      int phoneNumberCount = Integer.parseInt(in.nextLine());
      numberList = new ArrayList<>();
      for (int j = 0; j < phoneNumberCount; j++) {
        numberList.add(in.nextLine());
      }
      //Add list of numbers to list of lists
      listOfLists.add(numberList);
    }
    return listOfLists;
  }

  private static List<String> areValidNumbers(List<List<String>> phoneNumbersLists) {
    List<String> result = new ArrayList<>();

    //Each list of phone numbers can be validated in parallel
    phoneNumbersLists.stream()
        .parallel()
        .map(PhoneCheckApplication::isListValid)
        .forEachOrdered(result::add);

    return result;
  }

  /**
   * A list of phone numbers is valid if no number is a prefix of another number in the list.
   */
  private static String isListValid(List<String> phoneNumbers) {
    boolean allAreValid = true;

    //Sort list to get numbers who prefix each other next to each other
    //This way we only need to check the current number and the next number, instead of each number with all numbers
    Collections.sort(phoneNumbers);

    for (int i = 0; i < phoneNumbers.size() - 1; i++) {
      if (areNumbersInvalid(phoneNumbers.get(i), phoneNumbers.get(i + 1))) {
        allAreValid = false;
        break;
      }
    }

    return allAreValid ? "YES" : "NO";
  }

  private static boolean areNumbersInvalid(String phoneNumber, String otherNumber) {
    return !phoneNumber.equals(otherNumber) && otherNumber.startsWith(phoneNumber);
  }

}
