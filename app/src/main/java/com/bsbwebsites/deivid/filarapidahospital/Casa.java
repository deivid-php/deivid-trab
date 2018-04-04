package com.bsbwebsites.deivid.filarapidahospital;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Silva on 21/03/2018.
 */

public class Casa {
    private String Uid;
    private String name;
    private String telefone;
    private String email;
    private String Localizacao;

    public Casa(){

    }

    public Casa(String name, String telefone, String email, String localizacao) {
        this.name = name;
        this.telefone = telefone;
        this.email = email;
        this.Localizacao = localizacao;
    }

    public String getLocalizacao() {
        return Localizacao;
    }

    public void setLocalizacao(String localiza) {
        Localizacao = localiza;
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
        return " " + name  ;
    }
}
