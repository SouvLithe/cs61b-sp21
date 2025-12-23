/** Class that prints the Collatz sequence starting from a given number.
 *  @author YOUR NAME HERE
 */
public class Collatz {

    /** Buggy implementation of nextNumber! */
    // 我明白要干什么了，从lab4远端拉过来的时候不存在冲突，它是快速合并给覆盖掉了
    // 所以61b的lab4是想让你重置成原来的那份（原始版本）
    public static int nextNumber(int n) {
        return n % 2 == 0 ? n/2 : 3*n + 1;
    }
    
    public static void main(String[] args) {
        int n = 5;
        System.out.print(n + " ");
        while (n != 1) {
            n = nextNumber(n);
            System.out.print(n + " ");
        }
        System.out.println();
    }
}

