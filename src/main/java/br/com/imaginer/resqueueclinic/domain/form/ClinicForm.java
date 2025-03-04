package br.com.imaginer.resqueueclinic.domain.form;

import br.com.imaginer.resqueueclinic.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClinicForm {

    private String name;

    private String address;

    private String phone;

    private User user;

    public ClinicForm(ClinicFormSimple clinicForm, User user) {
        this.name = clinicForm.getName();
        this.address = clinicForm.getAddress();
        this.phone = clinicForm.getPhone();
        this.user = user;
    }

    public ClinicForm(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

}
