package com.chinmay.chaitanyabakingapp.Interface;

import com.chinmay.chaitanyabakingapp.Model.Step;

import java.util.ArrayList;

public interface FragmentOneListener {
    void setStep(int index, ArrayList<Step> steps);
    void setCurrent(int index);
}
