// Ch10 練習：計算正方形對角線長度
// 利用畢氏定理：對角線長度 = sqrt(side^2 + side^2)，搭配 Math.pow() 與 Math.sqrt()

public class Exercise_SquareDiagonal {
    public static void main(String[] args) {
        double side = 10;
        double diagonal = Math.sqrt(Math.pow(side, 2) + Math.pow(side, 2));
        System.out.println(diagonal); // 14.142135623730951
    }
}
