package sf.travel.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ConflictError extends RuntimeException{
    private final ErrorCode errorCode;
    public ConflictError(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
