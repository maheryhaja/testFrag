package fr.hdb.artibip.presentation.fragment.artisan.facturationetape.utilisationmateriel;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import java.util.ArrayList;
import java.util.List;
import de.greenrobot.event.EventBus;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.constantes.Strings;
import fr.hdb.artibip.donnee.dto.constantes.popup.PopUpType;
import fr.hdb.artibip.donnee.dto.event.EventSetMaterielDto;
import fr.hdb.artibip.donnee.dto.ws.artisan.facturation.MaterielDto;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.presentation.fragment.artisan.facturationetape.nouvelleintervention.FacturationNouvelleInterventionFragment_;

import static fr.hdb.artibip.presentation.activity.MainActivity.FACTURATION_POST;
import static fr.hdb.artibip.presentation.activity.MainActivity.PAGE_CURRENT;
import static fr.hdb.artibip.presentation.activity.MainActivity.clientPager;
import static fr.hdb.artibip.presentation.activity.MainActivity.listMateriel;


@EFragment(R.layout.facturation_liste_materiel_artisan)
public class FacturationListeMaterielFragment extends GenericFragment {

    @ViewById(R.id.list_view_materiel)
    ListView listViewMateriel;

    @ViewById(R.id.l1)
    LinearLayout l1;

    @ViewById(R.id.edit_text_designation)
    EditText designation;

    @ViewById(R.id.edit_text_prix)
    EditText prix;

    @ViewById(R.id.text_view_ajout_materiel)
    TextView ajout;
    ListMaterielAdapter listMaterielAdapter;

    @AfterViews
    void afterView(){
        setApplicationDesign();
    }

    void setApplicationDesign(){
        PAGE_CURRENT = 3;
        clientPager.setCurrentItem(PAGE_CURRENT,true);

        limitDecimal(prix, 7 , 2);

        setFooterVisibility(true);
        setHeaderVisibility(true,true);
        setToolbarVisibility(true,getString(R.string.liste_materiel_toolbar),false);
        if(listMateriel.size()==0) {
            listMateriel = new ArrayList<MaterielDto>();
        }else{
           afficherListe();
        }

    }

    public void limitDecimal(final EditText editText , final int beforComma , final int afterComma){
        editText.setFilters(new InputFilter[] { new DigitsKeyListener(
                Boolean.FALSE, Boolean.TRUE) {
            int beforeDecimal = beforComma, afterDecimal = afterComma;

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                String etText = editText.getText().toString();
                if (etText.isEmpty()){
                    return null;
                }
                String temp = editText.getText() + source.toString();
                if (temp.equals(".")) {
                    return "0.";
                } else if (temp.toString().indexOf(".") == -1) {
                    // no decimal point placed yet
                    if (temp.length() > beforeDecimal) {
                        return "";
                    }
                } else {
                    int dotPosition ;
                    int cursorPositon = editText.getSelectionStart();
                    if (etText.indexOf(".") == -1) {

                        dotPosition = temp.indexOf(".");

                    }else{
                        dotPosition = etText.indexOf(".");

                    }
                    if(cursorPositon <= dotPosition){

                        String beforeDot = etText.substring(0, dotPosition);
                        if(beforeDot.length()<beforeDecimal){
                            return source;
                        }else{
                            if(source.toString().equalsIgnoreCase(".")){
                                return source;
                            }else{
                                return "";
                            }

                        }
                    }else{

                        temp = temp.substring(temp.indexOf(".") + 1);
                        if (temp.length() > afterDecimal) {
                            return "";
                        }
                    }
                }

                return super.filter(source, start, end, dest, dstart, dend);
            }
        } });
    }

    @Click(R.id.button_valider_materiel)
    void onClickValidate(){
        if(listMateriel.size()>0) {
            FACTURATION_POST.setListeMateriels(listMateriel);
            FACTURATION_POST.setTotalMateriels(prixTotal(listMateriel));

            PAGE_CURRENT = 4;
            replaceFragment(R.id.main_container, new FacturationNouvelleInterventionFragment_(), true, true);
        }else{
            showCustomDialog(getString(R.string.information), getString(R.string.ajout_materiel_obligation), PopUpType.OK_CANCEL,0);
        }
    }

    @Click(R.id.text_view_ajout_materiel)
    void onClickAjoutMateriel(){
        if(validation()) {
            MaterielDto materielDto= new MaterielDto();
            materielDto.setNomMateriel(designation.getText().toString().toUpperCase());
            materielDto.setPrixMaterielHt(round(Double.parseDouble(prix.getText().toString()),2));
            listMateriel.add(materielDto);
            afficherListe();
            listViewMateriel.setSelection(listMateriel.size() - 1);
            initChamps();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(EventSetMaterielDto event){
        listMateriel.remove(event.getPosition());
        setListViewMaterielHeight(listMateriel.size());
        listMaterielAdapter= new ListMaterielAdapter(getContext(), listMateriel);
        listViewMateriel.setAdapter(listMaterielAdapter);
        listViewMateriel.setOnItemClickListener(new AdapterListener ());
        initChamps();
        if(listMateriel.size()==0){
            l1.setVisibility(View.INVISIBLE);
        }
    }

    private class AdapterListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adpterView, View view, int position, long id) {
            showCustomDialog(getString(R.string.information), getString(R.string.supprimer_materiel), PopUpType.DELETE_MATERIEL, position);
        }
    }

    public boolean validation() {
        View focusView = null;
        View otherFocusView = null;
        boolean validator = true;
        if (designation.getText().toString().isEmpty()){
            validator= false;
            focusView=designation;
            designation.setError(getString(R.string.error_field_required));
        }
        if( prix.getText().toString().isEmpty()){
            validator= false;
            otherFocusView=prix;
            prix.setError(getString(R.string.error_field_required));
        }
        if (focusView != null) {
            focusView.requestFocus();
        }
        if (otherFocusView != null) {
            otherFocusView.requestFocus();
        }
        return validator;
    }

    public void initChamps(){
        if (!designation.getText().toString().equalsIgnoreCase(Strings.EMPTY)) {
            designation.setText(Strings.EMPTY);
            designation.setError(null);
        }
        if (!prix.getText().toString().equalsIgnoreCase(Strings.EMPTY)) {
            prix.setText(Strings.EMPTY);
            prix.setError(null);
        }
    }
    public double prixTotal(List<MaterielDto> materielDto){
        double total=0;
        for(int i =0 ; i<materielDto.size();i++){
            total= total+ materielDto.get(i).getPrixMaterielHt();
        }
        return round(total,2);
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public void afficherListe(){
        setListViewMaterielHeight(listMateriel.size());
        l1.setVisibility(View.VISIBLE);
        listMaterielAdapter = new ListMaterielAdapter(getContext(), listMateriel);
        listViewMateriel.setAdapter(listMaterielAdapter);
        listViewMateriel.setOnItemClickListener(new AdapterListener());
    }

    void setListViewMaterielHeight(int count) {
        ViewGroup.LayoutParams params = listViewMateriel.getLayoutParams();
        params.height =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50 * count, getResources().getDisplayMetrics());
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        listViewMateriel.setLayoutParams(params);
        listViewMateriel.requestFocus();
    }


}