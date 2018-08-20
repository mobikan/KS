package com.solitary.ks.model;

import java.util.ArrayList;

public class KissList {

        private ArrayList<Kiss> kisses;

        public ArrayList<Kiss> getKisses()
        {
            return kisses;
        }

        public void setKisses(ArrayList<Kiss> kisses)
        {
            this.kisses = kisses;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [KissList = "+ kisses +"]";
        }



}
