package fr.hdb.artibip.presentation.fragment.client;

import android.text.TextUtils;
import android.webkit.WebView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.ws.post.cgv.CgvDto;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.presentation.widget.webview.WebViewListener;
import fr.hdb.artibip.presentation.widget.webview.WebViewUtils;
import fr.hdb.artibip.service.applicatif.client.cgv.CgvSA;
import fr.hdb.artibip.service.applicatif.client.cgv.CgvSAImpl;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;


@EFragment(R.layout.condition_fragment)
public class ConditionFragment extends GenericFragment {

    @Bean(CgvSAImpl.class)
    protected CgvSA cgvSA;

    private CgvDto cgvDto;

    @ViewById(R.id.webview_cgu)
    WebView webView;

    WebViewUtils mWebviewUtils;

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferencesSA;

    @AfterViews
    void afterView(){
        setFooterVisibility(false);
        if (preferencesSA.getToken() == null ||TextUtils.isEmpty(preferencesSA.getToken())) {
            setHeaderVisibility(true,true);
        } else {
            setHeaderVisibility(true,false);
        }
        setToolbarVisibility(true,getString(R.string.condition_generale),false);
        loadHtmlContents(cgvDto.getListCgv().get(0).getCgv());
    }

    private void loadHtmlContents(String html) {

        webView.setInitialScale(1);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setDisplayZoomControls(false);

        mWebviewUtils = new WebViewUtils(webView, getActivity(),
                new WebViewListener() {
                    @Override
                    public void onPageLoading() {
                        showLoader();
                    }
                    @Override
                    public void onPageFinished() {
                        hideLoader();
                    }
                });
        if (!TextUtils.isEmpty(html)) {
            mWebviewUtils.loadHtml(html);
        }
    }

    public CgvDto getCgvDto() {
        return cgvDto;
    }
    public void setCgvDto(CgvDto cgvDto) {
        this.cgvDto = cgvDto;
    }
}
