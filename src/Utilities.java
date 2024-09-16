public class Utilities {
    public static class Response{
        public int code;
        public String message;
        public Object obj;

        public Response(int code, String message, Object obj) {
            this.code = code;
            this.message = message;
            this.obj = obj;
        }
    }
}
