// Ch07 練習：獨立複製陣列
// 練習 Arrays.copyOf：在 Heap 上配置全新空間，與 b = a（共用同一塊記憶體）不同

import java.util.Arrays;

public class Exercise3_ArraysCopyOf {
    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        int[] b = Arrays.copyOf(a, a.length); // 改用 copyOf，產生獨立的新陣列

        b[0] = 99;

        // 業界慣例：copyOf 之後 a 與 b 互不影響，a[0] 仍是 1，b[0] 變成 99
        System.out.println("a[0] = " + a[0]);
        System.out.println("b[0] = " + b[0]);
    }
}
