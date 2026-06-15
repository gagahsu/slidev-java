// Ch17-adv 練習：用 Private 方法重構 Default 方法
// 介面 private 方法 validate() 被多個 default 方法（printSummary、printDetail）共用

interface ReportPrinter {
    String getData();

    // private 方法只能在介面內被呼叫，不能被實作類別或外部呼叫
    // 因為呼叫了 non-static 的 getData()，所以這裡是 private（non-static）
    private void validate() {
        if (getData() == null || getData().isEmpty()) {
            throw new IllegalStateException("資料為空");
        }
    }

    default void printSummary() {
        validate();
        System.out.println("摘要：" + getData());
    }

    default void printDetail() {
        validate();
        System.out.println("明細：" + getData());
    }
}

class SalesReport implements ReportPrinter {
    @Override
    public String getData() {
        return "銷售報表內容";
    }
}

public class Exercise_ReportPrinterPrivate {
    public static void main(String[] args) {
        SalesReport report = new SalesReport();
        report.printSummary(); // 摘要：銷售報表內容
        report.printDetail();  // 明細：銷售報表內容
    }
}
