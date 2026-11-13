package uz.businesshub.product.exception;

public class MissingTenantException extends RuntimeException {
    public MissingTenantException() {
        super("Token does not contain a valid company_id");
    }
}