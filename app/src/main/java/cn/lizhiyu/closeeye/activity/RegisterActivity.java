package cn.lizhiyu.closeeye.activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import cn.lizhiyu.closeeye.Common.CommonTool;

import org.w3c.dom.Text;

import cn.lizhiyu.closeeye.Common.CommonTool;
import cn.lizhiyu.closeeye.R;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static cn.lizhiyu.closeeye.R.*;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        setContentView(layout.activity_register);

        createView();
    }

    private void createView()
    {
        ImageView imageViewClose = (ImageView) findViewById(id.activity_register_close);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        TextView textView = (TextView) findViewById(id.register_hasAccount);

        textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);

                startActivity(intent);
            }
        });

        final EditText editTextAccount = (EditText) findViewById(id.register_account_edittext);

        final EditText editTextPass = (EditText) findViewById(id.register_pass_edittext);

        final EditText editTextCode = (EditText) findViewById(id.register_vali_edittext);

        final EditText editTextPassAgain = (EditText) findViewById(id.register_again_pass_edittext);

        final ImageView imageViewAction = (ImageView) findViewById(id.register_button);

        final TextView textViewGetCode = (TextView) findViewById(id.register_getValiCode);


        imageViewAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });

        textViewGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (editTextAccount.getText().length() == 0)
                {
                    Toast.makeText(RegisterActivity.this,"手机号码不能为空",Toast.LENGTH_LONG).show();

                    return;
                }

                if (!CommonTool.isMobile(editTextAccount.getText().toString()))
                {
                    Toast.makeText(RegisterActivity.this,"手机号码格式不正确",Toast.LENGTH_LONG).show();

                    return;
                }

                // 注册一个事件回调，用于处理发送验证码操作的结果
                SMSSDK.registerEventHandler(new EventHandler() {
                    public void afterEvent(int event, int result, Object data) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理成功得到验证码的结果
                            // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                        } else{
                            // TODO 处理错误的结果
                        }

                    }
                });

                // 触发操作
                SMSSDK.getVerificationCode("86", editTextAccount.getText().toString());

                Toast.makeText(RegisterActivity.this,"验证码已发送，请注意查收。",Toast.LENGTH_LONG).show();

                textViewGetCode.setClickable(false);

                final CountDownTimer timer = new CountDownTimer(60000,1000) {
                    @Override
                    public void onTick(long t)
                    {
                        textViewGetCode.setText(t/1000+" 后重发");
                    }

                    @Override
                    public void onFinish()
                    {
                        textViewGetCode.setText("获取验证码");

                        textViewGetCode.setClickable(true);
                    }
                }.start();
            }
        });

        final Button buttonCheck = (Button) findViewById(id.register_check);

        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                buttonCheck.setSelected(!buttonCheck.isSelected());

                if (buttonCheck.isSelected())
                {
                    buttonCheck.setBackgroundResource(drawable.check_box);
                }else
                {
                    buttonCheck.setBackgroundResource(drawable.uncheck_box);
                }
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editTextAccount.getText().length()>0 && editTextCode.getText().length()>0 && editTextPass.getText().length()>0 && editTextPassAgain.getText().length()>0)
                {
                    imageViewAction.setBackgroundColor(Color.parseColor("#f34649"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        editTextAccount.addTextChangedListener(textWatcher);

        editTextCode.addTextChangedListener(textWatcher);

        editTextPass.addTextChangedListener(textWatcher);

        editTextPassAgain.addTextChangedListener(textWatcher);
    }

}
