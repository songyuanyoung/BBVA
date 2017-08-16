package com.example.zach.bbva;

import java.util.ArrayList;

/**
 * Created by zhangwenpurdue on 8/15/2017.
 */


public class ResultList extends ArrayList<ResultsItem> {
    private static ResultList mInstance = null;
    public synchronized static ResultList getmInstance() {
        if (mInstance == null) {
            synchronized (ResultList.class) {
                if (mInstance == null) {
                    mInstance = new ResultList();
                }
            }
        }
        return mInstance;
    }
    private ResultList() {

    }
    private void test() {

    }
}