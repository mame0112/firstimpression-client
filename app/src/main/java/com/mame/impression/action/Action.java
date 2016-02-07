package com.mame.impression.action;

import android.util.SparseArray;

import com.mame.impression.constant.RequestAction;
import com.mame.impression.manager.Accessor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.IllegalFormatException;
import java.util.List;

/**
 * Define Action, Accessor(s) and parameters for each operation.
 * Created by kosukeEndo on 2015/12/27.
 */
public interface Action {


    public RequestAction getAction();

    /**
     * Get accessor as List format.
     * Key id (int) is order for accessors.
     *
     * @return
     */
    public SparseArray<Accessor> getAccessors();

    /**
     * Get parameter as JSONObject or JSONArray format. Throw IllegalFormatException if given format is not expected.
     *
     * @return
     */
    public String getParemeter() throws IllegalArgumentException, JSONException;

}
