package com.codenode.findmate

import android.app.Activity
import android.widget.EditText
import com.codenode.findmate.db.UserDBHelper
import android.os.Bundle
import com.codenode.findmate.R
import android.widget.Toast
import com.codenode.findmate.db.UserInfo
import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import com.codenode.findmate.MainActivity
import java.text.SimpleDateFormat
import java.util.*

class SignUpActivity : Activity() {
    private lateinit var userName: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var signUp: Button
    private lateinit var userDBHelper: UserDBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_signup)
        userName = findViewById(R.id.userName)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        confirmPassword = findViewById(R.id.confirmPassword)
        signUp = findViewById(R.id.signUp)

        /* SharedPreferences userData=getSharedPreferences("userData",MODE_PRIVATE);
        String savedUserName = userData.getString("userName","");
        String savedPassword = userData.getString("userPwd","");
        String savedEmail = userData.getString("userEmail","");
        SharedPreferences.Editor editor = userData.edit();//获取编辑器*/
        signUp.setOnClickListener(View.OnClickListener {
            Toast.makeText(application, userName.getText(), Toast.LENGTH_SHORT).show()
            Log.i(
                "signup",
                password.getText().toString() + "   " + confirmPassword.getText()
                    .toString() + "   " + (password.getText()
                    .toString() == confirmPassword.getText().toString())
            )
            if (password.getText().toString() != confirmPassword.getText().toString()) {
                Toast.makeText(
                    application,
                    "password and confirm password isn't equal",
                    Toast.LENGTH_SHORT
                ).show()
                return@OnClickListener
            }
            if (email.getText().isEmpty()) {
                Toast.makeText(application, "please enter email", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (userName.getText().isEmpty()) {
                Toast.makeText(application, "please enter userName", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            /* editor.putString("userName",savedUserName+","+userName.getText());
                    editor.putString("userPwd",savedPassword+","+password.getText());
                    editor.putString("userEmail",savedEmail+","+email.getText());
                    editor.commit();//提交修改*/
            val userInfo = UserInfo()
            userInfo.userName = userName.getText().toString()
            userInfo.email = email.getText().toString()
            userInfo.password = password.getText().toString()
            @SuppressLint("SimpleDateFormat") val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val nowTime = sdf.format(Date())
            userInfo.update_time = nowTime
            val result = userDBHelper!!.insert(userInfo)
            userDBHelper!!.closeLink()
            if (result == 1L) {
                Toast.makeText(application, "注册成功", Toast.LENGTH_SHORT).show()
            }
            val intent = Intent(this@SignUpActivity, MainActivity::class.java)
            startActivity(intent)
        })
    }

    override fun onStart() {
        super.onStart()
        userDBHelper = UserDBHelper.getInstance(this, 1)
        //打开数据库帮助器的写连接
        userDBHelper.openWriteLink()
    }
}