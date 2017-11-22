package com.wimso.cervatify.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Cerveja implements Parcelable {

    private int id;
    private String nome;
    private String preco;
    private String local;

    public Cerveja() {
    }

    protected Cerveja(Parcel in) {
        this.id = in.readInt();
        this.nome = in.readString();
        this.preco = in.readString();
        this.local = in.readString();
    }

    public static final Parcelable.Creator<Cerveja> CREATOR = new Parcelable.Creator<Cerveja>() {
        @Override
        public Cerveja createFromParcel(Parcel source) {
            return new Cerveja(source);
        }

        @Override
        public Cerveja[] newArray(int size) {
            return new Cerveja[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nome);
        dest.writeString(this.preco);
        dest.writeString(this.local);
    }

}