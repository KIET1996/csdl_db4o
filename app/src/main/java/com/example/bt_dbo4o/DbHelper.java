package com.example.bt_dbo4o;

import android.annotation.SuppressLint;
import android.util.Log;

import com.db4o.Db4oEmbedded;
import com.db4o.EmbeddedObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;
import com.example.bt_dbo4o.Model.Note;
import com.example.bt_dbo4o.Model.Storage;
import com.example.bt_dbo4o.Model.User;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class DbHelper {
    EmbeddedObjectContainer db;

    public DbHelper(String dbPath) {
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(User.class).cascadeOnDelete(true);
        config.common().objectClass(Note.class).cascadeOnDelete(true);
        config.common().objectClass(User.class).cascadeOnUpdate(true);
        config.common().objectClass(Note.class).cascadeOnUpdate(true);
        this.db = Db4oEmbedded.openFile(config, dbPath);
    }

    public void  DBDrop(String dbPath){
        new File(dbPath).delete();
    }

    public String checkLogin(){
        List<Storage> usernameList = new ArrayList<Storage>();
        try{
            Storage objUser = new Storage();
            ObjectSet<Object> storages = this.db.queryByExample(objUser);
            for (Object s: storages) {
                usernameList.add((Storage) s);
            }
            if(usernameList.isEmpty()){
                return "";
            } else if (usernameList.size() > 0){
                return usernameList.get(0).getStoreUsername();
            }
        }catch (Exception e){
            Log.e("ERROR LOGIN", "LOI STORE ===========>" + e);
        }
        return "";
    }

    public void closeDB(){
        this.db.close();
    }

    public boolean findUser(String username){
        List<User> userList = new ArrayList<>();
        try{
            User objUser = new User();
            objUser.setUSERNAME(username);
            ObjectSet<Object> users = this.db.queryByExample(objUser);
            for (Object u: users){
                userList.add((User) u);
                Log.e("Check User value: ", ((User) u).getUSERNAME());
            }
            if(userList.isEmpty()){
                return true;
            } else if (userList.size() > 0){
                return false;
            }
        }catch (Exception e){
            Log.e("ERROR FIND USER", "LOI ===========>" + e);
        }
        return false;
    }

    @SuppressLint("LongLogTag")
    public void addUser(String fullName, String username, String password) {
        try{
            User u1 = new User(fullName, username, password);
            Note n1 = new Note(0, "Xin chao", "Hay them ghi chu cua ban", Calendar.getInstance().getTime());
            Note n2 = new Note(1, "Hello everyone", "Hay them ghi chu cua ban", Calendar.getInstance().getTime());
            Note n3 = new Note(2, "Ghi chu 1", "Hay them ghi chu cua ban", Calendar.getInstance().getTime());
            Note n4 = new Note(3, "Ghi chu 2", "Hay them ghi chu cua ban", Calendar.getInstance().getTime());
            Note n5 = new Note(4, "Ghi chu 3", "Hay them ghi chu cua ban", Calendar.getInstance().getTime());
            Note n6 = new Note(5, "Ghi chu 4", "Hay them ghi chu cua ban", Calendar.getInstance().getTime());
            u1.addNote(n1);
            u1.addNote(n2);
            u1.addNote(n3);
            u1.addNote(n4);
            u1.addNote(n5);
            u1.addNote(n6);
            this.db.store(u1);
        } catch (Exception e){
            Log.e("ERROR ADD NOTE", "LOI ===========>" + e);
        }
    }

    public String login (String username, String password){
        List<User> userList = new ArrayList<User>();
        try{
            ObjectSet<User> naUsers = this.db.query(new Predicate<User>() {
                public boolean match(User user) {
                    return user.getUSERNAME().equals(username) && user.getPASSWORD().equals(password);
                }
            } );
            for (User u: naUsers) {
                userList.add((User) u);
            }
            if(userList.isEmpty()){
                return "";
            } else if (userList.size() > 0){
                String stoUsername = userList.get(0).getUSERNAME();
                Storage stoUser = new Storage();
                stoUser.setStoreUsername(stoUsername);
                this.db.store(stoUser);
                return userList.get(0).getUSERNAME();
            }
        }catch (Exception e){
            Log.e("ERROR LOGIN", "LOI ===========>" + e);
        }
        return "";
    }

    public boolean logout (String u_username){
        try {
            Storage objFind = new Storage();
            objFind.setStoreUsername(u_username);
            Storage s1 = (Storage) this.db.queryByExample(objFind).next();
            this.db.delete(s1);
            return true;
        } catch (Exception e){
            Log.e("ERROR DELETE NOTE", "LOI ===========>" + e);
            return false;
        }
    }

    @SuppressLint("LongLogTag")
    public ArrayList<User> getAllUser(){
        ArrayList<User> userList = new ArrayList<>();
        try{
            User objUser = new User();
            ObjectSet<Object> users = this.db.queryByExample(objUser);
            for (Object u: users){
                userList.add((User) u);
                Log.e("User value: ", ((User) u).getUSERNAME());
            }
        }catch (Exception e){
            Log.e("Test get all User: ", "OK =====>" + e);
        }
        return userList;
    }

    @SuppressLint("LongLogTag")
    public boolean addNote(String username, int id, String title, String content ) {
        try {
            Date createDate = Calendar.getInstance().getTime();
            Log.e("Info note:  ", "Note ========> " + username + "----- id: " + (id+1) + "-------title: " + title + "------content: "+ content);
            int noteID = (id+1);
            Note n1 = new Note(noteID, title, content, createDate);
            User u1 = (User) this.db.queryByExample(new User(username)).next();
            u1.addNote(n1);
            Log.e("Test aaaaaaa get all User: ", "OK " + u1.getNotes());
            this.db.store(u1);
            for(Note n : u1.getNotes()){
                this.db.store(n);
            }
            return true;
        } catch (Exception e){
            Log.e("ERROR ADD NOTE", "LOI ===========>" + e);
            return false;
        }
    }

    public boolean editNote(int id, String title, String content) {
        try {
            Note objFind = new Note();
            objFind.setID(id);
            Note n1 = (Note) this.db.queryByExample(objFind).next();
            n1.setTITLE(title);
            n1.setCONTENT(content);
            this.db.store(n1);
            return true;
        } catch (Exception e){
            Log.e("ERROR EDIT NOTE", "LOI ===========>" + e);
            return false;
        }
    }

    @SuppressLint("LongLogTag")
    public ArrayList<Note> getAllNote(String username){
        ArrayList<Note> noteList = new ArrayList<>();
        try{
            User objUser = new User();
            objUser.setUSERNAME(username);
            User user = (User)db.queryByExample(objUser).next();
            for (Note note : user.getNotes()){
                if (note != null){
                    noteList.add(note);
                }
            }
        }catch (Exception e){
            Log.e("Test get all Notes: ", "OK ===" + e );
        }
        return noteList;
    }

    public boolean deleteNote(int id) {
        try {
            Note objFind = new Note();
            objFind.setID(id);
            Note n1 = (Note) this.db.queryByExample(objFind).next();
            this.db.delete(n1);
            return true;
        } catch (Exception e){
            Log.e("ERROR DELETE NOTE", "LOI ===========>" + e);
            return false;
        }
    }

    public ArrayList<Note> getTEST(){
        ArrayList<Note> noteList = new ArrayList<>();
        try{
            Note ojbNote = new Note();
            ObjectSet<Object> notes = this.db.queryByExample(ojbNote);
            for (Object note : notes){
                if (note != null){
                    noteList.add((Note) note);
                }
            }
        }catch (Exception e){
            Log.e("Test get all Notes: ", "OK ===" + e );
        }
        return noteList;
    }
}
