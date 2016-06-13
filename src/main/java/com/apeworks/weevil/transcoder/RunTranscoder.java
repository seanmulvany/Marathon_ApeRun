package com.apeworks.weevil.transcoder;

/**
 * Created by seanmulvany on 11/06/2016.
 */
import java.io.InputStream;

import javax.servlet.ServletOutputStream;

import com.apeworks.weevil.domain.Run;
import com.apeworks.weevil.exception.EncodingException;

public interface RunTranscoder {
    public String encode(Run run);

    public void encode(Run run, ServletOutputStream outputStream) throws EncodingException;

    public Run decode(String runString) throws EncodingException;

    public Run decode(InputStream inputStream) throws EncodingException;
}
