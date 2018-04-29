package com.norbjd.mavenvssbt;

import org.hamcrest.core.StringStartsWith;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class JavaIntegrationTest {

    @Test
    public void integrationTestShouldBeExecutedFromJar() {
        MainClass mainClass = new MainClass();
        String pathToClass = mainClass.getClassName();
        assertThat(pathToClass, new StringStartsWith("jar"));
    }

}