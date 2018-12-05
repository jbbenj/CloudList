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
The "mpaaRatingValidator" attribute is the validator-id used in the JSF page within

    <f:validator validatorId="mpaaRatingValidator" />

to invoke the "validate" method of the annotated class given below.
 */
@FacesValidator("mpaaRatingValidator")
public class MpaaRatingValidator implements Validator {

    /*
    Motion Picture Association of America (MPAA) film ratings:
    G, PG, PG-13, R, NC-17
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        // Type cast the inputted "value" to String type
        String mpaaRating = (String) value;

        switch (mpaaRating) {
            case "G":
            case "PG":
            case "PG-13":
            case "R":
            case "NC-17":
                break;
            default:
                throw new ValidatorException(
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Invalid MPAA Film Rating!", "Acceptable ratings: G, PG, PG-13, R, NC-17")
                );
        }
    }

}
