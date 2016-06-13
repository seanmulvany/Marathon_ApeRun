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

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.apeworks.weevil.domain.Donation;
import com.apeworks.weevil.domain.Location;
import com.apeworks.weevil.domain.Map;
import com.apeworks.weevil.domain.Runner;
import com.apeworks.weevil.exception.EncodingException;

public class JsonMapTranscoderTest
{

    private JsonMapTranscoder transcoder;

    @Before
    public void setup()
    {
        transcoder = new JsonMapTranscoder();
    }

    @Test
    public void testEncode()
    {
        Map map = new Map();
        Runner runner = new Runner("id1", new Location(1, 2), 1, 2, 3, 4);
        map.setRunners(new Runner[] { runner });
        map.setDonations(new Donation[] { createDonation() });

        String json = transcoder.encode(map);
        assertEquals("{\"donations\":[{\"amount\":1,\"collectedBy\":\"collectedBy\",\"donorId\":111,\"id\":123,\"location\":{\"x\":4,\"y\":5},\"updated\":null}],\"lastModified\":0,\"runners\":[{\"id\":\"id1\",\"location\":{\"x\":1,\"y\":2},\"pin\":\"\",\"total\":0}]}", json);
    }

    private Donation createDonation()
    {
        Donation donation = new Donation();
        donation.setAmount(1);
        donation.setCollectedBy("collectedBy");
        donation.setDonorId(111);
        donation.setId(new Long(123));
        donation.setLocation(new Location(4, 5));
        donation.setUpdated(new Date());
        return donation;
    }

    @Test
    public void testDecode() throws Exception
    {
        Map map = transcoder.decode("{\"runners\":[{\"id\":\"id1\",\"location\":{\"x\":1,\"y\":2}}]}");
        Runner[] runners = map.getRunners();
        assertEquals(1, runners.length);
        assertEquals(new Runner("id1", new Location(1, 2), 1, 2, 3, 4), runners[0]);
    }

    @Test(expected = EncodingException.class)
    public void testDecodeMumboJumbo() throws EncodingException
    {
        transcoder.decode("mumboJumbo");
    }
}
