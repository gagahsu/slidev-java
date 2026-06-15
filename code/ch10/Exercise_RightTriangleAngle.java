// Ch10 自學練習：直角三角形角度計算
// 已知對邊與鄰邊，計算斜邊長度（畢氏定理）以及對邊與斜邊的夾角角度（atan + toDegrees）

public class Exercise_RightTriangleAngle {
    public static void main(String[] args) {
        double opposite = 8.0;
        double adjacent = 6.0;

        // 斜邊長度：畢氏定理 sqrt(對邊^2 + 鄰邊^2)
        double hypotenuse = Math.sqrt(Math.pow(opposite, 2) + Math.pow(adjacent, 2));
        System.out.printf("斜邊：%.1f%n", hypotenuse); // 10.0

        // 角度的正切值 = 對邊 / 鄰邊，atan() 回傳弧度，需用 toDegrees() 轉成角度
        double angleRad = Math.atan(opposite / adjacent);
        double angleDeg = Math.toDegrees(angleRad);
        System.out.printf("角度：%.2f 度%n", angleDeg); // 53.13 度
    }
}
