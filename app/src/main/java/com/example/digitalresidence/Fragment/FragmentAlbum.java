package com.example.digitalresidence.Fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.digitalresidence.R;
import com.github.chrisbanes.photoview.PhotoViewAttacher;


import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAlbum extends Fragment {
    private Bitmap currentImage;

    Gallery simpleGallery;
    CustomGalleryAdapter customGalleryAdapter;
    ImageView selectedImageView;
    // array of images
    int[] images = {R.drawable.vendor, R.drawable.profiledrawer, R.drawable.guest_survey_svg_thumb, R.drawable.meeting, R.drawable.directory_bg,
            R.drawable.bg1, R.drawable.directory_bg1, R.drawable.directory_card_bg2, R.drawable.directory_card_bg3, R.drawable.ic_event_note_black_24dp,
            R.drawable.ic_notifications_bell_24dp, R.drawable.complain, R.drawable.photo_album};

    public FragmentAlbum() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_album, container, false);
        simpleGallery = (Gallery) view.findViewById(R.id.simpleGallery); // get the reference of Gallery
        selectedImageView = (ImageView) view.findViewById(R.id.selectedImageView); // get the reference of ImageView
        customGalleryAdapter = new CustomGalleryAdapter(getContext(), images); // initialize the adapter
        simpleGallery.setAdapter(customGalleryAdapter); // set the adapter
        simpleGallery.setSpacing(10);
        // perform setOnItemClickListener event on the Gallery
        simpleGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set the selected image in the ImageView
                selectedImageView.setImageResource(images[position]);

            }
        });
        PhotoViewAttacher photoViewAttacher=new PhotoViewAttacher(selectedImageView);
        photoViewAttacher.update();
        Button add_pic_btn = view.findViewById(R.id.add_pic_btn);
        setHasOptionsMenu(true);

        add_pic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });

        return view;

    }


    public class CustomGalleryAdapter extends BaseAdapter {

        private Context context;
        private int[] images;

        public CustomGalleryAdapter(Context c, int[] images) {
            context = c;
            this.images = images;
        }

        // returns the number of images
        public int getCount() {
            return images.length;
        }

        // returns the ID of an item
        public Object getItem(int position) {
            return position;
        }

        // returns the ID of an item
        public long getItemId(int position) {
            return position;
        }

        // returns an ImageView view
        public View getView(int position, View convertView, ViewGroup parent) {

            // create a ImageView programmatically
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(images[position]); // set image in ImageView
            imageView.setLayoutParams(new Gallery.LayoutParams(200, 200)); // set ImageView param
            return imageView;
        }
    }

    //    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        // TODO Add your menu entries here
//        menu.add("add");
//        super.onCreateOptionsMenu(menu, inflater);
//    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri photoUri = data.getData();
            if (photoUri != null) {
                try {
                    currentImage = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), photoUri);
                    selectedImageView.setImageBitmap(currentImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}
}



