/*
 * Created by Muhammad Afiq Yusof on 2018.11.29  * 
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
The "ccNumberValidator" attribute is the validator-id used in a JSF facelets page within

    <f:validator validatorId="ccNumberValidator" />

to invoke the "validate" method of the annotated class given below.                           
 */
@FacesValidator("ccNumberValidator")

public class CcNumberValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        // Type cast the inputted "value" to String type
        String creditCardNumber = (String) value;

        if (creditCardNumber == null || creditCardNumber.isEmpty()) {
            // Do not take any action. 
            // The required="true" in the XHTML file will catch this and produce an error message.
            return;
        }
        
        /*
        See Bank Card Number at https://en.wikipedia.org/wiki/Bank_card_number
        Issuer Identification Number (IIN)
        We validate some numbers on some credit cards below. This is not a complete validation!
        
        Visa:               13, 16 or 19 digits, starting with IIN 4.
        MasterCard:         16 digits, starting with IIN 51 through 55.
        Discover:           16 digits, starting with IIN 6011 or 65.
        American Express:   15 digits, starting with IIN 34 or 37.
        Diners Club:        14 digits, starting with IIN 300 through 305, 36, or 38.
        JCB:                15 digits, starting with IIN 2131 or 1800, or 16 digits starting with 35.
        */

        // REGular EXpression (regex) to validate the credit card number entered
        String regex =  "^(?:(?<Visa>4[0-9]{12}(?:[0-9]{3})(?:[0-9]{3})?)|" +
                        "(?<MasterCard>5[1-5][0-9]{14})|" +
                        "(?<Discover>6(?:011|5[0-9]{2})[0-9]{12})|" +
                        "(?<AmericanExpress>3[47][0-9]{13})|" +
                        "(?<DinersClub>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" +
                        "(?<JCB>(?:2131|1800|35[0-9]{3})[0-9]{11}))$";

        if (!creditCardNumber.matches(regex)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Unrecognized Credit Card Number!", 
                    "Visa, MasterCard, Discover, American Express, Diners Club, or JCB is accepted!"));
        }
    }

}
