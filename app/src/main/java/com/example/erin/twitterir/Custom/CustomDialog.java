package com.example.erin.twitterir.Custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.erin.twitterir.R;

import org.w3c.dom.Text;

/**
 * Created by Erin on 2015-12-02.
 */
public class CustomDialog extends Dialog implements View.OnClickListener {

    Button dismissBtn;

    TextView textView;
    TextView nickView;
    TextView idView;
    TextView dateView;
    ImageView imageView;


    public CustomDialog(Context context, String text, String id, String name, String date, Drawable d) {

        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);

        dismissBtn = (Button)findViewById(R.id.dismiss_button);
        textView = (TextView)findViewById(R.id.list_text);
        nickView = (TextView)findViewById(R.id.list_nick);
        dateView = (TextView)findViewById(R.id.list_date);
        idView = (TextView)findViewById(R.id.list_id);
        imageView = (ImageView)findViewById(R.id.list_image);

        textView.setText(text);
        nickView.setText(name);
        idView.setText("@" + id);
        dateView.setText(date);
        imageView.setImageDrawable(d);

        dismissBtn.setOnClickListener(this);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onClick(View v) {

        if (v == dismissBtn)
        {
            dismiss();
        }
    }
}
