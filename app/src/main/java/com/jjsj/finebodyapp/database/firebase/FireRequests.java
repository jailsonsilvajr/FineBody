package com.jjsj.finebodyapp.database.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jjsj.finebodyapp.database.entitys.Measure;
import com.jjsj.finebodyapp.database.entitys.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireRequests {

    public MutableLiveData<Response> loginCoach(String email, String password){

        MutableLiveData<Response> result = new MutableLiveData<>();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Response response = new Response(200, "OK", task.getResult().getUser().getUid());
                            result.setValue(response);
                        }else{

                            Response response = new Response(401, task.getException().toString(), null);
                            result.setValue(response);
                        }
                    }
                });

        return result;
    }

    public MutableLiveData<Response> registerCoach(String email, String password){

        MutableLiveData<Response> result = new MutableLiveData<>();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Response response = new Response(200, "OK", task.getResult().getUser().getUid());
                            result.setValue(response);
                        }else{

                            Response response = new Response(409, task.getException().toString(), null);
                            result.setValue(response);
                        }
                    }
                });

        return result;
    }

    public MutableLiveData<Response> getOneStudent(String idStudent){

        MutableLiveData<Response> response = new MutableLiveData<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Student.nameCollection)
                .document(idStudent)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isSuccessful()){

                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){

                                //Create student
                                Student student = new Student();
                                student.setId(document.getId());
                                student.setName(document.get(Student.nameFieldName).toString());
                                student.setGenre(document.get(Student.nameFieldGenre).toString());
                                student.setAge(Integer.parseInt(document.get(Student.nameFieldAge).toString()));
                                student.setIdCoach(document.get(Student.nameFieldIdCoach).toString());
                                student.setPathPhoto(document.get(Student.nameFieldPathPhoto).toString());

                                Response res = new Response(302, "Found", student);
                                response.setValue(res);
                            }else{

                                Response res = new Response(404, "Not Found", null);
                                response.setValue(res);
                            }
                        }else{

                            Response res = new Response(500, "Internal Server Error", null);
                            response.setValue(res);
                        }
                    }
                });
        return response;
    }

    public MutableLiveData<Response> getAllStudent(String idCoach){

        MutableLiveData<Response> result = new MutableLiveData<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Student.nameCollection)
                .whereEqualTo(Student.nameFieldIdCoach, idCoach)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){

                            List<Student> listStudents = new ArrayList<>();
                            for(QueryDocumentSnapshot document : task.getResult()){

                                //Create student
                                Student student = new Student();
                                student.setId(document.getId());
                                student.setName(document.get(Student.nameFieldName).toString());
                                student.setGenre(document.get(Student.nameFieldGenre).toString());
                                student.setAge(Integer.parseInt(document.get(Student.nameFieldAge).toString()));
                                student.setIdCoach(document.get(Student.nameFieldIdCoach).toString());
                                student.setPathPhoto(document.get(Student.nameFieldPathPhoto).toString());

                                listStudents.add(student);
                            }
                            Response response = new Response(302, "Found", listStudents);
                            result.setValue(response);
                        }else{

                            Response response = new Response(404, "Error: " + task.getException(), null);
                        }
                    }
                });

        return result;
    }

    public MutableLiveData<Response> postOneStudent(Student student){

        MutableLiveData<Response> result = new MutableLiveData<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Student.nameCollection)
                .add(getMapStudent(student))
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                        if(task.isSuccessful()){

                            student.setId(task.getResult().getId());
                            Response response = new Response(201, "Created", student);
                            result.setValue(response);
                        }else{

                            Response response = new Response(500, task.getException().toString(), null);
                            result.setValue(response);
                        }
                    }
                });

        return result;
    }

    public MutableLiveData<Response> deleteOneStudent(String idStudent){

        MutableLiveData<Response> result = new MutableLiveData<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Student.nameCollection)
                .document(idStudent)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Response response = new Response(200, "OK", null);
                        result.setValue(response);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Response response = new Response(404, e.getMessage(), null);
                        result.setValue(response);
                    }
                });

        return result;
    }

    public MutableLiveData<Response> putOneStudent(Student student){

        MutableLiveData<Response> result = new MutableLiveData<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Student.nameCollection)
                .document(student.getId())
                .set(getMapStudent(student))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Response response = new Response(200, "OK", null);
                        result.setValue(response);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Response response = new Response(404, e.getMessage(), null);
                        result.setValue(response);
                    }
                });

        return result;
    }

    public MutableLiveData<Response> getAllMeasure(String idStudent){

        MutableLiveData<Response> result = new MutableLiveData<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Measure.nameCollection)
                .whereEqualTo(Measure.nameFieldIdStudent, idStudent)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){

                            List<Measure> listMeasure = new ArrayList<>();
                            for(QueryDocumentSnapshot document : task.getResult()){

                                Measure measure = new Measure();
                                measure.setId(document.getId());
                                measure.setIdStudent(document.get(Measure.nameFieldIdStudent).toString());
                                measure.setDate(document.get(Measure.nameFieldDate).toString());
                                measure.setWeight(Float.parseFloat(document.get(Measure.nameFieldWeight).toString()));
                                measure.setRightArm(Float.parseFloat(document.get(Measure.nameFieldRightArm).toString()));
                                measure.setLeftArm(Float.parseFloat(document.get(Measure.nameFieldLeftArm).toString()));
                                measure.setWaist(Float.parseFloat(document.get(Measure.nameFieldWaist).toString()));
                                measure.setHip(Float.parseFloat(document.get(Measure.nameFieldHip).toString()));
                                measure.setRightCalf(Float.parseFloat(document.get(Measure.nameFieldRightCalf).toString()));
                                measure.setLeftCalf(Float.parseFloat(document.get(Measure.nameFieldLeftCalf).toString()));

                                listMeasure.add(measure);
                            }
                            Response response = new Response(200, "OK", listMeasure);
                            result.setValue(response);
                        }else{

                            Response response = new Response(404, task.getException().getMessage(), null);
                            result.setValue(response);
                        }
                    }
                });

        return result;
    }

    public MutableLiveData<Response> postOneMeasure(Measure measure){

        MutableLiveData<Response> result = new MutableLiveData<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Measure.nameCollection)
                .add(getMapMeasure(measure))
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                        if(task.isSuccessful()){

                            measure.setId(task.getResult().getId());
                            Response response = new Response(200, "OK", measure);
                            result.setValue(response);
                        }else{

                            Response response = new Response(500, task.getException().getMessage(), null);
                            result.setValue(response);
                        }
                    }
                });

        return result;
    }

    public MutableLiveData<Response> deleteOneMeasure(String idMeasure){

        MutableLiveData<Response> result = new MutableLiveData<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Measure.nameCollection)
                .document(idMeasure)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Response response = new Response(200, "OK", null);
                        result.setValue(response);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Response response = new Response(404, e.getMessage(), null);
                        result.setValue(response);
                    }
                });

        return result;
    }

    public MutableLiveData<Response> putOneMeasure(Measure measure){

        MutableLiveData<Response> result = new MutableLiveData<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Measure.nameCollection)
                .document(measure.getId())
                .set(getMapMeasure(measure))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Response response = new Response(200, "OK", null);
                        result.setValue(response);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Response response = new Response(404, e.getMessage(), null);
                        result.setValue(response);
                    }
                });

        return result;
    }

    public MutableLiveData<byte[]> downloadPhoto(String path) throws IOException {

        MutableLiveData<byte[]> imgBytes = new MutableLiveData<>();

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference imgReference = storageReference.child(path);

        final long ONE_MEGABYTE = 1024 * 1024;
        imgReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                imgBytes.setValue(bytes);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                imgBytes.setValue(null);
            }
        });

        return imgBytes;
    }

    private Map<String, Object> getMapStudent(Student student){

        Map<String, Object> data = new HashMap<>();
        if(student.getId() != null) data.put(Student.nameFieldId, student.getId());
        data.put(Student.nameFieldName, student.getName());
        data.put(Student.nameFieldGenre, student.getGenre());
        data.put(Student.nameFieldAge, student.getAge());
        data.put(Student.nameFieldIdCoach, student.getIdCoach());
        data.put(Student.nameFieldPathPhoto, student.getPathPhoto());

        return data;
    }

    private Map<String, Object> getMapMeasure(Measure measure){

        Map<String, Object> data = new HashMap<>();

        if(measure.getId() != null) data.put(Measure.nameFieldId, measure.getId());
        data.put(Measure.nameFieldIdStudent, measure.getIdStudent());
        data.put(Measure.nameFieldDate, measure.getDate());
        data.put(Measure.nameFieldWeight, measure.getWeight());
        data.put(Measure.nameFieldRightArm, measure.getRightArm());
        data.put(Measure.nameFieldLeftArm, measure.getLeftArm());
        data.put(Measure.nameFieldWaist, measure.getWaist());
        data.put(Measure.nameFieldHip, measure.getHip());
        data.put(Measure.nameFieldRightCalf, measure.getRightCalf());
        data.put(Measure.nameFieldLeftCalf, measure.getLeftCalf());

        return data;
    }
}
