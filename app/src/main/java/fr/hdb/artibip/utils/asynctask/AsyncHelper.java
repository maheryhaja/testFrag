package fr.hdb.artibip.utils.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;


public abstract class AsyncHelper<Result> extends AsyncTask<Void, Void, Result> {

    private Context mContext;
    private Exception mException;

    public void launch(Context context) {
        mContext =context;
        this.execute();
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Result doInBackground(Void... params) {
        try {
            return this.background();
        } catch(Exception ex) {
            mException =ex;
            return null;
        }
    }

    @Override
    protected void onPostExecute (Result result) {
        try{
            if(result == null && mException != null) {
                this.error(mException);
                return;
            }
            this.success(result);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    protected abstract Result background() throws Exception;
    protected abstract void success(Result result);
    protected void error(Exception ex) {
        Toast.makeText(mContext, mException.getMessage(), Toast.LENGTH_LONG).show();
    }

}
