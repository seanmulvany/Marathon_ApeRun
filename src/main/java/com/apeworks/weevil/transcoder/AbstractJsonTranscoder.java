/*******************************************************************************
 * Copyright (C) 2012 apeworks
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 * 
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.apeworks.weevil.transcoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import com.apeworks.weevil.exception.EncodingException;

public class AbstractJsonTranscoder<T> implements Transcoder<T>
{

    protected JsonConfig jsonConfig;

    public AbstractJsonTranscoder(Class<T> clazz)
    {
        jsonConfig = new JsonConfig();
        jsonConfig.setRootClass(clazz);
    }

    public void encode(T object, OutputStream outputStream) throws EncodingException
    {
        try
        {
            outputStream.write(encode(object).toString().getBytes());
        }
        catch (IOException e)
        {
            throw new EncodingException("Failed to encode JSON", e);
        }
    }

    public String encode(T object)
    {
        JSONObject jsonObject = JSONObject.fromObject(object);
        return jsonObject.toString();
    }

    public T decode(InputStream inputStream) throws EncodingException
    {
        try
        {
            return decode(new BufferedReader(new InputStreamReader(inputStream)).readLine());
        }
        catch (IOException e)
        {
            throw new EncodingException("Failed to decode JSON", e);
        }
    }

    public T decode(String json) throws EncodingException
    {
        try
        {
            JSONObject object = JSONObject.fromObject(json);
            return (T) JSONSerializer.toJava(object, jsonConfig);
        }
        catch (JSONException e)
        {
            throw new EncodingException("Failed to decode JSON", e);
        }
    }

}
