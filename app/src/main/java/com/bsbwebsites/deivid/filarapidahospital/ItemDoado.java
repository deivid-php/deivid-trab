package com.bsbwebsites.deivid.filarapidahospital;

import android.net.Uri;

/**
 * Created by Silva on 03/04/2018.
 */

public class ItemDoado {
    private String Uid;
    private int classe;
    private int qtd;
    private String imgem;
    private String Localizacao;
    private String nome;






    public ItemDoado() {
    }

    public ItemDoado(String nome, int classe, int qtd, String imgem, String localizacao) {
        this.nome = nome;
        this.classe = classe;
        this.qtd = qtd;
        this.imgem = imgem;
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

    public int getClasse() {
        return classe;
    }

    public void setClasse(int classe) {
        this.classe = classe;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public String getimgem() {
        return imgem;
    }

    public void setimgem(String imgem) {
        this.imgem = imgem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
