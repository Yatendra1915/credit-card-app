package com.example.creditcard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.basgeekball.awesomevalidation.utility.custom.CustomValidation;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    TextInputLayout fname,lname,expiry,creditcard,cvv;
    Button submit;
    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fname=findViewById(R.id.first_name);
        lname=findViewById(R.id.last_name);
        cvv=findViewById(R.id.securitycode);
        creditcard=findViewById(R.id.credit_card);
        expiry=findViewById(R.id.date_month);
        submit=findViewById(R.id.submit);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this,R.id.first_name,"[a-zA-Z\\s']+",R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.last_name,"[a-zA-Z\\s']+",R.string.invalid_lastName);
        awesomeValidation.addValidation(this,R.id.date_month,"(?:0[1-9]|1[0-2])/[0-9]{2}",R.string.invalidExpiry);
        awesomeValidation.addValidation(this, R.id.credit_card, String.valueOf(new CustomValidation() {
            @Override
            public boolean compare(ValidationHolder validationHolder) {
                if(checkLuhn(String.valueOf(creditcard)))
                    return true;
                return false;
            }
        }),R.string.EnterValidCard);

        awesomeValidation.addValidation(this,R.id.securitycode,"^[0-9]{3,4}$",R.string.invalidCVV);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate()){
                    Toast.makeText(getApplicationContext(),"Payment Successful",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Validation Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkLuhn(String cardNo)
    {
        int nDigits = cardNo.length();

        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--)
        {

            int d = cardNo.charAt(i) - '0';

            if (isSecond == true)
                d = d * 2;

            // We add two digits to handle
            // cases that make two digits
            // after doubling
            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }
}