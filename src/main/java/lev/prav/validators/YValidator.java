package lev.prav.validators;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator("custom.yValidator")
public class YValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        // Проверка, что значение не пусто
        if (value == null) {
            return;
        }

        try {
            // Преобразование значения в число
            Double y = Double.parseDouble(value.toString());

            // Проверка диапазона
            if (y < -5 || y > 3) {
                value = null;
                throw new ValidatorException(new FacesMessage("Y должен быть в пределах от -5 до 3"));
            }

        } catch (NumberFormatException e) {
            // Если не удалось преобразовать в число
            value = null;
            throw new ValidatorException(new FacesMessage("Y должен быть числом"));
        }
    }
}