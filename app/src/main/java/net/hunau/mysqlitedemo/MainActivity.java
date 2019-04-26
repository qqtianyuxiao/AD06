package net.hunau.mysqlitedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.RadioButton;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    final static String TAG = "系统调试";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String msg;
        long id;
        final EditText nameText = (EditText) findViewById(R.id.name);
        final EditText pwdText = (EditText) findViewById(R.id.pwd);
        final EditText idEntry = (EditText) findViewById(R.id.id_entry);

        final TextView labelView = (TextView) findViewById(R.id.label);
        final TextView displayView = (TextView) findViewById(R.id.display);

        final RadioButton rb1 = (RadioButton) findViewById(R.id.RadioButton01);
        final RadioButton rb2 = (RadioButton) findViewById(R.id.RadioButton02);

        final RadioButton rb3 = (RadioButton) findViewById(R.id.RadioButton03);
        final RadioButton rb4 = (RadioButton) findViewById(R.id.RadioButton04);

        Button addButton = (Button) findViewById(R.id.add);
        Button queryAllButton = (Button) findViewById(R.id.query_all);
        Button clearButton = (Button) findViewById(R.id.clear);
        Button deleteAllButton = (Button) findViewById(R.id.delete_all);

        Button queryButton = (Button) findViewById(R.id.query);
        Button deleteButton = (Button) findViewById(R.id.delete);
        Button updateButton = (Button) findViewById(R.id.update);

        final DBAdapter dbAdepter = new DBAdapter(this);
        dbAdepter.open();


        Button.OnClickListener buttonListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.add:
                        User user = new User();
                        user.setName(nameText.getText().toString());
                        user.setPwd(pwdText.getText().toString());
                        if (rb1.isChecked()) {
                            user.setSexy(rb1.getText().toString());
                        } else {
                            user.setSexy(rb2.getText().toString());
                        }
                        if (rb3.isChecked()) {
                            user.setIsused(true);
                        } else {
                            user.setIsused(false);
                        }
                        long colunm = dbAdepter.insert(user);
                        if (colunm == -1) {
                            labelView.setText("添加过程错误！");
                        } else {
                            labelView.setText("成功添加数据，ID：" + String.valueOf(colunm));
                        }
                        return;
                    case R.id.query_all:
                        User[] users = dbAdepter.queryAllData();
                        if (users == null) {
                            labelView.setText("数据库中没有数据");
                            return;
                        }
                        labelView.setText("数据库：");
                        String msg = "";
                        for (int i = 0; i < users.length; i++) {
                            msg += users[i].toString() + "\n";
                        }
                        displayView.setText(msg);
                        return;
                    case R.id.clear:
                        displayView.setText("");
                        return;
                    case R.id.delete_all:
                        dbAdepter.deleteAllData();
                        msg = "数据全部删除";
                        labelView.setText(msg);
                        return;
                    case R.id.query:
                        int id = Integer.parseInt(idEntry.getText().toString());
                        User[] users1 = dbAdepter.queryOneData(id);
                        if (users1 == null) {
                            labelView.setText("数据库中没有ID为" + String.valueOf(id) + "的数据");
                            return;
                        }
                        labelView.setText("数据库：");
                        displayView.setText(users1[0].toString());
                        return;
                    case R.id.delete:
                        id = Integer.parseInt(idEntry.getText().toString());
                        long result = dbAdepter.deleteOneData(id);
                        msg = "删除ID为" + idEntry.getText().toString() + "的数据" + (result > 0 ? "成功" : "失败");
                        labelView.setText(msg);
                        return;
                    case R.id.update:
                        User user1 = new User();
                        user1.setName(nameText.getText().toString());
                        user1.setPwd(pwdText.getText().toString());
                        if (rb1.isChecked()) {
                            user1.setSexy(rb1.getText().toString());
                        } else {
                            user1.setSexy(rb2.getText().toString());
                        }
                        if (rb3.isChecked()) {
                            user1.setIsused(true);
                        } else {
                            user1.setIsused(false);
                        }
                        id = Integer.parseInt(idEntry.getText().toString());
                        long count = dbAdepter.updateOneData(id, user1);
                        if (count == -1) {
                            labelView.setText("更新错误！");
                        } else {
                            labelView.setText("更新成功，更新数据" + String.valueOf(count) + "条");
                        }
                        return;
                }
            }};
        addButton.setOnClickListener(buttonListener);
        queryAllButton.setOnClickListener(buttonListener);
        clearButton.setOnClickListener(buttonListener);
        deleteAllButton.setOnClickListener(buttonListener);
        queryButton.setOnClickListener(buttonListener);
        deleteButton.setOnClickListener(buttonListener);
        updateButton.setOnClickListener(buttonListener);
        }
}
