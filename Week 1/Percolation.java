import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */
public class Percolation {
    private boolean[][] grid;
    private int openSites = 0;
    private final int virtualTop;
    private final int virtualBottom;
    private final int size;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufFullness; // without bottom virtual site

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N must be greater than 0");
        }
        this.size = n;
        this.virtualBottom = n*n + 1;
        this.virtualTop = 0;
        this.grid = new boolean[n+1][n+1];
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.ufFullness = new WeightedQuickUnionUF(n * n + 1);
    }

    public void open(int row, int col) {
        if (this.isInvalidArgument(row, col)) {
            throw new IllegalArgumentException("Row or column must be between or 1 and N");
        }

        // If already opened, nothing to do
        if (grid[row][col]) {
            return;
        }

        // open now
        this.grid[row][col] = true;
        this.openSites++;

        if (col > 1 && this.grid[row][col-1]) {
            this.uf.union(this.positionToElement(row, col), this.positionToElement(row, col-1));
            this.ufFullness.union(this.positionToElement(row, col), this.positionToElement(row, col-1));
        }

        if (col < this.size && grid[row][col+1]) {
            this.uf.union(this.positionToElement(row, col), this.positionToElement(row, col+1));
            this.ufFullness.union(this.positionToElement(row, col), this.positionToElement(row, col+1));
        }

        if (row > 1 && grid[row-1][col]) {
            this.uf.union(this.positionToElement(row, col), this.positionToElement(row - 1, col));
            this.ufFullness.union(this.positionToElement(row, col), this.positionToElement(row - 1, col));
        }

        if (row < this.size && grid[row+1][col]) {
            this.uf.union(this.positionToElement(row, col), this.positionToElement(row + 1, col));
            this.ufFullness.union(this.positionToElement(row, col), this.positionToElement(row + 1, col));
        }

        if (row == 1) {
            this.uf.union(this.virtualTop, col);
            this.ufFullness.union(this.virtualTop, col);
        }

        if (row == this.size) {
            this.uf.union(this.virtualBottom, this.positionToElement(row, col));
        }
    }

    public boolean isOpen(int row, int col) {
        if (this.isInvalidArgument(row, col)) {
            throw new IllegalArgumentException("Row or column must be between or 1 and N");
        }
        return this.grid[row][col];
    }

    public boolean isFull(int row, int col) {
        if (this.isInvalidArgument(row, col)) {
            throw new IllegalArgumentException("Row or column must be between or 1 and N");
        }

        int root = this.ufFullness.find(this.positionToElement(row, col));
        int top = this.ufFullness.find(this.virtualTop);

        return (this.grid[row][col] && root == top);
    }

    public int numberOfOpenSites() {
        return this.openSites;
    }

    public boolean percolates() {
        int upperRoot = this.uf.find(this.virtualTop);
        int bottomRoot = this.uf.find(this.virtualBottom);

        return upperRoot == bottomRoot;
    }

    private int positionToElement(int row, int col) {
        return ((row - 1)*this.size + col);
    }

    private boolean isInvalidArgument(int row, int col) {
        if (row <= 0 || row > this.size || col <= 0 || col > this.size) {
            return true;
        }
        return false;
    }

    private void print() {
        for (int i = 1; i <= this.size; i++) {
            for (int j = 1; j <= this.size; j++) {
                if (this.grid[i][j])
                    System.out.print(" * ");
                else
                    System.out.print(" _ ");
            }
            System.out.println(" ");
        }
        System.out.println(" ");
    }


    // Main method for testing
    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation per = new Percolation(n);

        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            // System.out.println(per.percolates());
            per.open(p, q);
            // System.out.println(per.percolates());
            // System.out.println(per.isFull(3, 1));

            per.print();
        }
    }
}
