package com.example.chandu.attendapp;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = Subject.class, version =1)
public abstract class SubjectDtabase extends RoomDatabase {

    private static SubjectDtabase instance;

    public abstract SubjectDao subjectDao();

    public static synchronized SubjectDtabase getInstance(Context context)
    {
        if(instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    SubjectDtabase.class,"subject_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopAsyncTask(instance).execute();
        }
    };

    public static class PopAsyncTask extends AsyncTask<Void,Void,Void>{

        private SubjectDao subjectDao;

        private PopAsyncTask(SubjectDtabase sd){
            subjectDao=sd.subjectDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            subjectDao.insert(new Subject("Subject1",0,0));
            subjectDao.insert(new Subject("Subject2",0,0));
            subjectDao.insert(new Subject("Subject3",0,0));
            return null;
        }
    }

}


