package com.example.chandu.attendapp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Query;
import android.os.AsyncTask;

import java.util.List;

public class SubjectRepository {

    private SubjectDao subjectDao;
    private LiveData<List<Subject>> allsubs;
    private LiveData<Float>report;

    public SubjectRepository(Application application){
        SubjectDtabase database=SubjectDtabase.getInstance(application);
        subjectDao= database.subjectDao();
        allsubs=subjectDao.getSubjects();
        report=subjectDao.report();

    }

    public void insert(Subject subject){
        new InsertAsyncTask(subjectDao).execute(subject);
    }

    public void update(Subject subject){
        new updateAsyncTask(subjectDao).execute(subject);

    }

    public void delete(Subject subject){
        new deleteAsyncTask(subjectDao).execute(subject);

    }

    public LiveData<Float> report(){
     return report;
    }

    public LiveData<List<Subject>> getAllsubs() {
        return allsubs;
    }

    private static class InsertAsyncTask extends AsyncTask<Subject,Void,Void>{
        private SubjectDao subjectDao;

        private InsertAsyncTask(SubjectDao subjectDao){
            this.subjectDao=subjectDao;
        }
        @Override
        protected Void doInBackground(Subject... subjects) {

            subjectDao.insert(subjects[0]);
            return null;

        }
    }

    private static class updateAsyncTask extends AsyncTask<Subject,Void,Void>{
        private SubjectDao subjectDao;

        private updateAsyncTask(SubjectDao subjectDao){
            this.subjectDao=subjectDao;
        }
        @Override
        protected Void doInBackground(Subject... subjects) {

            subjectDao.update(subjects[0]);
            return null;

        }
    }

    private static class deleteAsyncTask extends AsyncTask<Subject,Void,Void>{
        private SubjectDao subjectDao;

        private deleteAsyncTask(SubjectDao subjectDao){
            this.subjectDao=subjectDao;
        }
        @Override
        protected Void doInBackground(Subject... subjects) {

            subjectDao.delete(subjects[0]);
            return null;

        }
    }
}
