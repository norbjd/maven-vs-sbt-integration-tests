package com.norbjd.mavenvssbt;

import org.hamcrest.core.StringStartsWith;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class JavaUnitTest {

    @Test
    public void unitTestShouldBeExecutedFromFile() {
        MainClass mainClass = new MainClass();
        String pathToClass = mainClass.getClassName();
        assertThat(pathToClass, new StringStartsWith("file"));
    }

}