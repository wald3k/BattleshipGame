/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipproject;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Waldemar Sobiecki
 */
public class Lang {
    
    /*
    String language;
    String country;
    Locale l;
    ResourceBundle r;
    public Lang(LangEnum langEnum){
        switch(langEnum){
            case ENGLISH:
                l = new Locale(langEnum.getLanguage(),langEnum.getCountry());
                r= ResourceBundle.getBundle("Bundle/Bundle",l);
                break;
            case POLISH:
                l = new Locale(langEnum.getLanguage(),langEnum.getCountry());
                r= ResourceBundle.getBundle("Bundle/Bundle_pl_PL",l);
                break;
        }
    }  
      */
    
    
    /*
    public static void  setDefaultLocal(LangEnum langEnum){
        Locale l = Locale.getDefault();
        ResourceBundle r;
        switch(langEnum){
            case ENGLISH:
                l = new Locale(langEnum.getLanguage(),langEnum.getCountry());  
                Locale.setDefault(l);
                break;
            case POLISH:
                l = new Locale(langEnum.getLanguage(),langEnum.getCountry());  
                Locale.setDefault(l);
                break;
        }
       //return r = ResourceBundle.getBundle("Bundle/Bundle",l);
    }
    */
    public static String getIntText(LangEnum langEnum, String text){
        Locale l;
        ResourceBundle r;    
        switch(langEnum){
            case ENGLISH:
                l = new Locale(langEnum.getLanguage(),langEnum.getCountry()); 
                break;
            case POLISH:
                l = new Locale(langEnum.getLanguage(),langEnum.getCountry()); 
                break;
            default:
                l = Locale.getDefault();
                break;                  
                        
        }
        r = ResourceBundle.getBundle("Bundle/Bundle",l);
        return r.getString(text);
    }
}




