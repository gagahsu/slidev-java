// Ch08 練習：定義 Light 類別並建立物件
// 練習定義 class（欄位 + 方法）、用 new 建立物件，並透過「.」存取欄位、呼叫方法

class Light {
    String color;
    boolean isOn; // 預設值為 false（boolean 欄位未設定時，Java 會自動給 false）

    void turnOn() {
        isOn = true;
    }
}

public class Exercise1_LightObject {
    public static void main(String[] args) {
        Light myLight = new Light();

        // 欄位未設定，直接使用預設值
        System.out.println(myLight.isOn); // false（預設值）

        myLight.color = "白色";
        myLight.turnOn();

        System.out.println(myLight.isOn); // true（呼叫方法後狀態改變）
    }
}
