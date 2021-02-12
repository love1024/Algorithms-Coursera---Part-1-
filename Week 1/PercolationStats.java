/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96d;
    private double[] results;
    private final int n;
    private final int t;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) {
            throw new IllegalArgumentException("N or trails must be greater than 0");
        }
        this.n = n;
        this.t = t;
        this.results = new double[t];

        this.run();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - (CONFIDENCE_95 * stddev()) / Math.sqrt(t));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + (CONFIDENCE_95 * stddev()) / Math.sqrt(t));
    }

    private void run() {
        for (int i = 0; i < this.t; ++i) {
            Percolation p1 = new Percolation(this.n);

            while (!p1.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col  = StdRandom.uniform(1, n + 1);
                p1.open(row, col);
            }
            this.results[i] = (double) p1.numberOfOpenSites() / (this.n*this.n);
        }
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);


        PercolationStats stats = new PercolationStats(n, t);

        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() +", " + stats.confidenceHi() + "]");
    }
}
