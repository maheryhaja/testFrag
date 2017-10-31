package fr.hdb.artibip.service.applicatif.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;


@EBean(scope = EBean.Scope.Singleton)
public class PreferencesSAImpl implements PreferencesSA {

    private static final String PREFERENCES_NAME = "artibip_preferences";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_LOGIN = "login";
    private static final String KEY_ROLE = "default";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_TYPE = "userType";
    private static final String ID_DEMANDE_STATUS = "demandeStatus";
    public static final String KEY_UID = "registration_id";
    public static final String KEY_PASS = "password";
    private SharedPreferences pref;

    @RootContext
    protected Context context;

    @AfterInject
    protected void afterInject(){
        pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    /**
     * récupération d'une valeur dans les préférences
     * @param key : la clé de la valeur à récupérer
     * @return
     */
    private String getStringValue(String key){
        return pref.getString(key, null);
    }

    /**
     * écriture d'une valeur dans les préférences
     * @param key : la clé de la valeur
     * @param value : la valeur
     */
    private void writeString(String key, String value){
        Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * sauvegarde d'une valeur de type entière dans les préférences
     *
     * @param key   : la clé de la valeur
     * @param value : la valeur
     */
    private void putInt(String key, int value) {
        Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * récupération d'une valeur de type entière depuis les préférences
     *
     * @param key : la clé de la valeur
     * @return
     */
    private int getInt(String key) {
        return pref.getInt(key, 0);
    }

    @Override
    public String getToken(){
        return getStringValue(KEY_TOKEN);
    }

    @Override
    public void setToken(String token){
        writeString(KEY_TOKEN,token);
    }

    @Override
    public String getLogin(){
        return getStringValue(KEY_LOGIN);
    }

    @Override
    public void setLogin(String login){
        writeString(KEY_LOGIN, login);
    }

    @Override
    public String getRole(){
        return getStringValue(KEY_ROLE);
    }

    @Override
    public void setRole(String userType){
        writeString(KEY_ROLE, userType);
    }

    @Override
    public String getEmail() {
        return getStringValue(KEY_EMAIL);
    }

    @Override
    public void setEmail(String email) {
        writeString(KEY_EMAIL, email);
    }

    @Override
    public void setId(int id){
        putInt(KEY_USER_ID,id);
    }

    @Override
    public int getId(){
        return getInt(KEY_USER_ID);
    }

    @Override
    public String getUid() {
        return getStringValue(KEY_UID);
    }

    @Override
    public void setUid(String uid) {
        writeString(KEY_UID, uid);
    }

    @Override
    public int getTypeUser() {
        return getInt(KEY_USER_TYPE);
    }

    @Override
    public void setTypeUser(int type) {
        putInt(KEY_USER_TYPE,type);
    }

    @Override
    public int getIdDemandeStatus(){
        return getInt(ID_DEMANDE_STATUS);
    }

    @Override
    public void setIdDemandeStatus(int idDemandeStatus){
        putInt(ID_DEMANDE_STATUS,idDemandeStatus);
    }

    @Override
    public String getPassword() {
        return getStringValue(KEY_PASS);
    }

    @Override
    public void setPassword(String pass) {
         writeString(KEY_PASS,pass);
    }

    /**
     * suppression de toutes les données
     */
    @Override
    public void clearAll(){
        Editor editor = pref.edit();
        editor.putString(KEY_TOKEN,"");
        editor.putString(KEY_LOGIN,"");
        editor.putString(KEY_ROLE,"");
        editor.putInt(KEY_USER_TYPE, -1);
        editor.putString(KEY_UID, "");
        editor.putString(KEY_PASS, "");
        editor.putString(KEY_EMAIL, "");
        editor.commit();
    }
}
