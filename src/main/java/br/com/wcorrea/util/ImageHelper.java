package br.com.wcorrea.util;

import java.io.*;
import java.util.Base64;

public class ImageHelper {

    public static void main(String[] args) {
        File f =  new File("C:/foto.jpg");
        String encodstring = encodeFileToBase64Binary(f);
        System.out.println(encodstring);
    }

    private static String encodeFileToBase64Binary(File file){
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = new String(Base64.getEncoder().encode(bytes), "UTF-8");
        } catch (FileNotFoundException e) {
            //TODO: Adicionar mensagem de erro internacionalizada
            e.printStackTrace();
            //TODO: Adicionar mensagem de erro internacionalizada
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            //TODO: Adicionar mensagem de erro internacionalizada
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encodedfile;
    }
}
