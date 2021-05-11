package com.abyte.valet.testan40121.model.person;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;

public class Person implements Serializable {
    private final Long id;
    private final String name;
    private final String password;
    private String photoName;
    private Bitmap photo;

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public void setPhoto(InputStream stream) {
        this.photo = BitmapFactory.decodeStream(stream);
    }

    public String getPhotoName() {
        return photoName;
    }

    public Person(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    @NonNull
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != null ? !id.equals(person.id) : person.id != null) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        return password != null ? password.equals(person.password) : person.password == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}