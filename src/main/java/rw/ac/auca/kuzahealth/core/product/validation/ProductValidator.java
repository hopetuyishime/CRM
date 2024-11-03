package rw.ac.auca.kuzahealth.core.product.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import rw.ac.auca.kuzahealth.core.product.domain.Product;
import rw.ac.auca.kuzahealth.core.util.IValidationErrorCode;
import rw.ac.auca.kuzahealth.core.util.IValidationMessage;

public class ProductValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product theProduct = (Product) target;
        ValidationUtils.rejectIfEmpty(errors, Product.Fields.serialNumber, IValidationErrorCode.PRODUCT_SERIAL_NUMBER_REQUIRED_CODE, IValidationMessage.PRODUCT_SERIAL_NUMBER_REQUIRED);

    }
}
