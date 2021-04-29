package server.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("jsonschema2pojo")
public class ServerModel implements Serializable {
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("ID")
    @Expose
    private Long ID;
    @SerializedName("authorID")
    @Expose
    private Long authorID;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("info")
    @Expose
    private String info;

    public Integer getType() {
        return type;
    }

    @SerializedName("name")
    @Expose
    private String name;

    public void setID(Long ID) {
        this.ID = ID;
    }

    public ServerModel(String name, String info, Long authorID, Integer type) {
        this.type = type;
        this.name = name;
        this.info = info;
        this.authorID = authorID;
    }

    public ServerModel(String name, String info, Long authorID, String photo, Integer type) {
        this.type = type;
        this.name = name;
        this.info = info;
        this.authorID = authorID;
        this.photo = photo;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Long getID() {
        return ID;
    }

    public Integer getPosition() {
        return position;
    }

    @SerializedName("photo")
    @Expose
    private String photo;

    public Long getAuthorID() {
        return authorID;
    }

    public void setAuthorID(Long authorID) {
        this.authorID = authorID;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "ServerModel{" +
                "name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", author=" + authorID +
                ", photo=" + photo +
                '}';
    }

}