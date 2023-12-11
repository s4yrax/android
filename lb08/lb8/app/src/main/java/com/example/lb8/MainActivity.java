package com.example.lb8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private Cursor cursor;
    private SQLiteDatabase db;
    Context context = this;

    SQLiteOpenHelper Mydb = new MyDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText viewNumb1 = findViewById(R.id.viewNumb1);
        EditText viewTxt1 = findViewById(R.id.viewTxt1);
        Button btnAdd = findViewById(R.id.addbtn);
        Button randbtn = findViewById(R.id.randbtn);
        Button deletebtn = findViewById(R.id.deletebtn);
        ListView listView = findViewById(R.id.listView);
            // Генерация случайного числа
            randbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Random random = new Random();
                    int randomNumber = random.nextInt(100);
                    viewNumb1.setText(String.valueOf(randomNumber));
                    viewTxt1.setText(generateRandomString(10));
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (cursor != null && cursor.moveToPosition(position)) {
                        int numberColumnIndex = cursor.getColumnIndex("RAND_NUMB");
                        int textColumnIndex = cursor.getColumnIndex("RAND_TXT");
                        int number = cursor.getInt(numberColumnIndex);
                        String text = cursor.getString(textColumnIndex);
                        viewNumb1.setText(String.valueOf(number));
                        viewTxt1.setText(text);
                    }
                }
            });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String randNum = viewNumb1.getText().toString();
                    String randTxt = viewTxt1.getText().toString();

                    SQLiteDatabase db = Mydb.getWritableDatabase();

                    MyDatabaseHelper.insData(db, Integer.valueOf(randNum), randTxt);

                    cursor = db.query("ZAMETKI", new String[]{"_id", "RAND_NUMB", "RAND_TXT"}, null, null, null, null, null);
                    SimpleCursorAdapter adapter = new SimpleCursorAdapter(context, android.R.layout.simple_list_item_2, cursor,
                            new String[]{"RAND_NUMB", "RAND_TXT"}, new int[]{android.R.id.text1, android.R.id.text2}, 0);
                    listView.setAdapter(adapter);

                    db.close();

                    viewNumb1.setText("");
                    viewTxt1.setText("");

                    Toast.makeText(context, "Запись успешно добавлена", Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    Toast.makeText(context, "Ошибка при добавлении записи", Toast.LENGTH_LONG).show();
                }
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int selectedPosition = listView.getCheckedItemPosition();
                    if (selectedPosition != AdapterView.INVALID_POSITION) {
                        cursor.moveToPosition(selectedPosition);
                        int idColumnIndex = cursor.getColumnIndex("_id");
                        long id = cursor.getLong(idColumnIndex);

                        SQLiteDatabase db = Mydb.getWritableDatabase();

                        db.delete("ZAMETKI", "_id=?", new String[]{String.valueOf(id)});

                        cursor = db.query("ZAMETKI", new String[]{"_id", "RAND_NUMB", "RAND_TXT"}, null, null, null, null, null);
                        SimpleCursorAdapter adapter = new SimpleCursorAdapter(context, android.R.layout.simple_list_item_2, cursor,
                                new String[]{"RAND_NUMB", "RAND_TXT"}, new int[]{android.R.id.text1, android.R.id.text2}, 0);
                        listView.setAdapter(adapter);

                        db.close();

                        viewNumb1.setText("");
                        viewTxt1.setText("");

                        Toast.makeText(context, "Запись успешно удалена", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Выберите запись для удаления", Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLException e) {
                    Toast.makeText(context, "Ошибка при удалении записи", Toast.LENGTH_LONG).show();
                }
            }
        });
        }

        // Метод для генерации случайной строки заданной длины
        private String generateRandomString(int length) {
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            StringBuilder randomString = new StringBuilder(length);
            Random random = new Random();
            for (int i = 0; i < length; i++) {
                char randomChar = characters.charAt(random.nextInt(characters.length()));
                randomString.append(randomChar);
            }
            return randomString.toString();
        }
    }
