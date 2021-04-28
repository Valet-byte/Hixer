package server.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class ServerModel implements Serializable {
    private String name;
    private String info;
    private Person author;
    private MultipartFile photo;

    public ServerModel(String name, String info, Person author) {
        this.name = name;
        this.info = info;
        this.author = author;
    }

    public ServerModel(String name, String info, Person author, MultipartFile photo) {
        this.name = name;
        this.info = info;
        this.author = author;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public Person getAuthor() {
        return author;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    @Override
    public String toString() {
        return "ServerModel{" +
                "name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", author=" + author +
                ", photo=" + photo +
                '}';
    }
}
