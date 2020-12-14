
package com.github.microwww.security.cli;

import com.github.microwww.security.cli.dto.Account;
import com.github.microwww.security.cli.dto.App;
import com.github.microwww.security.cli.dto.Permission;
import com.github.microwww.security.cli.dto.Token;

import java.util.List;
import java.util.Set;

/**
 * @author changshu.li
 */
public interface AccountAuthorityService {

    Token author(String appId, String appSecurity) throws NoRightException;

    Account login(String account, String password) throws NoRightException;

    List<Permission> listPermissionByAccount(String account);

    List<Permission> listPermissionByApp();

    List<Permission> listMenu(String account);

    List<Permission> savePermissionUrl(Set<String> set);

    App getApplication();

    boolean hasLoginRight(String account);

}
