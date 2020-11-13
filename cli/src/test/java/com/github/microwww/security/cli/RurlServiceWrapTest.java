/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.microwww.security.cli;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.microwww.security.cli.dto.App;
import com.github.microwww.security.cli.dto.Employee;
import com.github.microwww.security.cli.dto.RightURL;
import com.github.microwww.security.cli.help.Rconfig;
import com.github.microwww.security.cli.imp.RurlServiceImp;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lcs
 */
public class RurlServiceWrapTest {

    RurlService instance = new RurlServiceImp("simple", "https://www.baidu.com/api");

    @Test
    public void testListUrlRight() {
        System.out.println("listUrlRight");
        String account = "1";
        List<RightURL> result = instance.listUrlRight(account);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testListAppURL() {
        System.out.println("listAppURL");
        List<RightURL> result = instance.listAppURL();
        assertFalse(result.isEmpty());
    }

    @Test
    public void testListMenu() {
        System.out.println("listMenu");
        String account = "1";
        List<RightURL> result = instance.listMenu(account);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testLogin() throws Exception {
        System.out.println("login");
        String account = "1";
        String password = "1";
        Employee result = instance.login(account, password);
        assertEquals(account, result.getAccount());
    }

    @Test
    public void testSaveUrlRight() {
        System.out.println("saveUrlRight");
        Set<String> set = new HashSet<String>();
        set.add("/app/");
        set.add("/groups/");
        set.add("/url/");
        List<RightURL> result = instance.saveUrlRight(set);
        assertEquals(result.size(), set.size());
    }

    @Test
    public void testGetApplication() {
        System.out.println("getApplication");
        App result = instance.getApplication();
        assertEquals(Rconfig.getAppName(), result.getAppName());
    }
}
