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

import com.apeworks.weevil.domain.Donation;
import com.apeworks.weevil.domain.Location;
import com.apeworks.weevil.exception.EncodingException;

public class JsonDonationTranscoderTest
{
    private JsonDonationTranscoder transcoder;

    @Before
    public void setUp() throws Exception
    {
        transcoder = new JsonDonationTranscoder();
    }

    @Test
    public void testEncodeDonation()
    {
        Donation donation = new Donation(123, new Location(1, 2));
        donation.setDonorId(111);
        String donationString = transcoder.encode(donation);
        assertEquals("{\"amount\":123,\"collectedBy\":\"\",\"donorId\":111,\"id\":0,\"location\":{\"x\":1,\"y\":2},\"updated\":null}", donationString);
    }

    @Test
    public void testDecodeString() throws EncodingException
    {
        Donation donation = transcoder.decode("{\"amount\":123,\"location\":{\"x\":1,\"y\":2}}");
        assertEquals(new Donation(123, new Location(1, 2)), donation);
    }

    @Test(expected = EncodingException.class)
    public void testDecodeMumboJumbo() throws EncodingException
    {
        transcoder.decode("mumboJumbo");
    }
}
