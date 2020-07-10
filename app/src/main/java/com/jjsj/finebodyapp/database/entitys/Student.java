package com.jjsj.finebodyapp.database.entitys;

import java.io.Serializable;

public class Student implements Serializable {

    private String id;
    private String name;
    private String genre;
    private int age;
    private String idCoach;
    private String pathPhoto;

    public static final String nameCollection = "students";
    public static final String nameFieldId = "id";
    public static final String nameFieldName = "name";
    public static final String nameFieldGenre = "genre";
    public static final String nameFieldAge = "age";
    public static final String nameFieldIdCoach = "idCoach";
    public static final String nameFieldPathPhoto = "pathPhoto";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getIdCoach() {
        return idCoach;
    }

    public void setIdCoach(String idCoach) {
        this.idCoach = idCoach;
    }

    public String getPathPhoto() {
        return pathPhoto;
    }

    public void setPathPhoto(String pathPhoto) {
        this.pathPhoto = pathPhoto;
    }
}
