package afpcsoft.com.br.bestplaces.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by AndréFelipe on 26/05/2015.
 */
public class ImageUtils {

    public static String photoPath = "Best Places";
    public static String photoName = "tempFoto.jpg";
    public static String photoOutputName = "outputPhoto.jpg";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    public static String getOutputExternalFile(){
        File sdcard = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(sdcard, photoPath);
        File file = new File(dir,photoOutputName);
        Uri uri = Uri.fromFile(file);
        String path = uri.getPath();
        return path;
    }

    public static Bitmap getResizedImage(Uri uriFile, int width, int height) {
        try {
            // Carrega a imagem original em memória
            Bitmap bitmap = BitmapFactory.decodeFile(uriFile.getPath());

            int w = bitmap.getWidth();
            int h = bitmap.getHeight();

            float scaleX = (float) width / bitmap.getWidth();
            float scaleY = (float) height / bitmap.getHeight();

            Matrix matrix = new Matrix();
            matrix.setScale(scaleX, scaleY);

            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);

//            resizedBitmap = fixMatrix(uriFile, resizedBitmap);

            // Limpa a memória do arquivo original (o grande que fizemos resize)
            bitmap.recycle();
            bitmap = null;

            // Retorna a imagem com o resize
            return resizedBitmap;
        } catch (RuntimeException e) {
            Log.e("TESTE", e.getMessage(), e);
        }
        return null;
    }

    public static Bitmap fixMatrix(Uri uriFile, Bitmap bitmap) throws IOException {
        Matrix matrix = new Matrix();

        // Classe para ler tags escritas no JPEG
        /**
         * Para utilizar esta classe precisa de Android 2.2 ou superior
         */
        ExifInterface exif = new ExifInterface(uriFile.getPath());

        // Lê a orientação que foi salva a foto
        int orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);

        boolean fix = false;

        // Rotate bitmap
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180);
                fix = true;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(90);
                fix = true;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(270);
                fix = true;
                break;
            default:
                // ORIENTATION_ROTATE_0
                fix = false;
                break;
        }

        if(!fix) {
            return bitmap;
        }

        // Corrige a orientação (passa a matrix)
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        bitmap.recycle();
        bitmap = null;

        return newBitmap;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap){

        Matrix matrix = new Matrix();

        if (bitmap.getWidth()<bitmap.getHeight()) {
            matrix.postRotate(-90);
        }else{
            matrix.postRotate(90);
        }


        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth(),bitmap.getHeight(),true);

        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap .getWidth(), scaledBitmap .getHeight(), matrix, true);

        return rotatedBitmap;
    }

    public static Bitmap retornaImagem(String nomeArquivo, Context context) {

        Bitmap arquivoImagem = null;
        File filePath = context.getFileStreamPath(nomeArquivo);
        FileInputStream fi;
        try {

            fi = new FileInputStream(filePath);
            arquivoImagem = BitmapFactory.decodeStream(fi);

            fi.close();

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arquivoImagem;
    }

    public static boolean delete(String filesDir, String fileName){
        File image = createSdCardFile(filesDir, fileName);
        return image.delete();
    }

    public static File createSdCardFile(String dirName, String fileName){
        File sdcard = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(sdcard, dirName);
        if(!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir,fileName);
        return file;
    }

    public static File getSdCardFile(String dirName, String fileName){
        File sdcard = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(sdcard, dirName);
        File file = new File(dir,fileName);
        return file;
    }

    public static void criarArquivoJPEGMemoriaInterna(String nomeArquivo, Bitmap imagemBitmap, Context context){
        FileOutputStream fos;
        try {
            fos = context.openFileOutput(nomeArquivo, Context.MODE_PRIVATE);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            imagemBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            fos.write(bytes.toByteArray());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  Bitmap getBitmap(String dirName, String fileName){
        File file = getSdCardFile(dirName, fileName);
        Uri uri = Uri.fromFile(file);
        Bitmap bitmap= BitmapFactory.decodeFile(uri.getPath());
        return bitmap;
    }

    public static File resizeFile(String dirName, String fileName){
        File file = getSdCardFile(dirName, fileName);
        Uri uri = Uri.fromFile(file);
        Bitmap b= BitmapFactory.decodeFile(uri.getPath());
        Bitmap out = Bitmap.createScaledBitmap(b, 640, 480, false);

        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(file);
            out.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            b.recycle();
            out.recycle();
        } catch (Exception e) {}

        File fileFinal = getSdCardFile(dirName, fileName);
        return fileFinal;
    }

    /** Create a file Uri for saving an image or video */
    public static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    public static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }


    public String encodeImageBase64(File file) {
        try {
            FileInputStream imageInFile = new FileInputStream(file);
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);

            imageInFile.close();
            android.util.Base64.encode(imageData, 0);
            String result = encodeImage(imageData);
            return result;
        }catch (FileNotFoundException e){
            Log.e("Error Base 64", "Image not found" + e);
        } catch (IOException ioe) {
            Log.e("Error Base 64", "Exception while reading the Image " + ioe);
        }
        return null;
    }

    public Bitmap decodeImageBase64(String imageDataString) {

        try {
            byte[] imageByteArray = decodeImage(imageDataString);

            FileOutputStream imageOutFile = new FileOutputStream(getOutputExternalFile());

            imageOutFile.write(imageByteArray);

            imageOutFile.close();

            Bitmap decodedBitmap = getBitmap(photoPath, photoOutputName);
            return decodedBitmap;
        } catch (Exception ioe) {
            Log.e("Error Base 64", "Exception while reading the Image " + ioe);
        }

        return null;

    }

    /**
     * Decodes the base64 string into byte array
     *
     * @param imageDataString - a {@link java.lang.String}
     * @return byte array
     */
    public byte[] decodeImage(String imageDataString) {
        return new Base64().decode(imageDataString.getBytes());
    }

    /**
     * Encodes the byte array into base64 string
     *
     * @param imageByteArray - byte array
     * @return String a {@link java.lang.String}
     */
    public String encodeImage(byte[] imageByteArray) {
        return new String(Base64.encodeBase64(imageByteArray));
    }
}
