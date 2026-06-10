
public enum BR {
	 SUCCESS(200, "借閱成功"),
     NOT_FOUND(404, "查無此書"),
     NOT_AVAILABLE(409, "書籍已被借出");

     private final int code;
     private final String message;

     BR(int code, String message) {
         this.code = code;
         this.message = message;
     }

     public int getCode() {
         return code;
     }

     public String getMessage() {
         return message;
     }
}
