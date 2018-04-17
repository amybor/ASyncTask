package alb77.example.com.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText time;
    private Button button;
    private TextView displayResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        time = (EditText) findViewById(R.id.user_time);
        button = (Button) findViewById(R.id.btn_run);
        displayResult = (TextView) findViewById(R.id.sleep_result);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = time.getText().toString();
                runner.execute(sleepTime);
            }
        });
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String>{
        private String response;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute(){
            progressDialog = ProgressDialog.show(MainActivity.this, "Progress Dialog", "Please wait for " + time.getText().toString()+ " seconds");
        }

        @Override
        protected String doInBackground(String... params){
            publishProgress("Sleeping...");
            try{
                int time = Integer.parseInt(params[0])*1000;

                Thread.sleep(time);
                response = "Slept for " + params[0] + "seconds";
            }catch (InterruptedException e){
                e.printStackTrace();
                response = e.getMessage();
            }catch (Exception e){
                e.printStackTrace();
                response = e.getMessage();
            }
            return response;
        }

        @Override
        protected void onProgressUpdate(String... text){
            displayResult.setText(text[0]);

        }

        @Override
        protected void onPostExecute(String result){
            progressDialog.dismiss();
            displayResult.setText(result);
        }
    }
}
