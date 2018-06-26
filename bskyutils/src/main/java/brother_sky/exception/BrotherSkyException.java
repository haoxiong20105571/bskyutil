package brother_sky.exception;

public class BrotherSkyException extends RuntimeException {

    /*IN条件参数类型异常*/
    public static final String ALIPAY_FORM_EXCEPTION = "IN条件参数类型异常";

    public BrotherSkyException() {
        super();
    }

    public BrotherSkyException(String msg) {
        super(msg);
    }
}
