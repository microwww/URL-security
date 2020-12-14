
package com.github.microwww.security.cli;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.microwww.security.cli.dto.App;
import com.github.microwww.security.cli.dto.Permission;
import com.github.microwww.security.cli.dto.Token;
import com.github.microwww.security.cli.help.Rconfig;
import com.github.microwww.security.cli.imp.AuthorityServiceImp;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author lcs
 */
public class RurlServiceWrapTest {

    AccountAuthorityService instance = new AuthorityServiceImp("https://www.baidu.com/api", "simple", "security");

    @Test
    public void testListUrlRight() {
        System.out.println("listUrlRight");
        String account = "1";
        List<Permission> result = instance.listPermissionByAccount(account);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testListAppURL() {
        System.out.println("listAppURL");
        List<Permission> result = instance.listPermissionByApp();
        assertFalse(result.isEmpty());
    }

    @Test
    public void testListMenu() {
        System.out.println("listMenu");
        String account = "1";
        List<Permission> result = instance.listMenu(account);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testLogin() throws Exception {
        System.out.println("login");
        String account = "1";
        String password = "1";
        Token result = instance.author(account, password);
        assertNotNull(result.getToken());
    }

    @Test
    public void testSaveUrlRight() {
        System.out.println("saveUrlRight");
        Set<String> set = new HashSet<String>();
        set.add("/app/");
        set.add("/groups/");
        set.add("/url/");
        List<Permission> result = instance.savePermissionUrl(set);
        assertEquals(result.size(), set.size());
    }

    @Test
    public void testGetApplication() {
        System.out.println("getApplication");
        App result = instance.getApplication();
        assertEquals(Rconfig.getAppId(), result.getAppId());
    }
}
