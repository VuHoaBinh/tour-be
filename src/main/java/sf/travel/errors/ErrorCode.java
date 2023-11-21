package sf.travel.errors;
import lombok.Getter;

@Getter
public enum ErrorCode {
    EMAIL_ADDRESS_CANNOT_BE_DUPLICATED("401", "Email address cannot be duplicated"),
    TRAVEL_NOT_FOUND("404", "Travel not found"),
    CUSTOMER_NOT_FOUND("404", "Customer not found"),
    HIGHLIGHT_NOT_FOUND("404", "Highlight not found"),
    NEW_NOT_FOUND("404", "New not found"),
    ORDER_NOT_FOUND("404", "Order not found"),
    PROMOTION_NOT_FOUND("404", "Promotion not found"),
    HOTEL_NOT_FOUND("404", "Hotel not found"),
    ;
    private final String code;
    private final String message;
    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
