package com.example.generator.pojo.exception;

import com.example.generator.pojo.enums.BasicRespCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BasicException extends RuntimeException {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;

    private String errorCode = BasicRespCode.FAIL.getCode();
    private String errorMsg = BasicRespCode.FAIL.getDesc();

    public BasicException() {
        super();
    }

    public BasicException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public BasicException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BasicException(BasicRespCode basicRespCode) {
        super(basicRespCode.getDesc());
        this.errorCode = basicRespCode.getCode();
        this.errorMsg = basicRespCode.getDesc();
    }
}
