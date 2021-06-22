package br.com.zup.edu.ecommerce.product;

import java.util.Set;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class DistinctFeatureNameValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) return;
        final ProductRequest request = (ProductRequest) target;
        final Set<String> duplicated = request.findDuplicatedFeatures();
        if (!duplicated.isEmpty()) {
            errors.rejectValue("features", null, "with the same names: " + duplicated);
        }
    }

}
