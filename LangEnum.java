/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipproject;

/**
 *
 * @author Waldemar Sobiecki
 */
public enum LangEnum{
    ENGLISH("en", "US"), POLISH("pl","PL");
    LangEnum(String language, String country){
    this.language = language;
    this.country = country;
    }
    public String getLanguage(){
        return this.language;
    }
    public String getCountry(){
        return this.country;
    }
    private String language;
    private String country;
}