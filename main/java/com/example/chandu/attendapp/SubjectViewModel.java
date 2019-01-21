package com.example.chandu.attendapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class SubjectViewModel extends AndroidViewModel {
    private SubjectRepository repository;
    private LiveData<List<Subject>> allSubs;
    private LiveData<Float>report;

    public SubjectViewModel(@NonNull Application application) {
        super(application);
        repository =new SubjectRepository(application);
        allSubs=repository.getAllsubs();
        report=repository.report();
    }

    public void insert(Subject subject){
        repository.insert(subject);
    }

    public void update(Subject subject){
        repository.update(subject);
    }

    public void delete(Subject subject){
        repository.delete(subject);
    }

    public LiveData<Float> report(){
        return report;
    }

    public LiveData<List<Subject>> getallsubs(){
        return allSubs;
    }

}
