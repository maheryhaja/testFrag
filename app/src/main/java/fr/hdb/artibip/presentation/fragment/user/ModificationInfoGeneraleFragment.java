package fr.hdb.artibip.presentation.fragment.user;

import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import fr.hdb.artibip.R;
import fr.hdb.artibip.presentation.fragment.GenericFragment;


@EFragment(R.layout.modification_info_generale)
public class ModificationInfoGeneraleFragment extends GenericFragment {

    @ViewById(R.id.info_client)
    Button infoClient;

    @ViewById(R.id.paiement)
    Button paiement;

    @AfterViews
    void afterView(){
       /*TextView toolbarTitle= (TextView) getActivity().findViewById(R.id.text_toolbar_title);
        toolbarTitle.setTextSize(getActivity().getResources().getDimension(R.dimen.toolbar_text_size));*/
        infoClient.setBackgroundResource(R.color.color_primary_dark);
        paiement.setBackgroundResource(R.color.jaune_sombre);
        replaceFragment(R.id.layout_to_load, new InscriptionClientFragment_(),false,false);
    }


    @Click(R.id.info_client)
    void clickInfoClient(){
        infoClient.setBackgroundResource(R.color.color_primary_dark);
        paiement.setBackgroundResource(R.color.jaune_sombre);
        replaceFragment(R.id.layout_to_load, new InscriptionClientFragment_(),false,false);
    }

    @Click(R.id.paiement)
    void clickpaiement(){
        paiement.setBackgroundResource(R.color.color_primary_dark);
        infoClient.setBackgroundResource(R.color.jaune_sombre);
        replaceFragment(R.id.layout_to_load, new InformationBancaireFragment_(),false,false);
    }


}
