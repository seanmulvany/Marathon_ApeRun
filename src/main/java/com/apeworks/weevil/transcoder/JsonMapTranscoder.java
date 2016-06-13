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

import org.springframework.stereotype.Component;

import com.apeworks.weevil.domain.Donation;
import com.apeworks.weevil.domain.Map;
import com.apeworks.weevil.domain.Runner;

@Component(value = "mapTranscoder")
public class JsonMapTranscoder extends AbstractJsonTranscoder<Map>
{
    public JsonMapTranscoder()
    {
        super(Map.class);
    }

    @Override
    public String encode(Map map)
    {
        return super.encode(minimise(map));
    }

    private Map minimise(Map map)
    {
        for (Donation donation : map.getDonations())
            donation.setUpdated(null);

        for (Runner runner : map.getRunners())
            runner.setPin(null);

        return map;
    }
}
