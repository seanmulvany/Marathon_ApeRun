package com.apeworks.weevil.transcoder;

/**
 * Created by seanmulvany on 11/06/2016.
 */

import org.springframework.stereotype.Component;

import com.apeworks.weevil.domain.Run;

@Component(value = "runTranscoder")
public class JsonRunTranscoder  extends AbstractJsonTranscoder<Run>{

    public JsonRunTranscoder()
    {
        super(Run.class);
    }

}
