package fr.hdb.artibip.presentation.fragment.artisan.facturationetape.signature;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import fr.hdb.artibip.R;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.presentation.fragment.facturationfinale.FacturationFragment_;


@EFragment(R.layout.facturation_demande_signature_artisan)
public class FacturationSignatureFragment extends GenericFragment {
    @AfterViews
    void afterView(){
        setFooterVisibility(false);
        setHeaderVisibility(true,true);
        setToolbarVisibility(true,getString(R.string.signature_client_toolbar),false);
    }
    @Click(R.id.oui)
    void onClockOui(){
        replaceFragment(R.id.main_container,new FacturationFragment_(),true,true);
    }
}
