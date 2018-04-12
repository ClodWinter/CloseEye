package cn.lizhiyu.closeeye.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import cn.lizhiyu.closeeye.Common.CommonTool;
import cn.lizhiyu.closeeye.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //设置状态栏为透明
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_login);

        createView();
    }

    private void createView()
    {
        ImageView imageViewClose = (ImageView) findViewById(R.id.activity_login_close);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        final EditText editTextAccount = (EditText) findViewById(R.id.login_account_edittext);

        final EditText editTextPass = (EditText) findViewById(R.id.login_pass_edittext);

        View viewLogin = findViewById(R.id.activity_login_doLogin);

        viewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (editTextAccount.getText().length() == 0)
                {
                    Toast.makeText(LoginActivity.this,"手机号码不能为空",Toast.LENGTH_LONG).show();

                    return;
                }

                if (!CommonTool.isMobile(editTextAccount.getText().toString()))
                {
                    Toast.makeText(LoginActivity.this,"手机号码不正确",Toast.LENGTH_LONG).show();

                    return;
                }

                if (editTextPass.getText().length() == 0)
                {
                    Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_LONG).show();

                    return;
                }


            }
        });

    }
}
