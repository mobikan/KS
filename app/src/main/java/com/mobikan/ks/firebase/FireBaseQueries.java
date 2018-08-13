package com.mobikan.ks.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.mobikan.ks.model.Like;
import com.mobikan.ks.model.RatingData;

import java.util.HashMap;
import java.util.Map;

public class FireBaseQueries {

    private static FireBaseQueries instance;
    private FirebaseDatabase mDatabase;

    public static final int LIKE_POSITION = 0;
    public static final int LIKE_MASSAGE = 1;
    public static final int LIKE_SPOT = 2;
    public static final int LIKE_KISS = 3;

    private static final String LIKE_POSITION_KEY = "like/positions";
    private static final String LIKE_MASSAGE_KEY = "like/massage";
    private static final String LIKE_KISS_KEY = "like/kisses";
    private static final String  LIKE_SPOT_KEY= "like/spot";


    public static final int RATING_ONE = 1;
    public static final int RATING_TWO = 2;
    public static final int RATING_THREE = 3;
    public static final int RATING_FOUR = 4;
    public static final int RATING_FIVE = 5;

    private FireBaseQueries()
    {
        mDatabase = FirebaseDatabase.getInstance();
        try {
            mDatabase.setPersistenceEnabled(true);
        }
        catch (RuntimeException e)
        {
            e.printStackTrace();
        }

    }

    public static FireBaseQueries getInstance()
    {
        if(instance ==  null)
        {
            instance =  new FireBaseQueries();
        }
     return instance;
    }


    public void addLike(int tableType ,String id)
    {

        DatabaseReference myRef = mDatabase.getReference( getTableRef(tableType));

        Map<String, Object> users = new HashMap<>();
        users.put(id, new Like(id, 0, 0.0f));
        myRef.updateChildren(users);

    }

    public void readAllLikes(int tableType,ValueEventListener eventListener)
    {
        String tableName = getTableRef(tableType);

        DatabaseReference myRef = mDatabase.getReference(tableName);
        myRef.addValueEventListener(eventListener);
    }

    public void readLikeById(int tableType,String id,ValueEventListener eventListener)
    {
        String tableName = getTableRef(tableType)+"/"+id;

        DatabaseReference myRef = mDatabase.getReference(tableName);
        myRef.addValueEventListener(eventListener);
    }


    public String getTableRef(int tableType)
    {
        String tableName = "";
        switch (tableType)
        {
            case LIKE_POSITION :
                tableName = LIKE_POSITION_KEY;
                break;
            case LIKE_MASSAGE :
                tableName = LIKE_MASSAGE_KEY;
                break;
            case LIKE_SPOT :
                tableName = LIKE_SPOT_KEY;
                break;
            case LIKE_KISS :
                tableName = LIKE_KISS_KEY;
                break;

        }
    return tableName;
    }

    public void updateLike(int tableType,String id) {
        DatabaseReference myRef = mDatabase.getReference( getTableRef(tableType) + "/" + id);
        //Like like = new Like(id, 1, 3.5f);

        myRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Like like = mutableData.getValue(Like.class);
                if(like != null) {
                    if (like.getNo_of_like() == 0) {
                        like.setNo_of_like(1);
                        mutableData.setValue(like);
                    } else {
                        like.setNo_of_like(like.getNo_of_like() + 1);
                        mutableData.setValue(like);
                    }
                }

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(
                    DatabaseError databaseError, boolean committed, DataSnapshot dataSnapshot) {
                   System.out.println("Transaction completed "+databaseError);
            }
        });
    }

    public void resetLike(int tableType,String id)
    {
        DatabaseReference myRef = mDatabase.getReference( getTableRef(tableType) + "/" + id);
        //Like like = new Like(id, 1, 3.5f);

        myRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Like like = mutableData.getValue(Like.class);
                if(like != null) {
                    if (like.getNo_of_like() >= 0) {
                        like.setNo_of_like(like.getNo_of_like() - 1);
                        mutableData.setValue(like);
                    }
                }

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(
                    DatabaseError databaseError, boolean committed, DataSnapshot dataSnapshot) {
                System.out.println("Transaction completed "+databaseError);
            }
        });
    }


    public void updateRating(int tableType, String id, final int rating)
    {
        DatabaseReference myRef = mDatabase.getReference( getTableRef(tableType) + "/" + id);
        myRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Like like = mutableData.getValue(Like.class);
                if(like != null) {
                     like.setRating_data(getRatingData(like.getRating_data(),rating));
                     like = calculateRating(like);
                     mutableData.setValue(like);
                }

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(
                    DatabaseError databaseError, boolean committed, DataSnapshot dataSnapshot) {
                System.out.println("Transaction completed "+databaseError);
            }
        });
    }





    public RatingData getRatingData(RatingData ratingData,int rating)
    {
        if(ratingData == null)
        {
            ratingData =  new RatingData();
        }
        switch (rating)
        {
            case RATING_ONE:
                if(ratingData.getOne() == 0)
                {
                    ratingData.setOne(1);
                }
                else {
                    ratingData.setOne(ratingData.getOne()+1);
                }
                break;
            case RATING_TWO:
                if(ratingData.getTwo() == 0)
                {
                    ratingData.setTwo(1);
                }
                else {
                    ratingData.setTwo(ratingData.getTwo()+1);
                }
                break;
            case RATING_THREE:
                if(ratingData.getThree() == 0)
                {
                    ratingData.setThree(1);
                }
                else {
                    ratingData.setThree(ratingData.getThree()+1);
                }
                break;
            case RATING_FOUR:
                if(ratingData.getFour() == 0)
                {
                    ratingData.setFour(1);
                }
                else {
                    ratingData.setFour(ratingData.getFour()+1);
                }
                break;
            case RATING_FIVE:
                if(ratingData.getFive() == 0)
                {
                    ratingData.setFive(1);
                }
                else {
                    ratingData.setFive(ratingData.getFive()+1);
                }
                break;
        }


    return ratingData;
    }

    private Like calculateRating(Like like)
    {
        float rating = (like.getRating_data().getFive()*RATING_FIVE + like.getRating_data().getFour()*RATING_FOUR+like.getRating_data().getThree()*RATING_THREE
                +like.getRating_data().getTwo()*RATING_TWO+like.getRating_data().getOne()*RATING_ONE)
                / (like.getRating_data().getFive()+like.getRating_data().getFour() +like.getRating_data().getThree() +like.getRating_data().getTwo()+like.getRating_data().getOne());

        like.setRating(rating);
    return like;
    }

}
