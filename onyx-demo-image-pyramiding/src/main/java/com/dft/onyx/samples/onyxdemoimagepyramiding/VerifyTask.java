
package com.dft.onyx.samples.onyxdemoimagepyramiding;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.Toast;

import com.dft.onyx.FingerprintTemplate;
import com.dft.onyx.core;

public class VerifyTask extends AsyncTask<FingerprintTemplate, Void, Float> {
    private Exception mException = null;
    private Context mContext = null;

    public VerifyTask(Context context) {
        mContext = context;
    }

    @Override
    protected Float doInBackground(FingerprintTemplate... templates) {
        try {
            return core.verify(templates[0], templates[1]);
        } catch (Exception e) {
            mException = e;
        }
        return -1.0f;
    }

    @Override
    protected void onPostExecute(Float matchScore) {
        if (mException != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(mException.getMessage())
                    .setCancelable(false)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setTitle("Verification error");

            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            Toast.makeText(mContext, createMatchString(matchScore), Toast.LENGTH_SHORT).show();
        }
    }

    private String createMatchString(double score) {
        String matchString;
        if (score < 0.1) {
            matchString = new String("Failed");
        } else {
            matchString = new String("Match");
        }
        matchString += " (Score = " + String.format("%.2f", score) + ")";

        return matchString;
    }

}
