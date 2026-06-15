// Ch17 練習：寬螢幕裝置的多重身分
// 介面繼承多個父介面：SmartDevice extends Phone, Camera，Tablet 實作全部方法

interface Phone {
    void call();
}

interface Camera {
    void takePhoto();
}

// 證照常考：介面可以同時繼承多個父介面（class 不可以）
interface SmartDevice extends Phone, Camera {
    void connectWifi();
}

class Tablet implements SmartDevice {
    @Override
    public void call() {
        System.out.println("平板：撥打電話");
    }

    @Override
    public void takePhoto() {
        System.out.println("平板：拍照");
    }

    @Override
    public void connectWifi() {
        System.out.println("平板：連接 Wi-Fi");
    }
}

public class Exercise_SmartDeviceInterface {
    public static void main(String[] args) {
        Tablet tablet = new Tablet();
        tablet.call();       // 平板：撥打電話
        tablet.takePhoto();  // 平板：拍照
        tablet.connectWifi(); // 平板：連接 Wi-Fi
    }
}
