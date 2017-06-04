package uk.co.aminlive.kamil.databaseapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName, editSurname, editMarks, editID;
    Button btnAddData;
    Button btnView;
    Button btnUpdate;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        editName = (EditText) findViewById(R.id.editText_name);
        editSurname = (EditText) findViewById(R.id.editText_surname);
        editMarks = (EditText) findViewById(R.id.editText_marks);
        editID = (EditText) findViewById(R.id.editText5);
        btnAddData = (Button) findViewById(R.id.button);
        btnView = (Button) findViewById(R.id.button2);
        btnUpdate = (Button) findViewById(R.id.button3);
        btnDelete = (Button) findViewById(R.id.button4);

        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }

    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted =  myDb.insertData(editName.getText().toString(),
                                editSurname.getText().toString(),
                                editMarks.getText().toString() );
                        if(isInserted == true)
                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data is not Inserted", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
    public void viewAll(){
        btnView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res =  myDb.getAllData();
                        if(res.getCount() == 0) {
                            // Show message
                            showMessage("Error", "Nothing Found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("id :" + res.getString(0) + "\n");
                            buffer.append("Name :" + res.getString(1) + "\n");
                            buffer.append("Surname :" + res.getString(2) + "\n");
                            buffer.append("Marks :" + res.getString(3) + "\n\n");
                        }

                        //show all data
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }
    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    public void UpdateData(){
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(editID.getText().toString(), editName.getText().toString(),
                                editSurname.getText().toString(), editMarks.getText().toString());
                        if(isUpdate == true)
                            Toast.makeText(MainActivity.this, "Data is Updated", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Data is not Updated", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
    public void DeleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editID.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Data is Not Deleted", Toast.LENGTH_SHORT).show();
                     }
                }
        );
    }
}
