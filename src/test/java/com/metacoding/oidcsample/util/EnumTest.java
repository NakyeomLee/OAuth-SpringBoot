package com.metacoding.oidcsample.util;

import com.metacoding.oidcsample.user.ProviderEnum;
import org.junit.jupiter.api.Test;

public class EnumTest {

    @Test
    public void enum_math(){
        System.out.println(ProviderEnum.KAKAO);

        ProviderEnum enum1 = ProviderEnum.KAKAO; //
        System.out.println(enum1);
        System.out.println(enum1.getValue());
    }
}
