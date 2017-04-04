package info.androidhive.firebase;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class BasicInfoFragment extends Fragment implements View.OnClickListener {

    RadioGroup radioBgGroup;
    RadioButton radioBgButton;

    RadioGroup radioRhGroup;
    RadioButton radioRhButton;

    Button b;
    EditText name;
    EditText email;
    EditText mobile;
    EditText LastDonation;
    String id;

    public BasicInfoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_info, container, false);
        getActivity().setTitle(R.string.edit_info_title);
        initialize(view);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    void initialize(View view) {
        //VIEW OLD DATA AND ADD LISTENERS
        name = (EditText) view.findViewById(R.id.fb_name);

        email = (EditText) view.findViewById(R.id.fb_email);
        mobile = (EditText) view.findViewById(R.id.fb_mobile);

        //TAKE DATA FROM SERVER
        //name.setText("");
        //email.setText();
        //mob.setText();
        //LastDonation.setText();

        b = (Button) view.findViewById(R.id.save_edit);
        b.setOnClickListener(this);
        //get Radio Groups


    }

    private void Update( final String name, final String email,  final String mobile) {
      //  Log.d("id", id);
        Log.d("name", name);
        Log.d("email", email);

        Log.d("Mobile", mobile);
        Toast.makeText(getActivity(), "UPDATED", Toast.LENGTH_SHORT).show();

    }


    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        LastDonation.setText(sdf.format(myCalendar.getTime()));
    }

    public void saveTo() {

        boolean valid, exist, complete;

        //CHECK FOR EMAIL
     /*   String emailValid = email.getText().toString().trim();

        exist = fM.checkExistEmail(emailValid);*/



        /*DATA:
        name.getText().toString()
        email.getText().toString()
        mob.getText().toString()
        LastDonation.getText().toString()
        radioGenderButton.getText().toString()
        radioBgButton.getText().toString()
        radioRhButton.getText().toString()
        */

        //Check for empty fields
        if (name.getText().toString().trim().equals("") ||
                email.getText().toString().trim().equals("") ||
                mobile.getText().toString().trim().equals("") ||
                LastDonation.getText().toString().trim().equals("") ||
                LastDonation.getText().toString().trim().equals("")) {
            complete = false;
        } else {
            complete = true;
        }


        /*else if(!exist){
            Toast.makeText(getActivity(),"This email has already signed up,please provide another email",Toast.LENGTH_LONG).show();
        }*/
         if (!complete) {
            Toast.makeText(getActivity(), R.string.info_cannot_empty, Toast.LENGTH_LONG).show();
        } else {
            Send();


        }

    }


    @Override
    public void onResume(){
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    Fragment fragment = null;
                    fragment = new MainFragment();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.commit();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == LastDonation) {
            new DatePickerDialog(getActivity(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        } else {
            saveTo();
        }

    }

    public void Send() {
        // View view=
        Update( name.getText().toString().trim(), email.getText().toString().trim(), mobile.getText().toString());

    }
}
