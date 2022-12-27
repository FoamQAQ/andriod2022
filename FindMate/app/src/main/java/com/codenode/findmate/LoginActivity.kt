package com.codenode.findmate

import android.app.Activity
import android.widget.EditText
import android.widget.TextView
import com.codenode.findmate.db.UserDBHelper
import android.os.Bundle
import com.codenode.findmate.R
import android.widget.Toast
import com.codenode.findmate.db.UserInfo
import android.content.Intent
import android.view.View
import android.widget.Button
import com.codenode.findmate.MainActivity
import com.codenode.findmate.SignUpActivity
import java.util.ArrayList

class LoginActivity : Activity() {
    private lateinit var userName: EditText
    private lateinit var password: EditText
    private lateinit var signUp: TextView
    private lateinit var login: Button
    private lateinit var userDBHelper: UserDBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_login)
        userName = findViewById(R.id.userName)
        password = findViewById(R.id.password)
        signUp = findViewById(R.id.signUp)
        login = findViewById(R.id.login)

//        SharedPreferences userData=getSharedPreferences("userData",MODE_PRIVATE);
//        String savedUserName = userData.getString("userName","");
//        String savedPassword = userData.getString("userPwd","");
        login.setOnClickListener(View.OnClickListener {
            Toast.makeText(application, userName.getText(), Toast.LENGTH_SHORT).show()
            if (userName.getText().isEmpty()) {
                Toast.makeText(application, "please enter username", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (password.getText().isEmpty()) {
                Toast.makeText(application, "please enter password", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            val condition = String.format(
                "userName='%s' and password='%s'",
                userName.getText().toString(),
                password.getText().toString()
            )
            var tempList: List<UserInfo?> = ArrayList()
            tempList = userDBHelper!!.query(condition)
            //Toast.makeText(getApplication(),"数据长度："+tempList.size(),Toast.LENGTH_SHORT).show();
            if (tempList.isNotEmpty()) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(application, "invalid password or no user", Toast.LENGTH_SHORT)
                    .show()
            }
            /*if(savedUserName.indexOf(String.valueOf(userName.getText()))==-1){
                        Toast.makeText(getApplication(),"nonexistent username",Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        String[] userNameArray = savedUserName.split(",");
                        String[] userPasswordArray = savedPassword.split(",");
                        int idx = 0;
                        for(int i=0;i<userNameArray.length;i++){
                            if(userNameArray[i].equals(String.valueOf(userName.getText()))){
                                idx = i;
                            }
                        }
                        if(userPasswordArray[idx].equals(password.getText())){
                            Intent intent = new Intent(LoginActivity.this,ChooseActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplication(),"invalid password",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }*/
        })
        signUp.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        })
    }

    override fun onStart() {
        super.onStart()
        userDBHelper = UserDBHelper.getInstance(this, 1)
        //打开数据库帮助器的写连接
        userDBHelper.openReadLink()
    }
}