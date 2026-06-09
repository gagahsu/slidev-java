public class FindMax {
    public static void main(String[] args) {
        int[] arr = {34, 17, 89, 45, 23, 67};

        int maxVal = arr[0];
        int maxIdx = 0;

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > maxVal) {
                maxVal = arr[i];
                maxIdx = i;
            }
        }

        System.out.println("最大值：" + maxVal + "，位於索引：" + maxIdx);
    }
}
