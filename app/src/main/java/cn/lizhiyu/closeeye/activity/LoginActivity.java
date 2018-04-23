package cn.lizhiyu.closeeye.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.auth.TwitterHandle;

import org.w3c.dom.Text;

import java.util.HashMap;

import cn.lizhiyu.closeeye.Common.CommonTool;
import cn.lizhiyu.closeeye.R;
import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginActivity extends AppCompatActivity implements PlatformActionListener{

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

        final View viewLogin = findViewById(R.id.activity_login_doLogin);

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

                if (editTextAccount.getText().length()>0 && editTextPass.getText().length()>0 )
                {

                }
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (editTextAccount.getText().length()>0 && editTextPass.getText().length()>0 )
                {
                    viewLogin.setBackgroundColor(Color.parseColor("#f34649"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        editTextAccount.addTextChangedListener(textWatcher);

        editTextPass.addTextChangedListener(textWatcher);

        TextView textViewRegister = (TextView) findViewById(R.id.login_now_register);

        TextView textViewForget = (TextView) findViewById(R.id.login_forgetpass);

        textViewRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);

                startActivity(intent);
            }
        });

        textViewForget.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(LoginActivity.this,ForgetPasswordActivity.class);

                startActivity(intent);
            }
        });

        Button buttonWechat = (Button) findViewById(R.id.login_wechat);

        Button buttonQQ = (Button) findViewById(R.id.login_qq);

        Button buttonWeibo = (Button) findViewById(R.id.login_weibo);

        Button buttonWeFacebook = (Button) findViewById(R.id.login_facebook);

        Button buttonTwitter = (Button) findViewById(R.id.login_twitter);

        buttonWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);

                wechat.setPlatformActionListener(LoginActivity.this);

                wechat.authorize();
            }
        });

        buttonQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Platform qq = ShareSDK.getPlatform(QQ.NAME);

                qq.removeAccount(true);

                qq.setPlatformActionListener(LoginActivity.this);

                qq.authorize();

            }
        });

        buttonWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Platform weibo = ShareSDK.getPlatform( SinaWeibo.NAME);

                weibo.setPlatformActionListener(LoginActivity.this);

                weibo.authorize();
            }
        });

        buttonWeFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Platform facebook = ShareSDK.getPlatform(Facebook.NAME);

                facebook.setPlatformActionListener(LoginActivity.this);

                facebook.authorize();
            }
        });

        buttonTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
//                Platform twitter = ShareSDK.getPlatform(Twitter.Name);
//
//                twitter.setPlatformActionListener(LoginActivity.this);
//
//                twitter.authorize();
            }
        });
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i)
    {

    }
}
