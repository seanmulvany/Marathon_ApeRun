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

import java.io.InputStream;

import javax.servlet.ServletOutputStream;

import com.apeworks.weevil.domain.Runner;
import com.apeworks.weevil.exception.EncodingException;

public interface RunnerTranscoder
{
    public String encode(Runner runner);

    public void encode(Runner runner, ServletOutputStream outputStream) throws EncodingException;

    public Runner decode(String runnerString) throws EncodingException;

    public Runner decode(InputStream inputStream) throws EncodingException;
}
