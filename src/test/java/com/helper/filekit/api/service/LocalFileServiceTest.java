package com.helper.filekit.api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LocalFileServiceTest {

    @DisplayName("테스트")
    @Test
    void test() {
        // given
        String filename = "test.png";

        // when
        String extension = filename.split("\\.")[1];

        // then
        assertThat(extension).isEqualTo("png");
    }

}
