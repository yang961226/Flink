package com.example.flink.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.event.DateChangeEvent;
import com.example.flink.layout.note.NoteViewPagerBaseLayout;
import com.example.flink.network.RetrofitServiceManager;
import com.example.flink.network.api.TestApi;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TestNoteLayout2 extends NoteViewPagerBaseLayout {


    @BindView(R.id.btn_test_api)
    Button btnTestApi;
    @BindView(R.id.tv_result)
    TextView tvResult;

    @Override
    protected void init(Context context) {
        super.init(context);
    }

    @OnClick(R.id.btn_test_api)
    public void onViewClicked() {
        TestApi testApi = RetrofitServiceManager.getInstance().create(TestApi.class);
        testApi.getResult("flink", new HashMap<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        tvResult.setText(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        tvResult.setText(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public TestNoteLayout2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDateChange(DateChangeEvent dateChangeEvent) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_testnote2;
    }

    @Override
    public void onClickFunction() {
        Toast.makeText(getContext(), "TestNoteLayout2点击", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongClickFunction() {
        Toast.makeText(getContext(), "TestNoteLayout2长按", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewPagerScorll() {

    }
}