package com.solitary.ks.model;

import java.util.ArrayList;

public class TipsList {

        private ArrayList<Tips> tipsArrayList;

        public ArrayList<Tips> getTipsList()
        {
            return tipsArrayList;
        }

        public void setTipsList(ArrayList<Tips> kisses)
        {
            this.tipsArrayList = tipsArrayList;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [KissList = "+ tipsArrayList +"]";
        }



}