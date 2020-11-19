
package com.github.microwww.security.cli;

import java.util.List;
import java.util.Set;

import com.github.microwww.security.cli.dto.App;
import com.github.microwww.security.cli.dto.Account;
import com.github.microwww.security.cli.dto.Authority;

/**
 * @author changshu.li
 */
public interface RurlService {

    Account login(String account, String password) throws NoRightException;

    List<Authority> listUrlRight(String account);

    List<Authority> listAppURL();

    List<Authority> listMenu(String account);

    List<Authority> saveUrlRight(Set<String> set);

    App getApplication();

    boolean hasLoginRight(String appname, String account);

}
