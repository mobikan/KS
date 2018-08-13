package com.mobikan.ks.model;

import java.util.ArrayList;

public class PositionsList {

        private ArrayList<Position> Position;

        public ArrayList<Position> getPosition()
        {
            return Position;
        }

        public void setPosition(ArrayList<Position> Position)
        {
            this.Position = Position;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [Position = "+ Position +"]";
        }



}
