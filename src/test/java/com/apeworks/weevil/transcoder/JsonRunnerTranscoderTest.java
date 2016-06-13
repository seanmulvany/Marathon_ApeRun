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

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.apeworks.weevil.domain.Location;
import com.apeworks.weevil.domain.Runner;
import com.apeworks.weevil.exception.EncodingException;

public class JsonRunnerTranscoderTest
{

    private JsonRunnerTranscoder transcoder;

    @Before
    public void setup()
    {
        transcoder = new JsonRunnerTranscoder();
    }

    @Test
    public void testEncode()
    {
        Runner runner = new Runner("id1", new Location(1, 2), 1, 2, 3, 4);
        String json = transcoder.encode(runner);
        assertEquals("{\"id\":\"id1\",\"location\":{\"x\":1,\"y\":2},\"pin\":\"\",\"total\":0}", json);
    }

    @Test
    public void testDecode() throws Exception
    {
        // {"id":"id1","location":{"x":1,"y":2}}
        Runner runner = transcoder.decode("{\"id\":\"id1\",\"location\":{\"x\":1,\"y\":2}}");
        assertEquals(new Runner("id1", new Location(1, 2), 1, 2, 3, 4), runner);
    }

    @Test(expected = EncodingException.class)
    public void testDecodeMumboJumbo() throws EncodingException
    {
        transcoder.decode("mumboJumbo");
    }
}
