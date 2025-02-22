package br.com.imaginer.resqueueclinic.domain.form;


import br.com.imaginer.resqueueclinic.domain.model.User;

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

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public User getUser() {
        return user;
    }

}
