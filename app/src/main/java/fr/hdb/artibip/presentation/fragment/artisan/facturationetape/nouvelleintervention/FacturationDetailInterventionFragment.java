package fr.hdb.artibip.presentation.fragment.artisan.facturationetape.nouvelleintervention;

import android.text.TextUtils;
import android.widget.EditText;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import java.util.ArrayList;
import fr.hdb.artibip.R;
import fr.hdb.artibip.presentation.activity.MainActivity;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.presentation.fragment.photo.PhotoFragment_;
import fr.hdb.artibip.presentation.fragment.photo.ListPhotoFragment;

import static fr.hdb.artibip.presentation.activity.MainActivity.FACTURATION_POST;
import static fr.hdb.artibip.presentation.activity.MainActivity.PAGE_CURRENT;
import static fr.hdb.artibip.presentation.activity.MainActivity.clientPager;


@EFragment(R.layout.detail_intervention)
public class FacturationDetailInterventionFragment extends GenericFragment {

    @ViewById(R.id.edit_text_commentaire)
    protected EditText editTextComment;
    @AfterViews
    void afterView(){
        setApplicationDesign();
    }

    void setApplicationDesign(){
        PAGE_CURRENT = 5;
        clientPager.setCurrentItem(PAGE_CURRENT,true);

        setFooterVisibility(true);
        setHeaderVisibility(true,true);
        setToolbarVisibility(true,getString(R.string.prochaine_intervention),false);
    }

    @Click(R.id.button_create_intervention)
    void onClickCreateIntervention(){
        attemptToPostComment();
    }

    void attemptToPostComment() {

        String comment = editTextComment.getText().toString();

        if (!TextUtils.isEmpty(comment) && comment!=null){
            FACTURATION_POST.setCommentaires(comment);
       }else{
            FACTURATION_POST.setCommentaires("");
        }
            MainActivity.listFragment = new ArrayList<ListPhotoFragment>();
            PAGE_CURRENT = 6;
            replaceFragment(R.id.main_container, new PhotoFragment_(), true, true);
    //    }

    }
}
