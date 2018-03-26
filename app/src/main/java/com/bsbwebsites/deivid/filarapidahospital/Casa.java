package com.bsbwebsites.deivid.filarapidahospital;

/**
 * Created by Silva on 21/03/2018.
 */

public class Casa {
    private String Uid;
    private String name;
    private String telefone;
    private String email;
    public Casa(){

    }

    public Casa(String name, String telefone, String email) {
        this.name = name;
        this.telefone = telefone;
        this.email = email;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Instituição" +
                ": " + name  ;
    }
}
