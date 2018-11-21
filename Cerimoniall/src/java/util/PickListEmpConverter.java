package util;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import model.Pessoa;

@FacesConverter(value = "empConverter")
public class PickListEmpConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext fc, UIComponent comp, String value) {
        DualListModel<Pessoa> model = (DualListModel<Pessoa>) ((PickList) comp).getValue();
        for (Pessoa employee : model.getSource()) {
            if (employee.getSequencial().equals(Integer.parseInt(value))) {
                return employee;
            }
        }
        for (Pessoa employee : model.getTarget()) {
            if (employee.getSequencial().equals(Integer.parseInt(value))) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent comp, Object value) {
        return ((Pessoa) value).getSequencial().toString();
    }
}