package se.bettercode.phonecheck;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
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
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    PrintStream out = new PrintStream(System.out);
    main(in, out);
  }

  /**
   * Testable method
   */
  public static void main(BufferedReader in, PrintStream out) {
    List<List<String>> listOfNumbersList = readInput(in);
    //printListOfLists(listOfNumbersList);
    for (String result : areValidNumbers(listOfNumbersList)) {
      out.println(result);
    }
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

  private static List<String> areValidNumbers(List<List<String>> listList) {
    String allAreValid;
    List<String> isListValid = new ArrayList<>();

    for (List<String> phoneNumbers : listList) {
      allAreValid = "YES";
      for (String phoneNumber : phoneNumbers) {
        for (String otherNumber : phoneNumbers) {
          if (!phoneNumber.equals(otherNumber) && otherNumber.startsWith(phoneNumber)) {
            allAreValid = "NO";
            break;
          }
        }
      }
      isListValid.add(allAreValid);
    }
    return isListValid;
  }

  /**
   * Can be used for debugging
   */
  private static void printListOfLists(List<List<String>> listList) {
    for (List<String> phoneNumbers : listList) {
      phoneNumbers.forEach(System.out::println);
    }
  }
}
