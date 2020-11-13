
package com.github.microwww.security.cli;

import java.util.List;
import java.util.Set;

import com.github.microwww.security.cli.dto.App;
import com.github.microwww.security.cli.dto.Employee;
import com.github.microwww.security.cli.dto.RightURL;

/**
 * @author changshu.li
 */
public interface RurlService {

    Employee login(String account, String password) throws NoRightException;

    List<RightURL> listUrlRight(String account);

    List<RightURL> listAppURL();

    List<RightURL> listMenu(String account);

    List<RightURL> saveUrlRight(Set<String> set);

    App getApplication();

    boolean hasLoginRight(String appname, String account);

}
