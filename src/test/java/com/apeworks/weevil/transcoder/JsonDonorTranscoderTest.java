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

import com.apeworks.weevil.domain.Donor;
import com.apeworks.weevil.exception.EncodingException;

public class JsonDonorTranscoderTest
{

    private JsonDonorTranscoder transcoder;

    @Before
    public void setup()
    {
        transcoder = new JsonDonorTranscoder();
    }

    @Test
    public void testEncode()
    {
        Donor donor = new Donor();
        donor.setId(111l);
        donor.setName("name");
        donor.setEmail("email1");
        String json = transcoder.encode(donor);
        assertEquals("{\"email\":\"email1\",\"id\":111,\"name\":\"name\",\"pin\":\"\",\"totalCollected\":0,\"totalUncollected\":0}", json);
    }

    @Test
    public void testDecode() throws Exception
    {
        Donor donor = transcoder.decode("{\"name\":\"name\"}");
        Donor expected = new Donor();
        expected.setName("name");
        assertEquals(expected, donor);
    }

    @Test(expected = EncodingException.class)
    public void testDecodeMumboJumbo() throws EncodingException
    {
        transcoder.decode("mumboJumbo");
    }
}
