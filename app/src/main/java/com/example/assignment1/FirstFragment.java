package com.example.assignment1;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.assignment1.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;


    //I declared all the view objects outside methods so they can be public
    EditText principal, interest, amortization, payment_frequency;
    Spinner spinner_amortization;
    ArrayAdapter<CharSequence> dropDownArray;

    //This is my method to calculate the PMT or the monthly payment for the mortgage information entered
    public double onCalculate(double principal, double interest, double amortization) {
        double i = interest/100/12; //First, I convert the user input to a decimal point.
        double months = amortization*12; //Then, I convert the amortization from years to months
        //This is the overall calculation, I used the math pow function to do the powers.
        double payment = (principal*i*(Math.pow(1+i,months))/(Math.pow(1+i,months)-1));

        return payment;
    }


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Here, I attached the EditText objects I created earlier to their respective view
        principal = (EditText)view.findViewById(R.id.edittext_principal_input);
        interest = (EditText)view.findViewById(R.id.edittext_interest_input);
        //amortization = (EditText)view.findViewById(R.id.edittext_amortization_input); This is when my amortization was still a user input edittext
        spinner_amortization = (Spinner)view.findViewById(R.id.spinner_amortization);


        //Setting up the dropdownmenu options
        //First, I made a spinner view in the first fragment xml file, setting its mode to drop down
        //I then created a string array in the strings xml to populate the options in the menu.
        //Then, an array adapter is created in the fragment.java class to create the graphics of the drop-down menu and fill it out.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.dropdownmenu, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner_amortization.setAdapter(adapter);

        binding.buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Here, I grab the values from each view edited by the user.
                Editable i = principal.getText();
                Editable j = interest.getText();
                //Editable k = amortization.getText(); This is when my amortization was still a user input edittext
                String k = spinner_amortization.getSelectedItem().toString(); //This one is special since its grabbing an item from a list

                //This is just parsing the information so that it can be sent to my calculate method
                double i2 = Double.parseDouble(i.toString());
                double j2 = Double.parseDouble(j.toString());
                double k2 = Double.parseDouble(k);

                double i3 = onCalculate(i2,j2,k2); //Calling to calculate given the three values

                //Here I am declaring a new bundle so that I can send information to other fragments using the navcontroller
                Bundle bundle = new Bundle();
                bundle.putDouble("payment", i3);
                bundle.putDouble("test", k2);
                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);

                //NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        /*binding.spinnerAmortization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        // I added some functionality
        binding.hintButtonInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast myToast = Toast.makeText(getActivity(), getResources().getString(R.string.hint_interest), 1);
                myToast.show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}