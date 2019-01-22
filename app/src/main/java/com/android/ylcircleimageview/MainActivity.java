package com.android.ylcircleimageview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.library.YLCircleImageView;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, AdapterView.OnItemSelectedListener {

    @BindView(R.id.tvOriginal)
    TextView tvOriginal;
    @BindView(R.id.ivOriginal)
    ImageView ivOriginal;
    @BindView(R.id.circle6)
    YLCircleImageView circle6;
    @BindView(R.id.sbTopLeft)
    SeekBar sbLeftTop;
    @BindView(R.id.sbBottomLeft)
    SeekBar sbLeftBottom;
    @BindView(R.id.sbTopRight)
    SeekBar sbRightTop;
    @BindView(R.id.sbBottomRight)
    SeekBar sbRightBottom;
    @BindView(R.id.spinnerType)
    Spinner spinnerType;
    @BindView(R.id.spinnerMode)
    Spinner spinnerMode;
    @BindView(R.id.sbBorderWidth)
    SeekBar sbBorderWidth;
    @BindView(R.id.sbBorderSpace)
    SeekBar sbBorderSpace;

    private String[] urls;
    private int[] imgMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        urls = new String[]{"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547631452111&di=7cfb58904518ac97e3ec4a1c746125ae&imgtype=0&src=http%3A%2F%2Fimg.11665.com%2Fimg_p4%2Fi3%2F3297404733%2FTB2wPerc8HH8KJjy0FbXXcqlpXa_%2521%25213297404733.jpg"
                , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547623076229&di=83847f4a3c83df891eafe2236181f5b1&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201509%2F28%2F20150928190319_QNizx.jpeg"
                , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547628416050&di=28f29927c60bcdd1b1940e3d209baacd&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201512%2F28%2F20151228012100_STzAf.jpeg"
                , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547630900188&di=66a0f961060f7f42f30f667c587fdb13&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fq_70%2Cc_zoom%2Cw_640%2Fimages%2F20180611%2F69c6014e4f844c6391c75b594c9804d0.gif"
                , "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2564207084,3623649273&fm=26&gp=0.jpg"};

        imgMode = new int[]{YLCircleImageView.TOP, YLCircleImageView.CENTER, YLCircleImageView.BOTTOM, YLCircleImageView.FITXY};

        initImg(0);

        sbLeftTop.setOnSeekBarChangeListener(this);
        sbLeftBottom.setOnSeekBarChangeListener(this);
        sbRightTop.setOnSeekBarChangeListener(this);
        sbRightBottom.setOnSeekBarChangeListener(this);
        sbBorderWidth.setOnSeekBarChangeListener(this);
        sbBorderSpace.setOnSeekBarChangeListener(this);

        spinnerMode.setOnItemSelectedListener(this);
        spinnerType.setOnItemSelectedListener(this);
    }

    private void initImg(int i) {
        if (i == 3) {
            Glide.with(this).asGif().load(urls[i]).into(circle6);
            Glide.with(this).asGif().load(urls[i]).into(ivOriginal);
            return;
        }
        Glide.with(this).load(urls[i]).into(ivOriginal);
        Glide.with(this).load(urls[i]).into(circle6);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        progress = dpToPx(progress);
        switch (seekBar.getId()) {
            case R.id.sbTopLeft:
                circle6.setTopLeftRadius(progress);
                break;
            case R.id.sbBottomLeft:
                circle6.setBottomLeftRadius(progress);
                break;
            case R.id.sbTopRight:
                circle6.setTopRightRadius(progress);
                break;
            case R.id.sbBottomRight:
                circle6.setBottomRightRadius(progress);
                break;
            case R.id.sbBorderWidth:
                circle6.setBorderWidth(progress);
                break;
            case R.id.sbBorderSpace:
                circle6.setBorderSpace(progress);
                break;
            default:
        }
        circle6.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public int dpToPx(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinnerType:
                initImg(position);
                break;
            case R.id.spinnerMode:
                circle6.setStyleType(imgMode[position]);
                circle6.invalidate();
                break;
            default:
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}