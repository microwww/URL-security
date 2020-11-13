package com.github.microwww.security.cli.help;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class AntPathTest extends TestCase {

    AntPath antPath = new AntPath();

    @Test
    public void testMatch() {
        boolean match = antPath.match("/sdm/**/si.html", "/sdm/a/b/si.html");
        Assert.assertTrue(match);
    }
}