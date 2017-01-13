package com.megaspawn.utilities.safe;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.megaspawn.utilities.safe.model.*;
import com.megaspawn.utilities.safe.util.*;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class CategoryActivity extends AppCompatActivity {

    ListView listItemView;
    FloatingActionButton fab;
    List<SafeCategory> categoryList = new ArrayList<>();
    int parentId = 0; // Initialize parentId to 0 (top level parents)

    //RealmResults<SafeCategory> results;
    CustomArrayAdapter adapter;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Get all views
        listItemView = (ListView)findViewById(R.id.category_listview);
        fab = (FloatingActionButton)findViewById(R.id.add_category_btn);
        realm = Realm.getDefaultInstance();

        categoryList.addAll(realm.where(SafeCategory.class).equalTo("parentId", parentId).findAll());

        adapter = new CustomArrayAdapter(this, categoryList);
        listItemView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddCategoryDialog();
            }
        });

        listItemView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CategoryActivity.this, "Short clicked.", Toast.LENGTH_SHORT).show();
                SafeCategory category = (SafeCategory) parent.getAdapter().getItem(position);
                parentId = category.getId();
                categoryList.clear();
                categoryList.addAll(realm.where(SafeCategory.class).equalTo("parentId", parentId).findAll());
                for(SafeCategory safeCategory:categoryList) {
                    Log.d("VARUN", "Parent: " + parentId + " Category: " + safeCategory.getName());
                }
                adapter.notifyDataSetChanged();
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

                int count = (int) realm.where(SafeCategory.class).count();

                SafeCategory category = realm.createObject(SafeCategory.class);
                category.setId(count + 1);
                category.setName(subEditText.getText().toString());
                category.setParentId(parentId);

                realm.commitTransaction();

                categoryList.clear();
                categoryList.addAll(realm.where(SafeCategory.class).equalTo("parentId", parentId).findAll());
                for(SafeCategory safeCategory:categoryList) {
                    Log.d("VARUN", "Parent: " + parentId + " Category: " + safeCategory.getName());
                }
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CategoryActivity.this, "Canceled " + parentId, Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }
}
