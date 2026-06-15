// Ch10 綜合練習：自家到學校的距離
// 延伸 Haversine 公式的 calcDistance 方法，計算自家到學校的距離，並用 Math.round() 四捨五入到整數公里

public class Exercise_HomeToSchoolDistance {

    // haversine 輔助方法：包裝公式中重複出現的 sin(x/2)^2
    static double haversine(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

    // Haversine 公式：計算地球表面兩點間的大圓距離（公里）
    static double calcDistance(double lat1, double lon1,
                                double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        double a = haversine(dLat)
                + Math.cos(lat1) * Math.cos(lat2) * haversine(dLon);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 6371 * c; // 地球半徑（公里）
    }

    public static void main(String[] args) {
        // 家裡座標 (25.047, 121.517)，學校座標 (25.018, 121.540)
        double home2School = calcDistance(25.047, 121.517, 25.018, 121.540);

        // Math.round(double) 回傳 long，四捨五入到整數公里
        long rounded = Math.round(home2School);
        System.out.println("從家到學校約 " + rounded + " 公里");
        // 從家到學校約 4 公里
    }
}
