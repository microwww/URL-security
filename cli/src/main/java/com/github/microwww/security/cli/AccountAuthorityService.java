
package com.github.microwww.security.cli;

import java.util.List;
import java.util.Set;

import com.github.microwww.security.cli.dto.App;
import com.github.microwww.security.cli.dto.Account;
import com.github.microwww.security.cli.dto.Authority;

/**
 * @author changshu.li
 */
public interface AccountAuthorityService {

    Account login(String account, String password) throws NoRightException;

    List<Authority> listAuthorityByAccount(String account);

    List<Authority> listAuthorityByApp();

    List<Authority> listMenu(String account);

    List<Authority> saveAuthorityUrl(Set<String> set);

    App getApplication();

    boolean hasLoginRight(String appName, String account);

}
