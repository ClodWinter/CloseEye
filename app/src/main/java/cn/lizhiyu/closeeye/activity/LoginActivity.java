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

import org.w3c.dom.Text;

import java.util.HashMap;

import cn.lizhiyu.closeeye.Common.CommonTool;
import cn.lizhiyu.closeeye.R;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;

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
                Platform wechat = ShareSDK.getPlatform( Wechat.NAME);
//回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
                wechat.setPlatformActionListener(new PlatformActionListener() {

                    @Override
                    public void onError(Platform arg0, int arg1, Throwable arg2) {
                        // TODO Auto-generated method stub
                        arg2.printStackTrace();
                    }

                    @Override
                    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                        // TODO Auto-generated method stub
                        //输出所有授权信息
                        arg0.getDb().exportData();
                    }

                    @Override
                    public void onCancel(Platform arg0, int arg1) {
                        // TODO Auto-generated method stub

                    }
                });
                wechat.showUser(null);//执行登录，登录后在回调里面获取用户资料
//weibo.showUser(“3189087725”);//获取账号为“3189087725”的资料
            }
        });

        buttonQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        buttonWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Platform weibo = ShareSDK.getPlatform( SinaWeibo.NAME);
//回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
                weibo.setPlatformActionListener(new PlatformActionListener() {

                    @Override
                    public void onError(Platform arg0, int arg1, Throwable arg2) {
                        // TODO Auto-generated method stub
                        arg2.printStackTrace();
                    }

                    @Override
                    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                        // TODO Auto-generated method stub
                        //输出所有授权信息
                        arg0.getDb().exportData();
                    }

                    @Override
                    public void onCancel(Platform arg0, int arg1) {
                        // TODO Auto-generated method stub

                    }
                });
                weibo.showUser(null);//执行登录，登录后在回调里面获取用户资料
//weibo.showUser(“3189087725”);//获取账号为“3189087725”的资料
            }
        });

        buttonWeFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        buttonTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
