package com.domain.food.utils;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ImageUtilTest {

    @Test
    public void cutSquareImage() {
        String src = "C:\\food\\image\\462bc7023c9d426492a85688cbfaf712.jpeg";
        String dest = "C:\\food\\image\\sss\\dest.jpeg";
        ImageUtil.cutSquareImage(src, dest);
    }
}