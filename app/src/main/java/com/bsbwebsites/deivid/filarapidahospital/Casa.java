package com.bsbwebsites.deivid.filarapidahospital;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Silva on 21/03/2018.
 */

public class Casa {
    private String Uid;
    private String name;
    private String telefone;
    private String email;
    private String Localizacao;
    public Map<String, Boolean> stars = new HashMap<>();


    public Casa(){

    }

    public Casa(String name, String telefone, String email, String localizacao) {
        this.name = name;
        this.telefone = telefone;
        this.email = email;
        this.Localizacao = localizacao;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", Uid);
        result.put("Nome", name);
        result.put("Telefone", telefone);
        result.put("E-mail", email);
        result.put("Localização", Localizacao);
        result.put("stars", stars);

        return result;
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
