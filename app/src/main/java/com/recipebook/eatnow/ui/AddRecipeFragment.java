package com.recipebook.eatnow.ui;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.recipebook.eatnow.MainActivity;
import com.recipebook.eatnow.R;
import com.recipebook.eatnow.db.Recipe;
import com.recipebook.eatnow.db.RecipeViewModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddRecipeFragment extends android.support.v4.app.Fragment {

    private EditText rTitle, rServing, rPrepTime, rCookingTime, rIngredients,
            rInstrucitons;
    private Spinner rCategory;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private ImageView rImage;
    private String userChoosenTask;
    private Button saveButton;
    private RecipeViewModel recipeViewModel;
    private String recipeImagePath;


    public AddRecipeFragment() {
        //constructor
    }


    //create view (layout stuff)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);

        rTitle = view.findViewById(R.id.editRecipeTitle);
        rCategory = view.findViewById(R.id.recipeCategory);
        rServing = view.findViewById(R.id.editServing);
        rPrepTime = view.findViewById(R.id.editPrepTime);
        rCookingTime = view.findViewById(R.id.editCookingTime);
        rIngredients = view.findViewById(R.id.editIngredients);
        rInstrucitons = view.findViewById(R.id.editInstructions);
        rImage = view.findViewById(R.id.editImage);
        saveButton = view.findViewById(R.id.saveButton);

        rImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(v);
            }
        });

        //getting database items ready
        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        saveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String title = rTitle.getText().toString();
                String category = rCategory.getSelectedItem().toString();
                String serving = rServing.getText().toString();
                String prepTime = rPrepTime.getText().toString();
                String cookingTime = rCookingTime.getText().toString() ;
                String ingredient = rIngredients.getText().toString();
                String instruction = rInstrucitons.getText().toString() ;

                Recipe r = new Recipe();
                r.setRecipeTitle(title);
                r.setCategory(category);
                r.setServing(serving);
                r.setPrepTime(prepTime);
                r.setCookingTime(cookingTime);
                r.setIngredients(ingredient);
                r.setInstructions(instruction);
                r.setImagePath(recipeImagePath);

                //add recipe to database
                recipeViewModel.insertRecipe(r);
                Toast.makeText(getActivity(), "Recipe added successfully", Toast.LENGTH_LONG).show();

                //pop teh back stack
                MainActivity.fragmentManager.popBackStack();
                MainActivity.fragmentManager.getPrimaryNavigationFragment();
            }
        });
        return view;
    }

    //capture image
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,
                            new HomeFragment()).addToBackStack(null).commit();
                }
                break;
        }
    }

    //select image
    public void selectImage(View view) {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(getActivity());

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    //select image from gallery
    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    //take the photo
    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);


            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String fileName = System.currentTimeMillis() + ".jpg";

        try {
            FileOutputStream fileOutput = getContext().openFileOutput(fileName, 0);
            fileOutput.write(bytes.toByteArray());
            fileOutput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        rImage.setImageBitmap(thumbnail);
        this.recipeImagePath = fileName;
    }

    private void onSelectFromGalleryResult(Intent data) {

        String fileName = System.currentTimeMillis() + ".jpg";

        Bitmap bm=null;
        if (data != null) {
        try {
                ContentResolver contentResolver =  getContext().getContentResolver();
                bm = MediaStore.Images.Media.getBitmap(contentResolver, data.getData());
                byte[] buffer = new byte[8 * 1024];
                int bytesRead;
                InputStream inputStream = contentResolver.openInputStream(data.getData());
                FileOutputStream outStream = getContext().openFileOutput(fileName, 0);
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
                outStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        rImage.setImageBitmap(bm);
        this.recipeImagePath = fileName;
    }

}
