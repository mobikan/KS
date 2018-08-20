package com.solitary.ks.model;

import java.util.ArrayList;

public class MassageList {

        private ArrayList<Massage> massageList;

        public ArrayList<Massage> getMassageList()
        {
            return massageList;
        }

        public void setMassageList(ArrayList<Massage> kisses)
        {
            this.massageList = kisses;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [KissList = "+ massageList +"]";
        }



}
