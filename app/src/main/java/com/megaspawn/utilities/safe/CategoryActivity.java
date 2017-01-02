package com.megaspawn.utilities.safe;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.megaspawn.utilities.safe.model.Category;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class CategoryActivity extends AppCompatActivity {

    ListView listItemView;
    FloatingActionButton fab;

    Realm realm;

    String key = "F44DC8346B3796D51E497999E0E3F47E01A7807A50F1FAF75BF1A2009817DB14F1A17692C19803DB609ADE0AD1833B1F5AE1B66634C924B045492F5A7E479A3D";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Log.d("VARUN", "Realm ONCREATE");

        realm = Realm.getDefaultInstance();

        List<String> categoryList = new ArrayList<String>();

        RealmResults<Category> results = realm.where(Category.class).findAll();

        for(Category category:results) {
            categoryList.add(category.getId() + " - " + category.getName());
        }

        String[] listArr = categoryList.toArray(new String[categoryList.size()]);

        listItemView = (ListView)findViewById(R.id.category_listview);
        fab = (FloatingActionButton)findViewById(R.id.add_category_btn);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_2, android.R.id.text1, listArr);

        listItemView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openAddCategoryDialog();
            }
        });

    }

    private void openAddCategoryDialog(){
        LayoutInflater inflater = LayoutInflater.from(CategoryActivity.this);
        View subView = inflater.inflate(R.layout.category_dialog, null);
        final EditText subEditText = (EditText)subView.findViewById(R.id.add_category_editText);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add a new Category");
        builder.setMessage("");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                realm.beginTransaction();

                int count = (int) realm.where(Category.class).count();

                Category category = realm.createObject(Category.class);;
                category.setId(count + 1);
                category.setName(subEditText.getText().toString());

                realm.commitTransaction();
                Toast.makeText(CategoryActivity.this, "Category [" + subEditText.getText().toString() + "] added.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CategoryActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }
}
