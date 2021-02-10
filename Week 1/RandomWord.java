/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int counter = 1;
        String selected = "";
        while (!StdIn.isEmpty()) {
            String current = StdIn.readString();
            if (StdRandom.bernoulli(1d / counter)) {
                selected = current;
            }
            counter++;
        }
        System.out.println(selected);
    }
}
