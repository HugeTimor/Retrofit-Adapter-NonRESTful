package me.zeyuan.lib.adapter_nonrestful.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import me.zeyuan.adapter_nonrestful.BusinessException;
import me.zeyuan.adapter_nonrestful.NonRESTfulAdapterFactory;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String KEY = "e2f77a86de1dd0fb657e5b2d2dfcb0be";
    private TextView dashboard;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        dashboard = (TextView) findViewById(R.id.dashboard);
        send = (Button) findViewById(R.id.send);

        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                request();
                break;
        }
    }

    private void request() {
        Retrofit retrofit = buildRetrofit("http://op.juhe.cn");
        Service service = retrofit.create(Service.class);
        dashboard.setHint("Sending...");
        service.ask(KEY, "Hello !")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Answer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleError(e);
                    }

                    @Override
                    public void onNext(Answer answer) {
                        dashboard.setText(answer.toString());
                    }
                });
    }

    /**
     * For demonstration, just only toast .
     */
    private void handleError(Throwable e) {
        if (e instanceof IOException) {
            Toast.makeText(this, "IOException：" + e.getMessage(), Toast.LENGTH_SHORT).show();
        } else if (e instanceof HttpException) {
            HttpException exception = (HttpException) e;
            Toast.makeText(this, "HttpException：" + exception.code(), Toast.LENGTH_SHORT).show();
        } else if (e instanceof BusinessException) {
            BusinessException exception = (BusinessException) e;
            Toast.makeText(this, "BusinessException:" + exception.getCode(), Toast.LENGTH_SHORT).show();
        } else {
            e.printStackTrace();
        }
    }

    private Retrofit buildRetrofit(String baseUrl) {
        Retrofit.Builder builder = new Retrofit.Builder();
        return builder
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new NonRESTfulAdapterFactory(Wrapper.class))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
    }
}
