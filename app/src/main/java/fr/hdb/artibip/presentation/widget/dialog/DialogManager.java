package fr.hdb.artibip.presentation.widget.dialog;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.gcacace.signaturepad.views.SignaturePad;

import de.greenrobot.event.EventBus;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.event.EventSetPhotoDto;
import fr.hdb.artibip.donnee.dto.event.EventSetSignatureDto;

public class DialogManager {

    static Dialog customDialog;


    public static Dialog getCustomDialog() {
        return customDialog;

    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static void showLoader(Activity activity){
        customDialog = new Dialog(activity, R.style.MyDialog);
        customDialog.setContentView(R.layout.loader_layout);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final View content = activity.findViewById(android.R.id.content).getRootView();
        Bitmap image = BlurBuilder.blur(content);
        customDialog.getWindow().setBackgroundDrawable(new BitmapDrawable(activity.getResources(), image));
        WindowManager manager = (WindowManager) activity.getSystemService(Activity.WINDOW_SERVICE);
        int width, height;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            width = manager.getDefaultDisplay().getWidth();
            height = manager.getDefaultDisplay().getHeight();
        } else {
            Point point = new Point();
            manager.getDefaultDisplay().getSize(point);
            width = point.x;
            height = point.y;
        }
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = width;
        lp.height = height;
        customDialog.getWindow().setAttributes(lp);
        customDialog.setCancelable(false);
        customDialog.show();
    }


    public static void showMessageCustomOk(Activity activity ,String message, String titre,android.view.View.OnClickListener listener){
        customDialog=new Dialog(activity,R.style.MyDialog);
        customDialog.setContentView(R.layout.pop_up_layout);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final View content = activity.findViewById(android.R.id.content).getRootView();
        Bitmap image = BlurBuilder.blur(content);
        customDialog.getWindow().setBackgroundDrawable(new BitmapDrawable(activity.getResources(), image));

        WindowManager manager = (WindowManager) activity.getSystemService(Activity.WINDOW_SERVICE);
        int width, height;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            width = manager.getDefaultDisplay().getWidth();
            height = manager.getDefaultDisplay().getHeight();
        } else {
            Point point = new Point();
            manager.getDefaultDisplay().getSize(point);
            width = point.x;
            height = point.y;
        }
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = width;
        lp.height = height;
        customDialog.getWindow().setAttributes(lp);
        TextView title = (TextView)customDialog.findViewById(R.id.titre_pop_up);
        TextView messages = (TextView)customDialog.findViewById(R.id.message_pop_up);
        Button button = (Button) customDialog.findViewById(R.id.button_pop_pup);
        title.setText(message);
        messages.setText(titre);
        button.setOnClickListener(listener);
        customDialog.show();
    }
    public static void showMessageCustom(Activity activity ,String message, String titre,String leftCaption, String rightCaption, android.view.View.OnClickListener listenerLeft, android.view.View.OnClickListener listenerRight){
        customDialog=new Dialog(activity,R.style.MyDialog);
        customDialog.setContentView(R.layout.pop_up_custom_layout);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final View content = activity.findViewById(android.R.id.content).getRootView();
        Bitmap image = BlurBuilder.blur(content);
        customDialog.getWindow().setBackgroundDrawable(new BitmapDrawable(activity.getResources(), image));

        WindowManager manager = (WindowManager) activity.getSystemService(Activity.WINDOW_SERVICE);
        int width, height;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            width = manager.getDefaultDisplay().getWidth();
            height = manager.getDefaultDisplay().getHeight();
        } else {
            Point point = new Point();
            manager.getDefaultDisplay().getSize(point);
            width = point.x;
            height = point.y;
        }
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = width;
        lp.height = height;
        customDialog.getWindow().setAttributes(lp);
        TextView title = (TextView)customDialog.findViewById(R.id.titre_pop_up_custom);
        TextView messages = (TextView)customDialog.findViewById(R.id.message_pop_up_custom);
        Button buttonLeft = (Button) customDialog.findViewById(R.id.button_pop_up_left);
        Button buttonRight = (Button) customDialog.findViewById(R.id.button_pop_up_right);

        title.setText(message);
        messages.setText(titre);
        buttonLeft.setText(leftCaption);
        buttonRight.setText(rightCaption);

        buttonLeft.setOnClickListener(listenerLeft);
        buttonRight.setOnClickListener(listenerRight);


        customDialog.show();
    }


    public static void showSigning(final Activity activity){
        customDialog = new Dialog(activity,R.style.MyDialog);
        customDialog.setContentView(R.layout.facturation_demande_signature_artisan);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final View content = activity.findViewById(android.R.id.content).getRootView();
        Bitmap image = BlurBuilder.blur(content);
        customDialog.getWindow().setBackgroundDrawable(new BitmapDrawable(activity.getResources(), image));

        WindowManager manager = (WindowManager) activity.getSystemService(Activity.WINDOW_SERVICE);
        int width, height;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            width = manager.getDefaultDisplay().getWidth();
            height = manager.getDefaultDisplay().getHeight();
        } else {
            Point point = new Point();
            manager.getDefaultDisplay().getSize(point);
            width = point.x;
            height = point.y;
        }
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = width;
        lp.height = height;
        customDialog.getWindow().setAttributes(lp);
        final SignaturePad pad = (SignaturePad) customDialog.findViewById(R.id.signature_pad);
        final Button buttonLeft = (Button) customDialog.findViewById(R.id.button_pop_up_left);
        final Button buttonRight = (Button) customDialog.findViewById(R.id.button_pop_up_right);

        buttonLeft.setText(activity.getString(R.string.annuler));
        buttonRight.setText(activity.getString(R.string.ok));

        pad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                buttonLeft.setText(activity.getString(R.string.erase));
            }

            @Override
            public void onSigned() {
                buttonLeft.setText(activity.getString(R.string.erase));

            }

            @Override
            public void onClear() {
                buttonLeft.setText(activity.getString(R.string.annuler));
            }
        });

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (pad.isEmpty()) {
                    getCustomDialog().dismiss();

               } else {
                   pad.clear();
               }
            }
        });
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!pad.isEmpty()) {
                    Bitmap bitmap = pad.getSignatureBitmap();
                    EventBus.getDefault().post(new EventSetSignatureDto(bitmap));
                    getCustomDialog().dismiss();
                }else{
                    EventBus.getDefault().post(new EventSetSignatureDto(null));
                    getCustomDialog().dismiss();
                }

            }
        });


        customDialog.show();
    }


    public static void showImageFullScreen(Activity activity ,final int position, Bitmap bitmap){
        customDialog=new Dialog(activity,R.style.MyDialog);
        customDialog.setContentView(R.layout.pop_up_show_image);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final View content = activity.findViewById(android.R.id.content).getRootView();
        Bitmap image = BlurBuilder.blur(content);
        customDialog.getWindow().setBackgroundDrawable(new BitmapDrawable(activity.getResources(), image));

        WindowManager manager = (WindowManager) activity.getSystemService(Activity.WINDOW_SERVICE);
        int width, height;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            width = manager.getDefaultDisplay().getWidth();
            height = manager.getDefaultDisplay().getHeight();
        } else {
            Point point = new Point();
            manager.getDefaultDisplay().getSize(point);
            width = point.x;
            height = point.y;
        }
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = width;
        lp.height = height;
        customDialog.getWindow().setAttributes(lp);
        TextView messages = (TextView)customDialog.findViewById(R.id.message_pop_up_custom);
        ImageView imageView = (ImageView) customDialog.findViewById(R.id.image_to_show);
        Button buttonLeft = (Button) customDialog.findViewById(R.id.button_pop_up_left);
        Button buttonRight = (Button) customDialog.findViewById(R.id.button_pop_up_right);

        messages.setText(activity.getString(R.string.delete_photo));
        buttonLeft.setText(activity.getString(R.string.annuler));
        buttonRight.setText(activity.getString(R.string.supprimer));
        imageView.setImageBitmap(bitmap);

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCustomDialog().dismiss();
            }
        });
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventSetPhotoDto(position));
                getCustomDialog().dismiss();
            }
        });

        customDialog.show();
    }

    public static void showImageFullScreenFacture(Activity activity ,final int position, Bitmap bitmap){
        customDialog=new Dialog(activity,R.style.MyDialog);
        customDialog.setContentView(R.layout.pop_up_show_image_facture);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final View content = activity.findViewById(android.R.id.content).getRootView();
        Bitmap image = BlurBuilder.blur(content);
        customDialog.getWindow().setBackgroundDrawable(new BitmapDrawable(activity.getResources(), image));

        WindowManager manager = (WindowManager) activity.getSystemService(Activity.WINDOW_SERVICE);
        int width, height;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            width = manager.getDefaultDisplay().getWidth();
            height = manager.getDefaultDisplay().getHeight();
        } else {
            Point point = new Point();
            manager.getDefaultDisplay().getSize(point);
            width = point.x;
            height = point.y;
        }
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = width;
        lp.height = height;
        customDialog.getWindow().setAttributes(lp);
        ImageView imageView = (ImageView) customDialog.findViewById(R.id.image_to_show);
        Button buttonLeft = (Button) customDialog.findViewById(R.id.button_pop_up_left);

        buttonLeft.setText(activity.getString(R.string.retour));
        imageView.setImageBitmap(bitmap);

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCustomDialog().dismiss();
            }
        });
        customDialog.show();
    }

    public static void showMessageMenu(Activity activity ,String message, String titre,android.view.View.OnClickListener listener){
        customDialog=new Dialog(activity,R.style.MyDialog);
        customDialog.setContentView(R.layout.pop_up_layout);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final View content = activity.findViewById(android.R.id.content).getRootView();
        Bitmap image = BlurBuilder.blur(content);
        customDialog.getWindow().setBackgroundDrawable(new BitmapDrawable(activity.getResources(), image));

        WindowManager manager = (WindowManager) activity.getSystemService(Activity.WINDOW_SERVICE);
        int width, height;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            width = manager.getDefaultDisplay().getWidth();
            height = manager.getDefaultDisplay().getHeight();
        } else {
            Point point = new Point();
            manager.getDefaultDisplay().getSize(point);
            width = point.x;
            height = point.y;
        }
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = width;
        lp.height = height;
        customDialog.getWindow().setAttributes(lp);
        TextView title = (TextView)customDialog.findViewById(R.id.titre_pop_up);
        TextView messages = (TextView)customDialog.findViewById(R.id.message_pop_up);
        Button button = (Button) customDialog.findViewById(R.id.button_pop_pup);
        title.setText(message);
        messages.setText(titre);
        button.setOnClickListener(listener);
        customDialog.show();
    }

    public static void showEtabMenu(Activity activity ,String message, String titre,android.view.View.OnClickListener listener){
        customDialog=new Dialog(activity,R.style.MyDialog);
        customDialog.setContentView(R.layout.pop_up_layout);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final View content = activity.findViewById(android.R.id.content).getRootView();
        Bitmap image = BlurBuilder.blur(content);
        customDialog.getWindow().setBackgroundDrawable(new BitmapDrawable(activity.getResources(), image));

        WindowManager manager = (WindowManager) activity.getSystemService(Activity.WINDOW_SERVICE);
        int width, height;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            width = manager.getDefaultDisplay().getWidth();
            height = manager.getDefaultDisplay().getHeight();
        } else {
            Point point = new Point();
            manager.getDefaultDisplay().getSize(point);
            width = point.x;
            height = point.y;
        }
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = width;
        lp.height = height;
        customDialog.getWindow().setAttributes(lp);
        TextView title = (TextView)customDialog.findViewById(R.id.titre_pop_up);
        TextView messages = (TextView)customDialog.findViewById(R.id.message_pop_up);
        Button button = (Button) customDialog.findViewById(R.id.button_pop_pup);
        title.setText(message);
        messages.setText(titre);
        button.setOnClickListener(listener);
        customDialog.show();
    }
}
