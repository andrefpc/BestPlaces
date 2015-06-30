package afpcsoft.com.br.bestplaces.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.maps.model.LatLng;

import afpcsoft.com.br.bestplaces.R;
import afpcsoft.com.br.bestplaces.controller.FaceBookLoginActivity;
import afpcsoft.com.br.bestplaces.controller.SearchActivity;
import afpcsoft.com.br.bestplaces.controller.StreetViewActivity;

/**
 * Created by AndréFelipe on 24/05/2015.
 */
public class DialogUtils {

    public static void showLogoutAlertToUser(final Activity activity){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(activity.getString(R.string.dialog_title_logout))
                .setPositiveButton(activity.getString(R.string.dialog_sim),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                LoginManager.getInstance().logOut();
                                Intent intent = new Intent(activity, FaceBookLoginActivity.class);
                                activity.startActivity(intent);
                                activity.finish();
                            }
                        });
        alertDialogBuilder.setNegativeButton(activity.getString(R.string.dialog_nao),null);
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public static void showStreetViewAlertToUser(final LatLng latLng, final Activity activity){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(activity.getString(R.string.dialog_title_streeview))
                .setPositiveButton(activity.getString(R.string.dialog_sim),
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent intent = new Intent(activity, StreetViewActivity.class);
                                intent.putExtra("lat", latLng.latitude);
                                intent.putExtra("lng", latLng.longitude);
                                activity.startActivity(intent);
                            }
                        });
        alertDialogBuilder.setNegativeButton(activity.getString(R.string.dialog_nao),null);
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public static void showGPSDisabledAlertToUser(final Activity activity, final Profile profile){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(activity.getString(R.string.dialog_title_gps))
                .setCancelable(false)
                .setPositiveButton(activity.getString(R.string.dialog_ativar_gps),
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                activity.startActivityForResult(callGPSSettingIntent, 0);
                            }
                        });
        alertDialogBuilder.setNegativeButton(activity.getString(R.string.dialog_colocar_localizacao),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(activity, SearchActivity.class);
                        intent.putExtra("profile", profile);
                        activity.startActivity(intent);
                        activity.finish();
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public static void showFinishAlertToUser(final Activity activity){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(activity.getString(R.string.dialog_title_finish))
                .setPositiveButton(activity.getString(R.string.dialog_sim),
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                activity.finish();
                            }
                        });
        alertDialogBuilder.setNegativeButton(activity.getString(R.string.dialog_nao),null);
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public static void showErrorSendMessageDialog(final Context context){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(context.getString(R.string.errorSendData))
                .setPositiveButton(context.getString(R.string.dialog_ok), null);
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

}
