package com.mame.impression.action;

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
     *
     * @return
     */
    public List<Accessor> getAccessors();

    /**
     * Get parameter as JSONObject format. Throw IllegalFormatException if given format is not expected.
     *
     * @return
     */
    public JSONObject getParemeter() throws IllegalArgumentException, IllegalFormatException, JSONException;

}
