/*
 * Created by Muhammad Afiq Yusof on 2018.11.02  * 
 * Copyright © 2018 Muhammad Afiq Yusof. All rights reserved. * 
 */
package edu.vt.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/*
The @FacesValidator annotation on a class automatically registers the class with the runtime as a Validator. 
The "securityCodeValidator" attribute is the validator-id used in a JSF facelets page within

    <f:validator validatorId="securityCodeValidator" />

to invoke the "validate" method of the annotated class given below.                           
 */
@FacesValidator("securityCodeValidator")

public class SecurityCodeValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        // Type cast the inputted "value" to enteredCreditCardSecurityCode of type String
        String enteredCreditCardSecurityCode = (String) value;

        if (enteredCreditCardSecurityCode == null || enteredCreditCardSecurityCode.isEmpty()) {
            // Do not take any action. 
            // The required="true" in the XHTML file will catch this and produce an error message.
            return;
        }

        // REGular EXpression (regex) to validate if the entered credit card security code consists of digits only
        String regex = "[0-9]*";

        if (!enteredCreditCardSecurityCode.matches(regex)) {
            throw new ValidatorException(new FacesMessage(
                    FacesMessage.SEVERITY_FATAL,
                    "Unrecognized Credit Card Security Code!",
                    "Credit card security code must consist of only digits!"));
        }

        // Visa, MasterCard, and Discover credit card security codes are 3 digits. American Express uses 4 digits.
        if (enteredCreditCardSecurityCode.length() < 3 || enteredCreditCardSecurityCode.length() > 4) {
            throw new ValidatorException(new FacesMessage(
                    FacesMessage.SEVERITY_FATAL,
                    "Unrecognized Credit Card Security Code!",
                    "Credit card security code must be either 3 or 4 digits long."));          
        }
    }

}
