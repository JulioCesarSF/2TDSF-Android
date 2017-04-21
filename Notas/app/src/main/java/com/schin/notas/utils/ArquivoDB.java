package com.schin.notas.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by schin on 30/03/2017.
 */

public final class ArquivoDB {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    public static ArquivoDB arquivoDB;
    public static Context context;

    private static final String FILE_NAME = "notasPref";

    private ArquivoDB(){
        sharedPreferences = (sharedPreferences == null) ? context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE) : sharedPreferences;
    }

    public static synchronized ArquivoDB getInst(Context con) {
        context = con;
        return arquivoDB = (arquivoDB == null) ? new ArquivoDB() : arquivoDB;
    }

    public void gravarChaves(HashMap<String, String> map) {
        editor = sharedPreferences.edit();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            editor.putString(entry.getKey(), entry.getValue());
        }
        editor.commit();
    }

    public void excluirChaves(HashMap<String, String> map) {
        editor = sharedPreferences.edit();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            editor.remove(entry.getKey());
        }
        editor.commit();
    }

    public void excluirChaves(List<String> keys) {
        editor = sharedPreferences.edit();
        for (String key : keys) {
            editor.remove(key);
        }
        editor.commit();
    }

    public String retornarValor(String key) {
        return sharedPreferences.getString(key, "");
    }

    public boolean verificarValor(String key) {
        return sharedPreferences.contains(key);
    }

    public HashMap<String, Boolean> verificarTodasChaves(List<String> keys) {
        HashMap<String, Boolean> resp = null;

        for (String key :
                keys) {
            resp.put(key, sharedPreferences.contains(key));
        }

        return resp;
    }

    public void gravarArquivo(String valor) throws IOException {
        FileOutputStream out = context.openFileOutput(FILE_NAME.concat(".txt"), Context.MODE_PRIVATE);
        try {
            out.write(valor.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public String lerArquivo() throws FileNotFoundException {
        FileInputStream in = context.openFileInput(FILE_NAME.concat(".txt"));
        BufferedReader bR = new BufferedReader(new InputStreamReader(in));
        String texto = null;
        try {
            texto = bR.readLine();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return texto;
    }

    public boolean excluirArquivo() {
        try {
            return context.deleteFile(FILE_NAME.concat(".txt"));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
