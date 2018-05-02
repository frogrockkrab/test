package com.iamdeveloper.insertdata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText editName,editLastName;
    private Button Btn_insert;
    private static final String URL = "http://192.168.0.101/AlertCenter/AddMemberData.php";
    private String name,lastname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onBindView();

        Btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEditText();
                onButtonClick();
            }
        });
    }

    private void onBindView(){
        editName = (EditText) findViewById(R.id.editName);
        editLastName = (EditText) findViewById(R.id.editLastName);
        Btn_insert = (Button) findViewById(R.id.btn_insert);
    }

    private void onEditText(){
        name = editName.getText().toString();
        lastname = editLastName.getText().toString();

    }

    private void onButtonClick(){
        if(!name.isEmpty() && !lastname.isEmpty()){
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("onResponse",response);
                    editName.setText("");
                    editLastName.setText("");
                    Toast.makeText(MainActivity.this,"เพิ่มข้อมูลแล้วจ้า",Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("onError",error.toString());
                    Toast.makeText(MainActivity.this,"เกิดข้อผิดพลาดโปรดลองอีกครั้ง",Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("sUsername",name);
                    params.put("sPassword",lastname);

                    return params;
                }
            };
            requestQueue.add(request);
        }

    }
}
