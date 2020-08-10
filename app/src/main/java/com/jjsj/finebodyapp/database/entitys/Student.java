package com.jjsj.finebodyapp.database.entitys;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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

    public Student(String id, String name, String genre, int age, String idCoach, String pathPhoto) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.age = age;
        this.idCoach = idCoach;
        this.pathPhoto = pathPhoto;
    }

    public Student(DocumentSnapshot document){

        this.id = document.getId();
        this.name = document.get(nameFieldName).toString();
        this.genre = document.get(nameFieldGenre).toString();
        this.age = Integer.parseInt(document.get(nameFieldAge).toString());
        this.idCoach = document.get(nameFieldIdCoach).toString();
        this.pathPhoto = document.get(nameFieldPathPhoto).toString();
    }

    public Map<String, Object> getMapStudent(){

        Map<String, Object> data = new HashMap<>();
        if(this.id != null) data.put(Student.nameFieldId, this.id);
        data.put(Student.nameFieldName, this.name);
        data.put(Student.nameFieldGenre, this.genre);
        data.put(Student.nameFieldAge, this.age);
        data.put(Student.nameFieldIdCoach, this.idCoach);
        data.put(Student.nameFieldPathPhoto, this.pathPhoto);

        return data;
    }

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
